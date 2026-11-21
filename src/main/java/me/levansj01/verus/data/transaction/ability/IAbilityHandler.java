package me.levansj01.verus.data.transaction.ability;

import java.util.Deque;
import java.util.function.Predicate;
import me.levansj01.verus.compat.api.wrapper.PlayerAbilities;
import me.levansj01.verus.compat.packets.VPacketPlayOutAbilities;
import me.levansj01.verus.compat.packets.VPacketPlayOutGameStateChange;
import me.levansj01.verus.compat.packets.VPacketPlayOutRespawn;
import me.levansj01.verus.util.java.JavaV;
import org.bukkit.GameMode;

public interface IAbilityHandler {
    void handle(VPacketPlayOutRespawn<?> packet);

    void handle(VPacketPlayOutGameStateChange<?> packet);

    default boolean any(Predicate<PlayerAbilities> predicate) {
        PlayerAbilities abilities;
        Deque deque;
        return (abilities = this.getAbilities()) == null || predicate.test(abilities) || !(deque = this.getAbilitiesQueue()).isEmpty() && JavaV.anyMatch(predicate, deque);
    }

    PlayerAbilities getAbilities();

    boolean isSurvival();

    boolean isSurvivalOrAdventure();

    boolean possible(GameMode gameMode);

    default boolean all(Predicate<PlayerAbilities> predicate) {
        PlayerAbilities abilities;
        Deque deque;
        return (abilities = this.getAbilities()) != null && predicate.test(abilities) && ((deque = this.getAbilitiesQueue()).isEmpty() || JavaV.allMatch(predicate, deque));
    }

    boolean canFly();

    Deque<PlayerAbilities> getAbilitiesQueue();

    void handle(VPacketPlayOutAbilities<?> packet);
}
