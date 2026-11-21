package me.levansj01.verus.data.transaction.velocity;

public class BasicVelocity {
    private final double y;
    private final double z;
    private final boolean explosion;
    private final double x;

    public double getRoundedX() {
        return this.isExplosion() ? this.getX() : this.round(this.getX());
    }

    public double getRoundedY() {
        return this.isExplosion() ? this.getY() : this.round(this.getY());
    }

    public double getZ() {
        return this.z;
    }

    public double getRoundedZ() {
        return this.isExplosion() ? this.getZ() : this.round(this.getZ());
    }

    public BasicVelocity(double x, double y, double z, boolean explosion) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.explosion = explosion;
    }

    @Override
    public String toString() {
        return "BasicVelocity(x=" + this.getX() + ", y=" + this.getY() + ", z=" + this.getZ() + ", explosion=" + this.isExplosion() + ")";
    }

    public boolean isExplosion() {
        return this.explosion;
    }

    public double getY() {
        return this.y;
    }

    private double round(double value) {
        return (int)(value * 8000.0) / 8000.0;
    }

    public double getX() {
        return this.x;
    }
}
