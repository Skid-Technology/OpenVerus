package me.levansj01.verus.data.bytes;

import java.util.Arrays;
import java.util.function.Function;

public class BasicByteData implements ByteData {
    private final byte[] array;

    @Override
    public int getOffset() {
        return 0;
    }

    @Override
    public int getLength() {
        return this.array.length;
    }

    @Override
    public byte[] getArray() {
        return this.array;
    }

    protected boolean canEqual(Object obj) {
        return obj instanceof BasicByteData;
    }

    @Override
    public String toString() {
        return "BasicByteData(array=" + Arrays.toString(this.getArray()) + ")";
    }

    @Override
    public byte get(int value) {
        return this.array[value];
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof BasicByteData)) {
            return false;
        }

        BasicByteData basicByteData = (BasicByteData)obj;
        return !basicByteData.canEqual(this) ? false : Arrays.equals(this.getArray(), basicByteData.getArray());
    }

    @Override
    public int hashCode() {
        byte b = 59;
        byte b2 = 1;
        return b2 * 59 + Arrays.hashCode(this.getArray());
    }

    @Override
    public Function<byte[], ByteData> creator() {
        return BasicByteData::new;
    }

    @Override
    public void set(int value, byte b) {
        this.array[value] = b;
    }

    public BasicByteData(byte[] array) {
        this.array = array;
    }
}
