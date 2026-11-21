package me.levansj01.verus.command.impl;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.manager.CheckManager;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.command.BaseCommand;
import me.levansj01.verus.data.CheckData;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.data.manager.DataManager;
import me.levansj01.verus.lang.EnumMessage;
import me.levansj01.verus.storage.database.check.CheckValues;
import me.levansj01.verus.util.BukkitUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;

public class SetVLCommand extends BaseCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length >= 2 && args.length <= 3) {
            if (BukkitUtil.hasPermission(sender, this.getPermission()) || BukkitUtil.hasPermission(sender, "verus.admin")) {
                String serializable = args[0];

                CheckType type;
                try {
                    type = CheckType.valueOf(serializable);
                } catch (Throwable throwable) {
                    sender.sendMessage(ToggleCheckCommand.validTypesMessage);
                    return;
                }

                String str = type.ordinal() + "" + args[1];
                CheckValues values = CheckManager.getInstance().getValues(str);
                if (values == null) {
                    sender.sendMessage(ChatColor.RED + "A check with that ID does not exist!");
                    return;
                }

                int maxViolation = values.getMaxViolation();
                if (args.length == 2) {
                    sender.sendMessage(
                        String.format(
                            VerusPlugin.COLOR + "Max violations for %s: %s",
                            ChatColor.WHITE + serializable + " " + args[1] + VerusPlugin.COLOR,
                            ChatColor.WHITE + String.valueOf(maxViolation)
                        )
                    );
                    return;
                }

                int value;
                try {
                    int value2 = Integer.parseInt(args[2]);
                    value = value2;
                } catch (Throwable throwable) {
                    sender.sendMessage(ChatColor.RED + "Please enter a valid violation value.");
                    return;
                }

                if (value < 5) {
                    sender.sendMessage(ChatColor.RED + "Max violation must be greater than 4.");
                    return;
                }

                for (PlayerData playerData : DataManager.getInstance().getPlayers()) {
                    CheckData checkData = playerData.getCheckData();
                    Check check = checkData.getByIdentifier().get(str);
                    if (check != null) {
                        check.setMaxViolation(value);
                    }
                }

                CheckManager.getInstance().setMaxViolation(str, value);
                sender.sendMessage(
                    String.format(
                        VerusPlugin.COLOR + "Updated violations for %s from %s to %s",
                        ChatColor.WHITE + serializable + " " + args[1] + VerusPlugin.COLOR,
                        ChatColor.WHITE + String.valueOf(maxViolation) + VerusPlugin.COLOR,
                        ChatColor.WHITE + String.valueOf(value) + VerusPlugin.COLOR
                    )
                );
            }
        } else {
            sender.sendMessage(this.getUsageMessage());
        }
    }

    public SetVLCommand() {
        super(EnumMessage.SET_VL_COMMAND.get());
        this.setPermission("verus.staff.vl");
        this.setUsageMessage(ChatColor.RED + "Usage: /setvl <checkType> <subType> <violations>");
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        if (args.length == 1 && BukkitUtil.hasPermission(sender, "verus.staff.vl")) {
            ArrayList arrayList = Lists.newArrayList();
            StringUtil.copyPartialMatches(args[0], ToggleCheckCommand.validCheckTypes, arrayList);
            Collections.sort(arrayList);
            return arrayList;
        } else {
            return super.tabComplete(sender, alias, args);
        }
    }
}
