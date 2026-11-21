package me.levansj01.verus.check.checks.killaura;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInUseEntity;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(
    type = CheckType.KILL_AURA,
    subType = "E",
    friendlyName = "KillAura",
    version = CheckVersion.RELEASE,
    unsupportedAtleast = ClientVersion.V1_9,
    maxViolations = 10
)
public class KillAuraE extends Check implements PacketHandler {
    private boolean place = false;

    @Override
    public void handle(VPacketPlayInBlockPlace<?> packet) {
        this.place = true;
    }

    @Override
    public void handle(VPacketPlayInUseEntity<?> packet) {
        if (this.place && packet.getAction() == VPacketPlayInUseEntity.EntityUseAction.ATTACK) {
            this.handleViolation("", 1.0, true);
        }
    }

    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        this.place = false;
    }
}
