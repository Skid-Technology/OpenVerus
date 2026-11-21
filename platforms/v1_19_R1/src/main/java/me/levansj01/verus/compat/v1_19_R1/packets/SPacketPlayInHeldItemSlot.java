package me.levansj01.verus.compat.v1_19_R1.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInHeldItemSlot;
import net.minecraft.network.protocol.game.PacketPlayInHeldItemSlot;

public class SPacketPlayInHeldItemSlot extends VPacketPlayInHeldItemSlot<PacketPlayInHeldItemSlot> {
    public void accept(PacketPlayInHeldItemSlot packet) {
        this.slot = packet.b();
    }
}
