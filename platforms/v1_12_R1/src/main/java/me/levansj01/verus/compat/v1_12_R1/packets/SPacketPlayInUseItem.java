package me.levansj01.verus.compat.v1_12_R1.packets;

import me.levansj01.verus.compat.api.wrapper.BlockPosition;
import me.levansj01.verus.compat.packets.VPacketPlayInUseItem;
import net.minecraft.server.v1_12_R1.PacketPlayInUseItem;

public class SPacketPlayInUseItem extends VPacketPlayInUseItem<PacketPlayInUseItem> {
    public void accept(PacketPlayInUseItem packet) {
        this.position = new BlockPosition(packet.a().getX(), packet.a().getY(), packet.a().getZ());
        this.face = packet.b().ordinal();
        this.blockX = packet.d();
        this.blockY = packet.e();
        this.blockZ = packet.f();
        this.timestamp = packet.timestamp;
    }
}
