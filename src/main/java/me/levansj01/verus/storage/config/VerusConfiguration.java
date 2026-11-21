package me.levansj01.verus.storage.config;

import com.mongodb.MongoCredential;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import me.levansj01.launcher.VerusLauncher;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.storage.database.pool.ConnectionType;
import me.levansj01.verus.type.VerusTypeLoader;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

public class VerusConfiguration {
    private boolean butterflyAlerts;
    private String alertClickHover;
    private boolean pingKickCheck;
    private String bypassPermission;
    private String sqlDatabase;
    private boolean bypassAlerts;
    private int bansCleanupDays;
    private boolean directMemory;
    private File file;
    private String checkDatabaseName;
    private String alertMessage;
    private boolean checkPersistence;
    private String secretKeyPath;
    private boolean butterflyBans;
    private int heavyTicks;
    private boolean pingKick;
    private boolean slimePushFix;
    private String announcementMessage;
    private boolean heavyChecks;
    private boolean mongoEnabled;
    private List<String> banCommands;
    private boolean banAnnouncement;
    private boolean schemBans;
    private File checkFile;
    private int persistenceMins;
    private String sqlUsername;
    private int maxClicksPerSecond;
    private MongoCredential mongoCredential;
    private String sqlHost;
    private boolean discordBans;
    private boolean unloadedChunkFix;
    private int persistenceSeconds;
    private String sqlPassword;
    private boolean persistence;
    private boolean heavyPullback;
    private boolean autoBan;
    private boolean fixSlimeBlocks;
    private boolean moreTransactions;
    private boolean compression;
    private boolean mongoAuthEnabled;
    private boolean sqlEnabled;
    private boolean alertClick;
    private YamlConfiguration checkConfiguration;
    private int sqlPort;
    private boolean randomBanId;
    private String discordBansUrl;
    private boolean vanishPing;
    private String anticheatName;
    private boolean scrambleBanId;
    private String sqlDatabaseType;
    private boolean cleanup;
    private boolean sendStats;
    private boolean pingCommand;
    private int mongoPort;
    private String messagingChannel;
    private String discordLogsUrl;
    private int sqlPushSeconds;
    private boolean schem;
    private boolean geyser;
    private boolean blockLog;
    private boolean discordLogs;
    private YamlConfiguration banConfiguration;
    private String checkCommandsDatabaseName;
    private YamlConfiguration configuration = new YamlConfiguration();
    private String mongoHost;
    private String recentlogsMessage;
    private boolean hardDisable;
    private String geyserPrefix;
    private boolean databaseCheckPersistence;
    private int logsCleanupDays;
    private int pingTimeout;
    private String[] banMessage;
    private String mongoDatabase;
    private boolean announcement;
    private ConnectionType sqlConnectionType;
    private boolean bypassEnabled;
    private String alertClickCommand;
    private String alertCertainty;
    private int sqlPool;
    private boolean ignoreLag;
    private boolean bungeeBans;

    public void setBanConfiguration(YamlConfiguration banConfiguration) {
        this.banConfiguration = banConfiguration;
    }

    public String getSecretKeyPath() {
        return this.secretKeyPath;
    }

    public void setMongoHost(String mongoHost) {
        this.mongoHost = mongoHost;
    }

    public boolean isAutoBan() {
        return this.autoBan;
    }

    public String getRecentlogsMessage() {
        return this.recentlogsMessage;
    }

    public String getBypassPermission() {
        return this.bypassPermission;
    }

    public boolean isBlockLog() {
        return this.blockLog;
    }

    public void setCheckCommandsDatabaseName(String checkCommandsDatabaseName) {
        this.checkCommandsDatabaseName = checkCommandsDatabaseName;
    }

    public void setSqlPushSeconds(int sqlPushSeconds) {
        this.sqlPushSeconds = sqlPushSeconds;
    }

    public boolean isHardDisable() {
        return this.hardDisable;
    }

    public void setMongoCredential(MongoCredential mongoCredential) {
        this.mongoCredential = mongoCredential;
    }

    public boolean isUnloadedChunkFix() {
        return this.unloadedChunkFix;
    }

    public void setCheckDatabaseName(String checkDatabaseName) {
        this.checkDatabaseName = checkDatabaseName;
    }

    public String getSqlPassword() {
        return this.sqlPassword;
    }

    public void setFixSlimeBlocks(boolean fixSlimeBlocks) {
        this.fixSlimeBlocks = fixSlimeBlocks;
    }

    public ConnectionType getSqlConnectionType() {
        return this.sqlConnectionType;
    }

    public boolean isRandomBanId() {
        return this.randomBanId;
    }

    public void setMaxClicksPerSecond(int maxClicksPerSecond) {
        this.maxClicksPerSecond = maxClicksPerSecond;
    }

    public void setGeyserPrefix(String geyserPrefix) {
        this.geyserPrefix = geyserPrefix;
    }

    public void setHeavyChecks(boolean heavyChecks) {
        this.heavyChecks = heavyChecks;
    }

