package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.api.Transactionable;

public abstract class VPacketPlayInTransaction<P> extends VPacket<P> implements Transactionable {
    private static final int count = count();
    protected short id;

    @Override
    public short id() {
        return this.id;
    }

    @Override
    public int ordinal() {
        return count;
    }

    public short getId() {
        return this.id;
    }

    @Override
    public void handle(PacketHandler listener) {
        listener.handleIn(this);
    }
}
