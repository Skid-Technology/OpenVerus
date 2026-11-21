package me.levansj01.verus.compat.v1_20_R3.packets;

import me.levansj01.verus.compat.packets.VPacketPlayOutExplosion;
import net.minecraft.network.protocol.game.PacketPlayOutExplosion;

public class SPacketPlayOutExplosion extends VPacketPlayOutExplosion<PacketPlayOutExplosion> {
    public void accept(PacketPlayOutExplosion packet) {
        this.motX = packet.a();
        this.motY = packet.d();
        this.motZ = packet.e();
    }
}
