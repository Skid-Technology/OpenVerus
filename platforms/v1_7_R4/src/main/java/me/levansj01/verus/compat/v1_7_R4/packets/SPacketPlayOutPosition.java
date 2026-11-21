package me.levansj01.verus.compat.v1_7_R4.packets;

import com.google.common.collect.Sets;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashSet;
import me.levansj01.verus.compat.packets.VPacketPlayOutPosition;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_7_R4.PacketPlayOutPosition;

public class SPacketPlayOutPosition extends VPacketPlayOutPosition<PacketPlayOutPosition> {
    private static final Field x_field = SafeReflection.access(PacketPlayOutPosition.class, "a", "x");
    private static final Field y_field = SafeReflection.access(PacketPlayOutPosition.class, "b", "y");
    private static final Field z_field = SafeReflection.access(PacketPlayOutPosition.class, "c", "z");
    private static final Field yaw_field = SafeReflection.access(PacketPlayOutPosition.class, "d", "yaw");
    private static final Field pitch_field = SafeReflection.access(PacketPlayOutPosition.class, "e", "pitch");
    private static final Field flags_1_7_field;
    private static final Field flags_1_8_field;

    public void accept(PacketPlayOutPosition packet) {
        this.x = SafeReflection.<Double>fetch(x_field, packet);
        this.y = SafeReflection.<Double>fetch(y_field, packet) - 1.62;
        this.z = SafeReflection.<Double>fetch(z_field, packet);
        this.yaw = SafeReflection.<Float>fetch(yaw_field, packet);
        this.pitch = SafeReflection.<Float>fetch(pitch_field, packet);
        if (flags_1_7_field != null && SafeReflection.<Boolean>fetch(flags_1_7_field, packet)) {
            this.flags = Sets.newHashSet(VPacketPlayOutPosition.TeleportFlag.X_ROT, VPacketPlayOutPosition.TeleportFlag.Y_ROT);
        } else {
            this.flags = null;
        }

        byte b;
        if (flags_1_8_field != null && (b = SafeReflection.<Byte>fetch(flags_1_8_field, packet)) != 0) {
            this.flags = new HashSet<>();

            for (VPacketPlayOutPosition.TeleportFlag teleportFlag : VPacketPlayOutPosition.TeleportFlag.values()) {
                if (teleportFlag.contains(b)) {
                    this.flags.add(teleportFlag);
                }
            }
        } else if (this.flags == null) {
            this.flags = Collections.emptySet();
        }
    }

    static {
        Field field = null;

        try {
            field = SafeReflection.access(PacketPlayOutPosition.class, "f", "flags");
        } catch (IllegalArgumentException ignored) {
        }

        flags_1_7_field = field;
        field = null;

        try {
            field = SafeReflection.access(PacketPlayOutPosition.class, "relativeBitMask");
        } catch (IllegalArgumentException ignored) {
        }

        flags_1_8_field = field;
    }
}
