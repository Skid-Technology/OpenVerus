package me.levansj01.verus.messaging;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import me.levansj01.launcher.VerusLauncher;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.messaging.listener.BrandListener;
import me.levansj01.verus.storage.StorageEngine;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.Messenger;

public class MessagingHandler {
    private static final String verusChannel = NMSManager.getInstance().getServerVersion().afterEq(ServerVersion.v1_13_R2) ? "bungeecord:verus" : "VerusBungee";
    private static MessagingHandler instance;
    private ThreadLocal<Cipher> cipher;
    private BrandListener brandListener;
    private VerusPlugin plugin;

    public BrandListener getBrandListener() {
        return this.brandListener;
    }

    public void enable(VerusPlugin plugin) {
        this.plugin = plugin;
        this.registerChannels();
        String secretKeyPath = StorageEngine.getInstance().getVerusConfig().getSecretKeyPath();
        Path path = Paths.get(secretKeyPath);

        byte[] bytes;
        try {
            if (Files.notExists(path)) {
                bytes = new byte[16];
                SecureRandom.getInstanceStrong().nextBytes(bytes);
                Files.write(path, bytes);
                plugin.getPlugin().getLogger().log(Level.INFO, "Secret key " + secretKeyPath + " not found, generating...");
            } else {
                bytes = Files.readAllBytes(path);
            }

            plugin.getPlugin().getLogger().log(Level.WARNING, "Please configure VerusLink so it has access to this secret key (" + secretKeyPath + ").");
        } catch (IOException | NoSuchAlgorithmException exception) {
            plugin.getPlugin().getLogger().log(Level.SEVERE, "Failed to find or generate secret key for bungeecord messaging. ", exception);
            return;
        }

        this.cipher = ThreadLocal.withInitial(() -> {
            Cipher cipher = null;

            try {
                cipher = Cipher.getInstance("AES");
                cipher.init(1, new SecretKeySpec(bytes, "AES"));
            } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException exception) {
                plugin.getPlugin().getLogger().log(Level.SEVERE, "Failed to initialize Cipher for bungeecord messaging. ", exception);
            }

            return cipher;
        });
    }

    public static MessagingHandler getInstance() {
        return instance == null ? (instance = new MessagingHandler()) : instance;
    }

    private void registerChannels() {
        Messenger messenger = this.plugin.getServer().getMessenger();
        VerusLauncher launcher = this.plugin.getPlugin();
        String messagingChannel = StorageEngine.getInstance().getVerusConfig().getMessagingChannel();
        if (!messagingChannel.equals("BungeeCord")) {
            try {
                messenger.registerOutgoingPluginChannel(launcher, "BungeeCord");
            } catch (Throwable ignored) {
            }
        }

        try {
            messenger.registerOutgoingPluginChannel(launcher, verusChannel);
        } catch (Throwable ignored) {
        }

        try {
            messenger.registerOutgoingPluginChannel(launcher, messagingChannel);
        } catch (Throwable throwable) {
            this.plugin.getPlugin().getLogger().log(Level.SEVERE, "Failed to register bungeecord messaging channel " + messagingChannel + ", ", throwable);
        }

        this.registerBrandListener(messenger, launcher);
    }

    private void registerBrandListener(Messenger messenger, VerusLauncher launcher) {
        try {
            messenger.registerIncomingPluginChannel(
                launcher,
                NMSManager.getInstance().getServerVersion().afterEq(ServerVersion.v1_13_R2) ? "minecraft:brand" : "MC|Brand",
                this.brandListener = new BrandListener()
            );
        } catch (Throwable throwable) {
            this.plugin.getPlugin().getLogger().log(Level.SEVERE, "Failed to register Brand listener", throwable);
        }
    }

    public void disable() {
        if (this.plugin != null) {
            this.unregisterChannels();
            if (this.brandListener != null) {
                this.brandListener = null;
            }
        }
    }

    public void handleBan(Player player, String str) {
        ByteArrayDataOutput dataOutput = ByteStreams.newDataOutput();
        dataOutput.writeUTF("HandleBan");
        dataOutput.writeUTF(str);
        byte[] bytes = dataOutput.toByteArray();
        player.sendPluginMessage(this.plugin.getPlugin(), "BungeeCord", bytes);

        byte[] bytes2;
        try {
            bytes2 = this.cipher.get().doFinal(bytes);
        } catch (IllegalBlockSizeException | BadPaddingException exception) {
            this.plugin.getPlugin().getLogger().log(Level.SEVERE, "Failed to encrypt data for bungeecord messaging. ", exception);
            return;
        }

        ByteArrayDataOutput dataOutput2 = ByteStreams.newDataOutput();
        dataOutput2.writeUTF("verus");
        dataOutput2.writeInt(bytes2.length);
        dataOutput2.write(bytes2);
        player.sendPluginMessage(this.plugin.getPlugin(), StorageEngine.getInstance().getVerusConfig().getMessagingChannel(), dataOutput2.toByteArray());
    }

    private void unregisterChannels() {
        this.plugin.getServer().getMessenger().unregisterOutgoingPluginChannel(this.plugin.getPlugin());
        this.plugin.getServer().getMessenger().unregisterIncomingPluginChannel(this.plugin.getPlugin());
    }
}
