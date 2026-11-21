package me.levansj01.verus.check.checks.killaura;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.api.wrapper.BlockPosition;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInUseEntity;

@CheckInfo(
    type = CheckType.KILL_AURA,
    subType = "M",
    friendlyName = "KillAura",
    version = CheckVersion.RELEASE,
    unsupportedServerAtleast = ServerVersion.v1_11_R1,
    maxViolations = 10
)
public class KillAuraM extends Check implements PacketHandler {
    private boolean place;
    private boolean attack;

    @Override
    public void handle(VPacketPlayInBlockPlace<?> packet) {
        BlockPosition position = packet.getPosition();
        if (position.getX() != -1 && position.getY() != -1 && position.getZ() != -1) {
            this.place = true;
        }
    }

    @Override
    public void handle(VPacketPlayInUseEntity<?> packet) {
        if (packet.getAction() == VPacketPlayInUseEntity.EntityUseAction.ATTACK) {
            this.attack = true;
        }
    }

    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        if (this.place && this.attack) {
            this.handleViolation("", 1.0, true);
        }

        this.place = this.attack = false;
    }
}
