package me.levansj01.verus.util.location;

import me.levansj01.verus.util.java.MathHelper;

@Deprecated
public class PlayerLocation implements ILocationGround {
    private int tickTime;
    private float yaw;
    private double z;
    private float pitch;
    private double y;
    private Boolean ground;
    private long timestamp;
    private double x;
    private boolean teleport;

    @Override
    public double getZ() {
        return this.z;
    }

    public PlayerLocation(long timestamp, int tickTime, double x, double y, double z, float yaw, float pitch, Boolean ground, boolean teleport) {
        this.timestamp = timestamp;
        this.tickTime = tickTime;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.ground = ground;
        this.teleport = teleport;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public int getTickTime() {
        return this.tickTime;
    }

    public void setGround(Boolean ground) {
        this.ground = ground;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    @Override
    public boolean isGround() {
        return this.ground != null && this.ground;
    }

    public double distanceXZSquared(IVector3d vector3d) {
        return Math.pow(this.x - vector3d.getX(), 2.0) + Math.pow(this.z - vector3d.getZ(), 2.0);
    }

    public double distanceXZ(IVector3d vector3d) {
        return MathHelper.sqrt_double(this.distanceXZSquared(vector3d));
    }

    @Override
    public double getY() {
        return this.y;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    @Override
    public int hashCode() {
        int value = (int)(this.timestamp ^ this.timestamp >>> 32);
        value = 31 * value + this.tickTime;
        long value2 = Double.doubleToLongBits(this.x);
        value = 31 * value + (int)(value2 ^ value2 >>> 32);
        value2 = Double.doubleToLongBits(this.y);
        value = 31 * value + (int)(value2 ^ value2 >>> 32);
        value2 = Double.doubleToLongBits(this.z);
        value = 31 * value + (int)(value2 ^ value2 >>> 32);
        value = 31 * value + (this.yaw != 0.0F ? Float.floatToIntBits(this.yaw) : 0);
        return 31 * value + (this.pitch != 0.0F ? Float.floatToIntBits(this.pitch) : 0);
    }

    public Boolean getGround() {
        return this.ground;
    }

    public void setTickTime(int tickTime) {
        this.tickTime = tickTime;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public PlayerLocation add(double value, double value2, double value3) {
        return new PlayerLocation(this.timestamp, this.tickTime, this.x + value, this.y + value2, this.z + value3, this.yaw, this.pitch, this.ground, this.teleport);
    }

    public void setTeleport(boolean teleport) {
        this.teleport = teleport;
    }

    public PlayerLocation clone() {
        return new PlayerLocation(this.timestamp, this.tickTime, this.x, this.y, this.z, this.yaw, this.pitch, this.ground, this.teleport);
    }

    public PlayerLocation up() {
        return this.add(0.0, 1.0, 0.0);
    }

    @Override
    public float getPitch() {
        return this.pitch;
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj != null && this.getClass() == obj.getClass()) {
            PlayerLocation location = (PlayerLocation)obj;
            if (this.timestamp != location.timestamp) {
                return false;
            } else if (this.tickTime != location.tickTime) {
                return false;
            } else if (Double.compare(location.x, this.x) != 0) {
                return false;
            } else if (Double.compare(location.y, this.y) != 0) {
                return false;
            } else if (Double.compare(location.z, this.z) != 0) {
                return false;
            } else {
                return Float.compare(location.yaw, this.yaw) != 0 ? false : Float.compare(location.pitch, this.pitch) == 0;
            }
        } else {
            return false;
        }
    }

    @Override
    public float getYaw() {
        return this.yaw;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isTeleport() {
        return this.teleport;
    }
}
