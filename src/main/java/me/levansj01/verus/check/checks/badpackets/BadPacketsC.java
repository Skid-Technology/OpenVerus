package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInArmAnimation;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInUseEntity;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(
    type = CheckType.BAD_PACKETS,
    subType = "C",
    friendlyName = "KillAura",
    version = CheckVersion.RELEASE,
    unsupportedAtleast = ClientVersion.V1_9,
    unsupportedServerAtleast = ServerVersion.v1_11_R1
)
public class BadPacketsC extends Check implements PacketHandler {
    private boolean received;

    @Override
    public void handle(VPacketPlayInArmAnimation<?> packet) {
        this.received = true;
    }

    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        this.received = false;
    }

    @Override
    public void handle(VPacketPlayInUseEntity<?> packet) {
        if (packet.getAction() == VPacketPlayInUseEntity.EntityUseAction.ATTACK && this.playerData.isSurvival()) {
            if (!this.received) {
                this.handleViolation();
            }

            this.received = false;
        }
    }
}
