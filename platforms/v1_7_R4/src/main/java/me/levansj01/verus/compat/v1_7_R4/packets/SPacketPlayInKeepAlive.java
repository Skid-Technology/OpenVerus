package me.levansj01.verus.compat.v1_7_R4.packets;

import java.math.BigInteger;
import me.levansj01.verus.compat.packets.VPacketPlayInKeepAlive;
import net.minecraft.server.v1_7_R4.PacketPlayInKeepAlive;

public class SPacketPlayInKeepAlive extends VPacketPlayInKeepAlive<PacketPlayInKeepAlive> {
    public void accept(PacketPlayInKeepAlive packet) {
        this.id = BigInteger.valueOf(packet.c());
    }
}
