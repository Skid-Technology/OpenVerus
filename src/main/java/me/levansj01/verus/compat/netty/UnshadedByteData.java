package me.levansj01.verus.compat.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.nio.ByteBuffer;
import java.util.function.Function;
import me.levansj01.verus.data.bytes.ByteData;
import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4FastDecompressor;

public class UnshadedByteData implements ByteData {
    private final ByteBuf byteBuf;

    @Override
    public ByteData decompress(LZ4FastDecompressor lZ4FastDecompressor, int value) {
        ByteBuf buffer = Unpooled.directBuffer(value, value);
        this.byteBuf.resetReaderIndex();
        lZ4FastDecompressor.decompress(this.byteBuf.nioBuffer(), buffer.nioBuffer(0, value));
        return new UnshadedByteData(buffer);
    }

    @Override
    public byte get(int value) {
        return this.byteBuf.getByte(value);
    }

    @Override
    public void set(int value, byte b) {
        this.byteBuf.setByte(value, b);
    }

    @Override
    public ByteData slice(int value, int value2) {
        return new UnshadedByteData(this.byteBuf.slice(value, value2));
    }

    @Override
    public int getOffset() {
        return this.byteBuf.arrayOffset();
    }

    public ByteBuf getByteBuf() {
        return this.byteBuf;
    }

    @Override
    public Function<byte[], ByteData> creator() {
        return new UnshadedByteData.Creator(this.getOffset(), this.getLength());
    }

    @Override
    public int getLength() {
        return this.byteBuf.capacity();
    }

    public UnshadedByteData(ByteBuf byteBuf) {
        this.byteBuf = byteBuf;
    }

    @Override
    public void retain() {
        this.byteBuf.retain();
    }

    @Override
    public byte[] getArray() {
        return this.byteBuf.array();
    }

    @Override
    public void release() {
        this.byteBuf.release();
    }

    @Override
    public ByteData compress(LZ4Compressor lZ4Compressor) {
        int length = this.getLength();
        int value = lZ4Compressor.maxCompressedLength(length);
        ByteBuf buffer = Unpooled.directBuffer(value, value);

        try {
            ByteBuffer byteBuffer = buffer.nioBuffer(0, value);
            lZ4Compressor.compress(this.byteBuf.nioBuffer(), byteBuffer);
            value = byteBuffer.position();
            return new UnshadedByteData(buffer.copy(0, value));
        } finally {
            buffer.release();
        }
    }

    private static class Creator implements Function<byte[], ByteData> {
        private final int length;
        private final int offset;

        public ByteData apply(byte[] bytes) {
            ByteBuf buffer = Unpooled.directBuffer(this.length, this.length);
            buffer.writeBytes(bytes, this.offset, this.length);
            return new UnshadedByteData(buffer);
        }

        public Creator(int offset, int length) {
            this.offset = offset;
            this.length = length;
        }
    }
}