    public void readConfig() {
        Logger logger = VerusLauncher.getPlugin().getLogger();
        this.anticheatName = this.configuration.getString("verus.name", "&9&lVerus");
        this.alertCertainty = this.configuration.getString("verus.alerts.certainty", "&7failed");
        this.alertMessage = this.configuration.getString("verus.alerts.message", "{name} &8> &f{player} &7{certainty} &f{cheat} &7VL[&9{vl}&7]");
        this.alertClick = this.configuration.getBoolean("verus.alerts.click.enabled", this.alertClick);
        this.alertClickCommand = this.configuration.getString("verus.alerts.click.command", "/tp {player}");
        this.alertClickHover = this.configuration.getString("verus.alerts.click.hover", "Click to teleport to {player}.");
        this.banCommands = this.readList(this.configuration.get("verus.bans.commands", new String[]{"ban {player} &9&lVerus &8> &fCheating"}));
        this.banCommands = this.banCommands.stream().map(command -> ChatColor.translateAlternateColorCodes('&', command)).collect(Collectors.toList());
        Object obj = this.configuration.get("verus.bans.message", this.banMessage);
        if (obj instanceof String) {
            this.banMessage = new String[]{(String)obj};
        } else if (obj instanceof List) {
            this.banMessage = ((List<String>)obj).toArray(new String[0]);
        } else {
            this.banMessage = (String[])obj;
        }

        this.autoBan = this.configuration.getBoolean("verus.bans.enabled", this.autoBan);
        this.banAnnouncement = this.configuration.getBoolean("verus.bans.announce", this.banAnnouncement);
        this.randomBanId = this.configuration.getBoolean("verus.bans.randomize", this.randomBanId);
        this.scrambleBanId = this.configuration.getBoolean("verus.bans.scramble", this.scrambleBanId);
        this.recentlogsMessage = this.configuration
            .getString("verus.recentlogs.message", "{time} {name} &7Failed &f{type} Type {subType} &7VL: &f{vl} &7P: &f{ping}");
        this.geyser = this.configuration.getBoolean("verus.geyser.enabled", this.geyser);
        this.geyserPrefix = this.configuration.getString("verus.geyser.prefix", this.geyserPrefix);
        this.bungeeBans = this.configuration.getBoolean("verus.bungee.bans", this.bungeeBans);
        this.secretKeyPath = this.configuration.getString("verus.bungee.secretKeyPath", this.secretKeyPath);
        this.messagingChannel = this.configuration.getString("verus.bungee.messagingChannel", this.messagingChannel);
        this.bypassEnabled = this.configuration.getBoolean("verus.permissions.bypass.enabled", this.bypassEnabled);
        this.bypassPermission = this.configuration.getString("verus.permissions.bypass.permission", this.bypassPermission);
        this.bypassAlerts = this.configuration.getBoolean("verus.permissions.bypass.alerts", this.bypassAlerts);
        this.persistence = this.configuration.getBoolean("verus.checks.persistence.enabled", this.persistence);
        this.persistenceMins = this.configuration.getInt("verus.checks.persistence.mins", this.persistenceMins);
        this.persistenceSeconds = this.configuration.getInt("verus.checks.persistence.seconds", this.persistenceSeconds);
        this.schemBans = this.configuration.getBoolean("verus.checks.schemprinter.bans", this.schemBans);
        this.schem = this.configuration.getBoolean("verus.checks.schemprinter.enabled", this.schem);
        this.butterflyBans = this.configuration.getBoolean("verus.checks.butterflyclicking.bans", this.butterflyBans);
        this.butterflyAlerts = this.configuration.getBoolean("verus.checks.butterflyclicking.enabled", this.butterflyAlerts);
        this.heavyChecks = this.configuration.getBoolean("verus.checks.heavy.enabled", this.heavyChecks);
        this.heavyTicks = this.configuration.getInt("verus.checks.heavy.ticks", this.heavyTicks);
        this.heavyPullback = this.configuration.getBoolean("verus.checks.heavy.pullback", this.heavyPullback);
        this.hardDisable = this.configuration.getBoolean("verus.checks.hardDisable.enabled");
        this.pingKick = this.configuration.getBoolean("verus.checks.pingKick.enabled", this.pingKick);
        this.pingKickCheck = this.configuration.getBoolean("verus.checks.pingKick.combat", this.pingKickCheck);
        this.pingTimeout = this.configuration.getInt("verus.checks.pingKick.timeout", this.pingTimeout);
        this.pingTimeout = Math.max(15, Math.min(45, this.pingTimeout));
        this.slimePushFix = this.configuration.getBoolean("verus.checks.speed.slimePushFix", this.slimePushFix);
        this.moreTransactions = VerusTypeLoader.isEnterprise() || this.configuration.getBoolean("verus.checks.reach.moreTransactions", this.moreTransactions);
        this.ignoreLag = this.configuration.getBoolean("verus.checks.reach.ignoreLag", this.ignoreLag);
        this.unloadedChunkFix = this.configuration.getBoolean("verus.checks.fly.unloadedChunkFix", this.unloadedChunkFix);
        this.fixSlimeBlocks = this.configuration.getBoolean("verus.checks.fly.fixSlimeBlocks", this.fixSlimeBlocks);
        this.maxClicksPerSecond = this.configuration.getInt("verus.checks.autoclicker.maxcps", this.maxClicksPerSecond);
        if (!this.schemBans || !this.schem) {
            logger.warning("Disabling Schematica Printer checks may also allow scaffold and other cheats to bypass");
        }

        this.pingCommand = this.configuration.getBoolean("verus.commands.ping.enabled", this.pingCommand);
        this.vanishPing = this.configuration.getBoolean("verus.commands.ping.vanish", this.vanishPing);
        this.cleanup = this.configuration.getBoolean("verus.database.cleanup.enabled", this.cleanup);
        this.logsCleanupDays = this.configuration.getInt("verus.database.cleanup.logs.days", this.logsCleanupDays);
        this.bansCleanupDays = this.configuration.getInt("verus.database.cleanup.bans.days", this.bansCleanupDays);
        this.mongoEnabled = this.configuration.getBoolean("verus.mongo.enabled", this.mongoEnabled);
        this.mongoHost = this.configuration.getString("verus.mongo.host", this.mongoHost);
        this.mongoPort = this.configuration.getInt("verus.mongo.port", this.mongoPort);
        this.mongoDatabase = this.configuration.getString("verus.mongo.database", this.mongoDatabase);
        this.mongoAuthEnabled = this.configuration.getBoolean("verus.mongo.auth.enabled", this.mongoAuthEnabled);
        if (this.mongoAuthEnabled) {
            String string = this.configuration.getString("verus.mongo.auth.username", "verus");
            String string2 = this.configuration.getString("verus.mongo.auth.password", "");
            String string3 = this.configuration.getString("verus.mongo.auth.database", this.mongoDatabase);
            this.mongoCredential = MongoCredential.createCredential(string, string3, string2.toCharArray());
        }

        this.sqlEnabled = this.configuration.getBoolean("verus.sql.enabled", this.sqlEnabled);
        this.sqlHost = this.configuration.getString("verus.sql.host", this.sqlHost);
        this.sqlPushSeconds = this.configuration.getInt("verus.sql.push.seconds", this.sqlPushSeconds);
        this.sqlPort = this.configuration.getInt("verus.sql.port", this.sqlPort);
        this.sqlPool = this.configuration.getInt("verus.sql.pool", this.sqlPool);
        this.sqlDatabase = this.configuration.getString("verus.sql.database", this.sqlDatabase);
        this.sqlDatabaseType = this.configuration.getString("verus.sql.type", this.sqlDatabaseType);
        this.sqlUsername = this.configuration.getString("verus.sql.auth.username", this.sqlUsername);
        this.sqlPassword = this.configuration.getString("verus.sql.auth.password", this.sqlPassword);
        this.discordLogs = this.configuration.getBoolean("verus.discord.logs.enabled", this.discordLogs);
        this.discordLogsUrl = this.configuration.getString("verus.discord.logs.url", this.discordLogsUrl);
        this.discordBans = this.configuration.getBoolean("verus.discord.bans.enabled", this.discordBans);
        this.discordBansUrl = this.configuration.getString("verus.discord.bans.url", this.discordBansUrl);
        if (this.heavyPullback && NMSManager.getInstance().getServerVersion() != ServerVersion.v1_8_R3) {
            this.heavyPullback = false;
            logger.warning("Heavy pullback is only available on 1.8.8 server version");
        }

        try {
            this.sqlConnectionType = ConnectionType.valueOf(this.sqlDatabaseType.toUpperCase());
        } catch (IllegalArgumentException exception) {
            logger.warning("Invalid SQL type: " + this.sqlDatabaseType + ", reverting to MySQL");
            this.sqlConnectionType = ConnectionType.MYSQL;
        }

        if (!this.moreTransactions) {
            if (this.ignoreLag) {
                logger.severe("ignoreLag is enabled but moreTransactions is disabled, this may cause reach false positives.");
            } else {
                logger.info("moreTransactions is disabled, sending more transactions may improve the accuracy of reach checks");
            }
        }

        if (!this.heavyChecks) {
            logger.warning("Heavy checks are not enabled, it is recommended that you enable this if you wish to block spoof-ground bypasses");
        }

        if (!this.pingKick && !this.pingKickCheck) {
            logger.warning("Ping kick/combat is not enabled, it is recommended that you enable it to avoid ping spoof bypasses");
        }

        if (!this.mongoEnabled && !this.sqlEnabled) {
            if (this.persistence) {
                logger.info("You must be connected to a database in order to use check data persistence");
                this.persistence = false;
            }
        } else if (!this.persistence) {
            logger.warning("Check persistence is not enabled, it is recommended that you enable it to avoid relog bypasses");
        }
    }

