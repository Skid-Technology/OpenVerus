package me.levansj01.verus.data.transaction.tracker;

import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.location.IVector3d;

public class BasicLocation implements IServerLocation {
    protected final double z;
    protected final double y;
    protected final double x;

    public BasicLocation(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public BasicLocation add(double value, double value2, double value3) {
        return new BasicLocation(this.x + value, this.y + value2, this.z + value3);
    }

    @Override
    public double getX() {
        return this.x;
    }

    public BasicLocation add(IServerLocation serverLocation) {
        return this.add(serverLocation.getX(), serverLocation.getY(), serverLocation.getZ());
    }

    @Override
    public String toString() {
        return "BasicLocation(x=" + this.getX() + ", y=" + this.getY() + ", z=" + this.getZ() + ")";
    }

    @Override
    public double getZ() {
        return this.z;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof BasicLocation)) {
            return false;
        } else {
            BasicLocation basicLocation = (BasicLocation)obj;
            if (!basicLocation.canEqual(this)) {
                return false;
            } else if (Double.compare(this.getX(), basicLocation.getX()) != 0) {
                return false;
            } else {
                return Double.compare(this.getY(), basicLocation.getY()) != 0 ? false : Double.compare(this.getZ(), basicLocation.getZ()) == 0;
            }
        }
    }

    public Cuboid hitbox() {
        return new Cuboid(this.x, this.y, this.z).hitbox();
    }

    public BasicLocation interpolate(IVector3d vector3d, int value) {
        if (value <= 0) {
            return this;
        }

        if (value >= 3) {
            return new BasicLocation(vector3d.getX(), vector3d.getY(), vector3d.getZ());
        }

        double value2 = value / 3.0;
        return new BasicLocation(this.x + (vector3d.getX() - this.x) * value2, this.y + (vector3d.getY() - this.y) * value2, this.z + (vector3d.getZ() - this.z) * value2);
    }

    @Override
    public int hashCode() {
        byte b = 59;
        int value = 1;
        long value2 = Double.doubleToLongBits(this.getX());
        value = value * 59 + (int)(value2 >>> 32 ^ value2);
        long value3 = Double.doubleToLongBits(this.getY());
        value = value * 59 + (int)(value3 >>> 32 ^ value3);
        long value4 = Double.doubleToLongBits(this.getZ());
        return value * 59 + (int)(value4 >>> 32 ^ value4);
    }

    protected boolean canEqual(Object obj) {
        return obj instanceof BasicLocation;
    }
}
