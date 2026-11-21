package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;

public abstract class VPacketPlayInCustomPayload<P> extends VPacket<P> {
    private static final int count = count();
    protected String channel;
    protected int size;

    public String getChannel() {
        return this.channel;
    }

    @Override
    public int ordinal() {
        return count;
    }

    @Override
    public void handle(PacketHandler listener) {
        listener.handle(this);
    }

    public int getSize() {
        return this.size;
    }
}
