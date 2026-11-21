package me.levansj01.verus.data.transaction.effects;

import java.util.Collection;
import org.bukkit.potion.PotionEffectType;

public interface IEffectHandler {
    int SLOW_FALLING = 28;
    int DOLPHINS_GRACE = 30;
    int LEVITATION = 25;

    default boolean possible(PotionEffectType effectType) {
        return this.possible(effectType.getId());
    }

    default Collection<Integer> all(PotionEffectType effectType) {
        return this.all(effectType.getId());
    }

    boolean possible(int value);

    default int max(PotionEffectType effectType) {
        return this.max(effectType.getId());
    }

    Collection<Integer> all(int value);

    int max(int value);

    void handle(Effectable effectable);
}
