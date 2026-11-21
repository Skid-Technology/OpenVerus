package me.levansj01.verus.compat.v1_20_R3.packets;

import java.math.BigInteger;
import me.levansj01.verus.compat.packets.VPacketPlayOutKeepAlive;
import net.minecraft.network.protocol.common.ClientboundKeepAlivePacket;

public class SPacketPlayOutKeepAlive extends VPacketPlayOutKeepAlive<ClientboundKeepAlivePacket> {
    public void accept(ClientboundKeepAlivePacket packet) {
        this.id = BigInteger.valueOf(packet.a());
    }
}
