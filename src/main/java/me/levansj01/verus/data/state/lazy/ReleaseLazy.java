package me.levansj01.verus.data.state.lazy;

import java.util.concurrent.atomic.AtomicReference;
import me.levansj01.verus.data.state.Releasable;
import me.levansj01.verus.data.state.State;

public class ReleaseLazy<T extends Releasable> implements State<T> {
    private final AtomicReference<Ref<T>> reference = new AtomicReference<>();
    private State<T> supplier;

    public T get() {
        Ref ref = (Ref)this.reference.get();
        if (ref == null) {
            synchronized (this.reference) {
                if ((ref = (Ref)this.reference.get()) == null) {
                    ref = new Ref(this.supplier.get());
                    this.reference.set(ref);
                    this.supplier.release();
                }
            }

            this.supplier = null;
        }

        return (T)ref.getValue();
    }

    @Override
    public void release() {
        if (this.supplier != null) {
            this.supplier.release();
            this.supplier = null;
        }

        Ref andSet = (Ref)this.reference.getAndSet(null);
        if (andSet != null) {
            Releasable value = (Releasable)andSet.getValue();
            if (value != null) {
                value.release();
            }
        }
    }

    public ReleaseLazy(State<T> supplier) {
        this.supplier = supplier;
    }
}
