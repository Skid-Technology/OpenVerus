package me.levansj01.verus.data.transaction.effects;

public interface EffectUpdate {
    byte getEffect();

    int getDuration();

    byte getAmplifier();
}
