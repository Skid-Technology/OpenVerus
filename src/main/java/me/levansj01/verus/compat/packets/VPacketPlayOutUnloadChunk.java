package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;

public abstract class VPacketPlayOutUnloadChunk<P> extends VPacket<P> {
    private static final int count = count();
    protected int z;
    protected int x;

    public int getX() {
        return this.x;
    }

    @Override
    public int ordinal() {
        return count;
    }

    @Override
    public void handle(PacketHandler listener) {
        listener.handle(this);
    }

    public int getZ() {
        return this.z;
    }
}
