package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.api.wrapper.BlockPosition;

public abstract class VPacketPlayOutSpawnPosition<P> extends VPacket<P> {
    private static final int count = count();
    protected BlockPosition position;

    @Override
    public int ordinal() {
        return count;
    }

    @Override
    public void handle(PacketHandler listener) {
        listener.handle(this);
    }

    public BlockPosition getPosition() {
        return this.position;
    }
}
