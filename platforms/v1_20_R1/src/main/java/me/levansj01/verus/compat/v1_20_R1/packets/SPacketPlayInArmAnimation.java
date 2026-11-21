package me.levansj01.verus.compat.v1_20_R1.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInArmAnimation;
import net.minecraft.network.protocol.game.PacketPlayInArmAnimation;

public class SPacketPlayInArmAnimation extends VPacketPlayInArmAnimation<PacketPlayInArmAnimation> {
    public void accept(PacketPlayInArmAnimation packet) {
        this.hand = packet.a().ordinal();
    }
}
