package me.levansj01.verus.compat.v1_19_R1.packets;

import me.levansj01.verus.compat.packets.VPacketPlayOutExplosion;
import net.minecraft.network.protocol.game.PacketPlayOutExplosion;

public class SPacketPlayOutExplosion extends VPacketPlayOutExplosion<PacketPlayOutExplosion> {
    public void accept(PacketPlayOutExplosion packet) {
        this.motX = packet.b();
        this.motY = packet.c();
        this.motZ = packet.d();
    }
}
