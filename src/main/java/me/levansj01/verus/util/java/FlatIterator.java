package me.levansj01.verus.util.java;

import java.util.Iterator;

public class FlatIterator<T> implements Iterator<T> {
    private final Iterator<Iterator<T>> iterator;
    private Iterator<T> current;

    private void moveToNext() {
        if (this.iterator.hasNext()) {
            this.current = (Iterator<T>)this.iterator.next();
            if (!this.current.hasNext()) {
                this.moveToNext();
            }
        } else {
            this.current = null;
        }
    }

    public FlatIterator(Iterator<Iterator<T>> iterator) {
        this.iterator = iterator;
        this.moveToNext();
    }

    @Override
    public T next() {
        try {
            return this.current.next();
        } finally {
            if (!this.current.hasNext()) {
                this.moveToNext();
            }
        }
    }

    @Override
    public boolean hasNext() {
        return this.current != null;
    }
}
