package me.levansj01.verus.compat.v1_12_R1.packets;

import me.levansj01.verus.compat.api.wrapper.BlockPosition;
import me.levansj01.verus.compat.api.wrapper.Direction;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockDig;
import net.minecraft.server.v1_12_R1.PacketPlayInBlockDig;

public class SPacketPlayInBlockDig extends VPacketPlayInBlockDig<PacketPlayInBlockDig> {
    public void accept(PacketPlayInBlockDig packet) {
        this.blockPosition = new BlockPosition(packet.a().getX(), packet.a().getY(), packet.a().getZ());
        this.face = Direction.values()[packet.b().ordinal()];
        this.type = VPacketPlayInBlockDig.PlayerDigType.values()[packet.c().ordinal()];
    }
}
