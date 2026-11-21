package me.levansj01.verus.data.bytes;

import java.util.Arrays;
import java.util.function.Function;

public class OffsetByteData implements ByteData {
    private final byte[] array;
    private final int length;
    private final int offset;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof OffsetByteData)) {
            return false;
        } else {
            OffsetByteData offsetByteData = (OffsetByteData)obj;
            if (!offsetByteData.canEqual(this)) {
                return false;
            } else if (this.getOffset() != offsetByteData.getOffset()) {
                return false;
            } else {
                return this.getLength() != offsetByteData.getLength() ? false : Arrays.equals(this.getArray(), offsetByteData.getArray());
            }
        }
    }

    @Override
    public int getOffset() {
        return this.offset;
    }

    @Override
    public Function<byte[], ByteData> creator() {
        return new OffsetByteData.Creator(this.offset, this.length);
    }

    @Override
    public void set(int value, byte b) {
        this.array[this.offset + value] = b;
    }

    @Override
    public int hashCode() {
        byte b = 59;
        int value = 1;
        value = value * 59 + this.getOffset();
        value = value * 59 + this.getLength();
        return value * 59 + Arrays.hashCode(this.getArray());
    }

    @Override
    public byte[] getArray() {
        return this.array;
    }

    @Override
    public byte get(int value) {
        return this.array[this.offset + value];
    }

    public OffsetByteData(byte[] array, int offset, int length) {
        this.array = array;
        this.offset = offset;
        this.length = length;
    }

    @Override
    public int getLength() {
        return this.length;
    }

    @Override
    public String toString() {
        return "OffsetByteData(array=" + Arrays.toString(this.getArray()) + ", offset=" + this.getOffset() + ", length=" + this.getLength() + ")";
    }

    protected boolean canEqual(Object obj) {
        return obj instanceof OffsetByteData;
    }

    private static class Creator implements Function<byte[], ByteData> {
        private final int offset;
        private final int length;

        public ByteData apply(byte[] bytes) {
            return new OffsetByteData(bytes, this.offset, this.length);
        }

        public Creator(int offset, int length) {
            this.offset = offset;
            this.length = length;
        }
    }
}
