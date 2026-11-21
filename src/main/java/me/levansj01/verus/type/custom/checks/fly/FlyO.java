package me.levansj01.verus.type.custom.checks.fly;

import me.levansj01.verus.check.MovementCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.item.MaterialList;
import me.levansj01.verus.util.java.MathUtil;
import me.levansj01.verus.util.location.PlayerLocation;
import org.bukkit.World;

@CheckInfo(
    type = CheckType.FLY,
    subType = "O",
    friendlyName = "Strafe",
    version = CheckVersion.RELEASE,
    minViolations = -5.0,
    maxViolations = 100,
    logData = true
)
public class FlyO extends MovementCheck {
    private int constant;
    private static final double LIMIT = 0.0025;
    private int threshold;
    private int lastBypassTick;

    @Override
    public void handle(PlayerLocation location, PlayerLocation location2, long value) {
        int totalTicks = this.playerData.getTotalTicks();
        if (!this.playerData.isFlying() && totalTicks - this.lastBypassTick >= 20) {
            double value2 = location.distanceXZSquared(location2);
            if (value2 > 0.025) {
                boolean flag = location2.getGround() || location.getGround();
                double value3 = Math.toDegrees(-Math.atan2(location2.getX() - location.getX(), location2.getZ() - location.getZ()));
                double value4 = Math.min(
                    Math.abs(MathUtil.getDistanceBetweenAngles360(value3, location2.getYaw())), Math.abs(MathUtil.getDistanceBetweenAngles360(value3, location.getYaw()))
                );
                double value5 = Math.min((flag ? 90 : 180) - value4, value4);
                double value6 = Math.abs(MathUtil.getDistanceBetweenAngles360(value3, location2.getYaw())) % 90.0;
                double value7 = Math.abs(MathUtil.getDistanceBetweenAngles360(value3, location.getYaw())) % 90.0;
                double value8 = Math.max(Math.abs(45.0 - value6), Math.abs(45.0 - value7));
                if (Math.abs(location.getYaw() - location2.getYaw()) > 1.0F) {
                    this.constant = 5;
                }

                if (value8 < 0.0025 && value5 > 0.0025) {
                    if (this.threshold++ > (flag ? 4 : 0) && this.constant-- > 0) {
                        World world = this.player.getWorld();
                        Cuboid cuboid = new Cuboid(location2).expand(0.4, 0.5, 0.4);
                        this.run(() -> {
                            if (totalTicks - this.lastBypassTick >= 20) {
                                if (cuboid.checkBlocks(this.player, world, entry -> !MaterialList.LIQUIDS.contains(entry))) {
                                    this.handleViolation(String.format("ANG: %.5f SPD: %.2f G: %s", value5, Math.sqrt(value2), flag), 0.5);
                                } else {
                                    this.lastBypassTick = totalTicks;
                                }
                            }
                        });
                    }
                } else {
                    this.threshold = 0;
                    this.decreaseVL(0.02);
                }
            }
        }
    }
}
