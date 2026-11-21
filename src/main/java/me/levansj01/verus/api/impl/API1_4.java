package me.levansj01.verus.api.impl;

import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.api.manager.APIManager1_4;
import me.levansj01.verus.api.manager.VerusManager;
import me.levansj01.verus.api.manager.service.FakeBlockService;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

public class API1_4 extends API1_3 {
    private final FakeBlockService original = new FakeBlockService() {};
    private FakeBlockService fakeBlockService = this.original;

    @Override
    public MaterialData getFakeBlock(Player player, World world, int value, int value2, int value3) {
        return this.fakeBlockService.getFakeBlock(player, world, value, value2, value3);
    }

    @Override
    public VerusManager create(VerusPlugin plugin) {
        return new APIManager1_4(plugin, this);
    }

    public void setFakeBlockService(FakeBlockService fakeBlockService) {
        if (fakeBlockService == null) {
            fakeBlockService = this.original;
        }

        this.fakeBlockService = fakeBlockService;
    }
}
