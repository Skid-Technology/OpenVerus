package me.levansj01.verus.type.custom.checks.scaffold;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.api.wrapper.BlockPosition;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;

@CheckInfo(
    type = CheckType.SCAFFOLD,
    subType = "B",
    friendlyName = "Scaffold",
    version = CheckVersion.RELEASE,
    schem = true,
    minViolations = -1.5,
    maxViolations = 10
)
public class ScaffoldB extends Check implements PacketHandler {
    private double lastX;
    private Float lastYaw;
    private BlockPosition lastBlockPosition;
    private double lastY;
    private double lastZ;

    @Override
    public void handle(VPacketPlayInBlockPlace<?> packet) {
        if (packet.getFace() != 255) {
            BlockPosition position = packet.getPosition();
            double blockX = packet.getBlockX();
            double blockY = packet.getBlockY();
            double blockZ = packet.getBlockZ();
            if (this.lastBlockPosition != null && position.nearby(this.lastBlockPosition) && blockX == this.lastX && blockY == this.lastY && blockZ == this.lastZ) {
                float yaw = this.playerData.getLocation().getYaw();
                if (this.lastYaw != null) {
                    double abs = Math.abs(yaw - this.lastYaw);
                    if (abs > 20.0) {
                        this.handleViolation();
                    } else {
                        this.decreaseVL(0.025);
                    }
                }

                this.lastYaw = yaw;
            }

            this.lastX = blockX;
            this.lastY = blockY;
            this.lastZ = blockZ;
            this.lastBlockPosition = position;
        }
    }

    public ScaffoldB() {
        this.lastZ = this.lastX = this.lastY = 0.0;
    }

    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        this.decreaseVL(0.025);
    }
}
