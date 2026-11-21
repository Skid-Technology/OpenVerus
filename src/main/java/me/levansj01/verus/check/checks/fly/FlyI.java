package me.levansj01.verus.check.checks.fly;

import java.util.function.Predicate;
import me.levansj01.verus.check.MovementCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.type.VerusTypeLoader;
import me.levansj01.verus.util.BukkitUtil;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.item.MaterialList;
import me.levansj01.verus.util.java.MathUtil;
import me.levansj01.verus.util.location.PlayerLocation;
import me.levansj01.verus.verus2.data.player.TickerType;
import org.bukkit.World;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.potion.PotionEffectType;

@CheckInfo(type = CheckType.FLY, subType = "I", friendlyName = "Fly", version = CheckVersion.RELEASE, minViolations = -2.5, maxViolations = 50, logData = true)
public class FlyI extends MovementCheck {
    private int lastBypassTick;
    private double threshold;
    private int lastTPTick;
    private Double lastYDiff = null;

    @Override
    public void handle(PlayerLocation location, PlayerLocation location2, long value) {
        if (this.playerData.getTickerMap().get(TickerType.TELEPORT) <= 1) {
            this.lastYDiff = null;
            this.threshold = 0.0;
        } else {
            if (!location.getGround() || !location2.getGround()) {
                double deltaY = location2.getY() - location.getY();
                ClientVersion version = this.playerData.getVersion();
                double value2 = deltaY - 0.42F;
                if (this.lastYDiff != null
                    && this.playerData.getTotalTicks() - 40 > this.lastBypassTick
                    && !this.playerData.hasPlacedBucket()
                    && !this.playerData.hasPlacedBlock(true)
                    && !this.playerData.isFallFlying()
                    && !this.playerData.isGliding()
                    && !this.playerData.canFly()
                    && this.playerData.isSpawned()
                    && !this.playerData.isVehicle()
                    && this.playerData.getVelocityTicks() > (2 + this.playerData.getMaxPingTicks()) * 2
                    && this.playerData.getTickerMap().get(TickerType.EXPLOSION) > this.playerData.getMaxPingTicks() + 2
                    && !this.playerData.isLevitating()
                    && !this.playerData.isRiptiding()
                    && !this.playerData.hadJumpBoost()
                    && (
                        NMSManager.getInstance().getServerVersion().before(ServerVersion.v1_13_R2)
                            || location2.getY() < location.getY() && !BukkitUtil.hasEffect(this.playerData.getEffects(), 28)
                    )
                    && Math.abs(deltaY + 0.98F) > 1.0E-11
                    && Math.abs(deltaY + 0.09800000190735147) > 1.0E-11
                    && Math.abs(deltaY - 0.0030162615090425504) > 1.0E-9
                    && (!StorageEngine.getInstance().getVerusConfig().isUnloadedChunkFix() || Math.abs(deltaY / 0.98F + 0.08) > 1.0E-11)
                    && Math.abs(value2) > 1.0E-12F
                    && Math.abs(value2 - ((Integer)this.playerData.getJumpLevel().get()).intValue() * 0.1) > 1.0E-7F
                    && (version.before(ClientVersion.V1_9) || Math.abs(deltaY + 0.15233518685055714) > 1.0E-11 && Math.abs(deltaY + 0.07242780368044421) > 1.0E-11)
                    && (version.before(ClientVersion.V1_13) || Math.max(location2.getY(), location.getY()) < 255.0)) {
                    boolean flag = location.getX() != location2.getX() && location.getZ() != location2.getZ();
                    double value3 = (this.lastYDiff - 0.08) * 0.98F;
                    if ((!location2.getGround() || !(deltaY < 0.0) || !(value3 < deltaY) || !MathUtil.onGround(location2.getY()))
                        && (!(location.distanceXZSquared(location2) < 0.0025) || !BukkitUtil.hasEffect(this.playerData.getEffects(), PotionEffectType.JUMP))) {
                        if ((
                                VerusTypeLoader.isDev()
                                    || this.playerData.getVersion() != ClientVersion.V1_9
                                        && !BukkitUtil.hasEffect(this.playerData.getEffects(), PotionEffectType.JUMP)
                            )
                            && Math.abs(value3) < 0.005) {
                            value3 = 0.0;
                        }
                    } else {
                        value3 = deltaY;
                    }

                    double abs = Math.abs(value3 - deltaY);
                    double value4 = (value3 - deltaY) / value3;
                    int totalTicks = this.playerData.getTotalTicks();
                    if (abs > 2.0 && Math.abs(value4) > 300.0) {
                        this.handleViolation(
                            String.format("%s %s %.3f, %.3f%s", this.playerData.getTeleportTicks(), location.getGround(), abs, value4 * 100.0, "%"),
                            totalTicks - this.lastTPTick <= 20 && totalTicks - this.lastTPTick > 1 ? 1.0 : 0.5
                        );
                        this.lastTPTick = totalTicks;
                    }

                    if (!this.playerData.isTeleporting() && !location.getGround()) {
                        if (abs > 1.0E-7) {
                            World world = this.player.getWorld();
                            Cuboid cuboid = new Cuboid(location2).add(-0.5, 0.5, -1.0, 1.5, -0.5, 0.5);
                            double value5 = VerusTypeLoader.isDev() ? 0.29999 : 0.5;
                            double y = this.playerData.getLocation().getY();
                            Cuboid cuboid2 = Cuboid.withLimit(location, location2, 16).move(0.0, 2.0, 0.0).add(-value5, value5, -0.5, 0.5, -value5, value5);
                            this.run(
                                () -> {
                                    boolean waterLogged = location.toBlock().isWaterLogged(world);
                                    Predicate predicate = entry -> !MaterialList.BAD_VELOCITY.contains(entry) && !MaterialList.INVALID_SHAPE.contains(entry);
                                    if (cuboid.checkBlocks(this.player, world, predicate)
                                        && cuboid2.checkBlocks(this.player, world, entry -> entry == MaterialList.AIR)
                                        && !waterLogged) {
                                        for (Entity entity : this.player.getNearbyEntities(2.5, 2.5, 2.5)) {
                                            if (entity instanceof Boat || entity instanceof Minecart) {
                                                this.threshold = 0.0;
                                                this.lastBypassTick = totalTicks - 100;
                                                return;
                                            }
                                        }

                                        this.threshold++;
                                        boolean flag2 = this.playerData.hasLag() || this.playerData.hasFast() || !flag;
                                        this.handleViolation(
                                            () -> String.format("D: %s D2: %s P: %s V: %s", abs, deltaY, location2.getY() % 1.0, flag),
                                            (flag2 ? 0.1 : 1.0) * this.threshold
                                        );
                                    } else {
                                        this.decreaseVL(0.1);
                                        this.lastBypassTick = totalTicks;
                                        this.threshold = 0.0;
                                    }
                                }
                            );
                        } else {
                            this.decreaseVL(0.025);
                            this.threshold = 0.0;
                        }
                    }
                }
            }

            if (location2.getGround() && location.getGround()) {
                this.lastYDiff = null;
            } else {
                this.lastYDiff = location2.getY() - location.getY();
            }
        }
    }
}
