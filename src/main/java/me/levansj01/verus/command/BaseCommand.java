package me.levansj01.verus.command;

import java.util.List;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.util.BukkitUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class BaseCommand extends Command {
    private String usageMessage;
    private boolean database;

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (this.getPermission() != null && !BukkitUtil.hasPermission(sender, this.getPermission())) {
            sender.sendMessage(ChatColor.RED + this.getPermissionMessage());
            return false;
        } else if (this.database && !StorageEngine.getInstance().isConnected()) {
            sender.sendMessage(ChatColor.RED + "Please connect to a database to use this command.");
            return false;
        } else {
            this.execute(sender, args);
            return false;
        }
    }

    public BaseCommand(String str, String str2, String str3, List<String> list) {
        super(str, str2, str3, list);
    }

    public String getUsageMessage() {
        return this.usageMessage;
    }

    public void setDatabase(boolean database) {
        this.database = database;
    }

    public abstract void execute(CommandSender sender, String[] args);

    public BaseCommand(String str) {
        super(str);
    }

    public void setUsageMessage(String usageMessage) {
        this.usageMessage = usageMessage;
    }
}
