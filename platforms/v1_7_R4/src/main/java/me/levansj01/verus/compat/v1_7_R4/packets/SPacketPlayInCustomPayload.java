package me.levansj01.verus.compat.v1_7_R4.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayInCustomPayload;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_7_R4.PacketPlayInCustomPayload;

public class SPacketPlayInCustomPayload extends VPacketPlayInCustomPayload<PacketPlayInCustomPayload> {
    private static final Field tag_field = SafeReflection.access(PacketPlayInCustomPayload.class, "tag");

    public void accept(PacketPlayInCustomPayload packet) {
        this.channel = SafeReflection.fetch(tag_field, packet);
        byte[] bytes = packet.e();
        this.size = bytes == null ? 0 : bytes.length;
    }
}
