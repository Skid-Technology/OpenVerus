package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;

public abstract class VPacketPlayOutGameStateChange<P> extends VPacket<P> {
    private static final int count = count();
    protected float value;
    protected int setting;

    public float getValue() {
        return this.value;
    }

    public int getSetting() {
        return this.setting;
    }

    @Override
    public int ordinal() {
        return count;
    }

    @Override
    public void handle(PacketHandler listener) {
        listener.handle(this);
    }
}
