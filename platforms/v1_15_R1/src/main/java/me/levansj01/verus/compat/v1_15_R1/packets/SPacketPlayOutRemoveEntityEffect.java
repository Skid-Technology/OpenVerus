package me.levansj01.verus.compat.v1_15_R1.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutRemoveEntityEffect;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_15_R1.MobEffectList;
import net.minecraft.server.v1_15_R1.PacketPlayOutRemoveEntityEffect;

public class SPacketPlayOutRemoveEntityEffect extends VPacketPlayOutRemoveEntityEffect<PacketPlayOutRemoveEntityEffect> {
    private static final Field a_field = SafeReflection.access(PacketPlayOutRemoveEntityEffect.class, "a");
    private static final Field b_field = SafeReflection.access(PacketPlayOutRemoveEntityEffect.class, "b");

    public void accept(PacketPlayOutRemoveEntityEffect packet) {
        this.id = SafeReflection.<Integer>fetch(a_field, packet);
        this.effect = MobEffectList.getId(SafeReflection.fetch(b_field, packet));
    }
}
