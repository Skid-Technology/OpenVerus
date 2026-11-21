package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.data.state.State;
import me.levansj01.verus.data.transaction.world.LazyData;
import me.levansj01.verus.data.transaction.world.SectionData;
import me.levansj01.verus.data.transaction.world.SectionUpdate;
import me.levansj01.verus.type.enterprise.transaction.world.Section;
import me.levansj01.verus.type.enterprise.transaction.world.VerusChunk;
import me.levansj01.verus.util.IBlockPosition;

public abstract class VPacketPlayOutMultiBlockChange<P> extends VPacket<P> {
    private static final int count = count();
    protected int chunkX;
    protected int chunkZ;
    protected VPacketPlayOutMultiBlockChange.BlockChange[] changes;

    public int getChunkZ() {
        return this.chunkZ;
    }

    @Override
    public void handle(PacketHandler listener) {
        listener.handle(this);
    }

    public VPacketPlayOutMultiBlockChange.BlockChange[] getChanges() {
        return this.changes;
    }

    @Override
    public int ordinal() {
        return count;
    }

    public int getChunkX() {
        return this.chunkX;
    }

    public static class BlockChange implements IBlockPosition, SectionUpdate {
        private final int x;
        private final int z;
        private final LazyData data;
        private final int y;

        @Override
        public State<LazyData> get(int x, int y, int z) {
            return this.contains(x, y, z) ? State.of(this.data) : null;
        }

        @Override
        public int getY() {
            return this.y;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }

            if (!(obj instanceof VPacketPlayOutMultiBlockChange.BlockChange)) {
                return false;
            }

            VPacketPlayOutMultiBlockChange.BlockChange blockChange = (VPacketPlayOutMultiBlockChange.BlockChange)obj;
            if (!blockChange.canEqual(this)) {
                return false;
            }

            if (this.getX() != blockChange.getX()) {
                return false;
            }

            if (this.getY() != blockChange.getY()) {
                return false;
            }

            if (this.getZ() != blockChange.getZ()) {
                return false;
            }

            LazyData data = this.getData();
            LazyData data2 = blockChange.getData();
            return data == null ? data2 == null : data.equals(data2);
        }

        @Override
        public void after(VerusChunk runnable) {
            Section section = runnable.getSection(this.y >> 4);
            section.getUpdates().remove(this);
            SectionData data = section.getData();
            if (data != null) {
                data.set(this.x, this.y & 15, this.z, this.data);
            }
        }

        @Override
        public int getX() {
            return this.x;
        }

        @Override
        public boolean contains(int value, int value2, int value3) {
            return value == this.x && value2 == (this.y & 15) && value3 == this.z;
        }

        public LazyData getData() {
            return this.data;
        }

        @Override
        public String toString() {
            return "VPacketPlayOutMultiBlockChange.BlockChange(x="
                + this.getX()
                + ", y="
                + this.getY()
                + ", z="
                + this.getZ()
                + ", data="
                + this.getData()
                + ")";
        }

        public BlockChange(int x, int y, int z, LazyData data) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.data = data;
        }

        @Override
        public void before(VerusChunk runnable) {
            runnable.getSection(this.y >> 4).getUpdates().add(this);
        }

        @Override
        public int getZ() {
            return this.z;
        }

        @Override
        public int hashCode() {
            byte b = 59;
            int value = 1;
            value = value * 59 + this.getX();
            value = value * 59 + this.getY();
            value = value * 59 + this.getZ();
            LazyData data = this.getData();
            return value * 59 + (data == null ? 43 : data.hashCode());
        }

        protected boolean canEqual(Object obj) {
            return obj instanceof VPacketPlayOutMultiBlockChange.BlockChange;
        }
    }
}
