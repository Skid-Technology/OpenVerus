package me.levansj01.verus.compat.v1_19_R2.packets;

import java.math.BigInteger;
import me.levansj01.verus.compat.packets.VPacketPlayInKeepAlive;
import net.minecraft.network.protocol.game.PacketPlayInKeepAlive;

public class SPacketPlayInKeepAlive extends VPacketPlayInKeepAlive<PacketPlayInKeepAlive> {
    public void accept(PacketPlayInKeepAlive packet) {
        this.id = BigInteger.valueOf(packet.b());
    }
}
