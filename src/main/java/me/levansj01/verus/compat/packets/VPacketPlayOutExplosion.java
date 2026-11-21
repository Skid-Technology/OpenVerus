package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;

public abstract class VPacketPlayOutExplosion<P> extends VPacket<P> {
    private static final int count = count();
    protected float motY;
    protected float motX;
    protected float motZ;

    @Override
    public void handle(PacketHandler listener) {
        listener.handle(this);
    }

    public float getMotY() {
        return this.motY;
    }

    @Override
    public int ordinal() {
        return count;
    }

    public float getMotX() {
        return this.motX;
    }

    public float getMotZ() {
        return this.motZ;
    }
}
