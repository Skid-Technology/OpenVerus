package me.levansj01.verus.type.premium.checks.speed;

import me.levansj01.verus.check.MovementCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.item.MaterialList;
import me.levansj01.verus.util.java.MathUtil;
import me.levansj01.verus.util.location.PlayerLocation;
import org.bukkit.World;

@CheckInfo(type = CheckType.SPEED, subType = "E", friendlyName = "OmniSprint", version = CheckVersion.RELEASE, minViolations = -1.0, maxViolations = 20)
public class SpeedE extends MovementCheck {
    private int threshold = 0;
    private Double lastAngle = null;

    @Override
    public void handle(PlayerLocation location, PlayerLocation location2, long value) {
        if (this.playerData.isSprinting(true)
            && location2.getGround()
            && location.getGround()
            && !this.playerData.hasLag()
            && !this.playerData.isTeleporting(3)
            && this.playerData.getVelocityTicks() > this.playerData.getPingTicks()) {
            double deltaX = location2.getX() - location.getX();
            double deltaZ = location2.getZ() - location.getZ();
            double value2 = Math.toDegrees(-Math.atan2(deltaX, deltaZ));
            double value3 = Math.min(MathUtil.getDistanceBetweenAngles360(value2, location2.getYaw()), MathUtil.getDistanceBetweenAngles360(value2, location.getYaw()));
            if (this.lastAngle != null && deltaX != 0.0 && deltaZ != 0.0) {
                double distanceBetweenAngles360 = MathUtil.getDistanceBetweenAngles360(this.lastAngle, value3);
                double value4 = 50.0;
                if (value3 > value4) {
                    if (distanceBetweenAngles360 < 5.0 && this.threshold++ > 4) {
                        World world = this.player.getWorld();
                        Cuboid cuboid = new Cuboid(this.playerData.getLocation()).expand(0.5, 0.5, 0.5);
                        this.run(() -> {
                            if (cuboid.checkBlocks(this.player, world, entry -> !MaterialList.BAD_VELOCITY.contains(entry))) {
                                this.handleViolation(String.format("D: %s LD: %s", value3, distanceBetweenAngles360));
                            } else {
                                this.threshold = -20;
                            }
                        });
                        this.threshold = 0;
                    }
                } else {
                    this.threshold = 0;
                }
            }

            this.lastAngle = value3;
        } else {
            this.lastAngle = null;
            this.threshold = 0;
        }
    }
}
