package me.levansj01.verus.data.transaction.effects;

public class Effect implements EffectUpdate {
    private final byte effect;
    private final int duration;
    private final byte amplifier;

    @Override
    public byte getEffect() {
        return this.effect;
    }

    protected boolean canEqual(Object obj) {
        return obj instanceof Effect;
    }

    public Effect(int duration, byte effect, byte amplifier) {
        this.duration = duration;
        this.effect = effect;
        this.amplifier = amplifier;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof Effect)) {
            return false;
        } else {
            Effect effect = (Effect)obj;
            if (!effect.canEqual(this)) {
                return false;
            } else if (this.getDuration() != effect.getDuration()) {
                return false;
            } else {
                return this.getEffect() != effect.getEffect() ? false : this.getAmplifier() == effect.getAmplifier();
            }
        }
    }

    @Override
    public byte getAmplifier() {
        return this.amplifier;
    }

    @Override
    public int getDuration() {
        return this.duration;
    }

    @Override
    public String toString() {
        return "Effect(duration=" + this.getDuration() + ", effect=" + this.getEffect() + ", amplifier=" + this.getAmplifier() + ")";
    }

    @Override
    public int hashCode() {
        byte b = 59;
        int value = 1;
        value = value * 59 + this.getDuration();
        value = value * 59 + this.getEffect();
        return value * 59 + this.getAmplifier();
    }
}
