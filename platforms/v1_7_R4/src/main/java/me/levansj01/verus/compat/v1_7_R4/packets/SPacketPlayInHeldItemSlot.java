package me.levansj01.verus.compat.v1_7_R4.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInHeldItemSlot;
import net.minecraft.server.v1_7_R4.PacketPlayInHeldItemSlot;

public class SPacketPlayInHeldItemSlot extends VPacketPlayInHeldItemSlot<PacketPlayInHeldItemSlot> {
    public void accept(PacketPlayInHeldItemSlot packet) {
        this.slot = packet.c();
    }
}
