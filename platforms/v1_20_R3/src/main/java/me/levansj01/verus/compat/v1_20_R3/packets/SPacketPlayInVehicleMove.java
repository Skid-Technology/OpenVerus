package me.levansj01.verus.compat.v1_20_R3.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInVehicleMove;
import net.minecraft.network.protocol.game.PacketPlayInVehicleMove;

public class SPacketPlayInVehicleMove extends VPacketPlayInVehicleMove<PacketPlayInVehicleMove> {
    public void accept(PacketPlayInVehicleMove packet) {
        this.x = packet.a();
        this.y = packet.d();
        this.z = packet.e();
        this.yaw = packet.f();
        this.pitch = packet.g();
    }
}
