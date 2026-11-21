package me.levansj01.verus.compat.v1_20_R3.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutEntity;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.network.protocol.game.PacketPlayOutEntity;
import net.minecraft.network.protocol.game.PacketPlayOutEntity.PacketPlayOutRelEntityMove;
import net.minecraft.network.protocol.game.PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook;

public class SPacketPlayOutEntity extends VPacketPlayOutEntity<PacketPlayOutEntity> {
    private static final Field id_field = SafeReflection.access(PacketPlayOutEntity.class, "a");

    public void accept(PacketPlayOutEntity packet) {
        this.id = SafeReflection.<Integer>fetch(id_field, packet);
        this.x = packet.a();
        this.y = packet.d();
        this.z = packet.e();
        this.yaw = packet.f();
        this.pitch = packet.g();
        this.ground = packet.h();
        this.look = packet.i();
        this.pos = packet instanceof PacketPlayOutRelEntityMoveLook || packet instanceof PacketPlayOutRelEntityMove;
    }
}
