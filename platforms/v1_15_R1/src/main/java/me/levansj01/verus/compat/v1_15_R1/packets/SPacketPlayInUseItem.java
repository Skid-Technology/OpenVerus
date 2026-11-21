package me.levansj01.verus.compat.v1_15_R1.packets;

import me.levansj01.verus.compat.api.wrapper.BlockPosition;
import me.levansj01.verus.compat.packets.VPacketPlayInUseItem;
import net.minecraft.server.v1_15_R1.EnumDirection;
import net.minecraft.server.v1_15_R1.MovingObjectPositionBlock;
import net.minecraft.server.v1_15_R1.PacketPlayInUseItem;

public class SPacketPlayInUseItem extends VPacketPlayInUseItem<PacketPlayInUseItem> {
    public void accept(PacketPlayInUseItem packet) {
        MovingObjectPositionBlock movingObjectPositionBlock = packet.c();
        this.position = new BlockPosition(movingObjectPositionBlock.getBlockPosition().getX(), movingObjectPositionBlock.getBlockPosition().getY(), movingObjectPositionBlock.getBlockPosition().getZ());
        this.hand = packet.b().ordinal();
        EnumDirection direction = movingObjectPositionBlock.getDirection();
        this.face = direction.ordinal();
        this.blockX = direction.getAdjacentX();
        this.blockY = direction.getAdjacentY();
        this.blockZ = direction.getAdjacentZ();
        this.timestamp = packet.timestamp;
    }
}
