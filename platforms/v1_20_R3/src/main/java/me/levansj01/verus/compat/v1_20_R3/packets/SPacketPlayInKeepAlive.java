package me.levansj01.verus.compat.v1_20_R3.packets;

import java.math.BigInteger;
import me.levansj01.verus.compat.packets.VPacketPlayInKeepAlive;
import net.minecraft.network.protocol.common.ServerboundKeepAlivePacket;

public class SPacketPlayInKeepAlive extends VPacketPlayInKeepAlive<ServerboundKeepAlivePacket> {
    public void accept(ServerboundKeepAlivePacket packet) {
        this.id = BigInteger.valueOf(packet.a());
    }
}
