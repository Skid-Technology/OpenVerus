package me.levansj01.verus.data.transaction.world;

import me.levansj01.verus.data.state.Releasable;
import me.levansj01.verus.type.enterprise.transaction.world.VerusBlock;
import me.levansj01.verus.util.IBlockPosition;

public interface IVerusChunk extends Releasable {
    default VerusBlock getType(IBlockPosition blockPosition) {
        return this.getType(blockPosition.getX() & 15, blockPosition.getY(), blockPosition.getZ() & 15);
    }

    default ISection getSection(IBlockPosition blockPosition) {
        return this.getSection(blockPosition.getY() >> 4);
    }

    ISection getSection(int value);

    VerusBlock getType(int player, int world, int block);
}
