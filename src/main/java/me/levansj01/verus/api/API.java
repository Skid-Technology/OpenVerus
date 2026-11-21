package me.levansj01.verus.api;

import java.util.List;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.api.wrapper.BanResult;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;

public abstract class API {
    private static Boolean enabled = null;
    private static API api = null;
    private String version = "Unknown";

    public String getVersion() {
        return this.version;
    }

    @Deprecated
    protected boolean fireBanEvent(PlayerData playerData, Check check) {
        return false;
    }

    public static void check() {
        enabled = null;
    }

    public boolean fireInitEvent(PlayerData playerData) {
        return false;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void disable() {
    }

    public void enable(VerusPlugin plugin) {
    }

    protected static boolean isEnabled() {
        if (enabled == null) {
            enabled = Bukkit.getPluginManager().isPluginEnabled("VerusAPI");
        }

        return enabled;
    }

    public BanResult fireBanEvent(PlayerData playerData, Check check, boolean flag, List<String> list) {
        return this.fireBanEvent(playerData, check) ? BanResult.CANCEL : (flag ? BanResult.ANNOUNCE : BanResult.SILENT);
    }

    public static API getAPI() {
        if (isEnabled()) {
            if (api == null) {
                Plugin plugin = fetchPlugin();
                String version = plugin.getDescription().getVersion();

                try {
                    api = Class.forName("me.levansj01.verus.api.impl.API" + version.replace(".", "_")).asSubclass(API.class).newInstance();
                    api.setVersion(version);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                    enabled = false;
                }
            }

            return api;
        } else {
            return null;
        }
    }

    public static Plugin fetchPlugin() {
        return Bukkit.getPluginManager().getPlugin("VerusAPI");
    }

    public MaterialData getFakeBlock(Player player, World world, int value, int value2, int value3) {
        return null;
    }

    public abstract boolean fireViolationEvent(PlayerData playerData, Check check, int value);
}
