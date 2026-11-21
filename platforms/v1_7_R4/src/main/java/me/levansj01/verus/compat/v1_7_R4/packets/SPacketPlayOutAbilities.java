package me.levansj01.verus.compat.v1_7_R4.packets;

import me.levansj01.verus.compat.packets.VPacketPlayOutAbilities;
import net.minecraft.server.v1_7_R4.PacketPlayOutAbilities;

public class SPacketPlayOutAbilities extends VPacketPlayOutAbilities<PacketPlayOutAbilities> {
    public void accept(PacketPlayOutAbilities packet) {
        this.isInvulnerable = packet.c();
        this.isFlying = packet.d();
        this.canFly = packet.e();
        this.canInstantlyBuild = packet.f();
        this.walkSpeed = packet.g();
        this.flySpeed = packet.h();
    }
}
