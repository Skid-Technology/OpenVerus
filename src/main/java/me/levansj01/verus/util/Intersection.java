package me.levansj01.verus.util;

import me.levansj01.verus.compat.api.wrapper.Direction;
import me.levansj01.verus.util.location.Vector3d;

public class Intersection {
    private Double distance;
    private final Vector3d intercept;
    private final Direction direction;
    private final Vector3d displacement;

    @Override
    public String toString() {
        return "Intersection(intercept=" + this.getIntercept() + ", displacement=" + this.getDisplacement() + ")";
    }

    public double getDistance() {
        if (this.distance == null) {
            this.distance = this.displacement.length();
        }

        return this.distance;
    }

    protected boolean canEqual(Object obj) {
        return obj instanceof Intersection;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public Vector3d getIntercept() {
        return this.intercept;
    }

    @Override
    public int hashCode() {
        byte b = 59;
        int value = 1;
        long value2 = Double.doubleToLongBits(this.getDistance());
        value = value * 59 + (int)(value2 >>> 32 ^ value2);
        Vector3d intercept = this.getIntercept();
        value = value * 59 + (intercept == null ? 43 : intercept.hashCode());
        Vector3d displacement = this.getDisplacement();
        value = value * 59 + (displacement == null ? 43 : displacement.hashCode());
        Direction direction = this.getDirection();
        return value * 59 + (direction == null ? 43 : direction.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Intersection)) {
            return false;
        }

        Intersection intersection = (Intersection)obj;
        if (!intersection.canEqual(this)) {
            return false;
        }

        if (Double.compare(this.getDistance(), intersection.getDistance()) != 0) {
            return false;
        }

        Vector3d intercept = this.getIntercept();
        Vector3d intercept2 = intersection.getIntercept();
        if (intercept == null ? intercept2 == null : intercept.equals(intercept2)) {
            Vector3d displacement = this.getDisplacement();
            Vector3d displacement2 = intersection.getDisplacement();
            if (displacement == null ? displacement2 == null : displacement.equals(displacement2)) {
                Direction direction = this.getDirection();
                Direction direction2 = intersection.getDirection();
                return direction == null ? direction2 == null : direction.equals(direction2);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public Vector3d getDisplacement() {
        return this.displacement;
    }

    public Intersection(Vector3d intercept, Vector3d displacement, Direction direction) {
        this.intercept = intercept;
        this.displacement = displacement;
        this.direction = direction;
    }
}
