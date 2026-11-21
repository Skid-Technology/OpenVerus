package me.levansj01.verus.check.checks.speed;

import me.levansj01.verus.check.MovementCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.data.transaction.velocity.Velocity;
import me.levansj01.verus.type.VerusTypeLoader;
import me.levansj01.verus.util.BukkitUtil;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.item.MaterialList;
import me.levansj01.verus.util.java.DoubleWrapper;
import me.levansj01.verus.util.java.MathUtil;
import me.levansj01.verus.util.location.PlayerLocation;
import org.bukkit.World;
import org.bukkit.inventory.PlayerInventory;

@CheckInfo(
    type = CheckType.SPEED,
    subType = "A",
    friendlyName = "Speed",
    version = CheckVersion.RELEASE,
    minViolations = -5.0,
    maxViolations = 30,
    logData = true
)
public class SpeedA extends MovementCheck {
    private int bypassFallbackTicks;
    public static final double SPRINT = 0.2865;
    public static final double SPEED_CUBIC_AMOUNT = 0.0018;
    private int lastBypassTick;
    public static final double SPEED_POTION_GROUND = 0.0573;
    public static final double STRAIGHT_SPRINT = 0.281;
    private int lastFastAirTick;
    private int lastBlockAboveTick = -20;
    public static final double STRAIGHT_NON_SPRINT = 0.217;
    private int lastGroundTick;
    private int lastAirTick;
    private int lastSpeed;
    public static final double FAST_AIR = 0.6125;
    public static final double NON_SPRINT = 0.2325;
    public static final double NORMAL_AIR = 0.36;
    public static final double SPEED_POTION_AIR_FAST = 0.0375;
    public static final double AIR_SLOWDOWN_TICK = 0.125;
    public static final double SPEED_POTION_AIR = 0.0175;

