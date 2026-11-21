package me.levansj01.verus.compat.v1_20_R3.packets;

import me.levansj01.verus.compat.api.wrapper.BlockPosition;
import me.levansj01.verus.compat.packets.VPacketPlayInUseItem;
import net.minecraft.core.EnumDirection;
import net.minecraft.network.protocol.game.PacketPlayInUseItem;
import net.minecraft.world.phys.MovingObjectPositionBlock;

public class SPacketPlayInUseItem extends VPacketPlayInUseItem<PacketPlayInUseItem> {
    public void accept(PacketPlayInUseItem packet) {
        MovingObjectPositionBlock movingObjectPositionBlock = packet.d();
        this.position = new BlockPosition(movingObjectPositionBlock.a().u(), movingObjectPositionBlock.a().v(), movingObjectPositionBlock.a().w());
        this.hand = packet.a().ordinal();
        EnumDirection direction = movingObjectPositionBlock.b();
        this.face = direction.ordinal();
        this.blockX = direction.j();
        this.blockY = direction.k();
        this.blockZ = direction.l();
        this.timestamp = packet.timestamp;
    }
}
