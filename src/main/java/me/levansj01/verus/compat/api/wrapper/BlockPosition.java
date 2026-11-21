package me.levansj01.verus.compat.api.wrapper;

import me.levansj01.verus.util.IBlockPosition;
import me.levansj01.verus.util.MutableBlockLocation;

public class BlockPosition implements IBlockPosition {
    private int z;
    private int y;
    private int x;

    public BlockPosition copy() {
        return new BlockPosition(this.x, this.y, this.z);
    }

    @Override
    public int getZ() {
        return this.z;
    }

    public void setX(int x) {
        this.x = x;
    }

    public BlockPosition move(Direction direction, int value) {
        switch (direction) {
            case UP:
                this.y += value;
                break;
            case DOWN:
                this.y -= value;
                break;
            case SOUTH:
                this.z += value;
                break;
            case NORTH:
                this.z -= value;
                break;
            case EAST:
                this.x += value;
                break;
            case WEST:
                this.x -= value;
        }

        return this;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int hashCode() {
        byte b = 59;
        int value = 1;
        value = value * 59 + this.getX();
        value = value * 59 + this.getY();
        return value * 59 + this.getZ();
    }

    protected boolean canEqual(Object obj) {
        return obj instanceof BlockPosition;
    }

    @Override
    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public MutableBlockLocation toMutableBlock() {
        return new MutableBlockLocation(this.x, this.y, this.z);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof BlockPosition)) {
            return false;
        } else {
            BlockPosition blockPosition = (BlockPosition)obj;
            if (!blockPosition.canEqual(this)) {
                return false;
            } else if (this.getX() != blockPosition.getX()) {
                return false;
            } else {
                return this.getY() != blockPosition.getY() ? false : this.getZ() == blockPosition.getZ();
            }
        }
    }

    @Override
    public String toString() {
        return "BlockPosition(x=" + this.getX() + ", y=" + this.getY() + ", z=" + this.getZ() + ")";
    }

    public boolean nearby(BlockPosition blockPosition) {
        return this.diff(blockPosition) == 1;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public BlockPosition(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int diff(BlockPosition blockPosition) {
        return Math.abs(this.x - blockPosition.x) + Math.abs(this.z - blockPosition.z);
    }

    public void face(int value) {
        switch (value) {
            case 0:
                this.y--;
                break;
            case 1:
                this.y++;
                break;
            case 2:
                this.z--;
                break;
            case 3:
                this.z++;
                break;
            case 4:
                this.x--;
                break;
            case 5:
                this.x++;
        }
    }
}
