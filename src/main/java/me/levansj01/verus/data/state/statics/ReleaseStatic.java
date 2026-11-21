package me.levansj01.verus.data.state.statics;

import me.levansj01.verus.data.state.Releasable;
import me.levansj01.verus.data.state.State;

public class ReleaseStatic<T extends Releasable> implements State<T> {
    private final T data;

    @Override
    public void release() {
        if (this.data != null) {
            this.data.release();
        }
    }

    public T get() {
        return this.data;
    }

    public ReleaseStatic(T data) {
        this.data = data;
    }
}
