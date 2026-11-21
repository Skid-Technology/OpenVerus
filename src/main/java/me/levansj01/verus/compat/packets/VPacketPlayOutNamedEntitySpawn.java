package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.data.transaction.tracker.BasicLocation;
import me.levansj01.verus.data.transaction.tracker.IServerLocation;
import me.levansj01.verus.data.transaction.tracker.PacketLocation;

public abstract class VPacketPlayOutNamedEntitySpawn<P> extends VPacket<P> {
    private static final int count = count();
    protected byte yaw;
    protected int id;
    protected double y;
    protected byte pitch;
    protected double x;
    protected double z;

    public IServerLocation location() {
        return new BasicLocation(this.x, this.y, this.z);
    }

    public byte getYaw() {
        return this.yaw;
    }

    public byte getPitch() {
        return this.pitch;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getZ() {
        return this.z;
    }

    public double getY() {
        return this.y;
    }

    @Override
    public int ordinal() {
        return count;
    }

    @Override
    public void handle(PacketHandler listener) {
        listener.handle(this);
    }

    public void setYaw(byte yaw) {
        this.yaw = yaw;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public int getId() {
        return this.id;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public void setPitch(byte pitch) {
        this.pitch = pitch;
    }

    public PacketLocation toLocation(long value) {
        return new PacketLocation(this.x, this.y, this.z, value, Long.MAX_VALUE);
    }

    public void setId(int id) {
        this.id = id;
    }
}
