package me.levansj01.verus.util.java;

import java.util.Iterator;
import java.util.function.Function;

public class WrappingIterator<K, V> implements Iterator<K> {
    private final Iterator<V> iterator;
    private final Function<V, K> converter;

    public WrappingIterator(Iterator<V> iterator, Function<V, K> converter) {
        this.iterator = iterator;
        this.converter = converter;
    }

    @Override
    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override
    public K next() {
        V next = this.iterator.next();
        return next == null ? null : this.converter.apply(next);
    }
}
