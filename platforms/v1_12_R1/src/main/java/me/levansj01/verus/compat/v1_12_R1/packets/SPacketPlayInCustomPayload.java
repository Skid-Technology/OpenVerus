package me.levansj01.verus.compat.v1_12_R1.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInCustomPayload;
import net.minecraft.server.v1_12_R1.PacketPlayInCustomPayload;

public class SPacketPlayInCustomPayload extends VPacketPlayInCustomPayload<PacketPlayInCustomPayload> {
    public void accept(PacketPlayInCustomPayload packet) {
        this.channel = packet.a();

        try {
            this.size = packet.b().capacity();
        } catch (IllegalStateException ignored) {
        }
    }
}
