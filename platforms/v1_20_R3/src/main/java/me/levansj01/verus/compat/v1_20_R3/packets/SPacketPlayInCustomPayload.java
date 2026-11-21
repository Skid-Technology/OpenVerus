package me.levansj01.verus.compat.v1_20_R3.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInCustomPayload;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket.UnknownPayload;

public class SPacketPlayInCustomPayload extends VPacketPlayInCustomPayload<ServerboundCustomPayloadPacket> {
    public void accept(ServerboundCustomPayloadPacket packet) {
        this.channel = packet.a().a().toString();

        try {
            if (packet.a() instanceof UnknownPayload) {
                this.size = ((UnknownPayload)packet.a()).data().capacity();
            } else {
                this.size = 0;
            }
        } catch (IllegalStateException ignored) {
        }
    }
}
