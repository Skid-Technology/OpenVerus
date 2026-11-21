package me.levansj01.verus;

import java.util.List;
import java.util.Map;
import me.levansj01.launcher.VerusLaunch;
import me.levansj01.launcher.VerusLauncher;
import me.levansj01.verus.api.API;
import me.levansj01.verus.check.manager.CheckManager;
import me.levansj01.verus.command.BaseCommandHelp;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.data.manager.DataManager;
import me.levansj01.verus.discord.DiscordManager;
import me.levansj01.verus.gui.manager.GUIManager;
import me.levansj01.verus.lang.EnumMessage;
import me.levansj01.verus.listener.DataListener;
import me.levansj01.verus.messaging.MessagingHandler;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.storage.config.VerusConfiguration;
import me.levansj01.verus.task.ServerTickTask;
import me.levansj01.verus.type.Loader;
import me.levansj01.verus.type.VerusTypeLoader;
import me.levansj01.verus.util.java.SafeReflection;
import me.levansj01.verus.util.java.WordUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.event.HandlerList;
import org.bukkit.help.HelpMap;
import org.bukkit.permissions.ServerOperator;

public class VerusPlugin implements VerusLaunch {
    public static ChatColor COLOR = ChatColor.BLUE;
    private static String name = "verus";
    private DataListener dataListener;
    private SimpleCommandMap commandMap;
    private VerusLauncher plugin;
    private boolean serverShutdown;
    private final Server server = Bukkit.getServer();
    private HelpMap helpMap;
    private List<Command> commands;
    private VerusTypeLoader typeLoader;

    public static void restart() {
        VerusLauncher launcher = VerusLauncher.getPlugin();
        Bukkit.getPluginManager().disablePlugin(launcher);
        Bukkit.getPluginManager().enablePlugin(launcher);
    }

    public Server getServer() {
        return this.server;
    }

    public DataListener getDataListener() {
        return this.dataListener;
    }

    public List<Command> getCommands() {
        return this.commands;
    }

    public static long getBuild() {
        return 3952L;
    }

    public HelpMap getHelpMap() {
        return this.helpMap;
    }

    public static void setName(String name2) {
        name = name2;
    }

    public void setServerShutdown(boolean serverShutdown) {
        this.serverShutdown = serverShutdown;
    }

    private void registerListeners() {
        this.getServer().getPluginManager().registerEvents(this.dataListener = new DataListener(), this.plugin);
    }

    private void registerCommands() {
        this.commands = this.typeLoader.getCommands();
        this.commands.addAll(this.typeLoader.getBaseCommands());
        if (!StorageEngine.getInstance().getVerusConfig().isPingCommand()) {
            this.commands.removeIf(command -> command.getName().equals(Loader.getPingCommand()));
        }

        this.commands.forEach(command -> command.setPermissionMessage(EnumMessage.COMMAND_PERMISSION.get()));
        this.commandMap = SafeReflection.getCommandMap();
        this.helpMap = this.server.getHelpMap();
        Map knownCommands = SafeReflection.getKnownCommands(this.commandMap);
        this.commandMap.registerAll(name, this.commands);
        this.commands.forEach(command -> {
            knownCommands.put(command.getName(), command);
            this.helpMap.addTopic(new BaseCommandHelp(command));
        });
        NMSManager.getInstance().syncCommands(this.commands);
    }

    public SimpleCommandMap getCommandMap() {
        return this.commandMap;
    }

    public static String getNameFormatted() {
        return WordUtils.capitalize(name);
    }

    public static String getName() {
        return name;
    }

    public boolean isServerShutdown() {
        return this.serverShutdown;
    }

    @Override
    public void launch(VerusLauncher plugin) {
        long now = System.currentTimeMillis();
        this.plugin = plugin;
        this.typeLoader = new VerusTypeLoader();
        StorageEngine storageEngine = StorageEngine.getInstance();
        storageEngine.startConfig();
        VerusTypeLoader.loader();

        try {
            NMSManager.getInstance();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            Bukkit.shutdown();
            return;
        }

        storageEngine.start();
        CheckManager.getInstance().enable(this);
        DataManager.enable(this);
        VerusConfiguration verusConfig = storageEngine.getVerusConfig();
        if (verusConfig.isBungeeBans()) {
            MessagingHandler.getInstance().enable(this);
        }

        if (verusConfig.isDiscordBans() || verusConfig.isDiscordLogs()) {
            DiscordManager.getInstance().start();
        }

        this.registerListeners();
        this.registerCommands();
        ServerTickTask.getInstance().schedule();

        API api = API.getAPI();
        if (api != null) {
            api.enable(this);
        }

        long elapsed = System.currentTimeMillis() - now;
        String message = String.format("%s b%s launched successfully in %sms", getNameFormatted(), getBuild(), elapsed);
        Bukkit.getOnlinePlayers().stream().filter(ServerOperator::isOp).forEach(onlinePlayer -> onlinePlayer.sendMessage(ChatColor.GREEN + message));
        this.plugin.getLogger().info(message);
    }

    public static int getType() {
        return 2;
    }

    public VerusLauncher getPlugin() {
        return this.plugin;
    }

    public VerusTypeLoader getTypeLoader() {
        return this.typeLoader;
    }

    private void unregisterCommands() {
        if (this.commandMap != null) {
            Map knownCommands = SafeReflection.getKnownCommands(this.commandMap);
            knownCommands.values().removeAll(this.commands);

            for (Command command : this.commands) {
                command.unregister(this.commandMap);
            }
        }

        if (this.helpMap != null) {
            this.helpMap.getHelpTopics().removeIf(helpTopic -> helpTopic instanceof BaseCommandHelp);
        }
    }

    private void unregisterListeners() {
        if (this.dataListener != null) {
            HandlerList.unregisterAll(this.dataListener);
        }

        HandlerList.unregisterAll(VerusLauncher.getPlugin());
    }

    @Override
    public void shutdown() {
        ServerTickTask.getInstance().cancel();
        DataManager.disable();
        this.unregisterListeners();
        DiscordManager.getInstance().stop();
        StorageEngine.getInstance().stopConfig();
        CheckManager.getInstance().disable();
        GUIManager.getInstance().disable();
        StorageEngine.getInstance().stop();
        VerusConfiguration verusConfig = StorageEngine.getInstance().getVerusConfig();
        if (verusConfig != null && verusConfig.isBungeeBans()) {
            MessagingHandler.getInstance().disable();
        }

        API.check();
        API api = API.getAPI();
        if (api != null) {
            api.disable();
        }

        if (ServerTickTask.getInstance().getBukkitTask() != null) {
            ServerTickTask.getInstance().getBukkitTask().cancel();
        }

        this.unregisterCommands();
        this.plugin.getLogger().info(getNameFormatted() + " shutdown successfully");
        this.typeLoader.setCheckClasses(null);
    }
}
