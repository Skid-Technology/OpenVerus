package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.util.location.IPacketLocation;
import me.levansj01.verus.util.location.PacketLocation;

public abstract class VPacketPlayInFlying<P> extends VPacket<P> implements IPacketLocation {
    private static final int count = count();
    protected float pitch;
    protected boolean look;
    protected boolean teleport;
    protected boolean pos;
    protected double x;
    protected boolean ground;
    protected double z;
    protected float yaw;
    protected double y;

    @Override
    public boolean isPos() {
        return this.pos;
    }

    @Override
    public boolean isLook() {
        return this.look;
    }

    @Override
    public double getZ() {
        return this.z;
    }

    @Override
    public float getPitch() {
        return this.pitch;
    }

    public PacketLocation from(PacketLocation location) {
        double value;
        double value2;
        double value3;
        if (this.pos) {
            value = this.x;
            value2 = this.y;
            value3 = this.z;
        } else {
            value = location.getX();
            value2 = location.getY();
            value3 = location.getZ();
        }

        float value4;
        float value5;
        if (this.look) {
            value4 = this.yaw;
            value5 = this.pitch;
        } else {
            value4 = location.getYaw();
            value5 = location.getPitch();
        }

        return new PacketLocation(value, value2, value3, value4, value5, this.ground, this.pos, this.look, this.teleport);
    }

    @Override
    public void handle(PacketHandler listener) {
        listener.handle(this);
    }

    @Override
    public float getYaw() {
        return this.yaw;
    }

    @Override
    public boolean isGround() {
        return this.ground;
    }

    @Override
    public double getY() {
        return this.y;
    }

    public boolean isTeleport() {
        return this.teleport;
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public int ordinal() {
        return count;
    }

    public void setTeleport(boolean teleport) {
        this.teleport = teleport;
    }
}
