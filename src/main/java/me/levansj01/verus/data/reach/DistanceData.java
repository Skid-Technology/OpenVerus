package me.levansj01.verus.data.reach;

import me.levansj01.verus.util.Cuboid;

public class DistanceData {
    private final double vertical;
    private final double z;
    private final double reach;
    private final Cuboid hitbox;
    private final double previousTickExtra;
    private final double extra;
    private final double x;
    private final double angle;
    private final double horizontal;

    public double getPreviousTickExtra() {
        return this.previousTickExtra;
    }

    public double getX() {
        return this.x;
    }

    public DistanceData(Cuboid hitbox, double x, double z, double horizontal, double angle, double extra, double vertical, double reach, double previousTickExtra) {
        this.hitbox = hitbox;
        this.x = x;
        this.z = z;
        this.horizontal = horizontal;
        this.angle = angle;
        this.extra = extra;
        this.vertical = vertical;
        this.reach = reach;
        this.previousTickExtra = previousTickExtra;
    }

    protected boolean canEqual(Object obj) {
        return obj instanceof DistanceData;
    }

    public double getVertical() {
        return this.vertical;
    }

    @Override
    public int hashCode() {
        byte b = 59;
        int value = 1;
        long value2 = Double.doubleToLongBits(this.getX());
        value = value * 59 + (int)(value2 >>> 32 ^ value2);
        long value3 = Double.doubleToLongBits(this.getZ());
        value = value * 59 + (int)(value3 >>> 32 ^ value3);
        long value4 = Double.doubleToLongBits(this.getHorizontal());
        value = value * 59 + (int)(value4 >>> 32 ^ value4);
        long value5 = Double.doubleToLongBits(this.getAngle());
        value = value * 59 + (int)(value5 >>> 32 ^ value5);
        long value6 = Double.doubleToLongBits(this.getExtra());
        value = value * 59 + (int)(value6 >>> 32 ^ value6);
        long value7 = Double.doubleToLongBits(this.getVertical());
        value = value * 59 + (int)(value7 >>> 32 ^ value7);
        long value8 = Double.doubleToLongBits(this.getReach());
        value = value * 59 + (int)(value8 >>> 32 ^ value8);
        long value9 = Double.doubleToLongBits(this.getPreviousTickExtra());
        value = value * 59 + (int)(value9 >>> 32 ^ value9);
        Cuboid hitbox = this.getHitbox();
        return value * 59 + (hitbox == null ? 43 : hitbox.hashCode());
    }

    public double getExtra() {
        return this.extra;
    }

    public double getReach() {
        return this.reach;
    }

    public double getAngle() {
        return this.angle;
    }

    public double getZ() {
        return this.z;
    }

    public Cuboid getHitbox() {
        return this.hitbox;
    }

    @Override
    public String toString() {
        return "DistanceData(hitbox="
            + this.getHitbox()
            + ", x="
            + this.getX()
            + ", z="
            + this.getZ()
            + ", horizontal="
            + this.getHorizontal()
            + ", angle="
            + this.getAngle()
            + ", extra="
            + this.getExtra()
            + ", vertical="
            + this.getVertical()
            + ", reach="
            + this.getReach()
            + ", previousTickExtra="
            + this.getPreviousTickExtra()
            + ")";
    }

    public double getHorizontal() {
        return this.horizontal;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof DistanceData)) {
            return false;
        }

        DistanceData distanceData = (DistanceData)obj;
        if (!distanceData.canEqual(this)) {
            return false;
        }

        if (Double.compare(this.getX(), distanceData.getX()) != 0) {
            return false;
        }

        if (Double.compare(this.getZ(), distanceData.getZ()) != 0) {
            return false;
        }

        if (Double.compare(this.getHorizontal(), distanceData.getHorizontal()) != 0) {
            return false;
        }

        if (Double.compare(this.getAngle(), distanceData.getAngle()) != 0) {
            return false;
        }

        if (Double.compare(this.getExtra(), distanceData.getExtra()) != 0) {
            return false;
        }

        if (Double.compare(this.getVertical(), distanceData.getVertical()) != 0) {
            return false;
        }

        if (Double.compare(this.getReach(), distanceData.getReach()) != 0) {
            return false;
        }

        if (Double.compare(this.getPreviousTickExtra(), distanceData.getPreviousTickExtra()) != 0) {
            return false;
        }

        Cuboid hitbox = this.getHitbox();
        Cuboid hitbox2 = distanceData.getHitbox();
        return hitbox == null ? hitbox2 == null : hitbox.equals(hitbox2);
    }
}
