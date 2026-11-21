package me.levansj01.verus.compat.v1_16_R2.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_16_R2.PacketPlayInFlying;

public class SPacketPlayInFlying extends VPacketPlayInFlying<PacketPlayInFlying> {
    private static final Field field_hasPos = SafeReflection.access(PacketPlayInFlying.class, "hasPos");
    private static final Field field_hasLook = SafeReflection.access(PacketPlayInFlying.class, "hasLook");

    public void accept(PacketPlayInFlying packet) {
        this.x = packet.a(0.0);
        this.y = packet.b(0.0);
        this.z = packet.c(0.0);
        this.yaw = packet.a(0.0F);
        this.pitch = packet.b(0.0F);
        this.ground = packet.b();
        this.pos = SafeReflection.<Boolean>fetch(field_hasPos, packet);
        this.look = SafeReflection.<Boolean>fetch(field_hasLook, packet);
    }
}
