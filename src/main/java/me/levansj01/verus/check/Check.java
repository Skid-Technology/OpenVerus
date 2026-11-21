package me.levansj01.verus.check;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import me.levansj01.verus.alert.manager.AlertManager;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.manager.CheckManager;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.storage.config.VerusConfiguration;
import org.bukkit.entity.Player;

public class Check {
    private CheckVersion checkVersion;
    private boolean schem;
    protected PlayerData playerData;
    private List<ServerVersion> unsupportedServers;
    protected double violations;
    private List<ClientVersion> unsupportedVersions;
    private boolean blocks;
    private int lastViolation;
    private String friendlyName;
    private int priority;
    protected double minViolation;
    private boolean pullback;
    private String subType;
    private boolean heavy;
    private CheckType type;
    protected Player player;
    private boolean logData;
    private int maxViolation = Integer.MAX_VALUE;

    @Deprecated
    public boolean valid() {
        return this.type != null && this.subType != null && this.friendlyName != null && this.checkVersion != null && this.unsupportedVersions != null;
    }

    public void setType(CheckType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof Check)) {
            return false;
        } else {
            Check check = (Check)obj;
            if (!check.canEqual(this)) {
                return false;
            } else {
                CheckType type = this.getType();
                CheckType type2 = check.getType();
                if (type == null ? type2 == null : type.equals(type2)) {
                    String subType = this.getSubType();
                    String subType2 = check.getSubType();
                    return subType == null ? subType2 == null : subType.equals(subType2);
                } else {
                    return false;
                }
            }
        }
    }

    public CheckVersion getCheckVersion() {
        return this.checkVersion;
    }

    public void run(Runnable runnable) {
        NMSManager.getInstance().postToMainThread(() -> {
            if (this.playerData.isEnabled()) {
                runnable.run();
            }
        });
    }

    public void debug(String data) {
        this.debug(() -> data);
    }

    public void handleBan(boolean flag) {
        AlertManager.getInstance().handleBan(this.playerData, this, flag);
    }

    @Deprecated
    public Check(CheckType type, String subType, String friendlyName, CheckVersion checkVersion, ClientVersion... clientVersions) {
        this.priority = 1;
        this.unsupportedServers = Collections.emptyList();
        this.type = type;
        this.subType = subType;
        this.friendlyName = friendlyName;
        this.checkVersion = checkVersion;
        this.unsupportedVersions = Arrays.asList(clientVersions);
    }

    public String getSubType() {
        return this.subType;
    }

    public String getFriendlyName() {
        return this.friendlyName;
    }

    public boolean isLogData() {
        return this.logData;
    }

    private static <T extends Enum<T>> T[] maxOf(T t, T t2, T[] ts, Class<T> clazz) {
        if (t == t2) {
            return (T[])ts;
        }

        Enum[] enumConstants = (Enum[])clazz.getEnumConstants();
        Enum[] instance = (Enum[])Array.newInstance(clazz, enumConstants.length - t.ordinal() + ts.length);
        System.arraycopy(enumConstants, t.ordinal(), instance, 0, instance.length - ts.length);
        if (ts.length > 0) {
            System.arraycopy(ts, 0, instance, instance.length - ts.length, ts.length);
        }

        return (T[])instance;
    }

    public Player getPlayer() {
        return this.player;
    }

    protected boolean canEqual(Object obj) {
        return obj instanceof Check;
    }

    public void setLastViolation(int lastViolation) {
        this.lastViolation = lastViolation;
    }

    public void handleViolation(Supplier<String> data, double vl, boolean ban) {
        AlertManager.getInstance().handleViolation(this.playerData, this, data, vl, ban);
    }

    public String name() {
        return this.type.getName() + " " + this.subType;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public void handleViolation() {
        this.handleViolation("");
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void handleBan() {
        this.handleBan(false);
    }

    public void setUnsupportedVersions(List<ClientVersion> unsupportedVersions) {
        this.unsupportedVersions = unsupportedVersions;
    }

    @Override
    public int hashCode() {
        byte b = 59;
        int value = 1;
        CheckType type = this.getType();
        value = value * 59 + (type == null ? 43 : type.hashCode());
        String subType = this.getSubType();
        return value * 59 + (subType == null ? 43 : subType.hashCode());
    }

    public String identifier() {
        return this.type.ordinal() + "" + this.subType;
    }

    public void setMinViolation(double violations) {
        this.minViolation = this.violations = violations;
    }

    public boolean isBlocks() {
        return this.blocks;
    }

    public int getMaxViolation() {
        return this.maxViolation;
    }

    public void handleViolation(String data) {
        this.handleViolation(data, 1.0);
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setMaxViolation(int maxViolation) {
        this.maxViolation = maxViolation;
    }

    public List<ClientVersion> getUnsupportedVersions() {
        return this.unsupportedVersions;
    }

    public CheckType getType() {
        return this.type;
    }

    public void setPlayerData(PlayerData playerData) {
        this.playerData = playerData;
    }

    public void handleViolation(Supplier<String> data, double vl) {
        this.handleViolation(data, vl, false);
    }

    public void decreaseVL(double amount) {
        this.violations = this.violations - Math.min(this.violations - this.minViolation, amount);
    }

    public List<ServerVersion> getUnsupportedServers() {
        return this.unsupportedServers;
    }

    public void handleViolation(Supplier<String> data) {
        this.handleViolation(data, 1.0);
    }

    public PlayerData getPlayerData() {
        return this.playerData;
    }

    public void setLogData(boolean logData) {
        this.logData = logData;
    }

    public void setPullback(boolean pullback) {
        this.pullback = pullback;
    }

    public void handleViolation(String data, double vl) {
        this.handleViolation(data, vl, false);
    }

    public void setUnsupportedServers(ServerVersion... unsupportedServers) {
        this.unsupportedServers = Arrays.asList(unsupportedServers);
    }

    public double getMinViolation() {
        return this.minViolation;
    }

    public Check() {
        this.priority = 1;
        this.unsupportedServers = Collections.emptyList();
        if (this.getClass().isAnnotationPresent(CheckInfo.class)) {
            CheckInfo annotation = this.getClass().getAnnotation(CheckInfo.class);
            this.setType(annotation.type());
            this.setSubType(annotation.subType());
            this.setFriendlyName(annotation.friendlyName());
            if (this.friendlyName.isEmpty()) {
                this.setFriendlyName(this.type.getName());
            }

            this.setCheckVersion(annotation.version());
            ClientVersion clientVersion = annotation.unsupportedAtleast();
            ClientVersion[] clientVersions = annotation.unsupportedVersions();
            this.setUnsupportedVersions(Arrays.asList(maxOf(clientVersion, ClientVersion.NONE, clientVersions, ClientVersion.class)));
            ServerVersion serverVersion = annotation.unsupportedServerAtleast();
            ServerVersion[] serverVersions = annotation.unsupportedServers();
            this.setUnsupportedServers(maxOf(serverVersion, ServerVersion.NONE, serverVersions, ServerVersion.class));
            this.setMinViolation(annotation.minViolations());
            this.setPriority(annotation.priority());
            this.setHeavy(annotation.heavy());
            this.setSchem(annotation.schem());
            this.setBlocks(annotation.blocks());
            if (!annotation.schem() && !annotation.butterfly()) {
                this.setMaxViolation(annotation.maxViolations());
            } else {
                VerusConfiguration verusConfig = StorageEngine.getInstance().getVerusConfig();
                if (annotation.schem() && verusConfig.isSchemBans()) {
                    this.setMaxViolation(annotation.maxViolations());
                }

                if (annotation.butterfly() && verusConfig.isButterflyBans()) {
                    this.setMaxViolation(annotation.maxViolations());
                }
            }

            this.setLogData(annotation.logData());
        }
    }

    public boolean supported() {
        VerusConfiguration verusConfig = StorageEngine.getInstance().getVerusConfig();
        return !this.unsupportedVersions.contains(this.playerData.getVersion())
            && (this.playerData.getVersion() != ClientVersion.VERSION_UNSUPPORTED || this.unsupportedVersions.isEmpty())
            && (!this.heavy || verusConfig.isHeavyChecks())
            && (!this.schem || verusConfig.isSchem())
            && (!this.blocks || verusConfig.isBlockLog())
            && (!verusConfig.isHardDisable() || !CheckManager.getInstance().isDisabled(this));
    }

    public boolean isPullback() {
        return this.pullback;
    }

    public int getLastViolation() {
        return this.lastViolation;
    }

    public void debug(Supplier<String> data) {
        this.playerData.debug(data);
    }

    public double getViolations() {
        return this.violations;
    }

    public void handleViolation(String data, double vl, boolean ban) {
        this.handleViolation(() -> data, vl, ban);
    }

    public void setViolations(double violations) {
        this.violations = violations;
    }

    public boolean isHeavy() {
        return this.heavy;
    }

    public void setHeavy(boolean heavy) {
        this.heavy = heavy;
    }

    public void setCheckVersion(CheckVersion checkVersion) {
        this.checkVersion = checkVersion;
    }

    public void setBlocks(boolean blocks) {
        this.blocks = blocks;
    }

    public boolean isSchem() {
        return this.schem;
    }

    public void setSchem(boolean schem) {
        this.schem = schem;
    }

    public int getPriority() {
        return this.priority;
    }
}
