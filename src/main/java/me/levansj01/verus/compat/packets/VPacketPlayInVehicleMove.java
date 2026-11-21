package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;

public abstract class VPacketPlayInVehicleMove<P> extends VPacket<P> {
    private static final int count = count();
    protected double z;
    protected float yaw;
    protected float pitch;
    protected double y;
    protected double x;

    public double getZ() {
        return this.z;
    }

    @Override
    public int ordinal() {
        return count;
    }

    @Override
    public void handle(PacketHandler listener) {
        listener.handle(this);
    }

    public double getY() {
        return this.y;
    }

    public double getX() {
        return this.x;
    }

    public float getPitch() {
        return this.pitch;
    }

    public float getYaw() {
        return this.yaw;
    }
}
