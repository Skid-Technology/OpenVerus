package me.levansj01.verus.compat.v1_19_R1.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInPosition;
import net.minecraft.network.protocol.game.PacketPlayInFlying.PacketPlayInPosition;

public class SPacketPlayInPosition extends VPacketPlayInPosition<PacketPlayInPosition> {
    public void accept(PacketPlayInPosition packet) {
        this.x = packet.a(0.0);
        this.y = packet.b(0.0);
        this.z = packet.c(0.0);
        this.yaw = packet.a(0.0F);
        this.pitch = packet.b(0.0F);
        this.ground = packet.b();
        this.pos = true;
        this.look = false;
    }
}
