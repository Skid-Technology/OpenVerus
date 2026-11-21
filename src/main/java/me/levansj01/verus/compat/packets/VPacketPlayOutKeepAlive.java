package me.levansj01.verus.compat.packets;

import java.math.BigInteger;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;

public abstract class VPacketPlayOutKeepAlive<P> extends VPacket<P> {
    private static final int count = count();
    protected BigInteger id;

    @Override
    public void handle(PacketHandler listener) {
        listener.handle(this);
    }

    public long getId() {
        return this.id.longValueExact();
    }

    @Override
    public int ordinal() {
        return count;
    }
}
