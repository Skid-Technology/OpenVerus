package me.levansj01.verus.util.java;

import java.util.Iterator;

public class PrefixIterator<T> implements Iterator<T> {
    private final Iterator<T> iterator;
    private final T data;
    private boolean done;

    @Override
    public boolean hasNext() {
        return !this.done || this.iterator.hasNext();
    }

    @Override
    public T next() {
        if (this.done) {
            return this.iterator.next();
        }

        this.done = true;
        return this.data;
    }

    public PrefixIterator(T data, Iterator<T> iterator) {
        this.data = data;
        this.iterator = iterator;
        this.done = data == null;
    }
}
