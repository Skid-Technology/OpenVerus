package me.levansj01.verus.data.state.lazy;

import java.util.function.Supplier;
import me.levansj01.verus.data.state.State;

public class Lazy<T> implements State<T> {
    private T reference;
    private Supplier<T> supplier;
    private boolean fetched;

    public Lazy(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    @Override
    public void release() {
        this.supplier = null;
        this.reference = null;
    }

    @Override
    public T get() {
        if (!this.fetched) {
            this.reference = (T)this.supplier.get();
            this.fetched = true;
            this.supplier = null;
        }

        return this.reference;
    }
}
