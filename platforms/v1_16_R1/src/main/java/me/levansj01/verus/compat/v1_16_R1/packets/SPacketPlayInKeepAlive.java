package me.levansj01.verus.compat.v1_16_R1.packets;

import java.math.BigInteger;
import me.levansj01.verus.compat.packets.VPacketPlayInKeepAlive;
import net.minecraft.server.v1_16_R1.PacketPlayInKeepAlive;

public class SPacketPlayInKeepAlive extends VPacketPlayInKeepAlive<PacketPlayInKeepAlive> {
    public void accept(PacketPlayInKeepAlive packet) {
        this.id = BigInteger.valueOf(packet.b());
    }
}
