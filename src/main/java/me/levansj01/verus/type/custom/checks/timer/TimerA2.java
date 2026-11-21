package me.levansj01.verus.type.custom.checks.timer;

import java.util.concurrent.TimeUnit;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.handler.TeleportCheck;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayOutPosition;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(
    type = CheckType.TIMER,
    subType = "A2",
    friendlyName = "Timer",
    version = CheckVersion.RELEASE,
    minViolations = -1.0,
    maxViolations = 50,
    unsupportedAtleast = ClientVersion.V1_9
)
public class TimerA2 extends Check implements PacketHandler, TeleportCheck {
    private static final long CHECK_TIME_LINEAR = toNanos(45L);
    private long lastFlag;
    private long offset;
    private Long lastPacket = null;

    public static long toNanos(long value) {
        return TimeUnit.MILLISECONDS.toNanos(value);
    }

    public TimerA2() {
        this.offset = -100L;
    }

    @Override
    public void handle(VPacketPlayOutPosition packet, long value) {
        this.offset = this.offset - toNanos(50L);
    }

    public static long fromNanos(long value) {
        return TimeUnit.NANOSECONDS.toMillis(value);
    }

    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        long nanos = this.playerData.getNanos();
        if (this.lastPacket != null) {
            long value = nanos - this.lastPacket;
            long value2 = toNanos(50L) - value;
            this.offset += value2;
            if (this.offset > CHECK_TIME_LINEAR) {
                if (this.playerData.shouldHaveReceivedPing()
                    && this.playerData.getTotalTicks() > 300
                    && this.playerData.isSpawned()
                    && !this.playerData.isVehicle()) {
                    boolean moved = this.playerData.isMoved();
                    boolean flag = fromNanos(nanos - this.lastFlag) <= 1L;
                    double value3 = moved ? 1.0 : 0.1;
                    if (flag) {
                        value3 /= 2.0;
                    }

                    this.handleViolation(() -> String.format("O: %sms M: %s F: %s", fromNanos(this.offset), flag, moved), value3);
                    this.lastFlag = nanos;
                }

                this.offset = 0L;
            } else {
                this.decreaseVL(0.0025);
            }
        }

        this.lastPacket = nanos;
    }
}
