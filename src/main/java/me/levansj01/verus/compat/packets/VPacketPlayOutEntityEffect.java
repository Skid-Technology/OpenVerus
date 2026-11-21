package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.data.transaction.effects.Effect;
import me.levansj01.verus.data.transaction.effects.Effectable;

public abstract class VPacketPlayOutEntityEffect<P> extends VPacket<P> implements Effectable {
    private static final int count = count();
    protected byte effect;
    protected int id;
    protected byte flags;
    protected byte amplifier;
    protected int duration;

    public Effect toEffect() {
        return new Effect(this.duration, this.effect, this.amplifier);
    }

    public int getDuration() {
        return this.duration;
    }

    @Override
    public int ordinal() {
        return count;
    }

    public byte getAmplifier() {
        return this.amplifier;
    }

    public byte getEffect() {
        return this.effect;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void handle(PacketHandler listener) {
        listener.handle(this);
    }

    public byte getFlags() {
        return this.flags;
    }
}
