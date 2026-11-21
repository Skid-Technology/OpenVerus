package me.levansj01.verus.command.impl;

import java.util.ArrayList;
import java.util.List;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.alert.manager.AlertManager;
import me.levansj01.verus.check.checks.manual.ManualA;
import me.levansj01.verus.command.BaseCommand;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.data.manager.DataManager;
import me.levansj01.verus.lang.EnumMessage;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.storage.database.Database;
import me.levansj01.verus.storage.database.Log;
import me.levansj01.verus.util.BukkitUtil;
import me.levansj01.verus.util.java.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ManualBanCommand extends BaseCommand {
    public ManualBanCommand() {
        super(EnumMessage.MANUAL_BAN_COMMAND.get());
        this.setDatabase(true);
        this.setPermission("verus.admin");
        this.setUsageMessage(ChatColor.RED + "Usage: /" + this.getName() + " <name> OR /" + this.getName() + " check <name>");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 1) {
            String str = sender instanceof Player ? ((Player)sender).getName() : "Console";
            Player player = Bukkit.getPlayer(args[0]);
            if (player != null) {
                PlayerData player2 = DataManager.getInstance().getPlayer(player);
                ManualA manualBanCheck = player2.getCheckData().getManualBanCheck();
                if (manualBanCheck != null) {
                    manualBanCheck.setViolations(1.0);
                    manualBanCheck.handleViolation(str);
                    AlertManager alertManager = AlertManager.getInstance();
                    alertManager.run(() -> {
                        if (alertManager.insertBan(player2, manualBanCheck)) {
                            player2.setEnabled(false);
                        }
                    });
                }
            } else {
                sender.sendMessage(ChatColor.RED + EnumMessage.COMMAND_PLAYER_NOT_FOUND.get());
            }
        } else if (args.length == 2 && args[0].equalsIgnoreCase("check")) {
            String str = args[1];
            Database database = StorageEngine.getInstance().getDatabase();
            database.getUUID(
                str,
                entry -> {
                    if (entry == null) {
                        sender.sendMessage(ChatColor.RED + EnumMessage.COMMAND_PLAYER_NEVER_LOGGED_ON.get());
                    } else {
                        database.getManualLogs(
                            entry,
                            10,
                            entry2 -> {
                                if (!entry2.iterator().hasNext()) {
                                    sender.sendMessage(ChatColor.RED + EnumMessage.PLAYER_NO_LOGS.get());
                                } else {
                                    String serializable = ChatColor.GRAY + "(Gathering logs for " + args[0] + "/" + entry + "...)\n";
                                    long now = System.currentTimeMillis();

                                    for (Log log : entry2) {
                                        String data = log.getData();
                                        if (data != null) {
                                            serializable = serializable
                                                + ChatColor.GRAY
                                                + "("
                                                + ChatColor.WHITE
                                                + StringUtil.differenceAsTime(now - log.getTimestamp())
                                                + ChatColor.GRAY
                                                + ") "
                                                + VerusPlugin.COLOR
                                                + log.getName()
                                                + ChatColor.GRAY
                                                + " was manual banned by "
                                                + ChatColor.WHITE
                                                + log.getData()
                                                + "\n";
                                        }
                                    }

                                    sender.sendMessage(serializable);
                                }
                            }
                        );
                    }
                }
            );
        } else {
            sender.sendMessage(this.getUsageMessage());
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        if (BukkitUtil.hasPermission(sender, this.getPermission()) && (args.length == 1 || args.length == 2 && args[0].equalsIgnoreCase("check"))) {
            Player player = sender instanceof Player ? (Player)sender : null;
            ArrayList list = new ArrayList();

            for (Player player2 : sender.getServer().getOnlinePlayers()) {
                String name = player2.getName();
                if (player == null || player.canSee(player2)) {
                    String str = args[args.length - 1];
                    if (org.bukkit.util.StringUtil.startsWithIgnoreCase(name, str)) {
                        list.add(name);
                    }
                }
            }

            list.sort(String.CASE_INSENSITIVE_ORDER);
            return list;
        } else {
            return super.tabComplete(sender, alias, args);
        }
    }
}
