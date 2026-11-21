package me.levansj01.verus.type.premium.checks.velocity;

import me.levansj01.verus.check.MovementCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.item.MaterialList;
import me.levansj01.verus.util.java.MathUtil;
import me.levansj01.verus.util.location.PlayerLocation;
import org.bukkit.World;
import org.bukkit.util.Vector;

@CheckInfo(type = CheckType.VELOCITY, subType = "B", friendlyName = "hVelocity", version = CheckVersion.RELEASE, minViolations = -2.5)
public class VelocityB extends MovementCheck {
    private int lastBypassTicks;
    private double total;

    @Override
    public void handle(PlayerLocation location, PlayerLocation location2, long value) {
        if (this.playerData.getLastVelX() != 0.0
            && this.playerData.getLastVelZ() != 0.0
            && this.playerData.getHorizontalVelocityTicks() > this.playerData.getMoveTicks()) {
            double deltaY = location2.getY() - location.getY();
            if (deltaY > 0.0) {
                double distance = MathUtil.hypot(this.playerData.getLastVelX(), this.playerData.getLastVelZ());
                if (this.playerData.getLastLastLocation().getGround()
                    && location.getGround()
                    && MathUtil.onGround(location.getY())
                    && !location2.getGround()
                    && !MathUtil.onGround(location2.getY())
                    && this.playerData.getTotalTicks() - this.lastBypassTicks > 10
                    && distance > 0.25) {
                    PlayerLocation location3;
                    if (this.playerData.getLastNonMoveTicks() == 0) {
                        Vector vector = new Vector(
                            this.playerData.getLastLastLocation().getX(),
                            this.playerData.getLastLastLocation().getY(),
                            this.playerData.getLastLastLocation().getZ()
                        );
                        vector.subtract(new Vector(location.getX(), location.getY(), location.getZ()));
                        location3 = location2.add(vector.getX(), vector.getY(), vector.getZ());
                    } else {
                        location3 = location2.clone();
                    }

                    double distance2 = MathUtil.hypot(location2.getX() - location.getX(), location2.getZ() - location.getZ());
                    double distance3 = MathUtil.hypot(location3.getX() - location.getX(), location3.getZ() - location.getZ());
                    double value2 = Math.max(distance2, distance3) / distance;
                    this.decreaseVL(0.0025);
                    if (value2 < 1.0) {
                        World world = this.player.getWorld();
                        Cuboid cuboid = new Cuboid(location2).add(-1.0, 1.0, 0.0, 2.05, -1.0, 1.0);
                        int totalTicks = this.playerData.getTotalTicks();
                        this.run(() -> {
                            if (cuboid.checkBlocks(this.player, world, entry -> entry == MaterialList.AIR)) {
                                if ((this.total += 1.05 - value2) > 2.0) {
                                    this.total = 0.0;
                                    this.handleViolation(String.format("P: %.2f D: %.3f", value2, distance3));
                                }
                            } else {
                                this.total = this.total - Math.min(this.total, 1.05);
                                this.lastBypassTicks = totalTicks;
                            }
                        });
                    } else {
                        this.total = this.total - Math.min(this.total, 1.05);
                    }
                }

                this.playerData.setLastVelX(0.0);
                this.playerData.setLastVelZ(0.0);
            }
        }
    }
}
