package me.levansj01.verus.util.location;

public class Vector3d implements IVector3d {
    protected double x;
    protected double y;
    protected double z;

    public Vector3d copy() {
        return new Vector3d(this.x, this.y, this.z);
    }

    public Vector3d subtract(IVector3d vector3d) {
        this.x = this.x - vector3d.getX();
        this.y = this.y - vector3d.getY();
        this.z = this.z - vector3d.getZ();
        return this;
    }

    public void setX(double x) {
        this.x = x;
    }

    public Vector3d getIntermediateWithZValue(Vector3d vector3d, double value) {
        double deltaX = vector3d.x - this.x;
        double deltaY = vector3d.y - this.y;
        double deltaZ = vector3d.z - this.z;
        if (deltaZ * deltaZ < 1.0E-7F) {
            return null;
        }

        double value2 = (value - this.z) / deltaZ;
        return value2 >= 0.0 && value2 <= 1.0 ? new Vector3d(this.x + deltaX * value2, this.y + deltaY * value2, this.z + deltaZ * value2) : null;
    }

    public Vector3d add(Vector3d vector3d) {
        this.x = this.x + vector3d.x;
        this.y = this.y + vector3d.y;
        this.z = this.z + vector3d.z;
        return this;
    }

    @Override
    public String toString() {
        return "Vector3d(x=" + this.getX() + ", y=" + this.getY() + ", z=" + this.getZ() + ")";
    }

    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    @Override
    public double getY() {
        return this.y;
    }

    public double lengthSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }

    public Vector3d add(double value) {
        this.x += value;
        this.y += value;
        this.z += value;
        return this;
    }

    @Override
    public double getZ() {
        return this.z;
    }

    public Vector3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3d divide(double value) {
        this.x /= value;
        this.y /= value;
        this.z /= value;
        return this;
    }

    public Vector3d normalize() {
        return this.divide(this.length());
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double distanceSquared(Vector3d vector3d) {
        return (this.x - vector3d.x) * (this.x - vector3d.x) + (this.y - vector3d.y) * (this.y - vector3d.y) + (this.z - vector3d.z) * (this.z - vector3d.z);
    }

    public Vector3d multiply(double value) {
        this.x *= value;
        this.y *= value;
        this.z *= value;
        return this;
    }

    public Vector3d getIntermediateWithXValue(Vector3d vector3d, double value) {
        double deltaX = vector3d.x - this.x;
        double deltaY = vector3d.y - this.y;
        double deltaZ = vector3d.z - this.z;
        if (deltaX * deltaX < 1.0E-7F) {
            return null;
        }

        double value2 = (value - this.x) / deltaX;
        return value2 >= 0.0 && value2 <= 1.0 ? new Vector3d(this.x + deltaX * value2, this.y + deltaY * value2, this.z + deltaZ * value2) : null;
    }

    public Vector3d getIntermediateWithYValue(Vector3d vector3d, double value) {
        double deltaX = vector3d.x - this.x;
        double deltaY = vector3d.y - this.y;
        double deltaZ = vector3d.z - this.z;
        if (deltaY * deltaY < 1.0E-7F) {
            return null;
        }

        double value2 = (value - this.y) / deltaY;
        return value2 >= 0.0 && value2 <= 1.0 ? new Vector3d(this.x + deltaX * value2, this.y + deltaY * value2, this.z + deltaZ * value2) : null;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public double getX() {
        return this.x;
    }

    public double distance(Vector3d vector3d) {
        return Math.sqrt(this.distanceSquared(vector3d));
    }
}
