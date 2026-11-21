package me.levansj01.verus.compat.v1_15_R1.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutExplosion;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_15_R1.PacketPlayOutExplosion;

public class SPacketPlayOutExplosion extends VPacketPlayOutExplosion<PacketPlayOutExplosion> {
    private static final Field motX_field = SafeReflection.access(PacketPlayOutExplosion.class, "f");
    private static final Field motY_field = SafeReflection.access(PacketPlayOutExplosion.class, "g");
    private static final Field motZ_field = SafeReflection.access(PacketPlayOutExplosion.class, "h");

    public void accept(PacketPlayOutExplosion packet) {
        this.motX = SafeReflection.<Float>fetch(motX_field, packet);
        this.motY = SafeReflection.<Float>fetch(motY_field, packet);
        this.motZ = SafeReflection.<Float>fetch(motZ_field, packet);
    }
}
