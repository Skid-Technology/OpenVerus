package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;

public abstract class VPacketPlayInArmAnimation<P> extends VPacket<P> {
    private static final int count = count();
    protected int hand = 0;

    @Override
    public int ordinal() {
        return count;
    }

    public int getHand() {
        return this.hand;
    }

    @Override
    public void handle(PacketHandler listener) {
        listener.handle(this);
    }
}
