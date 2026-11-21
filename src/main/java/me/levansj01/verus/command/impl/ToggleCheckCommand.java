package me.levansj01.verus.command.impl;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.check.manager.CheckManager;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.command.BaseCommand;
import me.levansj01.verus.lang.EnumMessage;
import me.levansj01.verus.storage.database.check.CheckValues;
import me.levansj01.verus.util.BukkitUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;

public class ToggleCheckCommand extends BaseCommand {
    public static final List<String> validCheckTypes = Arrays.stream(CheckType.values()).filter(CheckType::valid).map(Enum::name).collect(Collectors.toList());
    public static final String validTypesMessage = String.format(ChatColor.RED + "Valid types: %s", String.join(", ", validCheckTypes));
    private static final String FORMAT = VerusPlugin.COLOR + "Updated %s state for %s from %s to %s";

    public ToggleCheckCommand() {
        super(EnumMessage.TOGGLE_CHECK_COMMAND.get());
        this.setPermission("verus.staff.toggle");
        this.setUsageMessage(ChatColor.RED + "Usage: /togglecheck <checkType> <subType> <alerts>");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 3) {
            sender.sendMessage(this.getUsageMessage());
        } else {
            if (BukkitUtil.hasPermission(sender, this.getPermission()) || BukkitUtil.hasPermission(sender, "verus.admin")) {
                String serializable = args[0];

                CheckType type;
                try {
                    type = CheckType.valueOf(serializable);
                } catch (Throwable throwable) {
                    sender.sendMessage(validTypesMessage);
                    return;
                }

                String str = type.ordinal() + "" + args[1];
                CheckValues values = CheckManager.getInstance().getValues(str);
                if (values == null) {
                    sender.sendMessage(ChatColor.RED + "A check with that ID does not exist!");
                    return;
                }

                boolean flag = Boolean.parseBoolean(args[2]);
                if (flag) {
                    boolean alert = values.isAlert();
                    CheckManager.getInstance().setEnabled(str, !alert);
                    sender.sendMessage(
                        String.format(
                            FORMAT,
                            "Alert",
                            ChatColor.WHITE + serializable + " " + args[1] + VerusPlugin.COLOR,
                            ChatColor.WHITE + String.valueOf(alert) + VerusPlugin.COLOR,
                            ChatColor.WHITE + String.valueOf(!alert) + VerusPlugin.COLOR
                        )
                    );
                } else {
                    boolean punish = values.isPunish();
                    CheckManager.getInstance().setAutoban(str, !punish);
                    sender.sendMessage(
                        String.format(
                            FORMAT,
                            "Bannable",
                            ChatColor.WHITE + serializable + " " + args[1] + VerusPlugin.COLOR,
                            ChatColor.WHITE + String.valueOf(punish) + VerusPlugin.COLOR,
                            ChatColor.WHITE + String.valueOf(!punish) + VerusPlugin.COLOR
                        )
                    );
                }
            }
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        if (args.length == 1 && BukkitUtil.hasPermission(sender, "verus.staff.vl")) {
            ArrayList arrayList = Lists.newArrayList();
            StringUtil.copyPartialMatches(args[0], validCheckTypes, arrayList);
            Collections.sort(arrayList);
            return arrayList;
        } else {
            return super.tabComplete(sender, alias, args);
        }
    }
}
