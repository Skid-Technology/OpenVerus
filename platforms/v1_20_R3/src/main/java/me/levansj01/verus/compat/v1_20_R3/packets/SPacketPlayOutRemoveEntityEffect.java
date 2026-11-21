package me.levansj01.verus.compat.v1_20_R3.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutRemoveEntityEffect;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.protocol.game.PacketPlayOutRemoveEntityEffect;
import net.minecraft.world.effect.MobEffectList;
import org.bukkit.craftbukkit.v1_20_R3.CraftRegistry;

public class SPacketPlayOutRemoveEntityEffect extends VPacketPlayOutRemoveEntityEffect<PacketPlayOutRemoveEntityEffect> {
    private static final Field a_field = SafeReflection.access(PacketPlayOutRemoveEntityEffect.class, "a");
    private static final Field b_field = SafeReflection.access(PacketPlayOutRemoveEntityEffect.class, "b");

    public void accept(PacketPlayOutRemoveEntityEffect packet) {
        this.id = SafeReflection.<Integer>fetch(a_field, packet);
        this.effect = CraftRegistry.getMinecraftRegistry(Registries.Q).a((MobEffectList)SafeReflection.fetch(b_field, packet));
    }
}
