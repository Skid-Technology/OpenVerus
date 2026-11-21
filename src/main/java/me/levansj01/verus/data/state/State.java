package me.levansj01.verus.data.state;

import java.util.function.Supplier;
import me.levansj01.verus.data.bytes.ByteData;
import me.levansj01.verus.data.state.compress.CachedCompress;
import me.levansj01.verus.data.state.lazy.AtomicLazy;
import me.levansj01.verus.data.state.lazy.Lazy;
import me.levansj01.verus.data.state.lazy.ReleaseLazy;
import me.levansj01.verus.data.state.lazy.ResetLazy;
import me.levansj01.verus.data.state.statics.ReleaseStatic;
import me.levansj01.verus.data.state.statics.Static;

public interface State<T> extends Supplier<T>, Releasable {
    static <T> State<T> fast(Supplier<T> supplier) {
        return new Lazy(supplier);
    }

    default boolean nonNull() {
        return this.get() != null;
    }

    static <T extends Releasable> State<T> release(State<T> state) {
        return new ReleaseLazy(state);
    }

    static <T> ResetState<T> reset(Supplier<T> type) {
        return new ResetLazy(type);
    }

    static <T> State<T> of(T t) {
        return new Static((T)t);
    }

    static <T> State<T> safe(Supplier<T> supplier) {
        return new AtomicLazy(supplier);
    }

    static State<ByteData> compress(ByteData byteData) {
        return release(new CachedCompress(byteData));
    }

    static <T extends Releasable> State<T> release(T t) {
        return new ReleaseStatic((T)t);
    }
}
