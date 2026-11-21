package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockDig;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(
    type = CheckType.BAD_PACKETS,
    subType = "F",
    friendlyName = "KillAura",
    version = CheckVersion.RELEASE,
    minViolations = -1.0,
    maxViolations = 3,
    unsupportedAtleast = ClientVersion.V1_9
)
public class BadPacketsF extends Check implements PacketHandler {
    private boolean place = false;

    @Override
    public void handle(VPacketPlayInBlockDig<?> packet) {
        if (packet.getType() == VPacketPlayInBlockDig.PlayerDigType.RELEASE_USE_ITEM
            && this.place
            && !this.playerData.isTeleporting()
            && !this.playerData.isVehicle()) {
            this.handleViolation("", 1.0, false);
        }
    }

    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        this.place = false;
    }

    @Override
    public void handle(VPacketPlayInBlockPlace<?> packet) {
        if (packet.isItem()) {
            this.place = true;
        }
    }
}
