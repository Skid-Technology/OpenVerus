package me.levansj01.verus.data.transaction.world;

import me.levansj01.verus.compat.packets.VPacketPlayInBlockDig;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.compat.packets.VPacketPlayOutBlockChange;
import me.levansj01.verus.compat.packets.VPacketPlayOutMapChunk;
import me.levansj01.verus.compat.packets.VPacketPlayOutMapChunkBulk;
import me.levansj01.verus.compat.packets.VPacketPlayOutMultiBlockChange;
import me.levansj01.verus.compat.packets.VPacketPlayOutRespawn;
import me.levansj01.verus.compat.packets.VPacketPlayOutUnloadChunk;
import me.levansj01.verus.data.state.Releasable;
import me.levansj01.verus.util.IBlockPosition;
import org.bukkit.util.NumberConversions;

public interface IVerusWorld extends Releasable {
    boolean isChangingDimension();

    void handle(VPacketPlayInBlockDig<?> packet);

    void handle(VPacketPlayOutMultiBlockChange<?> packet);

    void handle(VPacketPlayOutBlockChange<?> packet);

    void handle(VPacketPlayOutMapChunkBulk<?> packet);

    IVerusBlock get(int x, int y, int z);

    void handle(VPacketPlayInBlockPlace<?> packet);

    default IVerusBlock get(IBlockPosition blockPosition) {
        return this.get(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ());
    }

    void handle(VPacketPlayOutRespawn<?> packet);

    boolean isLoaded(int value, int value2);

    default IVerusChunk getChunk(IBlockPosition blockPosition) {
        return this.getChunk(blockPosition.getX() >> 4, blockPosition.getZ() >> 4);
    }

    IVerusChunk getChunk(int value, int value2);

    void handle(VPacketPlayOutUnloadChunk<?> packet);

    default boolean isLoaded(double value, double value2) {
        return this.isLoaded(NumberConversions.floor(value), NumberConversions.floor(value2));
    }

    void handle(VPacketPlayOutMapChunk<?> packet);
}