    public void setButterflyBans(boolean butterflyBans) {
        this.butterflyBans = butterflyBans;
    }

    public void setMongoDatabase(String mongoDatabase) {
        this.mongoDatabase = mongoDatabase;
    }

    public void setHeavyPullback(boolean heavyPullback) {
        this.heavyPullback = heavyPullback;
    }

    public void setSqlPassword(String sqlPassword) {
        this.sqlPassword = sqlPassword;
    }

    public void setBypassEnabled(boolean bypassEnabled) {
        this.bypassEnabled = bypassEnabled;
    }

    public boolean isAlertClick() {
        return this.alertClick;
    }

    public boolean isScrambleBanId() {
        return this.scrambleBanId;
    }

    public boolean isIgnoreLag() {
        return this.ignoreLag;
    }

    public void setBanCommands(List<String> banCommands) {
        this.banCommands = banCommands;
    }

    public void setConfiguration(YamlConfiguration configuration) {
        this.configuration = configuration;
    }

    public void setBungeeBans(boolean bungeeBans) {
        this.bungeeBans = bungeeBans;
    }

    public int getHeavyTicks() {
        return this.heavyTicks;
    }

    public void setUnloadedChunkFix(boolean unloadedChunkFix) {
        this.unloadedChunkFix = unloadedChunkFix;
    }

