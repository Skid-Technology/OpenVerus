package me.levansj01.verus.compat.v1_16_R1.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutEntityTeleport;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_16_R1.PacketPlayOutEntityTeleport;

public class SPacketPlayOutEntityTeleport extends VPacketPlayOutEntityTeleport<PacketPlayOutEntityTeleport> {
    private static final Field id_field = SafeReflection.access(PacketPlayOutEntityTeleport.class, "a");
    private static final Field x_field = SafeReflection.access(PacketPlayOutEntityTeleport.class, "b");
    private static final Field y_field = SafeReflection.access(PacketPlayOutEntityTeleport.class, "c");
    private static final Field z_field = SafeReflection.access(PacketPlayOutEntityTeleport.class, "d");
    private static final Field yaw_field = SafeReflection.access(PacketPlayOutEntityTeleport.class, "e");
    private static final Field pitch_field = SafeReflection.access(PacketPlayOutEntityTeleport.class, "f");
    private static final Field ground_field = SafeReflection.access(PacketPlayOutEntityTeleport.class, "g");

    public void accept(PacketPlayOutEntityTeleport packet) {
        this.id = SafeReflection.<Integer>fetch(id_field, packet);
        this.x = SafeReflection.<Double>fetch(x_field, packet);
        this.y = SafeReflection.<Double>fetch(y_field, packet);
        this.z = SafeReflection.<Double>fetch(z_field, packet);
        this.yaw = SafeReflection.<Byte>fetch(yaw_field, packet);
        this.pitch = SafeReflection.<Byte>fetch(pitch_field, packet);
        this.ground = SafeReflection.<Boolean>fetch(ground_field, packet);
    }
}
