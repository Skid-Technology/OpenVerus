package me.levansj01.verus.util.java;

import java.util.Arrays;
import java.util.Iterator;
import org.jetbrains.annotations.NotNull;

public class CappedQueue<T> implements BasicDeque<T> {
    private int lastIndex;
    private final Object[] internal;
    private int startIndex;

    @Override
    public int size() {
        return this.lastIndex - this.startIndex;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T pollFirst() {
        return (T)this.getAndSetInternal(modulo(this.startIndex++, this.internal.length), null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T peekLast() {
        return (T)this.internal[modulo(this.lastIndex - 1, this.internal.length)];
    }

    @Override
    public void addLast(T value) {
        Object previous = this.getAndSetInternal(modulo(this.lastIndex++, this.internal.length), value);
        if (previous != null) {
            this.startIndex++;
        }
    }

    @Override
    public void addFirst(T value) {
        Object previous = this.getAndSetInternal(modulo(--this.startIndex, this.internal.length), value);
        if (previous != null) {
            this.lastIndex--;
        }
    }

    @Override
    public void clear() {
        Arrays.fill(this.internal, null);
        this.lastIndex = this.startIndex;
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int current = CappedQueue.this.startIndex;

            @Override
            public boolean hasNext() {
                return this.current < CappedQueue.this.lastIndex;
            }

            @Override
            @SuppressWarnings("unchecked")
            public T next() {
                return (T)CappedQueue.this.internal[CappedQueue.modulo(this.current++, CappedQueue.this.internal.length)];
            }
        };
    }

    public CappedQueue(int capacity) {
        if (capacity <= 1) {
            throw new IllegalArgumentException("Cannot have length of " + capacity);
        }

        this.internal = new Object[capacity];
    }

    @Override
    @SuppressWarnings("unchecked")
    public T pollLast() {
        return (T)this.getAndSetInternal(modulo(--this.lastIndex, this.internal.length), null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T peekFirst() {
        return (T)this.internal[modulo(this.startIndex, this.internal.length)];
    }

    private static int modulo(int value, int length) {
        int result = value % length;
        if (result < 0) {
            result += length;
        }

        return result;
    }

    private Object getAndSetInternal(int index, Object value) {
        Object previous = this.internal[index];
        this.internal[index] = value;
        return previous;
    }
}
