package me.levansj01.verus.compat.v1_19_R3.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInCustomPayload;
import net.minecraft.network.protocol.game.PacketPlayInCustomPayload;

public class SPacketPlayInCustomPayload extends VPacketPlayInCustomPayload<PacketPlayInCustomPayload> {
    public void accept(PacketPlayInCustomPayload packet) {
        this.channel = packet.a().a();

        try {
            this.size = packet.d.capacity();
        } catch (IllegalStateException ignored) {
        }
    }
}
