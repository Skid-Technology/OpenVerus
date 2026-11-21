package me.levansj01.verus.util.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface BasicDeque<T> extends Iterable<T> {
    void clear();

    int size();

    default List<T> toList() {
        List<T> list = new ArrayList<>(this.size());

        for (T element : this) {
            list.add(element);
        }

        return list;
    }

    T pollFirst();

    void addFirst(T value);

    T pollLast();

    T peekLast();

    void addLast(T value);

    default Stream<T> stream() {
        return StreamSupport.stream(Spliterators.spliterator(this.iterator(), this.size(), 16), false);
    }

    T peekFirst();
}
