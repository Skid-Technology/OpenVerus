package me.levansj01.verus.compat.v1_7_R4.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInChat;
import net.minecraft.server.v1_7_R4.PacketPlayInChat;

public class SPacketPlayInChat extends VPacketPlayInChat<PacketPlayInChat> {
    public void accept(PacketPlayInChat packet) {
        this.message = packet.c();
        this.command = this.message.startsWith("!");
    }
}
