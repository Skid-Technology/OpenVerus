package me.levansj01.verus.data.bytes;

import java.util.function.Function;
import me.levansj01.verus.data.state.Releasable;
import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4FastDecompressor;

public interface ByteData extends Releasable {
    int getOffset();

    default ByteData decompress(LZ4FastDecompressor lZ4FastDecompressor, int value) {
        return new BasicByteData(lZ4FastDecompressor.decompress(this.getArray(), this.getOffset(), value));
    }

    default void retain() {
    }

    default ByteData compress(LZ4Compressor lZ4Compressor) {
        int value = lZ4Compressor.maxCompressedLength(this.getLength());
        byte[] bytes = new byte[value];
        value = lZ4Compressor.compress(this.getArray(), this.getOffset(), this.getLength(), bytes, 0, value);
        byte[] bytes2 = new byte[value];
        System.arraycopy(bytes, 0, bytes2, 0, value);
        return new BasicByteData(bytes2);
    }

    Function<byte[], ByteData> creator();

    default void setBytePair(int value, int value2) {
        this.set(value, (byte)(value2 & 0xFF));
        this.set(value + 1, (byte)(value2 >> 8 & 0xFF));
    }

    default ByteData slice(int value, int value2) {
        return new OffsetByteData(this.getArray(), this.getOffset() + value, value2);
    }

    byte get(int value);

    default int getBytePair(int value) {
        int value2 = this.get(value) & 255;
        int value3 = this.get(value + 1) & 255;
        return value3 << 8 | value2;
    }

    byte[] getArray();

    int getLength();

    void set(int value, byte b);
}
