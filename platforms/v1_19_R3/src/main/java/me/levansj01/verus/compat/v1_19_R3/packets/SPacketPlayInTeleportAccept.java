package me.levansj01.verus.compat.v1_19_R3.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInTeleportAccept;
import net.minecraft.network.protocol.game.PacketPlayInTeleportAccept;

public class SPacketPlayInTeleportAccept extends VPacketPlayInTeleportAccept<PacketPlayInTeleportAccept> {
    public void accept(PacketPlayInTeleportAccept packet) {
        this.id = packet.a();
    }
}
