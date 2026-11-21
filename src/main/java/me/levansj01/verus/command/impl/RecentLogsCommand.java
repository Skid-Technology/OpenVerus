package me.levansj01.verus.command.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.command.BaseCommand;
import me.levansj01.verus.lang.EnumMessage;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.storage.database.Log;
import me.levansj01.verus.util.BukkitUtil;
import me.levansj01.verus.util.java.StringUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RecentLogsCommand extends BaseCommand {
    private static final DateFormat FORMAT = new SimpleDateFormat("dd/MM HH:mm");

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 1) {
            if (!BukkitUtil.hasPermission(sender, "verus.staff") && !BukkitUtil.hasPermission(sender, "verus.staff.logs.recent")) {
                sender.sendMessage(ChatColor.RED + this.getPermissionMessage());
            } else {
                StorageEngine.getInstance().getDatabase().getUUID(args[0], entry -> {
                    if (entry == null) {
                        sender.sendMessage(ChatColor.RED + EnumMessage.COMMAND_PLAYER_NEVER_LOGGED_ON.get());
                    } else {
                        StorageEngine.getInstance().getDatabase().getLogs(entry, 10, entry2 -> {
                            if (!entry2.iterator().hasNext()) {
                                sender.sendMessage(ChatColor.RED + "This player has no logs");
                            } else {
                                String str = ChatColor.GRAY + "(Gathering logs for " + args[0] + "/" + entry + "...)\n";
                                long now = System.currentTimeMillis();

                                for (Log log : entry2) {
                                    str = str + this.getFormattedRecentLogs(log, now) + "\n";
                                }

                                sender.sendMessage(str);
                            }
                        });
                    }
                });
            }
        } else {
            sender.sendMessage(this.getUsageMessage());
        }
    }

    public String getFormattedRecentLogs(Log log, long value) {
        return ChatColor.translateAlternateColorCodes(
            '&',
            StorageEngine.getInstance()
                .getVerusConfig()
                .getRecentlogsMessage()
                .replace("{date}", ChatColor.GRAY + "[" + ChatColor.WHITE + FORMAT.format(value) + ChatColor.GRAY + "]")
                .replace("{time}", ChatColor.GRAY + "(" + ChatColor.WHITE + StringUtil.differenceAsTime(value - log.getTimestamp()) + ChatColor.GRAY + ")")
                .replace("{name}", VerusPlugin.COLOR + log.getName())
                .replace("{type}", log.getType())
                .replace("{subType}", log.getSubType())
                .replace("{vl}", String.valueOf(log.getViolations()))
                .replace("{ping}", String.valueOf(log.getPing()))
                .replace("{lag}", String.valueOf(log.getLag()))
        );
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        Player player = sender instanceof Player ? (Player)sender : null;
        ArrayList list = new ArrayList();

        for (Player player2 : sender.getServer().getOnlinePlayers()) {
            String name = player2.getName();
            if ((player == null || player.canSee(player2)) && args.length == 1) {
                String str = args[args.length - 1];
                if (org.bukkit.util.StringUtil.startsWithIgnoreCase(name, str)) {
                    list.add(name);
                }
            }
        }

        list.sort(String.CASE_INSENSITIVE_ORDER);
        return list;
    }

    public RecentLogsCommand() {
        super(EnumMessage.RECENT_LOGS_COMMAND.get());
        this.setDatabase(true);
        this.setUsageMessage(ChatColor.RED + "Usage: /" + this.getName() + " <name>");
    }
}
