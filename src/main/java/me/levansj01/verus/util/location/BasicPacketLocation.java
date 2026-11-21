package me.levansj01.verus.util.location;

public class BasicPacketLocation implements ILocation {
    protected final double z;
    protected final float yaw;
    protected final double y;
    protected final float pitch;
    protected final double x;

    @Override
    public float getYaw() {
        return this.yaw;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public float getPitch() {
        return this.pitch;
    }

    protected boolean canEqual(Object obj) {
        return obj instanceof BasicPacketLocation;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof BasicPacketLocation)) {
            return false;
        } else {
            BasicPacketLocation basicPacketLocation = (BasicPacketLocation)obj;
            if (!basicPacketLocation.canEqual(this)) {
                return false;
            } else if (Double.compare(this.getX(), basicPacketLocation.getX()) != 0) {
                return false;
            } else {
                return Double.compare(this.getY(), basicPacketLocation.getY()) != 0 ? false : Double.compare(this.getZ(), basicPacketLocation.getZ()) == 0;
            }
        }
    }

    @Override
    public String toString() {
        return "BasicPacketLocation(x="
            + this.getX()
            + ", y="
            + this.getY()
            + ", z="
            + this.getZ()
            + ", yaw="
            + this.getYaw()
            + ", pitch="
            + this.getPitch()
            + ")";
    }

    public BasicPacketLocation(double x, double y, double z, float yaw, float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    @Override
    public double getZ() {
        return this.z;
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

    @Override
    public double getX() {
        return this.x;
    }
}
