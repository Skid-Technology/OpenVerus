package me.levansj01.verus.data.transaction.world;

import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.compat.packets.VPacketPlayOutMapChunk;
import me.levansj01.verus.data.bytes.BasicByteData;
import me.levansj01.verus.data.state.State;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.storage.config.VerusConfiguration;

public class BlankSection implements SectionData {
    private VPacketPlayOutMapChunk.SectionDataImpl sectionData;

    @Override
    public State<LazyData> get(int x, int y, int z) {
        return this.sectionData == null ? LazyData.STATE_AIR : this.sectionData.get(x, y, z);
    }

    @Override
    public void set(int x, int y, int z, LazyData data) {
        if (this.sectionData == null) {
            VerusConfiguration config = StorageEngine.getInstance().getVerusConfig();
            this.sectionData = new VPacketPlayOutMapChunk.SectionDataImpl(
                State.of(config.isDirectMemory() ? NMSManager.getInstance().getNettyHandler().of(8192) : new BasicByteData(new byte[8192]))
            );
        }

        this.sectionData.set(x, y, z, data);
    }

    @Override
    public void release() {
        if (this.sectionData != null) {
            this.sectionData.release();
        }
    }
}
