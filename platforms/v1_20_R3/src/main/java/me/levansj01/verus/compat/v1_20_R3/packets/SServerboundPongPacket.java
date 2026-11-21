package me.levansj01.verus.compat.v1_20_R3.packets;

import me.levansj01.verus.compat.packets.VServerboundPongPacket;
import net.minecraft.network.protocol.common.ServerboundPongPacket;

public class SServerboundPongPacket extends VServerboundPongPacket<ServerboundPongPacket> {
    public void accept(ServerboundPongPacket packet) {
        this.id = packet.a();
    }
}
