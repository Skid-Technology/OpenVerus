package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.data.transaction.tracker.BasicLocation;
import me.levansj01.verus.data.transaction.tracker.IServerLocation;
import me.levansj01.verus.data.transaction.tracker.PacketLocation;

public abstract class VPacketPlayOutEntityTeleport<P> extends VPacket<P> {
    private static final int count = count();
    protected int id;
    protected byte yaw;
    protected double z;
    protected double x;
    protected byte pitch;
    protected boolean ground;
    protected double y;

    @Override
    public int ordinal() {
        return count;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public boolean isGround() {
        return this.ground;
    }

    public byte getPitch() {
        return this.pitch;
    }

    public IServerLocation location() {
        return new BasicLocation(this.x, this.y, this.z);
    }

    public int getId() {
        return this.id;
    }

    @Override
    public void handle(PacketHandler listener) {
        listener.handle(this);
    }

    public PacketLocation toLocation(long value) {
        return new PacketLocation(this.x, this.y + 0.015625, this.z, value, Long.MAX_VALUE);
    }

    public byte getYaw() {
        return this.yaw;
    }
}
