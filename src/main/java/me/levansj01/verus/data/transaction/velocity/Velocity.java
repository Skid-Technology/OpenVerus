package me.levansj01.verus.data.transaction.velocity;

import me.levansj01.verus.util.java.MathUtil;

@Deprecated
public class Velocity {
    private final int ticks;
    private Double hypotSquaredXZ = null;
    private boolean attenuated = false;
    private double z;
    private double y;
    private final double originalY;
    private final long timestamp;
    private double x;
    private final boolean explosion;

    public double getX() {
        return this.x;
    }

    public int getTicks() {
        return this.ticks;
    }

    @Deprecated
    public boolean attenuate(boolean flag) {
        if (flag) {
            this.y = 0.0;
        } else if (this.y > 0.0) {
            this.y = this.y - Math.min(this.y, 0.08);
        }

        this.y *= 0.98F;
        this.x *= 0.91F;
        this.z *= 0.91F;
        if (flag) {
            this.x *= 0.6F;
            this.z *= 0.6F;
        }

        this.hypotSquaredXZ = null;
        this.attenuated = true;
        return Math.abs(MathUtil.highestAbs(this.x, this.y, this.z)) <= 0.001;
    }

    public boolean isAttenuated() {
        return this.attenuated;
    }

    public double getOriginalY() {
        return this.originalY;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public ClientVelocity getClient() {
        return new ClientVelocity(this.x, this.y, this.z, this.ticks, this.timestamp, this.explosion);
    }

    public Velocity(int ticks, long timestamp, double x, double y, double z, boolean explosion) {
        this.ticks = ticks;
        this.timestamp = timestamp;
        this.x = x;
        this.y = y;
        this.originalY = y;
        this.z = z;
        this.explosion = explosion;
    }

    public boolean isExplosion() {
        return this.explosion;
    }

    public double getHypotSquaredXZ() {
        if (this.hypotSquaredXZ == null) {
            this.hypotSquaredXZ = MathUtil.hypotSquared(this.x, this.z);
        }

        return this.hypotSquaredXZ;
    }
}
