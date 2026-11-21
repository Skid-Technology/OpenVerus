package me.levansj01.verus.compat.api.trove;

import gnu.trove.iterator.TLongIterator;
import java.util.Iterator;

public class LongIteratorWrapper implements Iterator {
    private final TLongIterator internal;

    public LongIteratorWrapper(TLongIterator internal) {
        this.internal = internal;
    }

    public Long next() {
        return this.internal.next();
    }

    @Override
    public boolean hasNext() {
        return this.internal.hasNext();
    }
}
