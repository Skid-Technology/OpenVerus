package me.levansj01.verus.compat.v1_11_R1.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutEntityVelocity;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_11_R1.PacketPlayOutEntityVelocity;

public class SPacketPlayOutEntityVelocity extends VPacketPlayOutEntityVelocity<PacketPlayOutEntityVelocity> {
    private static final Field id_field = SafeReflection.access(PacketPlayOutEntityVelocity.class, "a", "id");
    private static final Field motX_field = SafeReflection.access(PacketPlayOutEntityVelocity.class, "b", "x");
    private static final Field motY_field = SafeReflection.access(PacketPlayOutEntityVelocity.class, "c", "y");
    private static final Field motZ_field = SafeReflection.access(PacketPlayOutEntityVelocity.class, "d", "z");

    public void accept(PacketPlayOutEntityVelocity packet) {
        this.id = SafeReflection.<Integer>fetch(id_field, packet);
        this.motX = SafeReflection.<Integer>fetch(motX_field, packet);
        this.motY = SafeReflection.<Integer>fetch(motY_field, packet);
        this.motZ = SafeReflection.<Integer>fetch(motZ_field, packet);
    }
}
