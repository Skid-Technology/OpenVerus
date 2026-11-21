package me.levansj01.verus.check.checks.timer;

import java.util.concurrent.TimeUnit;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(
    type = CheckType.TIMER,
    subType = "A",
    friendlyName = "Timer",
    version = CheckVersion.RELEASE,
    minViolations = -1.0,
    maxViolations = 60,
    logData = true,
    unsupportedAtleast = ClientVersion.V1_9
)
public class TimerA extends Check implements PacketHandler {
    private static final long CHECK_TIME_LINEAR = toNanos(45L);
    private long offset;
    private boolean place;
    private long lastFlag;
    private long lastBow;
    private Long lastPacket = null;

    @Override
    public void handle(VPacketPlayInBlockPlace<?> packet) {
        if (packet.isItem()) {
            this.place = true;
        }
    }

    public static long fromNanos(long value) {
        return TimeUnit.NANOSECONDS.toMillis(value);
    }

    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        long nanos = this.playerData.getNanos();
        if (this.place) {
            this.lastBow = nanos;
            this.place = false;
        }

        if (this.lastPacket != null) {
            long value = nanos - this.lastPacket;
            long value2 = toNanos(50L) - value;
            this.offset += value2;
            if (this.offset > CHECK_TIME_LINEAR) {
                boolean flag = fromNanos(nanos - this.lastBow) < 50L;
                if ((fromNanos(nanos - this.lastFlag) > 1L || flag)
                    && this.playerData.shouldHaveReceivedPing()
                    && this.playerData.getTotalTicks() > 400
                    && !this.playerData.isTeleporting(3)
                    && !this.playerData.isTeleportingV2()
                    && this.playerData.isSpawned()
                    && !this.playerData.isVehicle()) {
                    long lastTeleport = this.playerData.getLastTeleport();
                    boolean flag2 = this.playerData.isMoved() || flag;
                    double value3 = flag2 ? 1.0 : 0.3;
                    long timestamp = this.playerData.getTimestamp();
                    this.handleViolation(
                        () -> String.format(
                            "O: %sms F: %sms B: %sms T: %sms M: %s",
                            fromNanos(this.offset),
                            fromNanos(nanos - this.lastFlag),
                            fromNanos(nanos - this.lastBow),
                            lastTeleport == 0L ? -1L : timestamp - lastTeleport,
                            flag2
                        ),
                        value3
                    );
                }

                this.lastFlag = nanos;
                this.offset = 0L;
            } else {
                this.decreaseVL(0.0025);
            }
        }

        this.lastPacket = nanos;
    }

    public static long toNanos(long value) {
        return TimeUnit.MILLISECONDS.toNanos(value);
    }

    public TimerA() {
        this.offset = -100L;
    }
}
