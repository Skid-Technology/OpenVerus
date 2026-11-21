package me.levansj01.launcher;

import me.levansj01.verus.VerusPlugin;
import org.bukkit.plugin.java.JavaPlugin;

public class VerusLauncher extends JavaPlugin {
    private static VerusLauncher plugin;
    private VerusLaunch launch;

    public static VerusLauncher getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        this.launch = new VerusPlugin();
        this.launch.launch(this);
    }

    @Override
    public void onDisable() {
        if (this.launch != null) {
            this.launch.shutdown();
            this.launch = null;
        }
        plugin = null;
    }
}
