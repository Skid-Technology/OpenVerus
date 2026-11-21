package me.levansj01.verus.data.state.compress;

import java.lang.ref.WeakReference;
import java.util.function.Function;
import me.levansj01.verus.data.bytes.ByteData;

public class CachedCompress extends Compress {
    private final WeakReference<byte[]> ref;
    private final Function<byte[], ByteData> unwrap;

    @Override
    public ByteData get() {
        byte[] bytes = this.ref.get();
        return bytes != null ? this.unwrap.apply(bytes) : super.get();
    }

    @Override
    public void release() {
        super.release();
        this.ref.clear();
    }

    public CachedCompress(ByteData byteData) {
        super(byteData);
        this.unwrap = byteData.creator();
        this.ref = new WeakReference<>(byteData.getArray());
    }
}
