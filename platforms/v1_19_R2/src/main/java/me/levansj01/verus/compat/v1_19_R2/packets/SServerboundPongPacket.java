package me.levansj01.verus.compat.v1_19_R2.packets;

import me.levansj01.verus.compat.packets.VServerboundPongPacket;
import net.minecraft.network.protocol.game.ServerboundPongPacket;

public class SServerboundPongPacket extends VServerboundPongPacket<ServerboundPongPacket> {
    public void accept(ServerboundPongPacket packet) {
        this.id = packet.b();
    }
}
