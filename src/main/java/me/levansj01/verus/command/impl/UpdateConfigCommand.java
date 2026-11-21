package me.levansj01.verus.command.impl;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.command.BaseCommand;
import me.levansj01.verus.lang.EnumMessage;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.storage.config.VerusConfiguration;
import me.levansj01.verus.util.BukkitUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.util.StringUtil;

public class UpdateConfigCommand extends BaseCommand {
    private final Set<String> keys;
    private final YamlConfiguration configuration;
    private final VerusConfiguration verusConfig;

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        if (args.length < 1 || !BukkitUtil.hasPermission(sender, this.getPermission()) && !BukkitUtil.hasPermission(sender, "verus.admin")) {
            return super.tabComplete(sender, alias, args);
        }

        ArrayList arrayList = Lists.newArrayList();
        StringUtil.copyPartialMatches(
            args[0], this.keys.stream().filter(key -> this.instanceOf(this.configuration.get(key))).collect(Collectors.toList()), arrayList
        );
        Collections.sort(arrayList);
        return arrayList;
    }

    public UpdateConfigCommand() {
        super(EnumMessage.UPDATE_CONFIG_COMMAND.get());
        this.setPermission("verus.staff.updateconfig");
        this.setUsageMessage(ChatColor.RED + "Usage: /updateconfig <location> <value>");
        this.verusConfig = StorageEngine.getInstance().getVerusConfig();
        this.configuration = this.verusConfig.getConfiguration();
        this.keys = this.configuration.getKeys(true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length >= 1 && args.length <= 2) {
            if (BukkitUtil.hasPermission(sender, this.getPermission()) || BukkitUtil.hasPermission(sender, "verus.admin")) {
                String serializable = args[0];
                if (!this.keys.contains(serializable)) {
                    sender.sendMessage(ChatColor.RED + "Invalid config option!");
                    return;
                }

                Object obj = this.configuration.get(serializable);
                if (args.length == 1) {
                    if (this.instanceOf(obj)) {
                        sender.sendMessage(
                            String.format(
                                VerusPlugin.COLOR + "Config option for %s: %s",
                                ChatColor.WHITE + serializable + VerusPlugin.COLOR,
                                ChatColor.WHITE + String.valueOf(obj)
                            )
                        );
                    } else {
                        sender.sendMessage(ChatColor.RED + "Cannot view this config option!");
                    }

                    return;
                }

                String serializable2 = args[1];
                boolean flag;
                if (obj instanceof String) {
                    if (!(flag = Objects.equals(obj, serializable2))) {
                        this.configuration.set(serializable, serializable2);
                        this.verusConfig.saveConfig();
                    }
                } else if (obj instanceof Boolean) {
                    boolean flag2 = Boolean.parseBoolean(serializable2);
                    if (!(flag = Objects.equals(obj, flag2))) {
                        this.configuration.set(serializable, flag2);
                        this.verusConfig.saveConfig();
                    }
                } else if (obj instanceof Double) {
                    double value = Double.parseDouble(serializable2);
                    if (!(flag = Objects.equals(obj, value))) {
                        this.configuration.set(serializable, value);
                        this.verusConfig.saveConfig();
                    }
                } else {
                    if (!(obj instanceof Integer)) {
                        sender.sendMessage(ChatColor.RED + "Cannot update this config option!");
                        return;
                    }

                    int value = Integer.parseInt(serializable2);
                    if (!(flag = Objects.equals(obj, value))) {
                        this.configuration.set(serializable, value);
                        this.verusConfig.saveConfig();
                    }
                }

                if (flag) {
                    sender.sendMessage(ChatColor.RED + "Option is already set to that value!");
                    return;
                }

                sender.sendMessage(
                    String.format(
                        VerusPlugin.COLOR + "Updated Config option for %s from %s to %s",
                        ChatColor.WHITE + serializable + VerusPlugin.COLOR,
                        ChatColor.WHITE + String.valueOf(obj) + VerusPlugin.COLOR,
                        ChatColor.WHITE + serializable2 + VerusPlugin.COLOR
                    )
                );
                sender.sendMessage(ChatColor.RED + ChatColor.ITALIC.toString() + "Some options will require a restart to take affect!");
            }
        } else {
            sender.sendMessage(this.getUsageMessage());
        }
    }

    private boolean instanceOf(Object obj) {
        return obj instanceof String || obj instanceof Double || obj instanceof Integer || obj instanceof Boolean;
    }
}
