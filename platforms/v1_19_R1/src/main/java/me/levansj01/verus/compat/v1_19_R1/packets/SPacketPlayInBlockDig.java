package me.levansj01.verus.compat.v1_19_R1.packets;

import me.levansj01.verus.compat.api.wrapper.BlockPosition;
import me.levansj01.verus.compat.api.wrapper.Direction;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockDig;
import net.minecraft.network.protocol.game.PacketPlayInBlockDig;

public class SPacketPlayInBlockDig extends VPacketPlayInBlockDig<PacketPlayInBlockDig> {
    public void accept(PacketPlayInBlockDig packet) {
        this.blockPosition = new BlockPosition(packet.b().u(), packet.b().v(), packet.b().w());
        this.face = Direction.values()[packet.c().ordinal()];
        this.type = VPacketPlayInBlockDig.PlayerDigType.values()[packet.d().ordinal()];
    }
}
