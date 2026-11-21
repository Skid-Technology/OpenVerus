package me.levansj01.verus.compat.v1_19_R1.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutRemoveEntityEffect;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.network.protocol.game.PacketPlayOutRemoveEntityEffect;
import net.minecraft.world.effect.MobEffectList;

public class SPacketPlayOutRemoveEntityEffect extends VPacketPlayOutRemoveEntityEffect<PacketPlayOutRemoveEntityEffect> {
    private static final Field a_field = SafeReflection.access(PacketPlayOutRemoveEntityEffect.class, "a");
    private static final Field b_field = SafeReflection.access(PacketPlayOutRemoveEntityEffect.class, "b");

    public void accept(PacketPlayOutRemoveEntityEffect packet) {
        this.id = SafeReflection.<Integer>fetch(a_field, packet);
        this.effect = MobEffectList.a(SafeReflection.fetch(b_field, packet));
    }
}
