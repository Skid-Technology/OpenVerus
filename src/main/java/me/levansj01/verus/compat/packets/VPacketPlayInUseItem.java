package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.api.wrapper.BlockPosition;

public abstract class VPacketPlayInUseItem<P> extends VPacket<P> {
    private static final int count = count();
    protected float blockZ;
    protected float blockX;
    protected float blockY;
    protected long timestamp;
    protected int face;
    protected BlockPosition position;
    protected int hand;

    public float getBlockX() {
        return this.blockX;
    }

    public int getHand() {
        return this.hand;
    }

    @Override
    public void handle(PacketHandler listener) {
        listener.handle(this);
    }

    @Override
    public int ordinal() {
        return count;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public float getBlockY() {
        return this.blockY;
    }

    public float getBlockZ() {
        return this.blockZ;
    }

    public int getFace() {
        return this.face;
    }

    public BlockPosition getPosition() {
        return this.position;
    }
}
