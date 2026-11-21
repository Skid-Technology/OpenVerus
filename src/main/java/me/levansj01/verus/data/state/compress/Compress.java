package me.levansj01.verus.data.state.compress;

import me.levansj01.verus.data.bytes.ByteData;
import me.levansj01.verus.data.state.State;
import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4FastDecompressor;

public class Compress implements State<ByteData> {
    private static final LZ4Factory FACTORY = LZ4Factory.fastestJavaInstance();
    public static final LZ4Compressor compressor = FACTORY.fastCompressor();
    public static final LZ4FastDecompressor decompressor = FACTORY.fastDecompressor();
    private final int original;
    private final ByteData compress;

    public ByteData get() {
        return this.compress.decompress(decompressor, this.original);
    }

    public Compress(ByteData byteData) {
        try {
            this.compress = byteData.compress(compressor);
            this.original = byteData.getLength();
        } finally {
            byteData.release();
        }
    }

    @Override
    public void release() {
        this.compress.release();
    }
}
