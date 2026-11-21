package me.levansj01.verus.api.impl;

import me.levansj01.verus.api.API;
import me.levansj01.verus.api.events.BanEvent;
import me.levansj01.verus.api.events.ViolationEvent;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.data.PlayerData;
import org.bukkit.Bukkit;

public class API1_1 extends API {
    @Override
    public boolean fireViolationEvent(PlayerData playerData, Check check, int value) {
        ViolationEvent event = new ViolationEvent(playerData.getPlayer(), check.getType().getName(), check.getSubType(), value);
        Bukkit.getPluginManager().callEvent(event);
        return event.isCancelled();
    }

    @Override
    public boolean fireBanEvent(PlayerData playerData, Check check) {
        BanEvent event = new BanEvent(playerData.getPlayer(), check.getType().getName(), check.getSubType());
        Bukkit.getPluginManager().callEvent(event);
        return event.isCancelled();
    }
}
