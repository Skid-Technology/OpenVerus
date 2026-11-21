package me.levansj01.verus.api.manager;

import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.api.impl.API1_4;
import me.levansj01.verus.api.manager.service.FakeBlockService;
import me.levansj01.verus.api.version.ClientVersion;
import org.bukkit.entity.Player;

public class APIManager1_4 extends APIManager1_3 {
    private final API1_4 api;

    public void updateVersion(Player player, ClientVersion clientVersion) throws DataNotFoundException {
        this.getData(player).updateVersion(clientVersion.ordinal());
    }

    public APIManager1_4(VerusPlugin plugin, API1_4 api) {
        super(plugin);
        this.api = api;
    }

    public void setFakeBlockService(FakeBlockService fakeBlockService) {
        this.api.setFakeBlockService(fakeBlockService);
    }
}
