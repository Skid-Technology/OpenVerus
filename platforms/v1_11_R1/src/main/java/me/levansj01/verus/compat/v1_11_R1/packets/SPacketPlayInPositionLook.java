package me.levansj01.verus.compat.v1_11_R1.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInPositionLook;
import net.minecraft.server.v1_11_R1.PacketPlayInFlying.PacketPlayInPositionLook;

public class SPacketPlayInPositionLook extends VPacketPlayInPositionLook<PacketPlayInPositionLook> {
    public void accept(PacketPlayInPositionLook packet) {
        this.x = packet.a(0.0);
        this.y = packet.b(0.0);
        this.z = packet.c(0.0);
        this.yaw = packet.a(0.0F);
        this.pitch = packet.b(0.0F);
        this.ground = packet.a();
        this.pos = true;
        this.look = true;
    }
}
