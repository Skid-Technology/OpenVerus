package me.levansj01.verus.data.transaction.world;

import me.levansj01.verus.data.state.State;

public interface SectionData extends SectionUpdate {
    void set(int x, int y, int z, LazyData data);

    @Override
    State<LazyData> get(int x, int y, int z);

    @Override
    default boolean contains(int value, int value2, int value3) {
        return true;
    }
}
