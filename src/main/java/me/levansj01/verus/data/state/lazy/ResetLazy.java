package me.levansj01.verus.data.state.lazy;

import java.util.function.Supplier;
import me.levansj01.verus.data.state.ResetState;

public class ResetLazy<T> implements ResetState<T> {
    private boolean fetched;
    private T data;
    private Supplier<T> supplier;

    @Override
    public void reset() {
        this.fetched = false;
    }

    @Override
    public T get() {
        if (!this.fetched) {
            this.data = (T)this.supplier.get();
            this.fetched = true;
        }

        return this.data;
    }

    public ResetLazy(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    @Override
    public void release() {
        this.supplier = null;
        this.data = null;
    }
}
