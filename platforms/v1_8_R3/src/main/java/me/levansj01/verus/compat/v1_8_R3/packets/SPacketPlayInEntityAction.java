package me.levansj01.verus.compat.v1_8_R3.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInEntityAction;
import net.minecraft.server.v1_8_R3.PacketPlayInEntityAction;

public class SPacketPlayInEntityAction extends VPacketPlayInEntityAction<PacketPlayInEntityAction> {
    public void accept(PacketPlayInEntityAction packet) {
        this.action = VPacketPlayInEntityAction.PlayerAction.values()[packet.b().ordinal()];
        this.value = packet.c();
    }
}
