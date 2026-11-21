package me.levansj01.verus.compat.v1_8_R3.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInLook;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying.PacketPlayInLook;

public class SPacketPlayInLook extends VPacketPlayInLook<PacketPlayInLook> {
    public void accept(PacketPlayInLook packet) {
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
