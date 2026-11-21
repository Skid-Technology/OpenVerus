package me.levansj01.verus.compat.v1_20_R1.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutRelEntityMove;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.network.protocol.game.PacketPlayOutEntity;

public class SPacketPlayOutRelEntityMove extends VPacketPlayOutRelEntityMove<PacketPlayOutEntity> {
    private static final Field id_field = SafeReflection.access(PacketPlayOutEntity.class, "a");

    public void accept(PacketPlayOutEntity packet) {
        this.id = SafeReflection.<Integer>fetch(id_field, packet);
        this.x = packet.a();
        this.y = packet.c();
        this.z = packet.d();
        this.yaw = packet.e();
        this.pitch = packet.f();
        this.ground = packet.g();
        this.look = packet.h();
    }
}