    public void setSqlHost(String sqlHost) {
        this.sqlHost = sqlHost;
    }

    public String getAlertCertainty() {
        return this.alertCertainty;
    }

    public int getPersistenceSeconds() {
        return this.persistenceSeconds;
    }

    public void setMongoAuthEnabled(boolean mongoAuthEnabled) {
        this.mongoAuthEnabled = mongoAuthEnabled;
    }

    public void setAnticheatName(String anticheatName) {
        this.anticheatName = anticheatName;
    }

    public boolean isDiscordBans() {
        return this.discordBans;
    }

    public boolean isMoreTransactions() {
        return this.moreTransactions;
    }

    public String getDiscordLogsUrl() {
        return this.discordLogsUrl;
    }

    public YamlConfiguration getBanConfiguration() {
        return this.banConfiguration;
    }

    public void setAlertCertainty(String alertCertainty) {
        this.alertCertainty = alertCertainty;
    }

    public void setScrambleBanId(boolean scrambleBanId) {
        this.scrambleBanId = scrambleBanId;
    }

    public void setPingTimeout(int pingTimeout) {
        this.pingTimeout = pingTimeout;
    }

    public void setSqlPort(int sqlPort) {
        this.sqlPort = sqlPort;
    }

    public void disable() {
    }

    public boolean isMongoEnabled() {
        return this.mongoEnabled;
    }

    public void setGeyser(boolean geyser) {
        this.geyser = geyser;
    }

    public String getAlertClickCommand() {
        return this.alertClickCommand;
    }

    public void setLogsCleanupDays(int logsCleanupDays) {
        this.logsCleanupDays = logsCleanupDays;
    }

    public String getSqlDatabaseType() {
        return this.sqlDatabaseType;
    }

