package me.levansj01.verus.compat.v1_12_R1.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInLook;
import net.minecraft.server.v1_12_R1.PacketPlayInFlying.PacketPlayInLook;

public class SPacketPlayInLook extends VPacketPlayInLook<PacketPlayInLook> {
    public void accept(PacketPlayInLook packet) {
        this.x = packet.a(0.0);
        this.y = packet.b(0.0);
        this.z = packet.c(0.0);
        this.yaw = packet.a(0.0F);
        this.pitch = packet.b(0.0F);
        this.ground = packet.a();
        this.pos = false;
        this.look = true;
    }
}
