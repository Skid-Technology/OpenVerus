package me.levansj01.verus.command.impl;

import java.util.ArrayList;
import java.util.List;
import me.levansj01.verus.command.BaseCommand;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.data.manager.DataManager;
import me.levansj01.verus.lang.EnumMessage;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.util.BukkitUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public class PingCommand extends BaseCommand {
    public PingCommand() {
        super(EnumMessage.PING_COMMAND.get());
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!BukkitUtil.hasPermission(sender, "verus.ping")) {
            sender.sendMessage(ChatColor.RED + this.getPermissionMessage());
        } else {
            if (sender instanceof Player) {
                Player player = (Player)sender;
                if (args.length == 1) {
                    if (!BukkitUtil.hasPermission(sender, "verus.ping.other")) {
                        sender.sendMessage(ChatColor.RED + this.getPermissionMessage());
                        return;
                    }

                    Player player2 = Bukkit.getPlayer(args[0]);
                    if (player2 != null) {
                        boolean flag = true;
                        if (StorageEngine.getInstance().getVerusConfig().isVanishPing()) {
                            flag = player.canSee(player2);
                        }

                        PlayerData player3 = DataManager.getInstance().getPlayer(player2);
                        if (player3 != null && flag) {
                            String message = EnumMessage.PING_OTHER
                                .get()
                                .replace("{player}", player3.getName())
                                .replace("{ping}", player3.getAveragePing() + "ms (" + player3.getAverageTransactionPing() + "ms)");
                            player.sendMessage(message);
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + EnumMessage.COMMAND_PLAYER_NOT_FOUND.get());
                    }
                } else {
                    PlayerData player2 = DataManager.getInstance().getPlayer(player);
                    if (player2 != null) {
                        String message = EnumMessage.PING_SELF.get().replace("{ping}", player2.getAveragePing() + "ms (" + player2.getAverageTransactionPing() + "ms)");
                        player.sendMessage(message);
                    }
                }
            }
        }
    }

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
}
