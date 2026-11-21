package me.levansj01.verus.type.custom.checks.speed;

import me.levansj01.verus.check.MovementCheck2;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.util.java.DoubleWrapper;
import me.levansj01.verus.util.java.MathUtil;
import me.levansj01.verus.util.location.PacketLocation;

@CheckInfo(
    type = CheckType.SPEED,
    subType = "F",
    friendlyName = "NoSlowdown",
    version = CheckVersion.DEVELOPMENT,
    unsupportedAtleast = ClientVersion.V1_9,
    unsupportedVersions = ClientVersion.V1_7,
    minViolations = -2.5
)
public class SpeedF extends MovementCheck2 {
    private int threshold;
    private Double lastAngleDiff = null;
    private static final double MAX = 0.0437;

    public SpeedF() {
        this.threshold = 0;
    }

    @Override
    public void handle(PacketLocation location, PacketLocation location2, long value) {
        if (!this.playerData.isTeleporting(5)
            && !this.playerData.isTeleportingV2()
            && this.playerData.getVelocityTicks() > this.playerData.getMaxPingTicks() * 2
            && !this.playerData.isVehicle()
            && this.playerData.isBlocking()) {
            double movementSpeed = this.playerData.getMovementSpeed();
            double value2 = movementSpeed / 0.2;
            if (value2 > 4.0) {
                return;
            }

            double value3 = Math.toDegrees(-Math.atan2(location2.getX() - location.getX(), location2.getZ() - location.getZ()));
            double distanceBetweenAngles360 = MathUtil.getDistanceBetweenAngles360((float)value3, location2.getYaw());
            double distance = MathUtil.hypot(location2.getX() - location.getX(), location2.getZ() - location.getZ());
            DoubleWrapper doubleWrapper = new DoubleWrapper(0.0437);
            doubleWrapper.addAndGet(Math.max(((Integer)this.playerData.getSpeedLevel().get()).intValue() * 0.012, 0.0));
            if (movementSpeed > 0.2) {
                doubleWrapper.addAndGet(doubleWrapper.get() * value2);
            }

            if (this.lastAngleDiff != null && location2.isGround()) {
                if (this.lastAngleDiff > 0.5) {
                    doubleWrapper.addAndGet(0.03);
                }

                if (distance > doubleWrapper.get()) {
                    if (this.threshold > (this.playerData.getMaxPingTicks() + 2) * 2) {
                        this.handleViolation(() -> String.format("D: %.4f A: %s", distance - doubleWrapper.get(), this.lastAngleDiff));
                        this.threshold = 0;
                    }
                } else {
                    this.threshold = 0;
                    this.decreaseVL(0.05);
                }
            }

            this.lastAngleDiff = distanceBetweenAngles360;
        } else {
            this.threshold = 0;
            this.decreaseVL(0.001);
            this.lastAngleDiff = null;
        }
    }
}
