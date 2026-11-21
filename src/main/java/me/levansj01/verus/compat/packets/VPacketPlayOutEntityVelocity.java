package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;

public abstract class VPacketPlayOutEntityVelocity<P> extends VPacket<P> {
    private static final int count = count();
    protected int motX;
    protected int id;
    protected int motY;
    protected int motZ;

    public int getMotY() {
        return this.motY;
    }

    @Override
    public int ordinal() {
        return count;
    }

    public int getId() {
        return this.id;
    }

    public int getMotZ() {
        return this.motZ;
    }

    @Override
    public void handle(PacketHandler listener) {
        listener.handle(this);
    }

    public int getMotX() {
        return this.motX;
    }
}
