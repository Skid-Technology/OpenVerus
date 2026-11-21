package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.data.bytes.BasicByteData;
import me.levansj01.verus.data.bytes.ByteData;
import me.levansj01.verus.data.state.State;
import me.levansj01.verus.data.transaction.world.LazyData;
import me.levansj01.verus.data.transaction.world.SectionData;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.storage.config.VerusConfiguration;

public abstract class VPacketPlayOutMapChunk<P> extends VPacket<P> {
    private static final int count = count();
    protected int chunkX;
    protected int chunkZ;
    protected State<SectionData[]> chunkMap;
    protected boolean groundUp;
    protected boolean unload;

    public static ByteData wrap(byte[] data) {
        VerusConfiguration config = StorageEngine.getInstance().getVerusConfig();
        return config.isDirectMemory() ? NMSManager.getInstance().getNettyHandler().wrap(data, 0, data.length) : new BasicByteData(data);
    }

    @Override
    public void handle(PacketHandler listener) {
        listener.handle(this);
    }

    @Override
    public int ordinal() {
        return count;
    }

    public int getChunkX() {
        return this.chunkX;
    }

    public int getChunkZ() {
        return this.chunkZ;
    }

    public State<SectionData[]> getChunkMap() {
        return this.chunkMap;
    }

    public boolean isGroundUp() {
        return this.groundUp;
    }

    public boolean isUnload() {
        return this.unload;
    }

    public static class SectionDataImpl implements SectionData {
        private final State<ByteData> state;

        @Override
        public State<LazyData> get(int x, int y, int z) {
            return State.fast(() -> {
                ByteData byteData = (ByteData)this.state.get();
                int value = y << 8 | z << 4 | x;
                int bytePair = byteData.getBytePair(value << 1);
                return NMSManager.getInstance().fromIDToLazy(bytePair);
            });
        }

        public static VPacketPlayOutMapChunk.SectionDataImpl of(ByteData byteData, int value) {
            VerusConfiguration verusConfig = StorageEngine.getInstance().getVerusConfig();
            byteData = byteData.slice(value * 4096 * 2, 8192);
            byteData.retain();
            return new VPacketPlayOutMapChunk.SectionDataImpl(verusConfig.isCompression() ? State.compress(byteData) : State.release(byteData));
        }

        public SectionDataImpl(State<ByteData> state) {
            this.state = state;
        }

        @Override
        public void release() {
            this.state.release();
        }

        @Override
        public void set(int x, int y, int z, LazyData data) {
            ByteData byteData = (ByteData)this.state.get();
            int value = y << 8 | z << 4 | x;
            int value2 = (Integer)data.getValue().get();
            byteData.setBytePair(value << 1, value2);
        }
    }
}
