package me.levansj01.verus.api.impl;

import java.lang.reflect.Field;
import java.util.List;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.api.events.PunishEvent;
import me.levansj01.verus.api.events.VerusShutdownEvent;
import me.levansj01.verus.api.events.VerusStartEvent;
import me.levansj01.verus.api.manager.APIManager1_3;
import me.levansj01.verus.api.manager.VerusManager;
import me.levansj01.verus.api.wrapper.BanResult;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.util.java.SafeReflection;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

public class API1_3 extends API1_2_1 {
    private static final Field Event_async = SafeReflection.access(Event.class, "async");

    @Override
    public void enable(VerusPlugin plugin) {
        super.enable(plugin);
        Bukkit.getPluginManager().callEvent(new VerusStartEvent());
    }

    @Override
    public void disable() {
        Bukkit.getPluginManager().callEvent(new VerusShutdownEvent());
        super.disable();
    }

    @Override
    public VerusManager create(VerusPlugin plugin) {
        return new APIManager1_3(plugin);
    }

    @Override
    public BanResult fireBanEvent(PlayerData playerData, Check check, boolean flag, List<String> list) {
        PunishEvent event = new PunishEvent(playerData.getPlayer(), this.toAPI(check), flag, list);
        SafeReflection.set(Event_async, event, true);
        Bukkit.getPluginManager().callEvent(event);
        return event.isCancelled() ? BanResult.CANCEL : (event.isAnnounce() ? BanResult.ANNOUNCE : BanResult.SILENT);
    }
}
