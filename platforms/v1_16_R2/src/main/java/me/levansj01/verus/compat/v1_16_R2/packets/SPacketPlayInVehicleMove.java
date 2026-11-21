package me.levansj01.verus.compat.v1_16_R2.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInVehicleMove;
import net.minecraft.server.v1_16_R2.PacketPlayInVehicleMove;

public class SPacketPlayInVehicleMove extends VPacketPlayInVehicleMove<PacketPlayInVehicleMove> {
    public void accept(PacketPlayInVehicleMove packet) {
        this.x = packet.getZ();
        this.y = packet.getY();
        this.z = packet.getZ();
        this.yaw = packet.getYaw();
        this.pitch = packet.getPitch();
    }
}
