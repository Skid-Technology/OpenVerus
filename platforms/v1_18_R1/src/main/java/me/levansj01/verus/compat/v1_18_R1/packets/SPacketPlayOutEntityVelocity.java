package me.levansj01.verus.compat.v1_18_R1.packets;

import me.levansj01.verus.compat.packets.VPacketPlayOutEntityVelocity;
import net.minecraft.network.protocol.game.PacketPlayOutEntityVelocity;

public class SPacketPlayOutEntityVelocity extends VPacketPlayOutEntityVelocity<PacketPlayOutEntityVelocity> {
    public void accept(PacketPlayOutEntityVelocity packet) {
        this.id = packet.b();
        this.motX = packet.c();
        this.motY = packet.d();
        this.motZ = packet.e();
    }
}
