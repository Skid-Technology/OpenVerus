package me.levansj01.verus.compat.v1_18_R2.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutRelEntityMoveLook;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.network.protocol.game.PacketPlayOutEntity;

public class SPacketPlayOutRelEntityMoveLook extends VPacketPlayOutRelEntityMoveLook<PacketPlayOutEntity> {
    private static final Field id_field = SafeReflection.access(PacketPlayOutEntity.class, "a");

    public void accept(PacketPlayOutEntity packet) {
        this.id = SafeReflection.<Integer>fetch(id_field, packet);
        this.x = packet.b();
        this.y = packet.c();
        this.z = packet.d();
        this.yaw = packet.e();
        this.pitch = packet.f();
        this.ground = packet.g();
        this.look = packet.h();
    }
}
