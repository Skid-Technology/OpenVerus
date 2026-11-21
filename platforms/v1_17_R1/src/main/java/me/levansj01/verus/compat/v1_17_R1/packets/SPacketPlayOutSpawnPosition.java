package me.levansj01.verus.compat.v1_17_R1.packets;

import me.levansj01.verus.compat.api.wrapper.BlockPosition;
import me.levansj01.verus.compat.packets.VPacketPlayOutSpawnPosition;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnPosition;

public class SPacketPlayOutSpawnPosition extends VPacketPlayOutSpawnPosition<PacketPlayOutSpawnPosition> {
    public void accept(PacketPlayOutSpawnPosition packet) {
        this.position = new BlockPosition(packet.b().getX(), packet.b().getY(), packet.b().getZ());
    }
}
