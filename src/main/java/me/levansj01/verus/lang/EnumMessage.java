package me.levansj01.verus.lang;

import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.type.Loader;
import net.md_5.bungee.api.ChatColor;

public enum EnumMessage {
    PLAYER("player", "player"),
    VIOLATIONS("violations", "violations"),
    CHEAT("cheat", "cheat"),
    COMMAND_PERMISSION("command.permission", ChatColor.RED + "You do not have permission to do this."),
    COMMAND_PLAYER_NEVER_LOGGED_ON("command.player_never_logged_on", "This player has never logged onto the server"),
    COMMAND_PLAYER_NOT_FOUND("command.player_not_found", "Player not found"),
    LOGS_COMMAND("command.commands.name.logs", Loader.getLogsCommand()),
    RECENT_LOGS_COMMAND("command.commands.name.recentlogs", Loader.getRecentlogsCommand()),
    MANUAL_BAN_COMMAND("command.commands.name.manualban", Loader.getManualbanCommand()),
    ALERTS_COMMAND("command.commands.name.alerts", Loader.getAlertsCommand()),
    PING_COMMAND("command.commands.name.ping", Loader.getPingCommand()),
    CHECK_LOGS_COMMAND("command.commands.name.checklogs", Loader.getChecklogsCommand()),
    LANG_COMMAND("command.commands.name.lang", "reloadlang"),
    FOCUS_COMMAND("command.commands.name.vfocus", "vfocus"),
    SET_VL_COMMAND("command.commands.name.setvl", "setvl"),
    TOGGLE_CHECK_COMMAND("command.commands.name.togglecheck", "togglecheck"),
    UPDATE_CONFIG_COMMAND("command.commands.name.updateconfig", "updateconfig"),
    CHECK_CMDS_COMMAND("command.commands.name.checkcmds", "checkcmds"),
    COMMANDS_ARGUMENT("command.commands.arguments.commands", "commands"),
    RESTART_ARGUMENT("command.commands.arguments.restart", "restart"),
    GUI_ARGUMENT("command.commands.arguments.gui", "gui"),
    CHECK_ARGUMENT("command.commands.arguments.check", "check"),
    INFO_ARGUMENT("command.commands.arguments.info", "info"),
    TOP_ARGUMENT("command.commands.arguments.top", "top"),
    LIST_CMDS_ARGUMENT("command.commands.arguments.listcmds", "listcmds"),
    ADD_CMD_ARGUMENT("command.commands.arguments.addcmd", "addcmd"),
    REMOVE_CMD_ARGUMENT("command.commands.arguments.removecmd", "removecmd"),
    RESTART_DESCRIPTION("command.commands.restart_description", "Restart and automatically update"),
    GUI_DESCRIPTION("command.commands.gui_description", "View and control " + VerusPlugin.getNameFormatted()),
    CHECK_DESCRIPTION("command.commands.check_description", "Check a players' " + VerusPlugin.getNameFormatted() + " related bans"),
    COMMANDS_DESCRIPTION("command.commands.commands_description", "View all " + VerusPlugin.getNameFormatted() + " related commands"),
    INFO_DESCRIPTION("command.commands.info_description", "View useful information about a player"),
    TOP_DESCRIPTION("command.commands.top_description", "Gather players with the most violations"),
    LIST_CMDS_DESCRIPTION("command.commands.listcmds_description", "View per-check commands"),
    ADD_CMD_DESCRIPTION("command.commands.addcmd_description", "Add a per-check command"),
    REMOVE_CMD_DESCRIPTION("command.commands.removecmd_description", "Remove a per-check command"),
    VIEW_PLAYER_INFO("command.top.view_player_info", "Click to view {player}'s info"),
    PING_SELF("command.ping.self", VerusPlugin.COLOR + "Your ping is " + ChatColor.GRAY + "{ping}"),
    PING_OTHER("command.ping.other", VerusPlugin.COLOR + "{player}'s ping is " + ChatColor.GRAY + "{ping}"),
    ALERTS_ENABLED_COMMAND("alerts.enabled", "You are now viewing alerts"),
    ALERTS_DISABLED_COMMAND("alerts.disabled", "You are no longer viewing alerts"),
    PLAYER_NO_LOGS("command.logs.nologs", "This player has no logs."),
    LOG_FORMAT("log.format", "({date} | {timestamp}) {player} failed {checkType} Type {subType} VL: {vl} | Ping: {ping}ms Lag: {lag}ms"),
    DISCORD_LOG("discord.log", "```scala\n{log}\n```\n```scala\nClient Version: {version}\nClient Data: {client}\nClient Brand: {brand}\n```"),
    DISCORD_BAN(
        "discord.ban",
        "```scala\nPlayer: {player}\nDetected By: {checkType} {subType}\nClient Version: {version}\nClient Data: {client}\nClient Brand: {brand}\n```"
    );

    private final String fallback;
    private String value;
    private final String location;

    EnumMessage(String location, String fallback) {
        this.location = location;
        this.fallback = fallback;
    }

    public String getFallback() {
        return this.fallback;
    }

    public String getLocation() {
        return this.location;
    }

    public String get() {
        return this.value == null ? this.fallback : this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
