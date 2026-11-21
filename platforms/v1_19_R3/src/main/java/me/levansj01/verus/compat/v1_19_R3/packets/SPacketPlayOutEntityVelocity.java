package me.levansj01.verus.compat.v1_19_R3.packets;

import me.levansj01.verus.compat.packets.VPacketPlayOutEntityVelocity;
import net.minecraft.network.protocol.game.PacketPlayOutEntityVelocity;

public class SPacketPlayOutEntityVelocity extends VPacketPlayOutEntityVelocity<PacketPlayOutEntityVelocity> {
    public void accept(PacketPlayOutEntityVelocity packet) {
        this.id = packet.a();
        this.motX = packet.c();
        this.motY = packet.d();
        this.motZ = packet.e();
    }
}
