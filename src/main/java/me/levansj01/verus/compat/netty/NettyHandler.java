package me.levansj01.verus.compat.netty;

import java.util.concurrent.ThreadFactory;
import java.util.function.Consumer;
import me.levansj01.verus.data.bytes.ByteData;

public interface NettyHandler<C> {
    ByteData of(int value);

    default ThreadFactory newThreadFactory(String str) {
        return this.newThreadFactory(str, 5);
    }

    void uninject(C channel);

    default ThreadFactory newThreadFactory(String str, int value) {
        return this.newThreadFactory(str, false, value);
    }

    ByteData wrap(byte[] bytes, int value, int value2);

    ThreadFactory newThreadFactory(String str, boolean flag, int value);

    void inject(C channel, Consumer<Object> consumer);
}
