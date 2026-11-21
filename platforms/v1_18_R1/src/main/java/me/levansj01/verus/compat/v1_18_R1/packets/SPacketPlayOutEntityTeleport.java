package me.levansj01.verus.compat.v1_18_R1.packets;

import me.levansj01.verus.compat.packets.VPacketPlayOutEntityTeleport;
import net.minecraft.network.protocol.game.PacketPlayOutEntityTeleport;

public class SPacketPlayOutEntityTeleport extends VPacketPlayOutEntityTeleport<PacketPlayOutEntityTeleport> {
    public void accept(PacketPlayOutEntityTeleport packet) {
        this.id = packet.b();
        this.x = packet.c();
        this.y = packet.d();
        this.z = packet.e();
        this.yaw = packet.f();
        this.pitch = packet.g();
        this.ground = packet.h();
    }
}
