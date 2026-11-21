package me.levansj01.verus.compat.v1_17_R1.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInCustomPayload;
import net.minecraft.network.protocol.game.PacketPlayInCustomPayload;

public class SPacketPlayInCustomPayload extends VPacketPlayInCustomPayload<PacketPlayInCustomPayload> {
    public void accept(PacketPlayInCustomPayload packet) {
        this.channel = packet.b().getKey();

        try {
            this.size = packet.d.capacity();
        } catch (IllegalStateException ignored) {
        }
    }
}
