package me.levansj01.verus.check.checks.fly;

import me.levansj01.verus.check.MovementCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.util.BukkitUtil;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.item.MaterialList;
import me.levansj01.verus.util.java.MathHelper;
import me.levansj01.verus.util.java.MathUtil;
import me.levansj01.verus.util.location.PlayerLocation;
import org.bukkit.World;
import org.bukkit.util.Vector;

@CheckInfo(
    type = CheckType.FLY,
    subType = "Z",
    friendlyName = "Elytra Fly",
    version = CheckVersion.RELEASE,
    minViolations = -7.5,
    unsupportedVersions = {ClientVersion.V1_7, ClientVersion.V1_8},
    unsupportedServers = {ServerVersion.v1_7_R4, ServerVersion.v1_8_R3}
)
public class FlyZ extends MovementCheck {
    private int ticks;
    private static final float VALUE = (float) (Math.PI / 180.0);
    private int lastBypass;
    private final Vector lastMovement = new Vector();

    @Override
    public void handle(PlayerLocation location, PlayerLocation location2, long value) {
        if (this.playerData.isFallFlying()
            && !this.playerData.canFly()
            && !this.playerData.isBoosting()
            && !location2.getGround()
            && !this.playerData.isLevitating()
            && this.playerData.getVelocityTicks() > this.playerData.getMaxPingTicks()
            && !location.getGround()
            && this.playerData.getTotalTicks() - this.lastBypass > 20
            && (NMSManager.getInstance().getServerVersion().before(ServerVersion.v1_13_R2) || !BukkitUtil.hasEffect(this.playerData.getEffects(), 28))) {
            if (this.ticks++ > 9) {
                float pitch = location2.getPitch();
                float yaw = location2.getYaw();
                float value2 = pitch * (float) (Math.PI / 180.0);
                double deltaY = location2.getY() - location.getY();
                double y = this.lastMovement.getY();
                double length = this.lastMovement.length();
                Vector vectorForRotation = getVectorForRotation(pitch, yaw);
                double length2 = vectorForRotation.length();
                double distance = Math.sqrt(Math.pow(vectorForRotation.getX(), 2.0) + Math.pow(vectorForRotation.getZ(), 2.0));
                float value3 = (float)(Math.pow(MathHelper.cos(value2), 2.0) * Math.min(1.0, length2 / 0.4));
                double value4 = deltaY / 0.99F;
                double value5 = (y - 0.08) * 0.98F;
                if (value2 < 0.0F) {
                    value4 -= length * -MathHelper.sin(value2) * 0.04 * 3.2;
                }

                if (y < 0.0 && distance > 0.0) {
                    value4 -= y * -0.1 * value3;
                }

                if (this.playerData.getVersion().before(ClientVersion.V1_13)) {
                    value4 -= -0.08 + value3 * 0.06;
                } else {
                    value4 -= 0.08 * (-1.0 + value3 * 0.75);
                }

                double value6 = MathUtil.lowestAbs(y - value4, deltaY - value5);
                double value7 = MathUtil.lowestAbs((value4 - y) / value4, (deltaY - value5) / value5);
                if (Math.abs(value6) > 0.025 && Math.abs(value7) > 0.075) {
                    World world = this.player.getWorld();
                    Cuboid cuboid = new Cuboid(location2).expand(0.5, 1.0, 0.5).move(0.0, 1.0, 0.0);
                    int totalTicks = this.playerData.getTotalTicks();
                    this.run(() -> {
                        if (this.playerData.getTotalTicks() - this.lastBypass >= 20) {
                            if (cuboid.checkBlocks(this.player, world, entry -> !MaterialList.BAD_VELOCITY.contains(entry))) {
                                this.handleViolation(() -> String.format("%.2f %.2f", deltaY, value6));
                            } else {
                                this.lastBypass = totalTicks;
                                this.ticks = 0;
                            }
                        }
                    });
                } else {
                    this.decreaseVL(0.1);
                }
            }

            this.lastMovement.setX(location2.getX() - location.getX());
            this.lastMovement.setY(location2.getY() - location.getY());
            this.lastMovement.setZ(location2.getZ() - location.getZ());
        } else {
            this.ticks = 0;
        }
    }

    public FlyZ() {
        this.ticks = 0;
    }

    private static Vector getVectorForRotation(float value, float value2) {
        float value3 = MathHelper.cos(-value2 * (float) (Math.PI / 180.0) - (float) Math.PI);
        float value4 = MathHelper.sin(-value2 * (float) (Math.PI / 180.0) - (float) Math.PI);
        float value5 = -MathHelper.cos(-value * (float) (Math.PI / 180.0));
        float value6 = MathHelper.sin(-value * (float) (Math.PI / 180.0));
        return new Vector(value4 * value5, value6, value3 * value5);
    }
}
