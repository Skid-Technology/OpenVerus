package me.levansj01.verus.compat.v1_8_R3.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;

public class SPacketPlayInFlying extends VPacketPlayInFlying<PacketPlayInFlying> {
    public void accept(PacketPlayInFlying packet) {
        this.x = packet.a();
        this.y = packet.b();
        this.z = packet.c();
        this.yaw = packet.d();
        this.pitch = packet.e();
        this.ground = packet.f();
        this.pos = packet.g();
        this.look = packet.h();
    }
}
