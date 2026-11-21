package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;

public abstract class VPacketPlayOutSpawnEntityLiving<P> extends VPacket<P> {
    private static final int count = count();
    protected int id;

    @Override
    public int ordinal() {
        return count;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public void handle(PacketHandler listener) {
        listener.handle(this);
    }
}
