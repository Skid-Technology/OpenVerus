package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInUseEntity;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.verus2.data.player.TickerMap;
import me.levansj01.verus.verus2.data.player.TickerType;

@CheckInfo(
    friendlyName = "Ping Spoof",
    type = CheckType.BAD_PACKETS,
    subType = "G7",
    version = CheckVersion.RELEASE,
    maxViolations = 5,
    minViolations = -2.0,
    unsupportedAtleast = ClientVersion.V1_9,
    unsupportedServerAtleast = ServerVersion.v1_17_R1
)
public class BadPacketsG7 extends Check implements PacketHandler {
    private static final int TIME = 20 * StorageEngine.getInstance().getVerusConfig().getPingTimeout();

    @Override
    public void handle(VPacketPlayInUseEntity<?> packet) {
        if (StorageEngine.getInstance().getVerusConfig().isPingKickCheck()) {
            TickerMap tickerMap = this.playerData.getTickerMap();
            int value = tickerMap.get(TickerType.TOTAL);
            int value2 = value - tickerMap.get(TickerType.LAST_RECEIVED_TRANSACTION);
            int value3 = value - tickerMap.get(TickerType.LAST_SENT_TRANSACTION);
            if (value > TIME && value2 >= TIME && value3 < 10 && packet.isPlayer()) {
                this.handleViolation(() -> String.format("S: %s D: %s", value3, value2));
            } else {
                this.decreaseVL(0.01);
            }
        }
    }
}
