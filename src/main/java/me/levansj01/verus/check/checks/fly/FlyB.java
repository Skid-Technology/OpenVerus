package me.levansj01.verus.check.checks.fly;

import me.levansj01.verus.check.MovementCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.item.MaterialList;
import me.levansj01.verus.util.java.MathUtil;
import me.levansj01.verus.util.location.PlayerLocation;
import org.bukkit.World;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;

@CheckInfo(type = CheckType.FLY, subType = "B", friendlyName = "Fly", version = CheckVersion.RELEASE, minViolations = -5.0, maxViolations = 25)
public class FlyB extends MovementCheck {
    private int threshold;
    private int lastBypassTick = -10;

    @Override
    public void handle(PlayerLocation location, PlayerLocation location2, long value) {
        if (location.getGround()
            && location2.getGround()
            && location.getY() != location2.getY()
            && !MathUtil.onGround(location.getY())
            && this.playerData.getTotalTicks() > 200
            && !MathUtil.onGround(location2.getY())
            && this.playerData.isSpawned()
            && this.playerData.getTotalTicks() - 10 > this.lastBypassTick
            && !this.playerData.canFly()
            && !this.playerData.isFallFlying()
            && !this.playerData.isGliding()
            && !this.playerData.isLevitating()
            && !this.playerData.isFlying()
            && !this.playerData.hadJumpBoost()) {
            World world = this.player.getWorld();
            Cuboid cuboid = new Cuboid(this.playerData.getLocation()).expand(0.5, 0.5, 0.5);
            double deltaY = location2.getY() - location.getY();
            int totalTicks = this.playerData.getTotalTicks();
            this.run(
                () -> {
                    if (!cuboid.checkBlocks(
                        this.player,
                        world,
                        entry -> (NMSManager.getInstance().getServerVersion().before(ServerVersion.v1_11_R1) || entry != MaterialList.PURPLE_FUCKING_SHULKER)
                            && !MaterialList.INVALID_SHAPE.contains(entry)
                    )) {
                        this.threshold = 0;
                        this.violations = this.violations - Math.min(this.violations + 4.0, 0.05);
                        this.lastBypassTick = totalTicks;
                    } else {
                        for (Entity entity : this.player.getNearbyEntities(2.5, 2.5, 2.5)) {
                            if (entity instanceof Boat || entity instanceof Minecart || entity.getType().name().equalsIgnoreCase("SHULKER")) {
                                this.threshold = 0;
                                this.lastBypassTick = totalTicks - 100;
                                this.violations = this.violations - Math.min(this.violations + 4.0, 0.05);
                                return;
                            }
                        }

                        this.threshold++;
                        this.handleViolation(String.format("D: %s", deltaY), this.threshold);
                    }
                }
            );
        } else {
            this.threshold = 0;
            this.violations = this.violations - Math.min(this.violations + 4.0, 0.05);
        }
    }
}
