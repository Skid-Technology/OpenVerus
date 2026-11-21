package me.levansj01.verus.data.state.statics;

import me.levansj01.verus.data.state.State;

public class Static<T> implements State<T> {
    private final T data;

    public Static(T data) {
        this.data = data;
    }

    @Override
    public T get() {
        return this.data;
    }
}
