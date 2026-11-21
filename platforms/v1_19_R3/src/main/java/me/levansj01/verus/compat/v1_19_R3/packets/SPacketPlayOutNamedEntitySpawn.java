package me.levansj01.verus.compat.v1_19_R3.packets;

import me.levansj01.verus.compat.packets.VPacketPlayOutNamedEntitySpawn;
import net.minecraft.network.protocol.game.PacketPlayOutNamedEntitySpawn;

public class SPacketPlayOutNamedEntitySpawn extends VPacketPlayOutNamedEntitySpawn<PacketPlayOutNamedEntitySpawn> {
    public void accept(PacketPlayOutNamedEntitySpawn packet) {
        this.id = packet.a();
        this.x = packet.d();
        this.y = packet.e();
        this.z = packet.f();
        this.yaw = packet.g();
        this.pitch = packet.h();
    }
}
