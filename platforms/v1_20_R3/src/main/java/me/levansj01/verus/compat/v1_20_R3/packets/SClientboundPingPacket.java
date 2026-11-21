package me.levansj01.verus.compat.v1_20_R3.packets;

import me.levansj01.verus.compat.packets.VClientboundPingPacket;
import net.minecraft.network.protocol.common.ClientboundPingPacket;

public class SClientboundPingPacket extends VClientboundPingPacket<ClientboundPingPacket> {
    public void accept(ClientboundPingPacket packet) {
        this.id = packet.a();
    }
}
