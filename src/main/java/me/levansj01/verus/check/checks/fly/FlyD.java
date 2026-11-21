package me.levansj01.verus.check.checks.fly;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.item.MaterialList;
import me.levansj01.verus.util.java.MathUtil;
import org.bukkit.World;

@CheckInfo(type = CheckType.FLY, subType = "D", friendlyName = "Fly", version = CheckVersion.RELEASE, minViolations = -5.0, maxViolations = 25)
public class FlyD extends Check implements PacketHandler {
    private int lastBypassTick;
    private int jumped;
    private Double lastY;
    private boolean jumping = false;

    public FlyD() {
        this.lastY = null;
        this.lastBypassTick = -10;
    }

    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        if (!packet.isGround()
            && this.playerData.getVelocityTicks() > this.playerData.getPingTicks() * 2
            && this.playerData.getTeleportTicks() > this.playerData.getPingTicks()
            && !this.playerData.isTeleportingV2()
            && !this.playerData.isFlying()
            && this.playerData.isSurvival()
            && this.playerData.getTotalTicks() - 40 > this.lastBypassTick
            && !this.playerData.hasPlacedBucket()
            && !this.playerData.isFallFlying()
            && !this.playerData.isGliding()
            && !this.playerData.isRiptiding()
            && !this.playerData.isLevitating()
            && !this.playerData.isVehicle()) {
            if (this.lastY != null) {
                if (this.jumping && packet.getY() < this.lastY) {
                    if (this.jumped++ > 1 && !this.playerData.hadJumpBoost()) {
                        World world = this.player.getWorld();
                        Cuboid cuboid = Cuboid.withLimit(this.playerData.getLastLocation(), this.playerData.getLocation(), 16)
                            .add(-0.5, 0.5, -0.5, 2.0, -0.5, 0.5);
                        Cuboid cuboid2 = Cuboid.withLimit(this.playerData.getLastLocation(), this.playerData.getLocation(), 16)
                            .add(-0.5, 0.5, -2.0, 0.0, -0.5, 0.5);
                        int totalTicks = this.playerData.getTotalTicks();
                        this.run(
                            () -> {
                                boolean waterLogged = this.playerData.getLocation().toBlock().isWaterLogged(world);
                                ClientVersion version = this.playerData.getVersion();
                                ServerVersion serverVersion = NMSManager.getInstance().getServerVersion();
                                if (waterLogged
                                    || !cuboid.checkBlocks(this.player, world, entry -> !MaterialList.BAD_VELOCITY.contains(entry))
                                    || !version.before(ClientVersion.V1_13)
                                        && !serverVersion.before(ServerVersion.v1_13_R2)
                                        && !cuboid2.checkBlocks(this.player, world, entry -> !MaterialList.BOBBLE.contains(entry))) {
                                    this.jumped = 0;
                                    this.violations = this.violations - Math.min(this.violations + 1.0, 0.05);
                                    this.lastBypassTick = totalTicks;
                                } else {
                                    this.handleViolation("", this.jumped - 1);
                                }
                            }
                        );
                    }

                    this.jumping = false;
                } else if (packet.getY() > this.lastY) {
                    this.jumping = true;
                }
            }
        } else if (MathUtil.onGround(this.playerData.getLocation().getY())
            || (
                        (
                                NMSManager.getInstance().getServerVersion().afterEq(ServerVersion.v1_18_R1)
                                    ? Math.abs(this.playerData.getLocation().getY())
                                    : this.playerData.getLocation().getY()
                            )
                            - 0.42F
                    )
                    % 1.0
                > 1.0E-15) {
            this.jumped = 0;
            this.jumping = false;
        }

        this.violations = this.violations - Math.min(this.violations + 5.0, 0.025);
        if (packet.isPos()) {
            this.lastY = packet.getY();
        }
    }
}
