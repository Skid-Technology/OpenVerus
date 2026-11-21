package me.levansj01.verus.compat.v1_7_R4.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInLook;
import net.minecraft.server.v1_7_R4.PacketPlayInFlying;

public class SPacketPlayInLook extends VPacketPlayInLook<PacketPlayInFlying> {
    public void accept(PacketPlayInFlying packet) {
        this.x = packet.c();
        this.y = packet.d();
        this.z = packet.e();
        this.yaw = packet.g();
        this.pitch = packet.h();
        this.ground = packet.i();
        this.pos = packet.j();
        this.look = packet.k();
    }
}
