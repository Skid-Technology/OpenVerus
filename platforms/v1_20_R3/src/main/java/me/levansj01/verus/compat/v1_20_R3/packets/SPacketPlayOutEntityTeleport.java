package me.levansj01.verus.compat.v1_20_R3.packets;

import me.levansj01.verus.compat.packets.VPacketPlayOutEntityTeleport;
import net.minecraft.network.protocol.game.PacketPlayOutEntityTeleport;

public class SPacketPlayOutEntityTeleport extends VPacketPlayOutEntityTeleport<PacketPlayOutEntityTeleport> {
    public void accept(PacketPlayOutEntityTeleport packet) {
        this.id = packet.a();
        this.x = packet.d();
        this.y = packet.e();
        this.z = packet.f();
        this.yaw = packet.g();
        this.pitch = packet.h();
        this.ground = packet.i();
    }
}
