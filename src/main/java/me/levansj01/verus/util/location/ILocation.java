package me.levansj01.verus.util.location;

import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.MutableBlockLocation;

public interface ILocation extends IVector3d {
    default MutableBlockLocation toBlock() {
        return new MutableBlockLocation((int)Math.floor(this.getX()), (int)Math.floor(this.getY()), (int)Math.floor(this.getZ()));
    }

    default ILocation[] interpolate(ILocation location, int value) {
        ILocation[] locations = new ILocation[value + 1];
        int value2 = 0;
        locations[value2++] = this;
        double value3 = (location.getX() - this.getX()) / value;
        double value4 = (location.getY() - this.getY()) / value;
        double value5 = (location.getZ() - this.getZ()) / value;
        float value6 = (location.getYaw() - this.getYaw()) / value;
        float value7 = (location.getPitch() - this.getPitch()) / value;

        while (value2 < value) {
            locations[value2] = new BasicPacketLocation(
                this.getX() + value3 * value2, this.getY() + value4 * value2, this.getZ() + value5 * value2, this.getYaw() + value6 * value2, this.getPitch() + value7 * value2
            );
            value2++;
        }

        locations[value] = location;
        return locations;
    }

    default BasicPacketLocation pos(ILocation location) {
        return new BasicPacketLocation(location.getX(), location.getY(), location.getZ(), this.getYaw(), this.getPitch());
    }

    default boolean sameYawPitch(ILocation location) {
        return this.getYaw() == location.getYaw() && this.getPitch() == location.getPitch();
    }

    default Vector3d getDirection() {
        double value = Math.cos(Math.toRadians(this.getPitch()));
        return new Vector3d(
            -value * Math.sin(Math.toRadians(this.getYaw())), -Math.sin(Math.toRadians(this.getPitch())), value * Math.cos(Math.toRadians(this.getYaw()))
        );
    }

    default Cuboid to(ILocation location, int value) {
        return this.distanceSquared(location) > value
            ? new Cuboid(this)
            : new Cuboid(
                Math.min(this.getX(), location.getX()),
                Math.max(this.getX(), location.getX()),
                Math.min(this.getY(), location.getY()),
                Math.max(this.getY(), location.getY()),
                Math.min(this.getZ(), location.getZ()),
                Math.max(this.getZ(), location.getZ())
            );
    }

    float getYaw();

    default BasicPacketLocation withY(double value) {
        return new BasicPacketLocation(this.getX(), value, this.getZ(), this.getYaw(), this.getPitch());
    }

    default boolean sameXYZ(ILocation location) {
        return this.sameXZ(location) && this.getY() == location.getY();
    }

    default Vector3d toEyeVector(double value) {
        return new Vector3d(this.getX(), this.getY() + value, this.getZ());
    }

    default BasicPacketLocation look(ILocation location) {
        return new BasicPacketLocation(this.getX(), this.getY(), this.getZ(), location.getYaw(), location.getPitch());
    }

    default boolean sameXZ(ILocation location) {
        return this.getX() == location.getX() && this.getZ() == location.getZ();
    }

    @Deprecated
    default Vector3d toEyeVector(boolean flag) {
        return this.toEyeVector(flag ? 1.54F : 1.62F);
    }

    default double distance(ILocation location) {
        return Math.sqrt(this.distanceSquared(location));
    }

    float getPitch();

    default double distanceSquared(ILocation location) {
        return Math.pow(this.getX() - location.getX(), 2.0) + Math.pow(this.getY() - location.getY(), 2.0) + Math.pow(this.getZ() - location.getZ(), 2.0);
    }

    default boolean matches(ILocation location) {
        return this.sameXYZ(location) && this.sameYawPitch(location);
    }

    default Vector3d toVector() {
        return new Vector3d(this.getX(), this.getY(), this.getZ());
    }
}
