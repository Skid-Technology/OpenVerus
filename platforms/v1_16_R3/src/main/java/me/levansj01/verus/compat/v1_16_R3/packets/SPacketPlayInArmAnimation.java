package me.levansj01.verus.compat.v1_16_R3.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInArmAnimation;
import net.minecraft.server.v1_16_R3.PacketPlayInArmAnimation;

public class SPacketPlayInArmAnimation extends VPacketPlayInArmAnimation<PacketPlayInArmAnimation> {
    public void accept(PacketPlayInArmAnimation packet) {
        this.hand = packet.b().ordinal();
    }
}
