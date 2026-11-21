package me.levansj01.verus.compat.v1_7_R4.packets;

import me.levansj01.verus.compat.api.wrapper.BlockPosition;
import me.levansj01.verus.compat.api.wrapper.Direction;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockDig;
import net.minecraft.server.v1_7_R4.PacketPlayInBlockDig;

public class SPacketPlayInBlockDig extends VPacketPlayInBlockDig<PacketPlayInBlockDig> {
    public void accept(PacketPlayInBlockDig packet) {
        this.blockPosition = new BlockPosition(packet.c(), packet.d(), packet.e());
        this.face = Direction.values()[packet.f() % Direction.values().length];
        this.type = VPacketPlayInBlockDig.PlayerDigType.values()[packet.g()];
    }
}
