package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.api.wrapper.BlockPosition;
import me.levansj01.verus.compat.api.wrapper.Direction;

public abstract class VPacketPlayInBlockDig<P> extends VPacket<P> {
    private static final int count = count();
    protected BlockPosition blockPosition;
    protected Direction face;
    protected VPacketPlayInBlockDig.PlayerDigType type;

    @Override
    public int ordinal() {
        return count;
    }

    public BlockPosition getBlockPosition() {
        return this.blockPosition;
    }

    @Override
    public void handle(PacketHandler listener) {
        listener.handle(this);
    }

    public Direction getFace() {
        return this.face;
    }

    public VPacketPlayInBlockDig.PlayerDigType getType() {
        return this.type;
    }

    public enum PlayerDigType {
        START_DESTROY_BLOCK,
        ABORT_DESTROY_BLOCK,
        STOP_DESTROY_BLOCK,
        DROP_ALL_ITEMS,
        DROP_ITEM,
        RELEASE_USE_ITEM,
        SWAP_HELD_ITEMS;
    }
}
