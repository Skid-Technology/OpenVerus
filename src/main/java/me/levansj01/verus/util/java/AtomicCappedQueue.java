package me.levansj01.verus.util.java;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;
import org.jetbrains.annotations.NotNull;

public class AtomicCappedQueue<T> implements BasicDeque<T> {
    private final AtomicInteger lastIndex = new AtomicInteger(0);
    private final AtomicInteger startIndex = new AtomicInteger(0);
    private final int length;
    private final AtomicReferenceArray<T> internal;

    public AtomicCappedQueue(int length) {
        if (length <= 1) {
            throw new IllegalArgumentException("Cannot have length of " + length);
        }

        this.length = length;
        this.internal = new AtomicReferenceArray<>(length);
    }

    @Override
    public int size() {
        return this.lastIndex.get() - this.startIndex.get();
    }

    @Override
    public T peekLast() {
        return this.internal.get(modulo(this.lastIndex.get() - 1, this.length));
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int current = AtomicCappedQueue.this.startIndex.get();

            @Override
            public T next() {
                return AtomicCappedQueue.this.internal.get(AtomicCappedQueue.modulo(this.current++, AtomicCappedQueue.this.length));
            }

            @Override
            public boolean hasNext() {
                return this.current < AtomicCappedQueue.this.lastIndex.get();
            }
        };
    }

    @Override
    public T peekFirst() {
        return this.internal.get(modulo(this.startIndex.get(), this.length));
    }

    @Override
    public void addFirst(T value) {
        T previous = this.internal.getAndSet(modulo(this.startIndex.decrementAndGet(), this.length), value);
        if (previous != null) {
            this.lastIndex.decrementAndGet();
        }
    }

    private static int modulo(int value, int length) {
        int result = value % length;
        if (result < 0) {
            result += length;
        }

        return result;
    }

    @Override
    public T pollLast() {
        return this.internal.getAndSet(modulo(this.lastIndex.decrementAndGet(), this.length), null);
    }

    @Override
    public void addLast(T value) {
        T previous = this.internal.getAndSet(modulo(this.lastIndex.getAndIncrement(), this.length), value);
        if (previous != null) {
            this.startIndex.incrementAndGet();
        }
    }

    @Override
    public T pollFirst() {
        return this.internal.getAndSet(modulo(this.startIndex.getAndIncrement(), this.length), null);
    }

    @Override
    public void clear() {
        for (int i = 0; i < this.length; i++) {
            this.internal.set(i, null);
        }

        this.lastIndex.set(this.startIndex.get());
    }
}
