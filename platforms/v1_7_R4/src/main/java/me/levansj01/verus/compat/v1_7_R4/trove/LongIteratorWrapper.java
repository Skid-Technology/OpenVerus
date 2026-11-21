package me.levansj01.verus.compat.v1_7_R4.trove;

import java.util.Iterator;
import net.minecraft.util.gnu.trove.iterator.TLongIterator;

public class LongIteratorWrapper implements Iterator {
    private final TLongIterator internal;

    public Long next() {
        return this.internal.next();
    }

    @Override
    public boolean hasNext() {
        return this.internal.hasNext();
    }

    public LongIteratorWrapper(TLongIterator internal) {
        this.internal = internal;
    }
}
