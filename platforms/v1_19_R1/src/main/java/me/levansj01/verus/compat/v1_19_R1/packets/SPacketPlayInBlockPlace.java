package me.levansj01.verus.compat.v1_19_R1.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import net.minecraft.network.protocol.game.PacketPlayInBlockPlace;

public class SPacketPlayInBlockPlace extends VPacketPlayInBlockPlace<PacketPlayInBlockPlace> {
    public void accept(PacketPlayInBlockPlace packet) {
        this.timestamp = 0L;
        this.empty = true;
        this.hand = packet.b().ordinal();
    }
}
