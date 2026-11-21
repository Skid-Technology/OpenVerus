package me.levansj01.verus.data.transaction.vehicle;

public class TrackedVehicle {
    private double x;
    private double z;
    private double y;
    private final int entityId;

    public void setX(double x) {
        this.x = x;
    }

    public double getX() {
        return this.x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getEntityId() {
        return this.entityId;
    }

    public TrackedVehicle(int entityId) {
        this.entityId = entityId;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getZ() {
        return this.z;
    }

    public double getY() {
        return this.y;
    }
}
