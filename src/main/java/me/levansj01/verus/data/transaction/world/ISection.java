package me.levansj01.verus.data.transaction.world;

import java.util.Deque;
import me.levansj01.verus.data.state.Releasable;
import me.levansj01.verus.util.IBlockPosition;

public interface ISection extends Releasable {
    Deque<SectionUpdate> getUpdates();

    IVerusBlock get(int x, int y, int z);

    SectionData getData();

    default IVerusBlock get(IBlockPosition blockPosition) {
        return this.get(blockPosition.getX() & 15, blockPosition.getY() & 15, blockPosition.getZ() & 15);
    }
}
