package me.levansj01.verus.compat.v1_8_R3.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutMapChunk;
import me.levansj01.verus.data.bytes.ByteData;
import me.levansj01.verus.data.state.State;
import me.levansj01.verus.data.transaction.world.BlankSection;
import me.levansj01.verus.data.transaction.world.SectionData;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_8_R3.PacketPlayOutMapChunk;
import net.minecraft.server.v1_8_R3.PacketPlayOutMapChunk.ChunkMap;

public class SPacketPlayOutMapChunk extends VPacketPlayOutMapChunk<PacketPlayOutMapChunk> {
    private static final Field a_field = SafeReflection.access(PacketPlayOutMapChunk.class, "a");
    private static final Field b_field = SafeReflection.access(PacketPlayOutMapChunk.class, "b");
    private static final Field c_field = SafeReflection.access(PacketPlayOutMapChunk.class, "c");
    private static final Field d_field = SafeReflection.access(PacketPlayOutMapChunk.class, "d");

    public void accept(PacketPlayOutMapChunk packet) {
        this.chunkX = SafeReflection.<Integer>fetch(a_field, packet);
        this.chunkZ = SafeReflection.<Integer>fetch(b_field, packet);
        this.groundUp = SafeReflection.<Boolean>fetch(d_field, packet);
        ChunkMap chunkMap = SafeReflection.fetch(c_field, packet);
        this.unload = chunkMap == null || chunkMap.a.length == 0 && this.groundUp;
        this.chunkMap = this.unload ? State.of(null) : State.fast(() -> fromNMS(chunkMap, this.groundUp));
    }

    public static SectionData[] fromNMS(ChunkMap chunkMap, boolean flag) {
        SectionData[] sectionDatas = new SectionData[16];
        if (chunkMap == null) {
            return sectionDatas;
        }

        ByteData byteData = wrap(chunkMap.a);

        try {
            int value = chunkMap.b;
            int value2 = 0;

            for (int i = 0; i < 16; i++) {
                if ((value >> i & 1) != 0) {
                    sectionDatas[i] = VPacketPlayOutMapChunk.SectionDataImpl.of(byteData, value2);
                    value2++;
                } else if (flag) {
                    sectionDatas[i] = new BlankSection();
                }
            }

            return sectionDatas;
        } finally {
            byteData.release();
        }
    }
}
