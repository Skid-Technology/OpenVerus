package me.levansj01.verus.compat.v1_19_R3.packets;

import me.levansj01.verus.compat.packets.VPacketPlayOutEntityTeleport;
import net.minecraft.network.protocol.game.PacketPlayOutEntityTeleport;

public class SPacketPlayOutEntityTeleport extends VPacketPlayOutEntityTeleport<PacketPlayOutEntityTeleport> {
    public void accept(PacketPlayOutEntityTeleport packet) {
        this.id = packet.a();
        this.x = packet.c();
        this.y = packet.d();
        this.z = packet.e();
        this.yaw = packet.f();
        this.pitch = packet.g();
        this.ground = packet.h();
    }
}
