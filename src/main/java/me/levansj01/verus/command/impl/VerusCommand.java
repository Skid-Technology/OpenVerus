package me.levansj01.verus.command.impl;

import com.google.common.collect.Lists;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.manager.CheckManager;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.command.BaseArgumentCommand;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.data.manager.DataManager;
import me.levansj01.verus.gui.impl.MainGUI;
import me.levansj01.verus.gui.manager.GUIManager;
import me.levansj01.verus.lang.EnumMessage;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.storage.database.Ban;
import me.levansj01.verus.storage.database.Database;
import me.levansj01.verus.storage.database.check.CheckValues;
import me.levansj01.verus.type.VerusTypeLoader;
import me.levansj01.verus.util.BukkitUtil;
import me.levansj01.verus.util.java.WordUtils;
import me.levansj01.verus.verus2.data.player.TickerType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public class VerusCommand extends BaseArgumentCommand {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM HH:mm z");
    private static final String CMD_FORMAT = ChatColor.GRAY + "- " + VerusPlugin.COLOR + "/%s: " + ChatColor.WHITE + "%s";
    private final String FORMAT = ChatColor.GRAY
        + "- "
        + VerusPlugin.COLOR
        + "%s's "
        + WordUtils.capitalize(EnumMessage.VIOLATIONS.get())
        + ": "
        + ChatColor.WHITE
        + "%s";

    private void sendCommands(CommandSender sender) {
        sender.sendMessage(VerusPlugin.COLOR + VerusPlugin.getNameFormatted() + " Commands");
        sender.sendMessage(String.format(CMD_FORMAT, EnumMessage.ALERTS_COMMAND.get(), "Enable/Disable alerts"));
        sender.sendMessage(String.format(CMD_FORMAT, EnumMessage.LOGS_COMMAND.get(), "Obtain player logs"));
        sender.sendMessage(String.format(CMD_FORMAT, EnumMessage.RECENT_LOGS_COMMAND.get(), "Obtain a players' last 20 logs"));
        sender.sendMessage(String.format(CMD_FORMAT, EnumMessage.TOGGLE_CHECK_COMMAND.get(), "Toggle check alerts/bans"));
        if (BukkitUtil.hasPermission(sender, "verus.admin")) {
            sender.sendMessage(String.format(CMD_FORMAT, EnumMessage.MANUAL_BAN_COMMAND.get(), "Manually ban a player using " + VerusPlugin.getNameFormatted()));
        }

        if (StorageEngine.getInstance().getVerusConfig().isPingCommand()) {
            sender.sendMessage(String.format(CMD_FORMAT, EnumMessage.ALERTS_COMMAND.get(), "Displays a players' ping"));
        }

        if (VerusTypeLoader.isCustom()) {
            sender.sendMessage(String.format(CMD_FORMAT, EnumMessage.SET_VL_COMMAND.get(), "Set check ban vl"));
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        if (args.length == 1 && BukkitUtil.hasPermission(sender, "verus.staff")) {
            ArrayList arrayList = Lists.newArrayList();
            StringUtil.copyPartialMatches(args[0], this.arguments.keySet(), arrayList);
            Collections.sort(arrayList);
            return arrayList;
        }

        if (args.length == 2) {
            String str = args[0];
            if (str.equalsIgnoreCase(EnumMessage.LIST_CMDS_ARGUMENT.get())
                || str.equalsIgnoreCase(EnumMessage.ADD_CMD_ARGUMENT.get())
                || str.equalsIgnoreCase(EnumMessage.REMOVE_CMD_ARGUMENT.get())) {
                ArrayList arrayList = Lists.newArrayList();
                StringUtil.copyPartialMatches(args[1], ToggleCheckCommand.validCheckTypes, arrayList);
                Collections.sort(arrayList);
                return arrayList;
            }

            if (args[0].equalsIgnoreCase(EnumMessage.INFO_ARGUMENT.get()) || args[0].equalsIgnoreCase(EnumMessage.CHECK_ARGUMENT.get())) {
                Player player = sender instanceof Player ? (Player)sender : null;
                ArrayList list = new ArrayList();

                for (Player player2 : sender.getServer().getOnlinePlayers()) {
                    String name = player2.getName();
                    if (player == null || player.canSee(player2)) {
                        String str2 = args[args.length - 1];
                        if (StringUtil.startsWithIgnoreCase(name, str2)) {
                            list.add(name);
                        }
                    }
                }

                list.sort(String.CASE_INSENSITIVE_ORDER);
                return list;
            }
        }

        return super.tabComplete(sender, alias, args);
    }

    public VerusCommand() {
        super(VerusPlugin.getName());
        this.setPermission("verus.staff");
        this.addArgument(
            new BaseArgumentCommand.CommandArgument(
                EnumMessage.COMMANDS_ARGUMENT.get(), EnumMessage.COMMANDS_DESCRIPTION.get(), (first, second) -> this.sendCommands(first)
            )
        );
        this.addArgument(new BaseArgumentCommand.CommandArgument(EnumMessage.RESTART_ARGUMENT.get(), EnumMessage.RESTART_DESCRIPTION.get(), (player, second) -> {
            if (!BukkitUtil.hasPermission(player, "verus.restart")) {
                player.sendMessage(this.getPermissionMessage());
            } else {
                VerusPlugin.restart();
            }
        }));
        this.addArgument(new BaseArgumentCommand.CommandArgument(EnumMessage.GUI_ARGUMENT.get(), EnumMessage.GUI_DESCRIPTION.get(), (consoleCommandSender, second) -> {
            if (consoleCommandSender instanceof ConsoleCommandSender) {
                consoleCommandSender.sendMessage(ChatColor.RED + "This command can only be ran by players.");
            } else if (!BukkitUtil.hasPermission(consoleCommandSender, "verus.staff.gui")) {
                consoleCommandSender.sendMessage(this.getPermissionMessage());
            } else {
                MainGUI mainGui = GUIManager.getInstance().getMainGui();
                if (mainGui == null) {
                    consoleCommandSender.sendMessage(ChatColor.RED + "Failed to open GUI.");
                } else {
                    mainGui.openGui((Player)consoleCommandSender);
                }
            }
        }));
        this.addArgument(
            new BaseArgumentCommand.CommandArgument(
                EnumMessage.CHECK_ARGUMENT.get(),
                EnumMessage.CHECK_DESCRIPTION.get(),
                "(player)",
                (player, second) -> {
                    if (second.length != 2) {
                        this.sendHelp(player);
                    } else {
                        StorageEngine storageEngine = StorageEngine.getInstance();
                        if (!storageEngine.isConnected()) {
                            player.sendMessage(ChatColor.RED + "Please connect to a database to use this command.");
                        } else {
                            Database database = storageEngine.getDatabase();
                            database.getUUID(
                                second[1],
                                entry -> {
                                    if (entry == null) {
                                        player.sendMessage(ChatColor.RED + EnumMessage.COMMAND_PLAYER_NEVER_LOGGED_ON.get());
                                    } else {
                                        database.getBans(
                                            entry,
                                            entry2 -> {
                                                if (!entry2.iterator().hasNext()) {
                                                    player.sendMessage(ChatColor.RED + "This player hasn't been banned.");
                                                } else {
                                                    player.sendMessage(VerusPlugin.COLOR + second[1] + "'s ban(s):");

                                                    for (Ban ban : entry2) {
                                                        player.sendMessage(
                                                            ChatColor.GRAY
                                                                + "- "
                                                                + VerusPlugin.COLOR
                                                                + "Date: "
                                                                + ChatColor.WHITE
                                                                + DATE_FORMAT.format(new Date(ban.getTimestamp()))
                                                                + VerusPlugin.COLOR
                                                                + " Type: "
                                                                + ChatColor.WHITE
                                                                + ban.getType()
                                                                + " "
                                                                + ban.getSubType()
                                                        );
                                                    }
                                                }
                                            }
                                        );
                                    }
                                }
                            );
                        }
                    }
                }
            )
        );
        this.addArgument(
            new BaseArgumentCommand.CommandArgument(
                EnumMessage.INFO_ARGUMENT.get(),
                EnumMessage.INFO_DESCRIPTION.get(),
                "(player)",
                (player, second) -> {
                    if (second.length != 2) {
                        this.sendHelp(player);
                    } else {
                        Player player2 = Bukkit.getPlayer(second[1]);
                        if (player2 == null) {
                            player.sendMessage(ChatColor.RED + EnumMessage.COMMAND_PLAYER_NOT_FOUND.get());
                        } else {
                            PlayerData player3 = DataManager.getInstance().getPlayer(player2);
                            if (player3 == null) {
                                player.sendMessage(ChatColor.RED + EnumMessage.COMMAND_PLAYER_NOT_FOUND.get());
                            } else {
                                BaseComponent[] components = null;
                                int value = (int)Math.floor(player3.getTotalViolations());
                                if (value > 0) {
                                    components = TextComponent.fromLegacyText(ChatColor.GRAY + ChatColor.ITALIC.toString() + "Hover for violations...");
                                    String str = WordUtils.capitalize(EnumMessage.VIOLATIONS.get());
                                    String str2 = "";

                                    for (Check check : player3.getCheckData().getChecks()) {
                                        if (CheckManager.getInstance().isEnabled(check)) {
                                            int value2 = (int)Math.floor(check.getViolations());
                                            if (value2 > 0) {
                                                str2 = str2
                                                    + VerusPlugin.COLOR
                                                    + check.getType().getName()
                                                    + " "
                                                    + check.getSubType()
                                                    + " "
                                                    + str
                                                    + ": "
                                                    + ChatColor.WHITE
                                                    + value2
                                                    + " \n";
                                            }
                                        }
                                    }

                                    str2 = str2 + "\n" + VerusPlugin.COLOR + str + ": " + ChatColor.WHITE + value;
                                    HoverEvent event = new HoverEvent(Action.SHOW_TEXT, TextComponent.fromLegacyText(str2));
                                    Arrays.stream(components).forEach(Array -> Array.setHoverEvent(event));
                                }

                                player.sendMessage(player3.getInfo());
                                if (player instanceof Player) {
                                    Player player4 = (Player)player;
                                    if (components != null) {
                                        player4.spigot().sendMessage(components);
                                    }

                                    if (BukkitUtil.isDev(player4)) {
                                        player.sendMessage(
                                            ChatColor.GRAY
                                                + "Stats: "
                                                + ChatColor.WHITE
                                                + String.format(
                                                    "(current=%s) (lag=%s / fast=%s) (teleporting=%s / v2=%s) (keepalive=%s %sms / transaction=%s %sms) (ping=%s / maxping=%s) (spawned=%s / ground=%s / vehicle=%s / fly=%s|%s / survival=%s) (gl=%s|%s)",
                                                    player3.getTotalTicks(),
                                                    player3.hasLag(),
                                                    player3.hasFast(),
                                                    player3.isTeleporting(),
                                                    player3.isTeleportingV2(),
                                                    player3.getLastKeepAlive(),
                                                    player3.getPing(),
                                                    player3.getTickerMap().get(TickerType.LAST_SENT_TRANSACTION),
                                                    player3.getTransactionPing(),
                                                    player3.getPingTicks(),
                                                    player3.getMaxPingTicks(),
                                                    player3.isSpawned(),
                                                    player3.getLocation().getGround(),
                                                    player3.isVehicle(),
                                                    player3.canFly(),
                                                    player3.isFlying(),
                                                    player3.isSurvival(),
                                                    NMSManager.getInstance().isGliding(player4),
                                                    player3.isGliding()
                                                )
                                        );
                                    }
                                }
                            }
                        }
                    }
                }
            )
        );
        this.addArgument(
            new BaseArgumentCommand.CommandArgument(
                EnumMessage.TOP_ARGUMENT.get(),
                EnumMessage.TOP_DESCRIPTION.get(),
                (player, second) -> {
                    List mostViolations = DataManager.getInstance().getMostViolations();
                    boolean flag = false;

                    for (int i = 0; i < Math.min(mostViolations.size(), 10); i++) {
                        PlayerData playerData = (PlayerData)mostViolations.get(i);
                        if (playerData != null && playerData.getTotalViolations() > 1.0) {
                            if (i == 0) {
                                player.sendMessage(VerusPlugin.COLOR + "Top Cheaters (" + WordUtils.capitalize(EnumMessage.VIOLATIONS.get()) + "): ");
                            }

                            String name = playerData.getName();
                            BaseComponent[] components = TextComponent.fromLegacyText(
                                String.format(this.FORMAT, playerData.getName(), (int)Math.floor(playerData.getTotalViolations()))
                            );
                            HoverEvent event = new HoverEvent(
                                Action.SHOW_TEXT, TextComponent.fromLegacyText(EnumMessage.VIEW_PLAYER_INFO.get().replace("{player}", name))
                            );
                            ClickEvent event2 = new ClickEvent(
                                net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, "/" + VerusPlugin.getName() + " info " + name
                            );
                            Arrays.stream(components).forEach(Array -> {
                                Array.setHoverEvent(event);
                                Array.setClickEvent(event2);
                            });
                            if (player instanceof Player) {
                                ((Player)player).spigot().sendMessage(components);
                            } else {
                                player.sendMessage(TextComponent.toLegacyText(components));
                            }

                            flag = true;
                        }
                    }

                    if (!flag) {
                        player.sendMessage(ChatColor.RED + "No players with " + EnumMessage.VIOLATIONS.get() + " were found.");
                    }
                }
            )
        );
        if (VerusTypeLoader.isDev()) {
            this.addArgument(
                new BaseArgumentCommand.CommandArgument(
                    EnumMessage.LIST_CMDS_ARGUMENT.get(),
                    EnumMessage.LIST_CMDS_DESCRIPTION.get(),
                    "(checkType) (subType)",
                    (player, second) -> {
                        if (second.length != 3) {
                            this.sendHelp(player);
                        } else {
                            String str = second[1];

                            CheckType type;
                            try {
                                type = CheckType.valueOf(str);
                            } catch (Throwable throwable) {
                                player.sendMessage(ToggleCheckCommand.validTypesMessage);
                                return;
                            }

                            String str2 = type.ordinal() + "" + second[2];
                            CheckValues values = CheckManager.getInstance().getValues(str2);
                            if (values == null) {
                                player.sendMessage(ChatColor.RED + "A check with that ID does not exist!");
                            } else if (!values.hasCommands()) {
                                player.sendMessage(ChatColor.RED + "This check does not contain any per-check commands!");
                            } else {
                                int value = 0;
                                String str3 = VerusPlugin.COLOR + "Commands for " + ChatColor.WHITE + str + " " + second[2] + "\n";

                                for (String serializable : values.getCommands()) {
                                    str3 = str3
                                        + String.format(
                                            ChatColor.GRAY + "- " + VerusPlugin.COLOR + "'%s' %s\n",
                                            ChatColor.WHITE + serializable,
                                            ChatColor.GRAY + ChatColor.ITALIC.toString() + "(Index: " + value++ + ")"
                                        );
                                }

                                player.sendMessage(str3);
                            }
                        }
                    }
                )
            );
            this.addArgument(
                new BaseArgumentCommand.CommandArgument(
                    EnumMessage.ADD_CMD_ARGUMENT.get(),
                    EnumMessage.ADD_CMD_DESCRIPTION.get(),
                    "(checkType) (subType) (command)",
                    (player, second) -> {
                        if (second.length < 4) {
                            this.sendHelp(player);
                        } else {
                            String serializable = second[1];

                            CheckType type;
                            try {
                                type = CheckType.valueOf(serializable);
                            } catch (Throwable throwable) {
                                player.sendMessage(ToggleCheckCommand.validTypesMessage);
                                return;
                            }

                            String str = type.ordinal() + "" + second[2];
                            CheckValues values = CheckManager.getInstance().getValues(str);
                            if (values == null) {
                                player.sendMessage(ChatColor.RED + "A check with that ID does not exist!");
                            } else {
                                String[] args = new String[second.length - 3];
                                System.arraycopy(second, 3, args, 0, args.length);
                                String serializable2 = String.join(" ", args);
                                if (serializable2.contains(",")) {
                                    player.sendMessage(ChatColor.RED + "You cannot include ',' in a per-check command!");
                                } else if (values.hasCommand(serializable2)) {
                                    player.sendMessage(ChatColor.RED + "This per-check command already exists!");
                                } else {
                                    CheckManager.getInstance().addCommand(values, serializable2);
                                    player.sendMessage(
                                        String.format(
                                            VerusPlugin.COLOR + "Added '%s' to %s %s.",
                                            ChatColor.WHITE + serializable2 + VerusPlugin.COLOR,
                                            ChatColor.WHITE + serializable,
                                            second[2] + VerusPlugin.COLOR
                                        )
                                    );
                                }
                            }
                        }
                    }
                )
            );
            this.addArgument(
                new BaseArgumentCommand.CommandArgument(
                    EnumMessage.REMOVE_CMD_ARGUMENT.get(),
                    EnumMessage.REMOVE_CMD_DESCRIPTION.get(),
                    "(checkType) (subType) (index)",
                    (player, second) -> {
                        if (second.length != 4) {
                            this.sendHelp(player);
                        } else {
                            String serializable = second[1];

                            CheckType type;
                            try {
                                type = CheckType.valueOf(serializable);
                            } catch (Throwable throwable) {
                                player.sendMessage(ToggleCheckCommand.validTypesMessage);
                                return;
                            }

                            String str = type.ordinal() + "" + second[2];
                            CheckValues values = CheckManager.getInstance().getValues(str);
                            if (values == null) {
                                player.sendMessage(ChatColor.RED + "A check with that ID does not exist!");
                            } else {
                                int value;
                                try {
                                    value = Integer.parseInt(second[3]);
                                } catch (Throwable throwable) {
                                    player.sendMessage(ChatColor.RED + "Please enter a valid value!");
                                    return;
                                }

                                if (value + 1 > values.getCommandsSize()) {
                                    player.sendMessage(ChatColor.RED + "That per-check command does not exist!");
                                } else {
                                    String command = values.getCommand(value);
                                    CheckManager.getInstance().removeCommand(values, value);
                                    player.sendMessage(
                                        String.format(
                                            VerusPlugin.COLOR + "Removed '%s' from %s %s.",
                                            ChatColor.WHITE + command + VerusPlugin.COLOR,
                                            ChatColor.WHITE + serializable,
                                            second[2] + VerusPlugin.COLOR
                                        )
                                    );
                                }
                            }
                        }
                    }
                )
            );
        }
    }
}
