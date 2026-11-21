package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInKeepAlive;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.util.java.MathUtil;

@CheckInfo(
    type = CheckType.BAD_PACKETS,
    subType = "G3",
    friendlyName = "Ping Spoof",
    minViolations = -3.0,
    maxViolations = 30,
    version = CheckVersion.RELEASE,
    logData = true
)
public class BadPacketsG3 extends Check implements PacketHandler {
    private boolean receivedKeepAlive;

    @Override
    public void handle(VPacketPlayInKeepAlive<?> packet) {
        this.receivedKeepAlive = true;
    }

    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        if (this.receivedKeepAlive) {
            double value = MathUtil.highest(
                this.playerData.getLastTransactionPing(), this.playerData.getTransactionPing(), this.playerData.getAverageTransactionPing()
            );
            double value2 = MathUtil.lowest(this.playerData.getLastPing(), this.playerData.getPing(), this.playerData.getAveragePing());
            if (value2 - value > 50.0 + value / 4.0) {
                this.handleViolation(
                    () -> String.format("K(%s) T(%s) %s", value2, value, packet.isPos()),
                    StorageEngine.getInstance().getVerusConfig().isMoreTransactions() ? 0.25 : 1.0
                );
            } else {
                this.decreaseVL(StorageEngine.getInstance().getVerusConfig().isMoreTransactions() ? 0.3 : 0.1);
            }

            this.receivedKeepAlive = false;
        }
    }
}
