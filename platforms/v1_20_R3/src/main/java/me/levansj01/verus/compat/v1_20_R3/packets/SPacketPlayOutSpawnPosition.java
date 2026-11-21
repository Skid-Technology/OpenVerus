package me.levansj01.verus.compat.v1_20_R3.packets;

import me.levansj01.verus.compat.api.wrapper.BlockPosition;
import me.levansj01.verus.compat.packets.VPacketPlayOutSpawnPosition;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnPosition;

public class SPacketPlayOutSpawnPosition extends VPacketPlayOutSpawnPosition<PacketPlayOutSpawnPosition> {
    public void accept(PacketPlayOutSpawnPosition packet) {
        this.position = new BlockPosition(packet.a().u(), packet.a().v(), packet.a().w());
    }
}
