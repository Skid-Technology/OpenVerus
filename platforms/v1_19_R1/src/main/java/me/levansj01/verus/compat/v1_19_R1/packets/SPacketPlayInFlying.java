package me.levansj01.verus.compat.v1_19_R1.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import net.minecraft.network.protocol.game.PacketPlayInFlying;

public class SPacketPlayInFlying extends VPacketPlayInFlying<PacketPlayInFlying> {
    public void accept(PacketPlayInFlying packet) {
        this.x = packet.a(0.0);
        this.y = packet.b(0.0);
        this.z = packet.c(0.0);
        this.yaw = packet.a(0.0F);
        this.pitch = packet.b(0.0F);
        this.ground = packet.b();
        this.pos = packet.c();
        this.look = packet.d();
    }
}
