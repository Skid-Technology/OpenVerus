package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockDig;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.compat.packets.VPacketPlayInEntityAction;
import me.levansj01.verus.compat.packets.VPacketPlayInUseEntity;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.util.item.MaterialList;

@CheckInfo(
    type = CheckType.BAD_PACKETS,
    subType = "M",
    friendlyName = "NoSlowdown",
    version = CheckVersion.EXPERIMENTAL,
    unsupportedAtleast = ClientVersion.V1_9,
    unsupportedServerAtleast = ServerVersion.v1_11_R1,
    minViolations = -1.0
)
public class BadPacketsM extends Check implements PacketHandler {
    private int stage = 0;

    @Override
    public void handle(VPacketPlayInBlockDig<?> packet) {
        if (packet.getType() == VPacketPlayInBlockDig.PlayerDigType.RELEASE_USE_ITEM) {
            if (this.stage == 2) {
                this.stage = 0;
                this.decreaseVL(1.0);
            }
        }
    }

    @Override
    public void handle(VPacketPlayInUseEntity<?> packet) {
        if (packet.getAction() == VPacketPlayInUseEntity.EntityUseAction.ATTACK) {
            if (this.stage == 0 && this.playerData.isSprinting(false)) {
                this.stage = 1;
            } else if (this.stage == 2) {
                this.stage = 3;
            }
        }
    }

    @Override
    public void handle(VPacketPlayInBlockPlace<?> packet) {
        if (packet.isItem() && MaterialList.canPlace(packet.getItemStack())) {
            if (this.stage == 1) {
                this.stage = 2;
            } else if (this.stage == 3) {
                this.handleViolation();
                this.stage = 0;
            }
        }
    }

    @Override
    public void handle(VPacketPlayInEntityAction<?> packet) {
        VPacketPlayInEntityAction.PlayerAction action = packet.getAction();
        if (action.isSprint() && this.stage == 2) {
            this.stage = 0;
            this.decreaseVL(1.0);
        }
    }
}
