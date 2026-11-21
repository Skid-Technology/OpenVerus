package me.levansj01.verus.data.state.lazy;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import me.levansj01.verus.data.state.State;

public class AtomicLazy<T> implements State<T> {
    private Supplier<T> supplier;
    private final AtomicReference<Ref<T>> reference = new AtomicReference<>();

    @Override
    public T get() {
        Ref ref = (Ref)this.reference.get();
        if (ref == null) {
            synchronized (this.reference) {
                if ((ref = (Ref)this.reference.get()) == null) {
                    ref = new Ref(this.supplier.get());
                    this.reference.set(ref);
                }
            }

            this.supplier = null;
        }

        return (T)ref.getValue();
    }

    @Override
    public void release() {
        this.supplier = null;
        this.reference.lazySet(null);
    }

    public AtomicLazy(Supplier<T> supplier) {
        this.supplier = supplier;
    }
}
