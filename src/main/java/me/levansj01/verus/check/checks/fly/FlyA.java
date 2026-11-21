package me.levansj01.verus.check.checks.fly;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.location.PacketLocation;
import me.levansj01.verus.util.location.PlayerLocation;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;

@CheckInfo(
    type = CheckType.FLY,
    subType = "A",
    friendlyName = "Fly",
    version = CheckVersion.RELEASE,
    minViolations = -2.5,
    maxViolations = 30,
    logData = true,
    heavy = true
)
public class FlyA extends Check implements PacketHandler {
    private int threshold;
    private Cuboid cuboid = null;
    private boolean ground;
    private int lastBypassTick;
    private int delay;

    public static boolean isFullyPassable(Material material) {
        switch (material) {
            case AIR:
            case WATER:
            case STATIONARY_WATER:
            case LAVA:
            case STATIONARY_LAVA:
                return true;
            default:
                return false;
        }
    }

    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        PacketLocation currentLocation = this.playerData.getCurrentLocation2();
        int totalTicks = this.playerData.getTotalTicks();
        if (this.ground && currentLocation.isGround() && this.playerData.isSurvival() && totalTicks > this.lastBypassTick) {
            World world = this.player.getWorld();
            boolean pos = packet.isPos();
            if (this.cuboid == null
                || this.delay++ > StorageEngine.getInstance().getVerusConfig().getHeavyTicks() && pos && !this.cuboid.containsBlock(world, currentLocation)) {
                PlayerLocation lastLocation = this.playerData.getLastLocation().clone();
                PlayerLocation location = this.playerData.getLocation().clone();
                this.cuboid = Cuboid.withLimit(lastLocation, location, 16).move(0.0, -0.5, 0.0).expand(0.4, 0.5, 0.4);
                this.run(
                    () -> {
                        if (this.cuboid == null) {
                            this.cuboid = Cuboid.withLimit(lastLocation, location, 16).move(0.0, -0.5, 0.0).expand(0.4, 0.5, 0.4);
                        }

                        if (!this.playerData.isTeleporting(5) && this.cuboid.checkBlocks(this.player, world, FlyA::isFullyPassable)) {
                            for (Entity entity : this.player
                                .getNearbyEntities(1.0 + this.cuboid.getWidthX(), 1.0 + this.cuboid.getWidthY(), 1.0 + this.cuboid.getWidthZ())) {
                                if (entity instanceof Boat || entity instanceof Minecart) {
                                    this.lastBypassTick = totalTicks + 100;
                                    return;
                                }
                            }

                            this.cuboid = null;
                            if (this.threshold++ > this.playerData.getMaxPingTicks2()) {
                                this.handleViolation(
                                    String.format("%s > %s | l=%s p=%s", this.threshold, this.playerData.getMaxPingTicks(), this.playerData.hasLag(), pos),
                                    !pos && this.playerData.hasLag() ? 0.1 : 1.0
                                );
                            }

                            this.delay = StorageEngine.getInstance().getVerusConfig().getHeavyTicks() + 1;
                        } else {
                            this.threshold = 0;
                            this.decreaseVL(2.5);
                        }
                    }
                );
                this.delay = 0;
            }
        } else {
            this.threshold = 0;
            this.delay = StorageEngine.getInstance().getVerusConfig().getHeavyTicks() + 1;
        }

        this.ground = currentLocation.isGround();
    }
}
