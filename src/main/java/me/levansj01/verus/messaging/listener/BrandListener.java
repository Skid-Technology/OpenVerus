package me.levansj01.verus.messaging.listener;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.data.manager.DataManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class BrandListener implements PluginMessageListener {
    private static final Map<UUID, String> CACHE = NMSManager.getInstance().createCache(TimeUnit.SECONDS.toMillis(10L), null);
    private final String expectedChannel;

    public String getBrand(UUID uuid) {
        return CACHE.remove(uuid);
    }

    @Override
    public void onPluginMessageReceived(String str, Player player, byte[] bytes) {
        if (str.equals(this.expectedChannel) && bytes != null && bytes.length > 0) {
            UUID uniqueId = player.getUniqueId();
            PlayerData player2 = DataManager.getInstance().getPlayer(uniqueId);
            String str2 = new String(bytes, StandardCharsets.UTF_8);
            if (player2 == null) {
                CACHE.put(uniqueId, str2);
                return;
            }

            player2.getCheckData().getBrandCheck().handle(str2);
        }
    }

    public BrandListener() {
        if (NMSManager.getInstance().getServerVersion().afterEq(ServerVersion.v1_13_R2)) {
            this.expectedChannel = "minecraft:brand";
        } else {
            this.expectedChannel = "MC|Brand";
        }
    }
}