    private void setupConfig() {
        VerusLauncher launcher = VerusLauncher.getPlugin();
        this.file = new File(launcher.getDataFolder(), "verus.yml");
        this.checkFile = new File(launcher.getDataFolder(), "checks.yml");
        this.loadConfig(this.configuration, this.file);
        this.loadConfig(this.checkConfiguration, this.checkFile);
        this.checkConfiguration.options().copyHeader(true).header("Please do not edit this configuration, please use /verus gui to configure checks");
        this.configuration
            .options()
            .copyDefaults(true)
            .copyHeader(true)
            .header(
                "Verus - Unauthorized distribution of this program could lead to legal prosecution\n\n-------------------------------------------------------------------------------------\nname - Display name of anti-cheat in alert messages\nsendStats - Controls whether stats are sent to our session server\n\nalerts certainty - Placeholder in alert message\nalerts message - Alert message\nalerts click enabled - Whether alerts should be clickable\nalerts click command - Command executed when alert is clicked\nalerts click hover - Message shown when alerts are hovered over\n\nbans commands - Commands executed when a player is banned\nbans message - Ban announcement message which is broadcast to the server\nbans enabled - Whether bans are enabled\nbans announce - Whether bans should be announced and broadcast to the server\nbans randomize - Generate a random Cheat-ID e.g Cheat-5A\nbans scramble - Generate a scrambled Cheat-ID e.g Cheat-J7Dq\n\nrecentlogs message - Message format of /recentlogs command\n\ngeyser enable - Whether Verus should ignore players with the geyser prefix\ngeyser prefix - The player name prefix which Verus should ignore players with\n\nbungee bans - If you have VerusLink installed on BungeeCord, you may enable this so that the ban commands are executed on BungeeCord instead of the server\nbungee secretKeyPath - Path of secret key used in VerusLink\nbungee messagingChannel - Plugin messaging channel used by VerusLink\n\npermissions bypass enabled - Whether the bypass permission (which stops a player being banned) should be enabled\npermissions bypass permission - Permission node of bypass permission\npermissions bypass alerts - Whether bypassing players should set off alerts\n\ncommands ping enabled - Whether the /ping command should be enabled and registered\ncommands ping vanish - If enabled, players will not be able to check ping for vanished players\n\nchecks persistence enabled - Whether check persistence should be enabled, this means that VLs are saved so when a player relogs or switches servers their VLs are preserved\nchecks persistence mins - How many minutes should violation data be saved for \nchecks persistence seconds - How many seconds should violation data be saved for \n\nchecks schemprinter bans - Whether checks which are flagged by schematica printer should ban\nchecks schemprinter alerts - Whether checks which are flagged by schematica printer should alert\n\nchecks butterflyclicking bans - Whether checks which can be flagged by butterfly clicking should ban\nchecks butterflyclicking alerts - Whether checks which can be flagged by butterfly clicking should alert\n\nchecks heavy enabled - Whether checks which can be more performance heavy should be enabled\nchecks heavy ticks - How often in client ticks (50ms) should the server check the player for ground spoof in Fly 4A\n\nchecks hardDisable enabled - Whether checks which have both alerts and bans disabled should not be loaded in when a player joins\n\nchecks pingKick enabled - Whether players with incredibly high ping should be kicked from the server, this prevents ping spoofers from disabling parts of the anticheat\nchecks pingKick combat - Whether BadPackets Type G7 should be enabled, so when a player attacks with impossibly high ping they are flagged\nchecks pingKick timeout - The time in seconds (1s = 1000ms) after which a player should be kicked\n\nchecks speed modifier item - Whether speed checks should check the players current item for NMS modifiers which affect player speed\nchecks speed modifier armor - Whether speed checks should check the players armor for NMS modifiers\nchecks speed slimePushFix - Whether players that have been pushed by a slime should be ignored by Speed A, if disabled falses could be caused\n\nchecks reach moreTransactions - Whether more transaction packets should be sent to the client to improve reach accuracy\nchecks reach ignoreLag - Whether players which are lagging should be ignored, allows for the disabling of some reach checks\n\nchecks fly unloadedChunkFix - Whether players which are moving as if they are in an unloaded chunk should be ignored by Fly Type I, allows for a glide bypass.\nchecks fly fixSlimeBlocks - Whether slime block velocity should be same as default, can create self damage bypasses.\n\nchecks autoclicker maxcps - The amount of cps before AutoClicker X will flag\n\ndatabase cleanup enabled - Whether the logs and bans should expire after a certain amount of time so that the database does not become too large\ndatabase cleanup logs days - After how many days should logs expire\ndatabase cleanup bans days - After how many days should bans expire\n\ndiscord logs enabled - Display logs via discord (May get limited due to the amount of logs)\ndiscord logs url - Webhook URL\ndiscord bans enabled - Display bans via discord\ndiscord bans url - Webhook URL\n-------------------------------------------------------------------------------------\n"
            );
        this.configuration.addDefault("verus.name", "&9&lVerus");
        this.configuration.addDefault("verus.sendstats", this.sendStats);
        this.configuration.addDefault("verus.alerts.certainty", "&7failed");
        this.configuration.addDefault("verus.alerts.message", "{name} &8> &f{player} &7{certainty} &f{cheat} &7VL[&9{vl}&7]");
        this.configuration.addDefault("verus.alerts.click.enabled", this.alertClick);
        this.configuration.addDefault("verus.alerts.click.command", "/tp {player}");
        this.configuration.addDefault("verus.alerts.click.hover", "&9Click to teleport to {player}.");
        this.configuration.addDefault("verus.bans.commands", new String[]{"ban {player} &9&lVerus &8> &fCheating"});
        this.configuration.addDefault("verus.bans.message", this.banMessage);
        this.configuration.addDefault("verus.bans.enabled", true);
        this.configuration.addDefault("verus.bans.announce", false);
        this.configuration.addDefault("verus.bans.randomize", this.randomBanId);
        this.configuration.addDefault("verus.bans.scramble", this.scrambleBanId);
        this.configuration.addDefault("verus.recentlogs.message", "{time} {name} &7Failed &f{type} Type {subType} &7VL: &f{vl} &7P: &f{ping}");
        this.configuration.addDefault("verus.geyser.enabled", this.geyser);
        this.configuration.addDefault("verus.geyser.prefix", this.geyserPrefix);
        if (this.configuration.contains("verus.badlionapi")) {
            this.configuration.set("verus.badlionapi", null);
        }

        this.configuration.addDefault("verus.bungee.bans", this.bungeeBans);
        this.configuration.addDefault("verus.bungee.secretKeyPath", this.secretKeyPath);
        this.configuration.addDefault("verus.bungee.messagingChannel", this.messagingChannel);
        this.configuration.addDefault("verus.permissions.bypass.enabled", this.bypassEnabled);
        this.configuration.addDefault("verus.permissions.bypass.permission", this.bypassPermission);
        this.configuration.addDefault("verus.permissions.bypass.alerts", this.bypassAlerts);
        this.configuration.addDefault("verus.commands.ping.enabled", this.pingCommand);
        this.configuration.addDefault("verus.commands.ping.vanish", this.vanishPing);
        this.configuration.addDefault("verus.checks.persistence.enabled", this.persistence);
        this.configuration.addDefault("verus.checks.persistence.mins", this.persistenceMins);
        this.configuration.addDefault("verus.checks.persistence.seconds", this.persistenceSeconds);
        this.configuration.addDefault("verus.checks.schemprinter.bans", this.schemBans);
        this.configuration.addDefault("verus.checks.schemprinter.enabled", this.schem);
        this.configuration.addDefault("verus.checks.butterflyclicking.bans", this.butterflyBans);
        this.configuration.addDefault("verus.checks.butterflyclicking.enabled", this.butterflyAlerts);
        this.configuration.addDefault("verus.checks.heavy.enabled", this.heavyChecks);
        this.configuration.addDefault("verus.checks.heavy.ticks", this.heavyTicks);
        this.configuration.addDefault("verus.checks.heavy.pullback", this.heavyPullback);
        this.configuration.addDefault("verus.checks.hardDisable.enabled", this.hardDisable);
        this.configuration.addDefault("verus.checks.pingKick.enabled", this.pingKick);
        this.configuration.addDefault("verus.checks.pingKick.combat", this.pingKickCheck);
        this.configuration.addDefault("verus.checks.pingKick.timeout", this.pingTimeout);
        if (this.configuration.contains("verus.checks.speed.itemModifier")) {
            this.configuration.set("verus.checks.speed.itemModifier", null);
        }

        if (this.configuration.contains("verus.checks.speed.modifier")) {
            this.configuration.set("verus.checks.speed.modifier", null);
        }

        this.configuration.addDefault("verus.checks.speed.slimePushFix", this.slimePushFix);
        this.configuration.addDefault("verus.checks.reach.moreTransactions", this.moreTransactions);
        this.configuration.addDefault("verus.checks.reach.ignoreLag", this.ignoreLag);
        this.configuration.addDefault("verus.checks.fly.unloadedChunkFix", this.unloadedChunkFix);
        this.configuration.addDefault("verus.checks.fly.fixSlimeBlocks", this.fixSlimeBlocks);
        this.configuration.addDefault("verus.checks.autoclicker.maxcps", this.maxClicksPerSecond);
        this.configuration.addDefault("verus.database.cleanup.enabled", this.cleanup);
        this.configuration.addDefault("verus.database.cleanup.logs.days", this.logsCleanupDays);
        this.configuration.addDefault("verus.database.cleanup.bans.days", this.bansCleanupDays);
        this.configuration.addDefault("verus.mongo.enabled", this.mongoEnabled);
        this.configuration.addDefault("verus.mongo.host", this.mongoHost);
        this.configuration.addDefault("verus.mongo.port", this.mongoPort);
        this.configuration.addDefault("verus.mongo.database", this.mongoDatabase);
        this.configuration.addDefault("verus.mongo.auth.enabled", this.mongoAuthEnabled);
        this.configuration.addDefault("verus.mongo.auth.username", "verus");
        this.configuration.addDefault("verus.mongo.auth.password", "");
        this.configuration.addDefault("verus.mongo.auth.database", this.configuration.getString("verus.mongo.database", this.mongoDatabase));
        this.configuration.addDefault("verus.sql.enabled", this.sqlEnabled);
        this.configuration.addDefault("verus.sql.host", this.sqlHost);
        this.configuration.addDefault("verus.sql.push.seconds", this.sqlPushSeconds);
        this.configuration.addDefault("verus.sql.port", this.sqlPort);
        this.configuration.addDefault("verus.sql.pool", this.sqlPool);
        this.configuration.addDefault("verus.sql.database", this.sqlDatabase);
        this.configuration.addDefault("verus.sql.type", this.sqlDatabaseType);
        this.configuration.addDefault("verus.sql.auth.username", this.sqlUsername);
        this.configuration.addDefault("verus.sql.auth.password", this.sqlPassword);
        this.configuration.addDefault("verus.discord.logs.enabled", this.discordLogs);
        this.configuration.addDefault("verus.discord.logs.url", this.discordLogsUrl);
        this.configuration.addDefault("verus.discord.bans.enabled", this.discordBans);
        this.configuration.addDefault("verus.discord.bans.url", this.discordBansUrl);
        this.saveConfig(this.configuration, this.file);
        this.saveConfig(this.checkConfiguration, this.checkFile);
    }

