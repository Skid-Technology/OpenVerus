package me.levansj01.verus.compat.v1_19_R1.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInSteerVehicle;
import net.minecraft.network.protocol.game.PacketPlayInSteerVehicle;

public class SPacketPlayInSteerVehicle extends VPacketPlayInSteerVehicle<PacketPlayInSteerVehicle> {
    public void accept(PacketPlayInSteerVehicle packet) {
        this.forward = packet.b();
        this.strafe = packet.c();
    }
}
