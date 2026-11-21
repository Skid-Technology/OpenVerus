package me.levansj01.verus.compat.v1_12_R1.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInTeleportAccept;
import net.minecraft.server.v1_12_R1.PacketPlayInTeleportAccept;

public class SPacketPlayInTeleportAccept extends VPacketPlayInTeleportAccept<PacketPlayInTeleportAccept> {
    public void accept(PacketPlayInTeleportAccept packet) {
        this.id = packet.a();
    }
}
