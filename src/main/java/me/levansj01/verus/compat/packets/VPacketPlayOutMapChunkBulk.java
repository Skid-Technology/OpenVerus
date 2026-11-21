package me.levansj01.verus.compat.packets;

import java.util.Arrays;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.data.state.State;
import me.levansj01.verus.data.transaction.world.SectionData;

public abstract class VPacketPlayOutMapChunkBulk<P> extends VPacket<P> {
    private static final int count = count();
    protected boolean groundUp;
    protected State<VPacketPlayOutMapChunkBulk.MappedChunk[]> chunks;

    @Override
    public void handle(PacketHandler listener) {
        listener.handle(this);
    }

    @Override
    public int ordinal() {
        return count;
    }

    public State<VPacketPlayOutMapChunkBulk.MappedChunk[]> getChunks() {
        return this.chunks;
    }

    public boolean isGroundUp() {
        return this.groundUp;
    }

    public static class MappedChunk {
        private final boolean unload;
        private final int z;
        private final int x;
        private final SectionData[] chunkMap;

        public int getX() {
            return this.x;
        }

        public boolean isUnload() {
            return this.unload;
        }

        public MappedChunk(int x, int z, SectionData[] chunkMap, boolean unload) {
            this.x = x;
            this.z = z;
            this.chunkMap = chunkMap;
            this.unload = unload;
        }

        @Override
        public int hashCode() {
            byte b = 59;
            int value = 1;
            value = value * 59 + this.getX();
            value = value * 59 + this.getZ();
            value = value * 59 + (this.isUnload() ? 79 : 97);
            return value * 59 + Arrays.deepHashCode(this.getChunkMap());
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else if (!(obj instanceof VPacketPlayOutMapChunkBulk.MappedChunk)) {
                return false;
            } else {
                VPacketPlayOutMapChunkBulk.MappedChunk mappedChunk = (VPacketPlayOutMapChunkBulk.MappedChunk)obj;
                if (!mappedChunk.canEqual(this)) {
                    return false;
                } else if (this.getX() != mappedChunk.getX()) {
                    return false;
                } else if (this.getZ() != mappedChunk.getZ()) {
                    return false;
                } else {
                    return this.isUnload() != mappedChunk.isUnload() ? false : Arrays.deepEquals(this.getChunkMap(), mappedChunk.getChunkMap());
                }
            }
        }

        @Override
        public String toString() {
            return "VPacketPlayOutMapChunkBulk.MappedChunk(x="
                + this.getX()
                + ", z="
                + this.getZ()
                + ", chunkMap="
                + Arrays.deepToString(this.getChunkMap())
                + ", unload="
                + this.isUnload()
                + ")";
        }

        public int getZ() {
            return this.z;
        }

        public SectionData[] getChunkMap() {
            return this.chunkMap;
        }

        protected boolean canEqual(Object obj) {
            return obj instanceof VPacketPlayOutMapChunkBulk.MappedChunk;
        }
    }
}
