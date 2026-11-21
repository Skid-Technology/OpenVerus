package me.levansj01.verus.compat.v1_13_R2.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInTeleportAccept;
import net.minecraft.server.v1_13_R2.PacketPlayInTeleportAccept;

public class SPacketPlayInTeleportAccept extends VPacketPlayInTeleportAccept<PacketPlayInTeleportAccept> {
    public void accept(PacketPlayInTeleportAccept packet) {
        this.id = packet.b();
    }
}
