package me.levansj01.verus.check.checks.fly;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.task.ServerTickTask;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.MutableBlockLocation;
import me.levansj01.verus.util.item.MaterialList;
import me.levansj01.verus.util.location.PlayerLocation;
import org.bukkit.World;

@CheckInfo(type = CheckType.FLY, subType = "C", friendlyName = "Fly", version = CheckVersion.RELEASE, minViolations = -1.0, maxViolations = 50)
public class FlyC extends Check implements PacketHandler {
    private Double lastY = null;
    private int threshold;
    private int lastBypassTick = -10;

    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        if (this.lastY != null) {
            double value = packet.isPos() ? packet.getY() : this.lastY;
            if (this.lastY == value
                && value > 0.0
                && !this.playerData.isVehicle()
                && !packet.isGround()
                && !this.playerData.canFly()
                && this.playerData.isSurvival()
                && !this.playerData.isLevitating()
                && !this.playerData.isFallFlying()
                && !this.playerData.isGliding()
                && !this.playerData.isTeleporting()
                && !this.playerData.isTeleportingV2()
                && !this.playerData.hasPlacedBucket()
                && !this.playerData.hasPlacedBlock(true)
                && this.playerData.getTotalTicks() - 20 > this.lastBypassTick
                && this.playerData.getVelocityTicks() > this.playerData.getMaxPingTicks()
                && this.playerData.isSpawned()
                && !ServerTickTask.getInstance().isLagging(this.playerData.getTimestamp())) {
                PlayerLocation location = this.playerData.getLocation();
                World world = this.player.getWorld();
                Cuboid cuboid = new Cuboid(location).add(-0.5, 0.5, -0.5, 2.0, -0.5, 0.5);
                int totalTicks = this.playerData.getTotalTicks();
                boolean flag = this.playerData.hasLag() || this.playerData.hasFast();
                this.run(
                    () -> {
                        MutableBlockLocation mutableBlockLocation = location.toBlock();
                        boolean flag2 = mutableBlockLocation.isWaterLogged(world) || mutableBlockLocation.add(0, -1, 0).isWaterLogged(world) || mutableBlockLocation.add(0, 2, 0).isWaterLogged(world);
                        if (cuboid.checkBlocks(
                                this.player,
                                world,
                                entry -> !MaterialList.BAD_VELOCITY.contains(entry)
                                    && !MaterialList.SHULKER_BOX.contains(entry)
                                    && entry != MaterialList.PURPLE_FUCKING_SHULKER
                            )
                            && !flag2) {
                            if (this.threshold++ > (flag ? 4 : 1)) {
                                if (flag) {
                                    this.threshold = 0;
                                }

                                this.handleViolation(String.format("Y: %.2f", value), this.threshold - 1);
                            }
                        } else {
                            this.threshold = 0;
                            this.decreaseVL(0.01);
                            this.lastBypassTick = totalTicks;
                        }
                    }
                );
            } else {
                this.run(() -> {
                    this.threshold = 0;
                    this.decreaseVL(0.01);
                });
            }
        }

        if (packet.isPos()) {
            this.lastY = packet.getY();
        }
    }
}
