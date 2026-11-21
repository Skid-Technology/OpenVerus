package me.levansj01.verus.compat.v1_14_R1.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInChat;
import net.minecraft.server.v1_14_R1.PacketPlayInChat;

public class SPacketPlayInChat extends VPacketPlayInChat<PacketPlayInChat> {
    public void accept(PacketPlayInChat packet) {
        this.message = packet.b();
        this.command = this.message.startsWith("!");
    }
}
