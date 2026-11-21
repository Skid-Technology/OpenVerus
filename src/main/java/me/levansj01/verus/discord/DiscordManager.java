package me.levansj01.verus.discord;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.data.client.ClientData;
import me.levansj01.verus.lang.EnumMessage;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.storage.config.VerusConfiguration;
import org.bukkit.ChatColor;

public class DiscordManager implements Runnable {
    private static final ScheduledExecutorService DISCORD_SERVICE = Executors.newSingleThreadScheduledExecutor();
    private static final WebhookEmbedBuilder BUILDER = new WebhookEmbedBuilder();
    private static DiscordManager instance;
    private static final String AVATAR_URL = "https://minotar.net/helm/%s/100.png";
    private final Queue<WebhookEmbed> bansQueue;
    private WebhookClient bansClient;
    private final Queue<WebhookEmbed> logsQueue = new LinkedList<>();
    private WebhookClient logsClient;

    public static DiscordManager getInstance() {
        return instance == null ? (instance = new DiscordManager()) : instance;
    }

    public void start() {
        VerusConfiguration verusConfig = StorageEngine.getInstance().getVerusConfig();
        String discordLogsUrl = verusConfig.getDiscordLogsUrl();
        if (verusConfig.isDiscordLogs() && !discordLogsUrl.isEmpty()) {
            this.logsClient = WebhookClient.withUrl(discordLogsUrl);
        }

        String discordBansUrl = verusConfig.getDiscordBansUrl();
        if (verusConfig.isDiscordBans() && !discordBansUrl.isEmpty()) {
            this.bansClient = WebhookClient.withUrl(discordBansUrl);
        }

        if (this.logsClient != null || this.bansClient != null) {
            DISCORD_SERVICE.scheduleAtFixedRate(this, 0L, 5L, TimeUnit.SECONDS);
        }
    }

    public DiscordManager() {
        this.bansQueue = new LinkedList<>();
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            WebhookEmbed embed;
            if (!this.bansQueue.isEmpty()) {
                while ((embed = this.bansQueue.poll()) != null) {
                    this.bansClient.send(embed, new WebhookEmbed[0]);
                }
            }

            if (!this.logsQueue.isEmpty()) {
                while ((embed = this.logsQueue.poll()) != null) {
                    this.logsClient.send(embed, new WebhookEmbed[0]);
                }
            }
        }
    }

    public void sendLog(PlayerData playerData, String str) {
        if (this.logsClient != null) {
            ClientData clientData = playerData.getClientData();
            String message = EnumMessage.DISCORD_LOG
                .get()
                .replace("{log}", ChatColor.stripColor(str))
                .replace("{client}", clientData.getType().getDisplay())
                .replace("{brand}", clientData.getBrand())
                .replace("{version}", playerData.getVersion().getName());
            this.logsQueue
                .add(
                    BUILDER.setColor(3553598)
                        .setThumbnailUrl(String.format("https://minotar.net/helm/%s/100.png", playerData.getName()))
                        .setDescription(message)
                        .build()
                );
        }
    }

    public void stop() {
        if (this.logsClient != null) {
            this.logsClient.close();
        }

        if (this.bansClient != null) {
            this.bansClient.close();
        }

        this.logsQueue.clear();
        this.bansQueue.clear();
    }

    public void sendBan(PlayerData playerData, Check check) {
        if (this.bansClient != null) {
            ClientData clientData = playerData.getClientData();
            String message = EnumMessage.DISCORD_BAN
                .get()
                .replace("{player}", playerData.getName())
                .replace("{subType}", check.getSubType())
                .replace("{client}", clientData.getType().getDisplay())
                .replace("{brand}", clientData.getBrand())
                .replace("{version}", playerData.getVersion().getName())
                .replace("{checkType}", check.getType().getName());
            this.bansQueue
                .add(
                    BUILDER.setColor(3553598)
                        .setThumbnailUrl(String.format("https://minotar.net/helm/%s/100.png", playerData.getName()))
                        .setDescription(message)
                        .build()
                );
        }
    }
}
