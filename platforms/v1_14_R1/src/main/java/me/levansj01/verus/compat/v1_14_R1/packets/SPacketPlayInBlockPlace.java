package me.levansj01.verus.compat.v1_14_R1.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import net.minecraft.server.v1_14_R1.PacketPlayInBlockPlace;

public class SPacketPlayInBlockPlace extends VPacketPlayInBlockPlace<PacketPlayInBlockPlace> {
    public void accept(PacketPlayInBlockPlace packet) {
        this.timestamp = packet.timestamp;
        this.empty = true;
        this.hand = packet.b().ordinal();
    }
}
