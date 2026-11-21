package me.levansj01.verus.compat.v1_19_R1.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutEntityEffect;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.network.protocol.game.PacketPlayOutEntityEffect;
import net.minecraft.world.effect.MobEffectList;

public class SPacketPlayOutEntityEffect extends VPacketPlayOutEntityEffect<PacketPlayOutEntityEffect> {
    private static final Field d_field = SafeReflection.access(PacketPlayOutEntityEffect.class, "e");
    private static final Field e_field = SafeReflection.access(PacketPlayOutEntityEffect.class, "f");
    private static final Field f_field = SafeReflection.access(PacketPlayOutEntityEffect.class, "g");
    private static final Field g_field = SafeReflection.access(PacketPlayOutEntityEffect.class, "h");
    private static final Field h_field = SafeReflection.access(PacketPlayOutEntityEffect.class, "i");

    public void accept(PacketPlayOutEntityEffect packet) {
        this.id = SafeReflection.<Integer>fetch(d_field, packet);
        this.effect = (byte)MobEffectList.a(SafeReflection.fetch(e_field, packet));
        this.amplifier = SafeReflection.<Byte>fetch(f_field, packet);
        this.duration = SafeReflection.<Integer>fetch(g_field, packet);
        this.flags = SafeReflection.<Byte>fetch(h_field, packet);
    }
}
