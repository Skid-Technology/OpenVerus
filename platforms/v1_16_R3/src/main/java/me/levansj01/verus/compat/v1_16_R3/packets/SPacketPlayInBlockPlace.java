package me.levansj01.verus.compat.v1_16_R3.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import net.minecraft.server.v1_16_R3.PacketPlayInBlockPlace;

public class SPacketPlayInBlockPlace extends VPacketPlayInBlockPlace<PacketPlayInBlockPlace> {
    public void accept(PacketPlayInBlockPlace packet) {
        this.timestamp = packet.timestamp;
        this.empty = true;
        this.hand = packet.b().ordinal();
    }
}
