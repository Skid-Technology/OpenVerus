package me.levansj01.verus.data.transaction.world;

import me.levansj01.verus.compat.api.wrapper.BlockPosition;
import me.levansj01.verus.data.state.Releasable;
import me.levansj01.verus.data.state.State;
import me.levansj01.verus.type.enterprise.transaction.world.VerusChunk;

public interface SectionUpdate extends Releasable {
    default void before(VerusChunk runnable) {
    }

    boolean contains(int value, int value2, int value3);

    default void after(VerusChunk runnable) {
    }

    default boolean contains(BlockPosition blockPosition) {
        return this.contains(blockPosition.getX() & 15, blockPosition.getY() & 15, blockPosition.getZ() & 15);
    }

    State<LazyData> get(int x, int y, int z);
}
