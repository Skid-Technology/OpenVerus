package me.levansj01.verus.compat.v1_8_R3.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutMultiBlockChange;
import me.levansj01.verus.compat.v1_8_R3.NMSManager;
import me.levansj01.verus.data.transaction.world.LazyData;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.ChunkCoordIntPair;
import net.minecraft.server.v1_8_R3.PacketPlayOutMultiBlockChange;
import net.minecraft.server.v1_8_R3.PacketPlayOutMultiBlockChange.MultiBlockChangeInfo;

public class SPacketPlayOutMultiBlockChange extends VPacketPlayOutMultiBlockChange<PacketPlayOutMultiBlockChange> {
    private static final Field a_field = SafeReflection.access(PacketPlayOutMultiBlockChange.class, "a", "chunkCoordIntPair");
    private static final Field b_field = SafeReflection.access(PacketPlayOutMultiBlockChange.class, "b", "multiBlockChangeInfoArray");

    public void accept(PacketPlayOutMultiBlockChange packet) {
        ChunkCoordIntPair chunkCoordIntPair = SafeReflection.fetch(a_field, packet);
        this.chunkX = chunkCoordIntPair.x;
        this.chunkZ = chunkCoordIntPair.z;
        MultiBlockChangeInfo[] multiBlockChangeInfos = SafeReflection.fetch(b_field, packet);
        this.changes = new VPacketPlayOutMultiBlockChange.BlockChange[multiBlockChangeInfos.length];

        for (int i = 0; i < multiBlockChangeInfos.length; i++) {
            MultiBlockChangeInfo multiBlockChangeInfo = multiBlockChangeInfos[i];
            BlockPosition blockPosition = multiBlockChangeInfo.a();
            LazyData lazyData = NMSManager.getInstance().toLazy(multiBlockChangeInfo.c());
            this.changes[i] = new VPacketPlayOutMultiBlockChange.BlockChange(blockPosition.getX() & 15, blockPosition.getY(), blockPosition.getZ() & 15, lazyData);
        }
    }
}
