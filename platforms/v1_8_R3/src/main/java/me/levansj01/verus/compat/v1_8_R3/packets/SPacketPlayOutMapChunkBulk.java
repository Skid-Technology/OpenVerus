package me.levansj01.verus.compat.v1_8_R3.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutMapChunkBulk;
import me.levansj01.verus.data.state.State;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_8_R3.PacketPlayOutMapChunkBulk;
import net.minecraft.server.v1_8_R3.PacketPlayOutMapChunk.ChunkMap;

public class SPacketPlayOutMapChunkBulk extends VPacketPlayOutMapChunkBulk<PacketPlayOutMapChunkBulk> {
    private static final Field a_field = SafeReflection.access(PacketPlayOutMapChunkBulk.class, "a");
    private static final Field b_field = SafeReflection.access(PacketPlayOutMapChunkBulk.class, "b");
    private static final Field c_field = SafeReflection.access(PacketPlayOutMapChunkBulk.class, "c");
    private static final Field d_field = SafeReflection.access(PacketPlayOutMapChunkBulk.class, "d");

    public void accept(PacketPlayOutMapChunkBulk packet) {
        this.groundUp = SafeReflection.<Boolean>fetch(d_field, packet);
        int[] values = SafeReflection.fetch(a_field, packet);
        int[] values2 = SafeReflection.fetch(b_field, packet);
        ChunkMap[] chunkMaps = SafeReflection.fetch(c_field, packet);
        this.chunks = State.fast(() -> {
            VPacketPlayOutMapChunkBulk.MappedChunk[] mappedChunks = new VPacketPlayOutMapChunkBulk.MappedChunk[chunkMaps.length];

            for (int i = 0; i < mappedChunks.length; i++) {
                int value = values[i];
                int value2 = values2[i];
                ChunkMap chunkMap = chunkMaps[i];
                boolean flag = chunkMap == null || chunkMap.a.length == 0;
                mappedChunks[i] = new VPacketPlayOutMapChunkBulk.MappedChunk(value, value2, flag ? null : SPacketPlayOutMapChunk.fromNMS(chunkMap, true), flag);
            }

            return mappedChunks;
        });
    }
}
