package me.levansj01.verus.command.impl;

import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.command.BaseCommand;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.data.manager.DataManager;
import me.levansj01.verus.lang.EnumMessage;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.util.BukkitUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AlertsCommand extends BaseCommand {
    public AlertsCommand() {
        super(EnumMessage.ALERTS_COMMAND.get());
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!BukkitUtil.hasPermission(sender, "verus.staff")
            && !BukkitUtil.hasPermission(sender, "verus.staff.alerts")
            && !BukkitUtil.hasPermission(sender, "verus.alerts")) {
            sender.sendMessage(ChatColor.RED + this.getPermissionMessage());
        } else if (sender instanceof Player) {
            Player player = (Player)sender;
            PlayerData player2 = DataManager.getInstance().getPlayer(player);
            if (player2 != null) {
                if (BukkitUtil.isDev(player) && args.length == 1 && args[0].toLowerCase().equalsIgnoreCase("debug")) {
                    player2.setDebug(!player2.isDebug());
                    this.update(player2);
                    BukkitUtil.setMeta(player, "verus.admin", player2.isDebug());
                    sender.sendMessage(VerusPlugin.COLOR + "You are " + (player2.isDebug() ? "now" : "no longer") + " in debug mode");
                    return;
                }

                player2.setAlerts(!player2.isAlerts());
                this.update(player2);
                BukkitUtil.setMeta(player, "verus.alerts", player2.isAlerts());
                EnumMessage message = player2.isAlerts() ? EnumMessage.ALERTS_ENABLED_COMMAND : EnumMessage.ALERTS_DISABLED_COMMAND;
                sender.sendMessage(VerusPlugin.COLOR + message.get());
            } else {
                player.sendMessage(ChatColor.RED + "Your data is not currently loaded.");
            }
        }
    }

    private void update(PlayerData playerData) {
        StorageEngine storageEngine = StorageEngine.getInstance();
        if (storageEngine.isConnected()) {
            storageEngine.getDatabase().updateAlerts(playerData);
        }
    }
}
