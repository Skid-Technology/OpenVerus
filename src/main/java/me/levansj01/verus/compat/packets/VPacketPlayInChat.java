package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;

public abstract class VPacketPlayInChat<P> extends VPacket<P> {
    private static final int count = count();
    protected boolean command;
    protected String message;

    @Override
    public void handle(PacketHandler listener) {
        listener.handle(this);
    }

    @Override
    public int ordinal() {
        return count;
    }

    public boolean isCommand() {
        return this.command;
    }

    public String getMessage() {
        return this.message;
    }
}
