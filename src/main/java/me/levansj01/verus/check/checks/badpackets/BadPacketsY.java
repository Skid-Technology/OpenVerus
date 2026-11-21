package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.api.Transactionable;
import me.levansj01.verus.compat.packets.VPacketPlayInKeepAlive;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(
    type = CheckType.BAD_PACKETS,
    subType = "Y",
    friendlyName = "Blink",
    version = CheckVersion.RELEASE,
    logData = true,
    minViolations = -2.5,
    maxViolations = 15
)
public class BadPacketsY extends Check implements PacketHandler {
    private Long lastKeepAlive = 0L;
    private Long lastTickDelay;

    @Override
    public void handle(VPacketPlayInKeepAlive<?> packet) {
        this.lastKeepAlive = this.playerData.getTimestamp();
    }

    @Override
    public void handleIn(Transactionable transactionable) {
        if (this.playerData.shouldHaveReceivedPing()
            && !this.player.isDead()
            && !this.playerData.isVehicle()
            && this.playerData.isSurvival()
            && this.playerData.getTotalTicks() > 800) {
            long value = Math.max(50, Math.max(this.playerData.getTransactionPing(), this.playerData.getPing()));
            long diff = this.playerData.getTimestamp() - this.playerData.getLastFlying();
            if (this.lastTickDelay != null) {
                long value2 = diff - this.lastTickDelay;
                long diff2 = this.playerData.getTimestamp() - this.lastKeepAlive;
                long value3 = (this.playerData.getVersion().after(ClientVersion.V1_8) ? 2000 : 500) + value * 2L;
                if (diff > value3 && value2 > Math.max(250L, value) && value2 < 500L && diff2 < 1000L + value * 2L) {
                    this.handleViolation(() -> String.format("T: %s D: %s L: %s O: %s K: %s", diff, value2, value3, Math.abs(diff - value3), diff2), 0.5);
                } else {
                    this.decreaseVL(0.05);
                }
            }

            this.lastTickDelay = diff;
        }
    }
}
