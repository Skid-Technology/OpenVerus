package me.levansj01.verus.compat.v1_7_R4.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutEntityEffect;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityEffect;

public class SPacketPlayOutEntityEffect extends VPacketPlayOutEntityEffect<PacketPlayOutEntityEffect> {
    private static final Field a_field = SafeReflection.access(PacketPlayOutEntityEffect.class, "a");
    private static final Field b_field = SafeReflection.access(PacketPlayOutEntityEffect.class, "b");
    private static final Field c_field = SafeReflection.access(PacketPlayOutEntityEffect.class, "c");
    private static final Field d_field = SafeReflection.access(PacketPlayOutEntityEffect.class, "d");

    public void accept(PacketPlayOutEntityEffect packet) {
        this.id = SafeReflection.<Integer>fetch(a_field, packet);
        this.effect = SafeReflection.<Byte>fetch(b_field, packet);
        this.amplifier = SafeReflection.<Byte>fetch(c_field, packet);
        this.duration = SafeReflection.<Short>fetch(d_field, packet).intValue();
        this.flags = 0;
    }
}
