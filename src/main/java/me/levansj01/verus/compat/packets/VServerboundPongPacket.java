package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.api.Transactionable;

public abstract class VServerboundPongPacket<P> extends VPacket<P> implements Transactionable {
    private static final int count = count();
    protected int id;

    @Override
    public void handle(PacketHandler listener) {
        listener.handleIn(this);
    }

    @Override
    public int ordinal() {
        return count;
    }

    @Override
    public short id() {
        return (short)this.id;
    }

    public int getId() {
        return this.id;
    }
}
