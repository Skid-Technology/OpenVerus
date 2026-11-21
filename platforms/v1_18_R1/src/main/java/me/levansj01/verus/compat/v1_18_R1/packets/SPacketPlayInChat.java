package me.levansj01.verus.compat.v1_18_R1.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInChat;
import net.minecraft.network.protocol.game.PacketPlayInChat;

public class SPacketPlayInChat extends VPacketPlayInChat<PacketPlayInChat> {
    public void accept(PacketPlayInChat packet) {
        this.message = packet.b();
        this.command = this.message.startsWith("!");
    }
}
