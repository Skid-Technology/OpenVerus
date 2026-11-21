package me.levansj01.verus.check.checks.fly;

import me.levansj01.verus.check.MovementCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.item.MaterialList;
import me.levansj01.verus.util.location.PlayerLocation;
import org.bukkit.World;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;

@CheckInfo(type = CheckType.FLY, subType = "E", friendlyName = "Fly", version = CheckVersion.RELEASE, minViolations = -3.5, maxViolations = 30, logData = true)
public class FlyE extends MovementCheck {
    private double threshold;
    private int lastBypassTick = -10;

    @Override
    public void handle(PlayerLocation location, PlayerLocation location2, long value) {
        if (this.playerData.getTotalTicks() - 20 >= this.lastBypassTick) {
            if (location.getGround()
                && location2.getY() > location.getY()
                && this.playerData.getVelocityTicks() > (this.playerData.getMaxPingTicks() + 1) * 4
                && !this.playerData.isTeleporting()
                && this.playerData.isSpawned()
                && !this.playerData.isFallFlying()
                && !this.playerData.isGliding()
                && !this.playerData.hasPlacedBucket()
                && !this.playerData.isLevitating()
                && !this.playerData.isTeleportingV2()
                && !this.playerData.hasPlacedBlock(true)
                && !this.playerData.canFly()) {
                double deltaY = location2.getY() - location.getY();
                double value2 = !this.playerData.getVersion().before(ClientVersion.V1_9) ? 0.419999986886978 : 0.42F;
                boolean flag = this.playerData.getVelocityTicks() <= (this.playerData.getMaxPingTicks() + 1) * 4;
                if (this.playerData.getVelocityQueue().stream().anyMatch(entry -> entry.isExplosion() || Math.abs(entry.getOriginalY() - deltaY) <= 1.25E-4)) {
                    return;
                }

                if (deltaY < value2 && (location2.getY() - value2) % 1.0 > 1.0E-15) {
                    World world = this.player.getWorld();
                    Cuboid cuboid = Cuboid.withLimit(location, location2, 16).move(0.0, 2.0, 0.0).expand(0.5, 0.5, 0.5);
                    Cuboid cuboid2 = Cuboid.withLimit(location, location2, 16).move(0.0, -0.25, 0.0).expand(0.5, 0.75, 0.5);
                    int totalTicks = this.playerData.getTotalTicks();
                    if (this.playerData.hasJumpBoost()) {
                        this.lastBypassTick = totalTicks;
                    }

                    this.run(
                        () -> {
                            if (this.playerData.getTotalTicks() - 20 >= this.lastBypassTick) {
                                if (cuboid.checkBlocks(this.player, world, entry -> entry == MaterialList.AIR)
                                    && cuboid2.checkBlocks(
                                        this.player,
                                        world,
                                        entry -> !MaterialList.INVALID_SHAPE.contains(entry)
                                            && !MaterialList.BAD_VELOCITY.contains(entry)
                                            && !MaterialList.BED.contains(entry)
                                    )) {
                                    for (Entity entity : this.player.getNearbyEntities(2.5, 2.5, 2.5)) {
                                        if (entity instanceof Boat || entity instanceof Minecart) {
                                            this.threshold = 0.0;
                                            this.decreaseVL(0.025);
                                            this.lastBypassTick = totalTicks - 100;
                                            return;
                                        }
                                    }

                                    this.threshold += flag ? 0.4 : 1.0;
                                    this.handleViolation(String.format("D: %s Y: %s", deltaY, location2.getY()), this.threshold);
                                } else {
                                    this.threshold = 0.0;
                                    this.decreaseVL(0.025);
                                    this.lastBypassTick = totalTicks;
                                }
                            }
                        }
                    );
                } else {
                    this.threshold = 0.0;
                    this.decreaseVL(0.025);
                }
            }
        }
    }

    @Override
    public void setMaxViolation(int maxViolation) {
        if (this.playerData != null && this.playerData.getVersion().afterEq(ClientVersion.V1_9)) {
            maxViolation *= 5;
        }

        super.setMaxViolation(maxViolation);
    }
}
