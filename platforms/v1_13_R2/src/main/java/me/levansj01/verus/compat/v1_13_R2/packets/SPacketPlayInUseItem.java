package me.levansj01.verus.compat.v1_13_R2.packets;

import me.levansj01.verus.compat.api.wrapper.BlockPosition;
import me.levansj01.verus.compat.packets.VPacketPlayInUseItem;
import net.minecraft.server.v1_13_R2.PacketPlayInUseItem;

public class SPacketPlayInUseItem extends VPacketPlayInUseItem<PacketPlayInUseItem> {
    public void accept(PacketPlayInUseItem packet) {
        this.position = new BlockPosition(packet.b().getX(), packet.b().getY(), packet.b().getZ());
        this.face = packet.c().ordinal();
        this.blockX = packet.e();
        this.blockY = packet.f();
        this.blockZ = packet.g();
        this.timestamp = packet.timestamp;
    }
}
