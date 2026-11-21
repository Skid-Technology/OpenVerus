package me.levansj01.verus.util;

import me.levansj01.verus.util.location.Vector3d;
import org.bukkit.block.Block;

public class MutableBlockLocation implements IBlockPosition {
    private int y;
    private int x;
    private int z;

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getZ() {
        return this.z;
    }

    public MutableBlockLocation incrementX() {
        return this.add(1, 0, 0);
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof MutableBlockLocation)) {
            return false;
        } else {
            MutableBlockLocation mutableBlockLocation = (MutableBlockLocation)obj;
            if (!mutableBlockLocation.canEqual(this)) {
                return false;
            } else if (this.getX() != mutableBlockLocation.getX()) {
                return false;
            } else {
                return this.getY() != mutableBlockLocation.getY() ? false : this.getZ() == mutableBlockLocation.getZ();
            }
        }
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    @Override
    public int hashCode() {
        byte b = 59;
        int value = 1;
        value = value * 59 + this.getX();
        value = value * 59 + this.getY();
        return value * 59 + this.getZ();
    }

    public MutableBlockLocation andThen(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public MutableBlockLocation incrementZ() {
        return this.add(0, 0, 1);
    }

    @Override
    public int getY() {
        return this.y;
    }

    public MutableBlockLocation andThen(Vector3d vector3d) {
        this.x = (int)Math.floor(vector3d.getX());
        this.y = (int)Math.floor(vector3d.getY());
        this.z = (int)Math.floor(vector3d.getZ());
        return this;
    }

    public MutableBlockLocation(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public MutableBlockLocation incrementY() {
        return this.add(0, 1, 0);
    }

    protected boolean canEqual(Object obj) {
        return obj instanceof MutableBlockLocation;
    }

    public MutableBlockLocation add(int value, int value2, int value3) {
        this.x += value;
        this.y += value2;
        this.z += value3;
        return this;
    }

    public static MutableBlockLocation from(Block block) {
        return new MutableBlockLocation(block.getX(), block.getY(), block.getZ());
    }

    public static MutableBlockLocation from(Vector3d vector3d) {
        return new MutableBlockLocation((int)Math.floor(vector3d.getX()), (int)Math.floor(vector3d.getY()), (int)Math.floor(vector3d.getZ()));
    }

    @Override
    public String toString() {
        return "MutableBlockLocation(x=" + this.getX() + ", y=" + this.getY() + ", z=" + this.getZ() + ")";
    }
}
