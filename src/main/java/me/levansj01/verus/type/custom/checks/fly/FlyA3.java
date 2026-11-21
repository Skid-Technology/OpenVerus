package me.levansj01.verus.type.custom.checks.fly;

import java.util.function.BooleanSupplier;
import me.levansj01.verus.check.MovementCheck2;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.handler.TeleportCheck;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.compat.packets.VPacketPlayOutPosition;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.item.MaterialList;
import me.levansj01.verus.util.location.PacketLocation;
import org.bukkit.World;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;

@CheckInfo(type = CheckType.FLY, subType = "A3", friendlyName = "Fly", minViolations = -20.0, version = CheckVersion.DEVELOPMENT, heavy = true)
public class FlyA3 extends MovementCheck2 implements TeleportCheck {
    private int lastBypassTick = 20;
    private int z;
    private int singles;
    private int x;
    private int deltas;
    private int y;
    private PacketLocation valid;

    @Override
    public void handle(VPacketPlayOutPosition packet, long value) {
        if (this.valid != null && !this.valid.sameXZ(packet)) {
            this.valid = null;
        }
    }

    @Override
    public void handle(PacketLocation location, PacketLocation location2, long value) {
        if (location2.isGround()) {
            int x = (int)location2.getX();
            int y = (int)location2.getY();
            int z = (int)location2.getZ();
            if (this.x == x && this.y == y && this.z == z) {
                if (this.violations > this.getMinViolation()) {
                    this.check(location, location2, () -> this.singles++ > (this.playerData.getMaxPingTicks2() + 2) * 2, () -> false, () -> {
                        this.deltas = 0;
                        this.singles = 0;
                    });
                }
            } else {
                this.check(
                    location,
                    location2,
                    () -> this.deltas++ > this.playerData.getPingTicks() / 2,
                    () -> this.violations > 2.0 || this.deltas + this.singles > this.playerData.getPingTicks() / 2,
                    () -> {
                        this.deltas = 0;
                        this.singles = 0;
                    }
                );
                this.x = x;
                this.y = y;
                this.z = z;
            }
        }
    }

    public void check(PacketLocation location, PacketLocation valid, BooleanSupplier booleanSupplier, BooleanSupplier booleanSupplier2, Runnable runnable) {
        int totalTicks = this.playerData.getTotalTicks();
        if (this.lastBypassTick <= totalTicks) {
            Player player = this.playerData.getPlayer();
            World world = player.getWorld();
            Cuboid cuboid = valid.to(location, 25).move(0.0, -0.5, 0.0).expand(0.4, 0.5, 0.4);
            this.run(
                () -> {
                    if (!cuboid.checkBlocks(player, world, MaterialList.AIR::equals)) {
                        this.valid = valid;
                        runnable.run();
                        this.decreaseVL(1.0);
                    } else {
                        for (Entity entity : player.getNearbyEntities(1.0 + cuboid.getWidthX(), 1.0 + cuboid.getWidthY(), 1.0 + cuboid.getWidthZ())) {
                            if (entity instanceof Boat || entity instanceof Minecart) {
                                this.lastBypassTick = totalTicks + 100;
                                return;
                            }
                        }

                        if (booleanSupplier.getAsBoolean()) {
                            this.handleViolation();
                        }

                        if (this.valid != null && StorageEngine.getInstance().getVerusConfig().isHeavyPullback() && booleanSupplier2.getAsBoolean()) {
                            NMSManager.getInstance()
                                .sendTeleport(
                                    player,
                                    this.valid.look(this.playerData.getLocation()).withY(Math.min(this.valid.getY(), this.playerData.getLocation().getY()))
                                );
                        }
                    }
                }
            );
        }
    }
}
