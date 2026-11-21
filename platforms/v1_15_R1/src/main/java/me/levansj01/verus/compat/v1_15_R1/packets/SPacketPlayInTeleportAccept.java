package me.levansj01.verus.compat.v1_15_R1.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInTeleportAccept;
import net.minecraft.server.v1_15_R1.PacketPlayInTeleportAccept;

public class SPacketPlayInTeleportAccept extends VPacketPlayInTeleportAccept<PacketPlayInTeleportAccept> {
    public void accept(PacketPlayInTeleportAccept packet) {
        this.id = packet.b();
    }
}
