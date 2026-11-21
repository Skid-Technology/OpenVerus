package me.levansj01.verus.compat.v1_16_R1.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutEntityLook;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_16_R1.PacketPlayOutEntity;

public class SPacketPlayOutEntityLook extends VPacketPlayOutEntityLook<PacketPlayOutEntity> {
    private static final Field id_field = SafeReflection.access(PacketPlayOutEntity.class, "a");
    private static final Field x_field = SafeReflection.access(PacketPlayOutEntity.class, "b");
    private static final Field y_field = SafeReflection.access(PacketPlayOutEntity.class, "c");
    private static final Field z_field = SafeReflection.access(PacketPlayOutEntity.class, "d");
    private static final Field yaw_field = SafeReflection.access(PacketPlayOutEntity.class, "e");
    private static final Field pitch_field = SafeReflection.access(PacketPlayOutEntity.class, "f");
    private static final Field ground_field = SafeReflection.access(PacketPlayOutEntity.class, "g");
    private static final Field look_field = SafeReflection.access(PacketPlayOutEntity.class, "h");

    public void accept(PacketPlayOutEntity packet) {
        this.id = SafeReflection.<Integer>fetch(id_field, packet);
        this.x = SafeReflection.fetch(x_field, packet, short.class);
        this.y = SafeReflection.fetch(y_field, packet, short.class);
        this.z = SafeReflection.fetch(z_field, packet, short.class);
        this.yaw = SafeReflection.<Byte>fetch(yaw_field, packet);
        this.pitch = SafeReflection.<Byte>fetch(pitch_field, packet);
        this.ground = SafeReflection.<Boolean>fetch(ground_field, packet);
        this.look = SafeReflection.<Boolean>fetch(look_field, packet);
    }
}
