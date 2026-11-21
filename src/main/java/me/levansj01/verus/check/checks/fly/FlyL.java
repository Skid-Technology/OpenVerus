package me.levansj01.verus.check.checks.fly;

import me.levansj01.verus.check.MovementCheck2;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.type.VerusTypeLoader;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.item.MaterialList;
import me.levansj01.verus.util.location.PacketLocation;
import org.bukkit.World;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;

@CheckInfo(type = CheckType.FLY, subType = "L", friendlyName = "Fly", version = CheckVersion.RELEASE, maxViolations = 100, minViolations = -1.0)
public class FlyL extends MovementCheck2 {
    @Override
    public void handle(PacketLocation location, PacketLocation location2, long value) {
        if (location.isGround()
            && location.getY() % 0.5 * 16.0 % 1.0 != 0.0
            && location.getY() % 0.5 * 64.0 != 1.0
            && location.getY() % 0.5 != 0.0
            && location2.getY() % 1.0 != 0.0
            && location.getY() % 1.0 != 0.09375
            && Math.abs(location.getY() % 0.5 - 0.015555072702202466) > 1.0E-12
            && !location2.isTeleport()
            && (VerusTypeLoader.isEnterprise() || !this.playerData.isTeleporting(2))
            && !this.playerData.canFly()
            && this.playerData.getVelocityTicks() > this.playerData.getPingTicks()
            && !this.playerData.isVehicle()
            && this.playerData.getTotalTicks() > 200) {
            World world = this.player.getWorld();
            Cuboid cuboid = Cuboid.withLimit(location, location2, 16).add(-0.5, 0.5, -0.42, 1.8, -0.5, 0.5);
            Cuboid cuboid2 = Cuboid.withLimit(location, location2, 16).add(-0.5, 0.5, -2.0, 0.0, -0.5, 0.5);
            this.run(
                () -> {
                    for (Entity entity : this.player.getNearbyEntities(2.5, 2.5, 2.5)) {
                        if (entity instanceof Boat || entity instanceof Minecart) {
                            return;
                        }
                    }

                    if (cuboid2.checkBlocks(this.player, world, entry -> !MaterialList.BOUND_UP.contains(entry))
                        && cuboid.checkBlocks(this.player, world, MaterialList.FULLY_PASSABLE::contains)) {
                        this.handleViolation(String.format("F: %s T: %s M: %s", location.getY() % 1.0, location2.getY() % 1.0, this.playerData.getNonMoveTicks()));
                    }
                }
            );
        } else {
            this.decreaseVL(0.001);
        }
    }
}
