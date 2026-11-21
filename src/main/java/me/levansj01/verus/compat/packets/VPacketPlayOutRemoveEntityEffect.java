package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.data.transaction.effects.Effectable;
import me.levansj01.verus.data.transaction.effects.RemovedEffect;

public abstract class VPacketPlayOutRemoveEntityEffect<P> extends VPacket<P> implements Effectable {
    private static final int count = count();
    protected int id;
    protected int effect;

    public int getEffect() {
        return this.effect;
    }

    @Override
    public int ordinal() {
        return count;
    }

    @Override
    public void handle(PacketHandler listener) {
        listener.handle(this);
    }

    public RemovedEffect toEffect() {
        return new RemovedEffect(this.effect);
    }

    @Override
    public int getId() {
        return this.id;
    }
}
