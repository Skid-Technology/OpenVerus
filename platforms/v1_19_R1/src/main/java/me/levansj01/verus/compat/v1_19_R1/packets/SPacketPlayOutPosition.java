package me.levansj01.verus.compat.v1_19_R1.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutPosition;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.network.protocol.game.PacketPlayOutPosition;

public class SPacketPlayOutPosition extends VPacketPlayOutPosition<PacketPlayOutPosition> {
    private static final Field flags_field = SafeReflection.access(PacketPlayOutPosition.class, "f", "flags");

    public void accept(PacketPlayOutPosition packet) {
        this.x = packet.b();
        this.y = packet.c();
        this.z = packet.d();
        this.yaw = packet.e();
        this.pitch = packet.f();
        this.flags = SafeReflection.fetch(flags_field, packet);
    }
}
