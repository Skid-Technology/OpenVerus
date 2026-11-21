package me.levansj01.verus.compat.v1_20_R1.packets;

import java.math.BigInteger;
import me.levansj01.verus.compat.packets.VPacketPlayOutKeepAlive;
import net.minecraft.network.protocol.game.PacketPlayOutKeepAlive;

public class SPacketPlayOutKeepAlive extends VPacketPlayOutKeepAlive<PacketPlayOutKeepAlive> {
    public void accept(PacketPlayOutKeepAlive packet) {
        this.id = BigInteger.valueOf(packet.a());
    }
}
