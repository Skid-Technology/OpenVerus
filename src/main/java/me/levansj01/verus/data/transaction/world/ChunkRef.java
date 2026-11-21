package me.levansj01.verus.data.transaction.world;

public class ChunkRef<C> {
    private boolean init;
    private int x;
    private C chunk;
    private int z;

    public void setX(int x) {
        this.x = x;
    }

    public C getChunk() {
        return this.chunk;
    }

    @Override
    public int hashCode() {
        byte b = 59;
        int value = 1;
        value = value * 59 + (this.isInit() ? 79 : 97);
        value = value * 59 + this.getX();
        value = value * 59 + this.getZ();
        Object chunk = this.getChunk();
        return value * 59 + (chunk == null ? 43 : chunk.hashCode());
    }

    protected boolean canEqual(Object obj) {
        return obj instanceof ChunkRef;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof ChunkRef)) {
            return false;
        }

        ChunkRef chunkRef = (ChunkRef)obj;
        if (!chunkRef.canEqual(this)) {
            return false;
        }

        if (this.isInit() != chunkRef.isInit()) {
            return false;
        }

        if (this.getX() != chunkRef.getX()) {
            return false;
        }

        if (this.getZ() != chunkRef.getZ()) {
            return false;
        }

        Object chunk = this.getChunk();
        Object chunk2 = chunkRef.getChunk();
        return chunk == null ? chunk2 == null : chunk.equals(chunk2);
    }

    public C set(C chunk, int x, int z) {
        this.chunk = chunk;
        this.x = x;
        this.z = z;
        this.init = true;
        return chunk;
    }

    public boolean same(int value, int value2) {
        return this.init && this.x == value && this.z == value2;
    }

    public void setChunk(C chunk) {
        this.chunk = chunk;
    }

    @Override
    public String toString() {
        return "ChunkRef(init=" + this.isInit() + ", chunk=" + this.getChunk() + ", x=" + this.getX() + ", z=" + this.getZ() + ")";
    }

    public int getX() {
        return this.x;
    }

    public boolean isInit() {
        return this.init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getZ() {
        return this.z;
    }
}
