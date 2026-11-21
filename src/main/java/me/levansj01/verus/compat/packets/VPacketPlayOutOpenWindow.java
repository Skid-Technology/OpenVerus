package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;

public abstract class VPacketPlayOutOpenWindow<P> extends VPacket<P> {
    private static final int count = count();

    @Override
    public void handle(PacketHandler listener) {
        listener.handle(this);
    }

    @Override
    public int ordinal() {
        return count;
    }
}