    public void saveConfig(YamlConfiguration yaml, File file) {
        try {
            yaml.save(file);
        } catch (Throwable throwable) {
            VerusLauncher.getPlugin().getLogger().severe("Failed to save " + file.getName() + " " + throwable.getClass().getSimpleName() + ": " + throwable.getMessage());
        }
    }

    public String getMongoHost() {
        return this.mongoHost;
    }

    public boolean isCompression() {
        return this.compression;
    }

    public boolean isVanishPing() {
        return this.vanishPing;
    }

    public void setCheckPersistence(boolean checkPersistence) {
        this.checkPersistence = checkPersistence;
    }

    public void setSqlEnabled(boolean sqlEnabled) {
        this.sqlEnabled = sqlEnabled;
    }

    public void setSchem(boolean schem) {
        this.schem = schem;
    }

    public void setMongoPort(int mongoPort) {
        this.mongoPort = mongoPort;
    }

    public void setCheckFile(File checkFile) {
        this.checkFile = checkFile;
    }

    public boolean isCheckPersistence() {
        return this.checkPersistence;
    }

    public boolean isHeavyPullback() {
        return this.heavyPullback;
    }

    public void setAlertClickCommand(String alertClickCommand) {
        this.alertClickCommand = alertClickCommand;
    }

    public void setAlertClick(boolean alertClick) {
        this.alertClick = alertClick;
    }

    public int getBansCleanupDays() {
        return this.bansCleanupDays;
    }

    public boolean isDiscordLogs() {
        return this.discordLogs;
    }

    public boolean isHeavyChecks() {
        return this.heavyChecks;
    }

    public boolean isButterflyAlerts() {
        return this.butterflyAlerts;
    }

    public boolean isSqlEnabled() {
        return this.sqlEnabled;
    }

    public String getCheckDatabaseName() {
        return this.checkDatabaseName;
    }

    public boolean isBanAnnouncement() {
        return this.banAnnouncement;
    }

    public void setMoreTransactions(boolean moreTransactions) {
        this.moreTransactions = moreTransactions;
    }

    public boolean isButterflyBans() {
        return this.butterflyBans;
    }

    public void setPingCommand(boolean pingCommand) {
        this.pingCommand = pingCommand;
    }

    public boolean isGeyser() {
        return this.geyser;
    }

    public void setPersistence(boolean persistence) {
        this.persistence = persistence;
    }

    public boolean isDatabaseCheckPersistence() {
        return this.databaseCheckPersistence;
    }

