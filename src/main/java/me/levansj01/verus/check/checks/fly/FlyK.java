package me.levansj01.verus.check.checks.fly;

import me.levansj01.verus.check.MovementCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.item.MaterialList;
import me.levansj01.verus.util.location.PlayerLocation;
import me.levansj01.verus.verus2.data.player.TickerType;
import org.bukkit.World;

@CheckInfo(type = CheckType.FLY, subType = "K", friendlyName = "Fly", version = CheckVersion.RELEASE, minViolations = -2.0, maxViolations = 10, logData = true)
public class FlyK extends MovementCheck {
    @Override
    public void handle(PlayerLocation location, PlayerLocation location2, long value) {
        double deltaY = location.getY() - location2.getY();
        if (location.getGround()
            && deltaY > 0.079
            && !this.playerData.isFlying()
            && this.playerData.isSurvival()
            && !this.playerData.isTeleporting(2)
            && this.playerData.getTickerMap().get(TickerType.TELEPORT) > 1
            && this.playerData.getVelocityTicks() > this.playerData.getPingTicks() / 2
            && this.playerData.isSpawned()
            && !this.playerData.hasLag()
            && !this.playerData.isFallFlying()
            && !this.playerData.isGliding()
            && Math.abs(location.getY() % 0.5 - 0.015555072702202466) > 1.0E-12
            && !this.playerData.hasPlacedBlock(true)) {
            World world = this.player.getWorld();
            Cuboid cuboid = new Cuboid(this.playerData.getLocation()).add(-0.5, 0.5, -1.0, 1.5, -0.5, 0.5);
            this.run(() -> {
                if (cuboid.checkBlocks(this.player, world, entry -> !MaterialList.BAD_VELOCITY.contains(entry) && !MaterialList.INVALID_SHAPE.contains(entry))) {
                    this.handleViolation(String.format("D: %s G: %s", deltaY, location2.getGround()));
                }
            });
        } else {
            this.decreaseVL(0.005);
        }
    }
}
