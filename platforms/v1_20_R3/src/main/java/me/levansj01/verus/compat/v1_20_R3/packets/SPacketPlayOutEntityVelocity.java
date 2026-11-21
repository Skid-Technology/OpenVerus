package me.levansj01.verus.compat.v1_20_R3.packets;

import me.levansj01.verus.compat.packets.VPacketPlayOutEntityVelocity;
import net.minecraft.network.protocol.game.PacketPlayOutEntityVelocity;

public class SPacketPlayOutEntityVelocity extends VPacketPlayOutEntityVelocity<PacketPlayOutEntityVelocity> {
    public void accept(PacketPlayOutEntityVelocity packet) {
        this.id = packet.a();
        this.motX = packet.d();
        this.motY = packet.e();
        this.motZ = packet.f();
    }
}
