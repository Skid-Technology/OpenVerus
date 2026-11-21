package me.levansj01.verus.compat.v1_20_R1.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInVehicleMove;
import net.minecraft.network.protocol.game.PacketPlayInVehicleMove;

public class SPacketPlayInVehicleMove extends VPacketPlayInVehicleMove<PacketPlayInVehicleMove> {
    public void accept(PacketPlayInVehicleMove packet) {
        this.x = packet.a();
        this.y = packet.c();
        this.z = packet.d();
        this.yaw = packet.e();
        this.pitch = packet.f();
    }
}
