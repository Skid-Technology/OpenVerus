package me.levansj01.verus.check.checks.timer;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.util.java.BasicDeque;
import me.levansj01.verus.util.java.CappedQueue;
import me.levansj01.verus.util.java.MathUtil;

@CheckInfo(
    type = CheckType.TIMER,
    subType = "B",
    friendlyName = "Timer",
    version = CheckVersion.RELEASE,
    logData = true,
    maxViolations = 40,
    minViolations = -3.0
)
public class TimerB extends Check implements PacketHandler {
    private Long lastLook;
    private final BasicDeque<Long> delays = new CappedQueue(40);
    private Long lastPacket;

    @Override
    public void handle(VPacketPlayInBlockPlace<?> packet) {
        if ((packet.isUse() || packet.isEmpty())
            && this.lastLook != null
            && this.playerData.getTimestamp() - this.lastLook < 5L
            && this.playerData.getVersion().afterEq(ClientVersion.V1_17)) {
            this.delays.addFirst(250L);
        }
    }

    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        long timestamp = this.playerData.getTimestamp();
        if (this.playerData.shouldHaveReceivedPing()
            && this.playerData.getTotalTicks() > 200
            && !this.playerData.isTeleporting(3)
            && !this.playerData.isTeleportingV2()
            && this.playerData.isSpawned()
            && !this.playerData.isVehicle()
            && !this.playerData.hasLag()
            && packet.isPos()
            && (
                this.playerData.getVersion().after(ClientVersion.V1_8) && this.playerData.getVersion() != ClientVersion.VERSION_UNSUPPORTED
                    || this.playerData.isMoved()
            )) {
            if (this.lastPacket != null) {
                this.delays.addFirst(timestamp - this.lastPacket);
                if (this.delays.size() == 40) {
                    double value = MathUtil.average(this.delays);
                    double value2 = 50.0 / value;
                    if (value <= 48.0) {
                        this.handleViolation(String.format("T: %.1f M: %.2f", value, value2), Math.min(Math.floor(value2 / 0.6), 2.0));
                    } else {
                        this.decreaseVL(0.25);
                    }

                    this.delays.clear();
                }
            }

            this.lastPacket = timestamp;
        }

        if (packet.isLook()) {
            this.lastLook = timestamp;
        }
    }
}
