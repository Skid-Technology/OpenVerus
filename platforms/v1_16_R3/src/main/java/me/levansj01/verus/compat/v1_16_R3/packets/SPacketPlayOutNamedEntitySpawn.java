package me.levansj01.verus.compat.v1_16_R3.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutNamedEntitySpawn;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_16_R3.PacketPlayOutNamedEntitySpawn;

public class SPacketPlayOutNamedEntitySpawn extends VPacketPlayOutNamedEntitySpawn<PacketPlayOutNamedEntitySpawn> {
    private static final Field id_field = SafeReflection.access(PacketPlayOutNamedEntitySpawn.class, "a");
    private static final Field x_field = SafeReflection.access(PacketPlayOutNamedEntitySpawn.class, "c");
    private static final Field y_field = SafeReflection.access(PacketPlayOutNamedEntitySpawn.class, "d");
    private static final Field z_field = SafeReflection.access(PacketPlayOutNamedEntitySpawn.class, "e");
    private static final Field yaw_field = SafeReflection.access(PacketPlayOutNamedEntitySpawn.class, "f");
    private static final Field pitch_field = SafeReflection.access(PacketPlayOutNamedEntitySpawn.class, "g");

    public void accept(PacketPlayOutNamedEntitySpawn packet) {
        this.id = SafeReflection.<Integer>fetch(id_field, packet);
        this.x = SafeReflection.<Double>fetch(x_field, packet);
        this.y = SafeReflection.<Double>fetch(y_field, packet);
        this.z = SafeReflection.<Double>fetch(z_field, packet);
        this.yaw = SafeReflection.<Byte>fetch(yaw_field, packet);
        this.pitch = SafeReflection.<Byte>fetch(pitch_field, packet);
    }
}
