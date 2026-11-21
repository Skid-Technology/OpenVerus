package me.levansj01.verus.type.premium.checks.killaura;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockDig;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInUseEntity;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(
    type = CheckType.KILL_AURA,
    subType = "F",
    friendlyName = "Auto Clicker",
    version = CheckVersion.RELEASE,
    maxViolations = 10,
    unsupportedAtleast = ClientVersion.V1_9
)
public class KillAuraF extends Check implements PacketHandler {
    private boolean place;
    private boolean dig;

    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        this.dig = this.place = false;
    }

    @Override
    public void handle(VPacketPlayInBlockPlace<?> packet) {
        this.place = true;
    }

    @Override
    public void handle(VPacketPlayInBlockDig<?> packet) {
        if (packet.getType() != VPacketPlayInBlockDig.PlayerDigType.DROP_ITEM && packet.getType() != VPacketPlayInBlockDig.PlayerDigType.DROP_ALL_ITEMS) {
            this.dig = true;
        }
    }

    @Override
    public void handle(VPacketPlayInUseEntity<?> packet) {
        if (!this.place && !this.playerData.isVehicle() && this.dig && packet.getAction() == VPacketPlayInUseEntity.EntityUseAction.ATTACK) {
            this.handleViolation("", 1.0);
        }
    }
}