    @Override
    public void handle(PlayerLocation location, PlayerLocation location2, long value) {
        if ((location2.getX() != location.getX() || location2.getZ() != location.getZ())
            && !this.playerData.isTeleportingV2()
            && !this.playerData.isVehicle()
            && !this.playerData.isGliding()
            && !this.playerData.isFallFlying()
            && !this.playerData.isHooked()
            && !this.playerData.isRiptiding()) {
            if (this.playerData.canFly() || this.playerData.isFlying()) {
                if (this.playerData.isFlying()) {
                    this.bypassFallbackTicks = 20;
                }

                return;
            }

            double distance = MathUtil.hypot(location2.getX() - location.getX(), location2.getZ() - location.getZ());
            DoubleWrapper doubleWrapper = new DoubleWrapper(0.0);
            int value2 = Math.max((Integer)this.playerData.getSpeedLevel().get(), 0);
            if (this.lastSpeed > value2) {
                value2 = this.lastSpeed - 1;
            }

            if (location2.getGround()) {
                if (this.bypassFallbackTicks > 0) {
                    this.bypassFallbackTicks -= 10;
                }

                this.lastGroundTick = this.playerData.getTotalTicks();
                double value3 = Math.toDegrees(-Math.atan2(location2.getX() - location.getX(), location2.getZ() - location.getZ()));
                double distanceBetweenAngles360 = MathUtil.getDistanceBetweenAngles360(value3, location2.getYaw());
                boolean flag = distanceBetweenAngles360 < 5.0 && distanceBetweenAngles360 < 90.0;
                doubleWrapper.addAndGet(value2 * 0.0573);
                doubleWrapper.addAndGet(flag ? 0.281 : 0.2865);
                if (this.playerData.getMovementSpeed() > 0.2) {
                    doubleWrapper.set(doubleWrapper.get() * this.playerData.getMovementSpeed() / 0.2);
                }

                if (this.lastAirTick >= this.lastGroundTick - 10) {
                    doubleWrapper.addAndGet((this.lastGroundTick - this.lastAirTick) * 0.125);
                }
            } else {
                if (this.bypassFallbackTicks > 0) {
                    doubleWrapper.addAndGet(0.1);
                    this.bypassFallbackTicks--;
                }

                this.lastAirTick = this.playerData.getTotalTicks();
                boolean flag = false;
                if (distance > 0.36 && this.lastFastAirTick < this.lastGroundTick) {
                    this.lastFastAirTick = this.playerData.getTotalTicks();
                    doubleWrapper.addAndGet(0.6125);
                    flag = true;
                } else {
                    doubleWrapper.addAndGet(0.36);
                }

                if (this.playerData.isFallFlying()) {
                    this.bypassFallbackTicks = 100;
                    doubleWrapper.set(doubleWrapper.get() * 5.0);
                }

                double value3 = value2;
                if (this.lastAirTick - this.lastGroundTick < 1 + (value2 - 1) / 2) {
                    value3 += value2 * value2 * value2 * 0.0018;
                }

                doubleWrapper.addAndGet(value3 * (flag ? 0.0375 : 0.0175));
                if (this.playerData.getMovementSpeed() > 0.2) {
                    doubleWrapper.addAndGet(doubleWrapper.get() * (this.playerData.getMovementSpeed() - 0.2) * 2.0);
                }
            }

            this.lastSpeed = value2;
            if (!VerusTypeLoader.isDev() && this.playerData.getHorizontalSpeedTicks() < (this.playerData.getPingTicks() + 10) * 2) {
                return;
            }

            boolean hasLag = this.playerData.hasLag();
            Velocity velocity = MathUtil.max(this.playerData.getVelocityQueue(), Velocity::getHypotSquaredXZ);
            if (velocity != null) {
                doubleWrapper.addAndGet(Math.sqrt(velocity.getHypotSquaredXZ()));
            }

            if (NMSManager.getInstance().getServerVersion().afterEq(ServerVersion.v1_13_R2) && BukkitUtil.hasEffect(this.playerData.getEffects(), 30)
                || this.playerData.hadAttributes()
                || this.playerData.hadSpeedBoost()) {
                doubleWrapper.set(doubleWrapper.get() * 2.0);
            }

            if (distance > doubleWrapper.get()) {
                World world = this.player.getWorld();
                PlayerLocation location3 = this.playerData.getLocation();
                Cuboid cuboid = new Cuboid(location3).move(0.0, 2.0, 0.0).expand(0.5, 0.5, 0.5);
                Cuboid cuboid2 = new Cuboid(location3).add(-0.5, 0.5, -1.99, 0.5, -0.5, 0.5);
                int totalTicks = this.playerData.getTotalTicks();
                int lastFastAirTick = this.lastFastAirTick;
                this.run(
                    () -> {
                        ServerVersion serverVersion = NMSManager.getInstance().getServerVersion();
                        if (serverVersion.after(ServerVersion.v1_7_R4)) {
                            PlayerInventory inventory = this.player.getInventory();
                            if (BukkitUtil.hasEnchantment(inventory.getBoots(), "DEPTH_STRIDER")
                                && !cuboid2.checkBlocks(this.player, world, entry -> !MaterialList.LIQUIDS.contains(entry))) {
                                return;
                            }

                            if (serverVersion.afterEq(ServerVersion.v1_16_R1)) {
                                int enchantment = BukkitUtil.getEnchantment(inventory.getBoots(), "SOUL_SPEED");
                                if (enchantment > 0) {
                                    doubleWrapper.addAndGet(doubleWrapper.get() * (0.9 + enchantment * 0.125));
                                }
                            }
                        }

                        boolean flag = totalTicks - 20 < this.lastBlockAboveTick;
                        if (!flag && !cuboid.checkBlocks(this.player, world, entry -> entry == MaterialList.AIR)) {
                            flag = true;
                            this.lastBlockAboveTick = totalTicks;
                        }

                        boolean flag2 = totalTicks - 60 < this.lastBypassTick;
                        if (!flag2
                            && !cuboid2.checkBlocks(
                                this.player,
                                world,
                                entry -> !MaterialList.ICE.contains(entry)
                                    && !MaterialList.SLABS.contains(entry)
                                    && !MaterialList.STAIRS.contains(entry)
                                    && !MaterialList.STICKY.contains(entry)
                            )) {
                            flag2 = true;
                            this.lastBypassTick = totalTicks;
                        }

                        int value3 = hasLag ? 2 : 1;
                        if (flag) {
                            doubleWrapper.addAndGet(0.3 * value3);
                        }

                        if (flag2) {
                            doubleWrapper.addAndGet((location2.getGround() ? 0.25 : 0.3) * value3);
                            this.bypassFallbackTicks = 60;
                        }

                        if (distance > doubleWrapper.get()) {
                            double value4 = (hasLag ? (distance - doubleWrapper.get()) * 0.5 + 0.1 : distance - doubleWrapper.get()) + 0.3;
                            if (this.playerData.isTeleporting()) {
                                value4 = 0.15;
                            }

                            this.handleViolation(
                                () -> String.format(
                                    "DST: %.3f LM %.3f G: %s L: %s V: %s FA: %s",
                                    distance,
                                    doubleWrapper.get(),
                                    location2.getGround(),
                                    hasLag,
                                    velocity == null ? 0.0 : Math.sqrt(velocity.getHypotSquaredXZ()),
                                    totalTicks - lastFastAirTick
                                ),
                                value4
                            );
                        } else {
                            this.decreaseVL(0.02);
                        }
                    }
                );
            } else {
                this.decreaseVL(0.02);
            }
        }
    }

    public SpeedA() {
        this.lastBypassTick = -50;
    }
}
