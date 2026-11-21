package me.levansj01.verus.compat.api.trove;

import gnu.trove.set.TLongSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

public class LongSetWrapper implements Set<Long> {
    private final TLongSet internal;

    @Override
    public int size() {
        return this.internal.size();
    }

    @Override
    public boolean contains(Object obj) {
        return this.internal.contains((Long)obj);
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> collection) {
        return this.internal.containsAll(collection);
    }

    @Override
    public boolean remove(Object obj) {
        return this.internal.remove((Long)obj);
    }

    public boolean add(Long longValue) {
        return this.internal.add(longValue);
    }

    @NotNull
    @Override
    public <T> T[] toArray(@NotNull T[] ts) {
        return (T[])Arrays.stream(this.internal.toArray()).boxed().toArray();
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> collection) {
        return this.internal.removeAll(collection);
    }

    @NotNull
    @Override
    public Object[] toArray() {
        return Arrays.stream(this.internal.toArray()).boxed().toArray();
    }

    @NotNull
    @Override
    public Iterator<Long> iterator() {
        return new LongIteratorWrapper(this.internal.iterator());
    }

    @Override
    public void clear() {
        this.internal.clear();
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends Long> collection) {
        return this.internal.addAll(collection);
    }

    @Override
    public boolean isEmpty() {
        return this.internal.isEmpty();
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> collection) {
        return this.internal.retainAll(collection);
    }

    public LongSetWrapper(TLongSet internal) {
        this.internal = internal;
    }
}
