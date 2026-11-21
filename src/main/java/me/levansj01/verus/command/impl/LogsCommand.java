package me.levansj01.verus.command.impl;

import java.util.ArrayList;
import java.util.List;
import me.levansj01.verus.alert.manager.AlertManager;
import me.levansj01.verus.command.BaseCommand;
import me.levansj01.verus.lang.EnumMessage;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.storage.database.Database;
import me.levansj01.verus.util.BukkitUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public class LogsCommand extends BaseCommand {
    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        Player player = sender instanceof Player ? (Player)sender : null;
        ArrayList list = new ArrayList();

        for (Player player2 : sender.getServer().getOnlinePlayers()) {
            String name = player2.getName();
            if ((player == null || player.canSee(player2)) && args.length == 1) {
                String str = args[args.length - 1];
                if (StringUtil.startsWithIgnoreCase(name, str)) {
                    list.add(name);
                }
            }
        }

        list.sort(String.CASE_INSENSITIVE_ORDER);
        return list;
    }

    public LogsCommand() {
        super(EnumMessage.LOGS_COMMAND.get());
        this.setDatabase(true);
        this.setUsageMessage(ChatColor.RED + "Usage: /" + this.getName() + " <name> <amount/all>");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!BukkitUtil.hasPermission(sender, "verus.staff") && !BukkitUtil.hasPermission(sender, "verus.staff.logs")) {
            sender.sendMessage(ChatColor.RED + this.getPermissionMessage());
        } else if (args.length == 2) {
            int value;
            try {
                value = Integer.parseInt(args[1]);
            } catch (Throwable throwable) {
                String str = args[1];
                if (str.equalsIgnoreCase("all")) {
                    this.getLogs(sender, args[0]);
                } else if (str.startsWith("verbose") && BukkitUtil.hasPermission(sender, "verus.admin")) {
                    this._getLogs(sender, args[0], true, str.equals("verbose1") ? 0 : 500);
                } else {
                    sender.sendMessage(this.getUsageMessage());
                }

                return;
            }

            this.getLogs(sender, args[0], value);
        } else if (args.length == 1) {
            this.getLogs(sender, args[0], 200);
        } else {
            sender.sendMessage(this.getUsageMessage());
        }
    }

    private void getLogs(CommandSender sender, String str) {
        this._getLogs(sender, str, false, 0);
    }

    private void getLogs(CommandSender uuid, String limit, int consumer) {
        this._getLogs(uuid, limit, false, consumer);
    }

    private void _getLogs(CommandSender sender, String str, boolean flag, int value) {
        Database database = StorageEngine.getInstance().getDatabase();
        database.getUUID(str, entry -> {
            if (entry == null) {
                sender.sendMessage(ChatColor.RED + EnumMessage.COMMAND_PLAYER_NEVER_LOGGED_ON.get());
            } else {
                if (value != 0) {
                    database.getLogs(entry, value, entry2 -> {
                        if (!entry2.iterator().hasNext()) {
                            sender.sendMessage(ChatColor.RED + EnumMessage.PLAYER_NO_LOGS.get());
                        } else {
                            AlertManager.getInstance().uploadLogs(sender, entry, entry2, flag);
                        }
                    });
                } else {
                    database.getLogs(entry, entry2 -> {
                        if (!entry2.iterator().hasNext()) {
                            sender.sendMessage(ChatColor.RED + EnumMessage.PLAYER_NO_LOGS.get());
                        } else {
                            AlertManager.getInstance().uploadLogs(sender, entry, entry2, flag);
                        }
                    });
                }
            }
        });
    }
}
