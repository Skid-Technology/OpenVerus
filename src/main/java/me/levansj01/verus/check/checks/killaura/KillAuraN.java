package me.levansj01.verus.check.checks.killaura;

import me.levansj01.verus.check.MovementCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.util.java.MathUtil;
import me.levansj01.verus.util.location.PlayerLocation;

@CheckInfo(
    type = CheckType.KILL_AURA,
    subType = "N",
    friendlyName = "KillAura",
    version = CheckVersion.RELEASE,
    minViolations = -2.5,
    maxViolations = 5,
    unsupportedAtleast = ClientVersion.V1_9
)
public class KillAuraN extends MovementCheck {
    private Double lastValue;
    private int threshold;
    private static final double SPEED = 0.02;
    private boolean lastOnGround = false;
    private static final double NON_SPRINT = 0.2325;

    @Override
    public void handle(PlayerLocation location, PlayerLocation location2, long value) {
        if (location.getGround() && location2.getGround() && this.lastOnGround && this.playerData.isSprinting(true) && this.playerData.isSurvival()) {
            double distance = MathUtil.hypot(location2.getX() - location.getX(), location2.getZ() - location.getZ());
            if (this.lastValue != null && this.playerData.getLastAttackTicks() <= 1 && this.playerData.getLastAttacked() != null) {
                double value2 = 0.2325;
                value2 += ((Integer)this.playerData.getSpeedLevel().get()).intValue() * 0.02;
                if (this.playerData.getMovementSpeed() > 0.2) {
                    value2 *= this.playerData.getMovementSpeed() / 0.2;
                }

                if (distance >= this.lastValue * 0.99 && distance > value2) {
                    if (this.threshold++ > 3) {
                        this.handleViolation(String.format("D: %s", distance - value2), 1.0, true);
                        this.threshold = 0;
                    }
                } else {
                    this.threshold = 0;
                    this.decreaseVL(0.05);
                }
            }

            this.lastValue = distance;
        } else {
            this.lastValue = null;
        }

        this.lastOnGround = location.getGround();
    }

    public KillAuraN() {
        this.lastValue = null;
        this.threshold = 0;
    }
}
