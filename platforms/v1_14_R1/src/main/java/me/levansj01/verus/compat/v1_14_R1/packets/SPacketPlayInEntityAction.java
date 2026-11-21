package me.levansj01.verus.compat.v1_14_R1.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInEntityAction;
import net.minecraft.server.v1_14_R1.PacketPlayInEntityAction;

public class SPacketPlayInEntityAction extends VPacketPlayInEntityAction<PacketPlayInEntityAction> {
    public void accept(PacketPlayInEntityAction packet) {
        switch (packet.c()) {
            case START_SNEAKING:
                this.action = VPacketPlayInEntityAction.PlayerAction.START_SNEAKING;
                break;
            case STOP_SNEAKING:
                this.action = VPacketPlayInEntityAction.PlayerAction.STOP_SNEAKING;
                break;
            case STOP_SLEEPING:
                this.action = VPacketPlayInEntityAction.PlayerAction.STOP_SLEEPING;
                break;
            case START_SPRINTING:
                this.action = VPacketPlayInEntityAction.PlayerAction.START_SPRINTING;
                break;
            case STOP_SPRINTING:
                this.action = VPacketPlayInEntityAction.PlayerAction.STOP_SPRINTING;
                break;
            case START_RIDING_JUMP:
            case STOP_RIDING_JUMP:
                this.action = VPacketPlayInEntityAction.PlayerAction.RIDING_JUMP;
                break;
            case OPEN_INVENTORY:
                this.action = VPacketPlayInEntityAction.PlayerAction.OPEN_INVENTORY;
                break;
            case START_FALL_FLYING:
                this.action = VPacketPlayInEntityAction.PlayerAction.START_FALL_FLYING;
        }

        this.value = packet.d();
    }
}
