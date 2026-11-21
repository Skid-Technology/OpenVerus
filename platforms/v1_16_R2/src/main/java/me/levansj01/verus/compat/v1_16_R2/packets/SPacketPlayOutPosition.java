package me.levansj01.verus.compat.v1_16_R2.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutPosition;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_16_R2.PacketPlayOutPosition;

public class SPacketPlayOutPosition extends VPacketPlayOutPosition<PacketPlayOutPosition> {
    private static final Field x_field = SafeReflection.access(PacketPlayOutPosition.class, "a", "x");
    private static final Field y_field = SafeReflection.access(PacketPlayOutPosition.class, "b", "y");
    private static final Field z_field = SafeReflection.access(PacketPlayOutPosition.class, "c", "z");
    private static final Field yaw_field = SafeReflection.access(PacketPlayOutPosition.class, "d", "yaw");
    private static final Field pitch_field = SafeReflection.access(PacketPlayOutPosition.class, "e", "pitch");
    private static final Field flags_field = SafeReflection.access(PacketPlayOutPosition.class, "f", "flags");

    public void accept(PacketPlayOutPosition packet) {
        this.x = SafeReflection.<Double>fetch(x_field, packet);
        this.y = SafeReflection.<Double>fetch(y_field, packet);
        this.z = SafeReflection.<Double>fetch(z_field, packet);
        this.yaw = SafeReflection.<Float>fetch(yaw_field, packet);
        this.pitch = SafeReflection.<Float>fetch(pitch_field, packet);
        this.flags = SafeReflection.fetch(flags_field, packet);
    }
}
