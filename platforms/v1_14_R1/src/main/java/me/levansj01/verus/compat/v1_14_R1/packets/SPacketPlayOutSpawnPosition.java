package me.levansj01.verus.compat.v1_14_R1.packets;

import me.levansj01.verus.compat.api.wrapper.BlockPosition;
import me.levansj01.verus.compat.packets.VPacketPlayOutSpawnPosition;
import net.minecraft.server.v1_14_R1.PacketPlayOutSpawnPosition;

public class SPacketPlayOutSpawnPosition extends VPacketPlayOutSpawnPosition<PacketPlayOutSpawnPosition> {
    public void accept(PacketPlayOutSpawnPosition packet) {
        this.position = new BlockPosition(packet.position.getX(), packet.position.getY(), packet.position.getZ());
    }
}
