package me.levansj01.verus.compat.v1_15_R1.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInSteerVehicle;
import net.minecraft.server.v1_15_R1.PacketPlayInSteerVehicle;

public class SPacketPlayInSteerVehicle extends VPacketPlayInSteerVehicle<PacketPlayInSteerVehicle> {
    public void accept(PacketPlayInSteerVehicle packet) {
        this.forward = packet.b();
        this.strafe = packet.c();
    }
}