    public void setBansCleanupDays(int bansCleanupDays) {
        this.bansCleanupDays = bansCleanupDays;
    }

    public String getGeyserPrefix() {
        return this.geyserPrefix;
    }

    public int getMongoPort() {
        return this.mongoPort;
    }

    public boolean isSendStats() {
        return this.sendStats;
    }

    public String getAlertClickHover() {
        return this.alertClickHover;
    }

    public void setHardDisable(boolean hardDisable) {
        this.hardDisable = hardDisable;
    }

    public void setDiscordLogsUrl(String discordLogsUrl) {
        this.discordLogsUrl = discordLogsUrl;
    }

    public String getDiscordBansUrl() {
        return this.discordBansUrl;
    }

    public boolean isCleanup() {
        return this.cleanup;
    }

    public int getSqlPort() {
        return this.sqlPort;
    }

    public int getMaxClicksPerSecond() {
        return this.maxClicksPerSecond;
    }

    public long getPersistenceMillis() {
        return TimeUnit.MINUTES.toMillis(this.persistenceMins) + TimeUnit.SECONDS.toMillis(this.persistenceSeconds);
    }

    public void setMessagingChannel(String messagingChannel) {
        this.messagingChannel = messagingChannel;
    }

    public void setSqlUsername(String sqlUsername) {
        this.sqlUsername = sqlUsername;
    }

    public void setBanMessage(String[] banMessage) {
        this.banMessage = banMessage;
    }

