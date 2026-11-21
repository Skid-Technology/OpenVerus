package me.levansj01.verus.type.custom;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.levansj01.launcher.VerusLauncher;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.lang.EnumMessage;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.storage.config.VerusConfiguration;
import me.levansj01.verus.util.java.SafeReflection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.SimplePluginManager;

public class Loader extends me.levansj01.verus.type.Loader {
    @Override
    public void load() {
        try {
            String defaultName = VerusPlugin.getName();
            String defaultColor = String.valueOf(ChatColor.valueOf(VerusPlugin.COLOR.name()).getChar());
            VerusConfiguration verusConfiguration = StorageEngine.getInstance().getVerusConfig();
            YamlConfiguration configuration = verusConfiguration.getConfiguration();
            configuration.addDefault("verus.custom.pluginName", defaultName);
            configuration.addDefault("verus.custom.colorName", defaultColor);
            configuration.addDefault("verus.custom.commands.logs", "logs");
            configuration.addDefault("verus.custom.commands.recentlogs", "recentlogs");
            configuration.addDefault("verus.custom.commands.manualban", "manualban");
            configuration.addDefault("verus.custom.commands.ping", "ping");
            configuration.addDefault("verus.custom.commands.alerts", "alerts");
            verusConfiguration.saveConfig(configuration, verusConfiguration.getFile());
            VerusPlugin.setName(configuration.getString("verus.custom.pluginName", defaultName));
            VerusPlugin.COLOR = net.md_5.bungee.api.ChatColor.getByChar(configuration.getString("verus.custom.colorName", defaultColor).charAt(0));
            logsCommand = configuration.getString("verus.custom.commands.logs");
            recentlogsCommand = configuration.getString("verus.custom.commands.recentlogs");
            manualbanCommand = configuration.getString("verus.custom.commands.manualban");
            pingCommand = configuration.getString("verus.custom.commands.ping");
            alertsCommand = configuration.getString("verus.custom.commands.alerts");
            VerusLauncher launcher = VerusLauncher.getPlugin();
            Logger logger = launcher.getLogger();
            if (!Objects.equals(VerusPlugin.getName(), defaultName)) {
                PluginDescriptionFile pluginDescriptionFile = launcher.getDescription();
                String nameFormatted = VerusPlugin.getNameFormatted();
                SafeReflection.setLocalField(PluginDescriptionFile.class, pluginDescriptionFile, "name", nameFormatted);
                SafeReflection.setLocalField(PluginDescriptionFile.class, pluginDescriptionFile, "description", null);
                SafeReflection.setLocalField(PluginDescriptionFile.class, pluginDescriptionFile, "authors", new ArrayList());
                Map<String, Plugin> lookupNames = SafeReflection.getLocalField(
                    SimplePluginManager.class, Bukkit.getPluginManager(), new String[]{"lookupNames"}
                );
                lookupNames.put(nameFormatted, launcher);
                SafeReflection.setLocalField(Logger.class, logger, "name", nameFormatted);
                if (logger instanceof PluginLogger) {
                    SafeReflection.setLocalField(PluginLogger.class, logger, "pluginName", "[" + nameFormatted + "] ");
                }
            }

            YamlConfiguration lang = new YamlConfiguration();
            lang.options().header("Configure messages for Verus").copyDefaults(true).copyHeader(true);
            File file = new File(launcher.getDataFolder(), "lang.yml");
            if (file.exists()) {
                try {
                    lang.load(file);
                } catch (InvalidConfigurationException | IOException exception) {
                    logger.log(Level.SEVERE, "Failed to load " + file.getName() + ": ", exception);
                }
            }

            for (EnumMessage message : EnumMessage.values()) {
                lang.addDefault(message.getLocation(), message.getFallback().replace('§', '&'));
                message.setValue(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', lang.getString(message.getLocation(), message.getFallback())));
            }

            try {
                lang.save(file);
            } catch (IOException exception) {
                logger.log(Level.SEVERE, "Failed to save " + file.getName() + ": ", exception);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
