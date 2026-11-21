package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.api.Transactionable;

public abstract class VPacketPlayOutTransaction<P> extends VPacket<P> implements Transactionable {
    private static final int count = count();
    protected int window;
    protected boolean accepted;
    protected short id;

    public int getWindow() {
        return this.window;
    }

    @Override
    public void handle(PacketHandler listener) {
        listener.handleOut(this);
    }

    public boolean isAccepted() {
        return this.accepted;
    }

    @Override
    public int ordinal() {
        return count;
    }

    @Override
    public boolean valid() {
        return this.window == 0 && !this.accepted;
    }

    public short getId() {
        return this.id;
    }

    @Override
    public short id() {
        return this.id;
    }
}
