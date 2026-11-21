package me.levansj01.verus.api.impl;

import me.levansj01.verus.api.events.PlayerInitEvent;
import me.levansj01.verus.data.PlayerData;
import org.bukkit.Bukkit;

public class API1_2_1 extends API1_2 {
    @Override
    public boolean fireInitEvent(PlayerData playerData) {
        PlayerInitEvent event = new PlayerInitEvent(playerData.getPlayer());
        Bukkit.getPluginManager().callEvent(event);
        return event.isCancelled();
    }
}
