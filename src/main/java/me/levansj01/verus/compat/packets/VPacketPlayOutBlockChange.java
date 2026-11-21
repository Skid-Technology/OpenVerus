package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.data.state.State;
import me.levansj01.verus.data.transaction.world.LazyData;
import me.levansj01.verus.data.transaction.world.SectionData;
import me.levansj01.verus.data.transaction.world.SectionUpdate;
import me.levansj01.verus.type.enterprise.transaction.world.Section;
import me.levansj01.verus.type.enterprise.transaction.world.VerusChunk;

public abstract class VPacketPlayOutBlockChange<P> extends VPacket<P> implements SectionUpdate {
    private static final int count = count();
    protected LazyData data;
    protected int z;
    protected int x;
    protected int y;

    @Override
    public void handle(PacketHandler listener) {
        listener.handle(this);
    }

    @Override
    public State<LazyData> get(int x, int y, int z) {
        return this.contains(x, y, z) ? State.of(this.data) : null;
    }

    public int getZ() {
        return this.z;
    }

    public int getX() {
        return this.x;
    }

    @Override
    public boolean contains(int value, int value2, int value3) {
        return value == (this.x & 15) && value2 == (this.y & 15) && value3 == (this.z & 15);
    }

    public int getY() {
        return this.y;
    }

    @Override
    public int ordinal() {
        return count;
    }

    @Override
    public void before(VerusChunk runnable) {
        runnable.getSection(this.y >> 4).getUpdates().add(this);
    }

    @Override
    public void after(VerusChunk runnable) {
        Section section = runnable.getSection(this.y >> 4);
        section.getUpdates().remove(this);
        SectionData data = section.getData();
        if (data != null) {
            data.set(this.x & 15, this.y & 15, this.z & 15, this.data);
        }
    }

    public LazyData getData() {
        return this.data;
    }
}
