package me.levansj01.verus.compat.v1_15_R1.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutEntityEffect;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_15_R1.PacketPlayOutEntityEffect;

public class SPacketPlayOutEntityEffect extends VPacketPlayOutEntityEffect<PacketPlayOutEntityEffect> {
    private static final Field a_field = SafeReflection.access(PacketPlayOutEntityEffect.class, "a");
    private static final Field b_field = SafeReflection.access(PacketPlayOutEntityEffect.class, "b");
    private static final Field c_field = SafeReflection.access(PacketPlayOutEntityEffect.class, "c");
    private static final Field d_field = SafeReflection.access(PacketPlayOutEntityEffect.class, "d");
    private static final Field e_field = SafeReflection.access(PacketPlayOutEntityEffect.class, "e");

    public void accept(PacketPlayOutEntityEffect packet) {
        this.id = SafeReflection.<Integer>fetch(a_field, packet);
        this.effect = SafeReflection.<Byte>fetch(b_field, packet);
        this.amplifier = SafeReflection.<Byte>fetch(c_field, packet);
        this.duration = SafeReflection.<Integer>fetch(d_field, packet);
        this.flags = SafeReflection.<Byte>fetch(e_field, packet);
    }
}
