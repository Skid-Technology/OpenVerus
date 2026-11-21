package me.levansj01.verus.compat.v1_19_R1.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInEntityAction;
import net.minecraft.network.protocol.game.PacketPlayInEntityAction;

public class SPacketPlayInEntityAction extends VPacketPlayInEntityAction<PacketPlayInEntityAction> {
    public void accept(PacketPlayInEntityAction packet) {
        switch (packet.c()) {
            case a:
                this.action = VPacketPlayInEntityAction.PlayerAction.START_SNEAKING;
                break;
            case b:
                this.action = VPacketPlayInEntityAction.PlayerAction.STOP_SNEAKING;
                break;
            case c:
                this.action = VPacketPlayInEntityAction.PlayerAction.STOP_SLEEPING;
                break;
            case d:
                this.action = VPacketPlayInEntityAction.PlayerAction.START_SPRINTING;
                break;
            case e:
                this.action = VPacketPlayInEntityAction.PlayerAction.STOP_SPRINTING;
                break;
            case f:
            case g:
                this.action = VPacketPlayInEntityAction.PlayerAction.RIDING_JUMP;
                break;
            case h:
                this.action = VPacketPlayInEntityAction.PlayerAction.OPEN_INVENTORY;
                break;
            case i:
                this.action = VPacketPlayInEntityAction.PlayerAction.START_FALL_FLYING;
        }

        this.value = packet.d();
    }
}
