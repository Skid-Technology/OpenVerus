package me.levansj01.verus.data.transaction.effects;

public class RemovedEffect implements EffectUpdate {
    private final int effect;

    @Override
    public byte getEffect() {
        return (byte)this.effect;
    }

    public RemovedEffect(int effect) {
        this.effect = effect;
    }

    @Override
    public byte getAmplifier() {
        return -1;
    }

    @Override
    public int getDuration() {
        return 0;
    }
}
