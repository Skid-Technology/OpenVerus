package me.levansj01.verus.api.impl;

import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.api.API;
import me.levansj01.verus.api.VerusAPI;
import me.levansj01.verus.api.events.BanEvent;
import me.levansj01.verus.api.events.ViolationEvent;
import me.levansj01.verus.api.manager.APIManager1_2;
import me.levansj01.verus.api.manager.VerusManager;
import me.levansj01.verus.api.manager.impl.BlankManager;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.data.PlayerData;
import org.bukkit.Bukkit;

public class API1_2 extends API {
    @Override
    public void enable(VerusPlugin plugin) {
        VerusAPI verusAPI = (VerusAPI)fetchPlugin();
        verusAPI.setVerusManager(this.create(plugin));
    }

    @Override
    public boolean fireViolationEvent(PlayerData playerData, Check check, int value) {
        ViolationEvent event = new ViolationEvent(playerData.getPlayer(), this.toAPI(check), value);
        Bukkit.getPluginManager().callEvent(event);
        return event.isCancelled();
    }

    protected me.levansj01.verus.api.check.Check toAPI(Check check) {
        return new me.levansj01.verus.api.check.Check(check.getType().getName(), check.getSubType(), check.getFriendlyName());
    }

    @Override
    public boolean fireBanEvent(PlayerData playerData, Check check) {
        BanEvent event = new BanEvent(playerData.getPlayer(), this.toAPI(check));
        Bukkit.getPluginManager().callEvent(event);
        return event.isCancelled();
    }

    @Override
    public void disable() {
        VerusAPI verusAPI = (VerusAPI)fetchPlugin();
        verusAPI.setVerusManager(new BlankManager());
    }

    public VerusManager create(VerusPlugin plugin) {
        return new APIManager1_2(plugin);
    }
}
