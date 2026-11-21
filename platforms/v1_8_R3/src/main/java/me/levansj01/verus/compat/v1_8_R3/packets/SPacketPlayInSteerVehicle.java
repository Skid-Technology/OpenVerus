package me.levansj01.verus.compat.v1_8_R3.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInSteerVehicle;
import net.minecraft.server.v1_8_R3.PacketPlayInSteerVehicle;

public class SPacketPlayInSteerVehicle extends VPacketPlayInSteerVehicle<PacketPlayInSteerVehicle> {
    public void accept(PacketPlayInSteerVehicle packet) {
        this.forward = packet.a();
        this.strafe = packet.b();
    }
}