    public void setDiscordLogs(boolean discordLogs) {
        this.discordLogs = discordLogs;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setPingKickCheck(boolean pingKickCheck) {
        this.pingKickCheck = pingKickCheck;
    }

    public boolean isMongoAuthEnabled() {
        return this.mongoAuthEnabled;
    }

    public void setSlimePushFix(boolean slimePushFix) {
        this.slimePushFix = slimePushFix;
    }

    public boolean isBungeeBans() {
        return this.bungeeBans;
    }

    public void setSendStats(boolean sendStats) {
        this.sendStats = sendStats;
    }

    public void setPingKick(boolean pingKick) {
        this.pingKick = pingKick;
    }

    public void setBlockLog(boolean blockLog) {
        this.blockLog = blockLog;
    }

    public void setRecentlogsMessage(String recentlogsMessage) {
        this.recentlogsMessage = recentlogsMessage;
    }

    public int getPersistenceMins() {
        return this.persistenceMins;
    }

    public void setRandomBanId(boolean randomBanId) {
        this.randomBanId = randomBanId;
    }

    public String getSqlDatabase() {
        return this.sqlDatabase;
    }

    public void setDatabaseCheckPersistence(boolean databaseCheckPersistence) {
        this.databaseCheckPersistence = databaseCheckPersistence;
    }

    public boolean isSlimePushFix() {
        return this.slimePushFix;
    }

    public boolean isPingKick() {
        return this.pingKick;
    }

    public void setDirectMemory(boolean directMemory) {
        this.directMemory = directMemory;
    }

    public int getSqlPool() {
        return this.sqlPool;
    }

    public void setButterflyAlerts(boolean butterflyAlerts) {
        this.butterflyAlerts = butterflyAlerts;
    }

    public int getPingTimeout() {
        return this.pingTimeout;
    }

    public void setCompression(boolean compression) {
        this.compression = compression;
    }

    public void setDiscordBansUrl(String discordBansUrl) {
        this.discordBansUrl = discordBansUrl;
    }

    public void setPersistenceMins(int persistenceMins) {
        this.persistenceMins = persistenceMins;
    }

    public int getSqlPushSeconds() {
        return this.sqlPushSeconds;
    }

    public String getSqlUsername() {
        return this.sqlUsername;
    }

    public String getAlertMessage() {
        return this.alertMessage;
    }

    public int getLogsCleanupDays() {
        return this.logsCleanupDays;
    }

    public void setSchemBans(boolean schemBans) {
        this.schemBans = schemBans;
    }

    public YamlConfiguration getCheckConfiguration() {
        return this.checkConfiguration;
    }

    public void setBypassPermission(String bypassPermission) {
        this.bypassPermission = bypassPermission;
    }

    public void setSqlConnectionType(ConnectionType sqlConnectionType) {
        this.sqlConnectionType = sqlConnectionType;
    }

    public <T> List<T> readList(Object obj) {
        return obj instanceof List ? (List)obj : Arrays.asList((T[])((Object[])obj));
    }

    public void loadConfig(YamlConfiguration yaml, File file) {
        try {
            yaml.load(file);
        } catch (Throwable throwable) {
            VerusLauncher.getPlugin().getLogger().severe("Failed to load " + file.getName() + " " + throwable.getClass().getSimpleName() + ": " + throwable.getMessage());
        }
    }

    public boolean isPingCommand() {
        return this.pingCommand;
    }

    public List<String> getBanCommands() {
        return this.banCommands;
    }

    public void enable() {
        this.setupConfig();
        this.readConfig();
    }

    public boolean isAnnouncement() {
        return this.announcement;
    }

    public void setDiscordBans(boolean discordBans) {
        this.discordBans = discordBans;
    }

    public void setSqlDatabase(String sqlDatabase) {
        this.sqlDatabase = sqlDatabase;
    }

    public void setAnnouncement(boolean announcement) {
        this.announcement = announcement;
    }

    public boolean isFixSlimeBlocks() {
        return this.fixSlimeBlocks;
    }

    public void setSqlDatabaseType(String sqlDatabaseType) {
        this.sqlDatabaseType = sqlDatabaseType;
    }

    public String getCheckCommandsDatabaseName() {
        return this.checkCommandsDatabaseName;
    }

    public void setAlertClickHover(String alertClickHover) {
        this.alertClickHover = alertClickHover;
    }

    public String getAnticheatName() {
        return this.anticheatName;
    }

    public void setCleanup(boolean cleanup) {
        this.cleanup = cleanup;
    }

    public void setAnnouncementMessage(String announcementMessage) {
        this.announcementMessage = announcementMessage;
    }

    public void setHeavyTicks(int heavyTicks) {
        this.heavyTicks = heavyTicks;
    }

    public boolean isPersistence() {
        return this.persistence;
    }

    public String getMessagingChannel() {
        return this.messagingChannel;
    }

    public boolean isDirectMemory() {
        return this.directMemory;
    }

    public void setVanishPing(boolean vanishPing) {
        this.vanishPing = vanishPing;
    }

    public boolean isPingKickCheck() {
        return this.pingKickCheck;
    }

    public String getAnnouncementMessage() {
        return this.announcementMessage;
    }

    public VerusConfiguration() {
        this.banConfiguration = new YamlConfiguration();
        this.checkConfiguration = new YamlConfiguration();
        this.banMessage = new String[]{"&9&lVerus &8> &f{player} &7has been removed from the network"};
        this.bypassPermission = "verus.bypass";
        this.checkDatabaseName = "checks";
        this.checkCommandsDatabaseName = "checkCommands";
        this.geyserPrefix = "*";
        this.alertClick = true;
        this.sendStats = false;
        this.autoBan = true;
        this.schemBans = true;
        this.schem = true;
        this.butterflyAlerts = true;
        this.heavyChecks = true;
        this.hardDisable = true;
        this.bypassAlerts = true;
        this.persistence = true;
        this.cleanup = true;
        this.pingKick = true;
        this.pingKickCheck = true;
        this.moreTransactions = VerusTypeLoader.isEnterprise();
        this.ignoreLag = true;
        this.unloadedChunkFix = true;
        this.randomBanId = !VerusTypeLoader.isEnterprise();
        this.secretKeyPath = "plugins" + File.separator + "Verus" + File.separator + "secretKey";
        this.messagingChannel = NMSManager.getInstance().getServerVersion().afterEq(ServerVersion.v1_13_R2) ? "bungeecord:main" : "BungeeCord";
        this.persistenceMins = 60;
        this.logsCleanupDays = 90;
        this.bansCleanupDays = 730;
        this.pingTimeout = 20;
        this.heavyTicks = 5;
        this.maxClicksPerSecond = 20;
        this.mongoHost = "localhost";
        this.mongoDatabase = "Verus";
        this.mongoPort = 27017;
        this.sqlUsername = "verus";
        this.sqlPassword = "";
        this.sqlHost = "localhost";
        this.sqlDatabase = "Verus";
        this.sqlDatabaseType = ConnectionType.MYSQL.name();
        this.sqlPushSeconds = 10;
        this.sqlPort = 3306;
        this.sqlPool = 4;
        this.discordLogsUrl = "";
        this.discordBansUrl = "";
    }

    public boolean isBypassAlerts() {
        return this.bypassAlerts;
    }

    public void saveConfig() {
        this.saveConfig(this.configuration, this.file);
    }

    public void setPersistenceSeconds(int persistenceSeconds) {
        this.persistenceSeconds = persistenceSeconds;
    }

    public File getCheckFile() {
        return this.checkFile;
    }

    public boolean isSchemBans() {
        return this.schemBans;
    }

    public void setBanAnnouncement(boolean banAnnouncement) {
        this.banAnnouncement = banAnnouncement;
    }

    public MongoCredential getMongoCredential() {
        return this.mongoCredential;
    }

    public void setMongoEnabled(boolean mongoEnabled) {
        this.mongoEnabled = mongoEnabled;
    }

    public String getMongoDatabase() {
        return this.mongoDatabase;
    }

    public String[] getBanMessage() {
        return this.banMessage;
    }

    public File getFile() {
        return this.file;
    }

    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }

    public boolean isBypassEnabled() {
        return this.bypassEnabled;
    }

    public String getSqlHost() {
        return this.sqlHost;
    }

    public boolean isSchem() {
        return this.schem;
    }

    public void setIgnoreLag(boolean ignoreLag) {
        this.ignoreLag = ignoreLag;
    }

    public void setBypassAlerts(boolean bypassAlerts) {
        this.bypassAlerts = bypassAlerts;
    }

    public YamlConfiguration getConfiguration() {
        return this.configuration;
    }

    public void setCheckConfiguration(YamlConfiguration checkConfiguration) {
        this.checkConfiguration = checkConfiguration;
    }

    public void setSqlPool(int sqlPool) {
        this.sqlPool = sqlPool;
    }

    public void setSecretKeyPath(String secretKeyPath) {
        this.secretKeyPath = secretKeyPath;
    }

    public void setAutoBan(boolean autoBan) {
        this.autoBan = autoBan;
    }
}
