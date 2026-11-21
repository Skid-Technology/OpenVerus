package me.levansj01.verus.compat.v1_16_R2.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInChat;
import net.minecraft.server.v1_16_R2.PacketPlayInChat;

public class SPacketPlayInChat extends VPacketPlayInChat<PacketPlayInChat> {
    public void accept(PacketPlayInChat packet) {
        this.message = packet.b();
        this.command = this.message.startsWith("!");
    }
}
