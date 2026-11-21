package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.data.transaction.tracker.IServerLocation;
import me.levansj01.verus.data.transaction.tracker.PacketLocation;
import me.levansj01.verus.data.transaction.tracker.ServerLocation;

public abstract class VPacketPlayOutEntity<P> extends VPacket<P> {
    public static final double DIVIDER = NMSManager.getInstance().getServerVersion().after(ServerVersion.v1_8_R3) ? 4096.0 : 32.0;
    private static final int count = count();
    protected byte pitch;
    protected boolean pos;
    protected boolean look;
    protected int y;
    protected int id;
    protected boolean ground;
    protected int z;
    protected int x;
    protected byte yaw;

    public PacketLocation move(PacketLocation location) {
        return location.add(this.x / DIVIDER, this.y / DIVIDER, this.z / DIVIDER);
    }

    public int getId() {
        return this.id;
    }

    public int getY() {
        return this.y;
    }

    public boolean isGround() {
        return this.ground;
    }

    public byte getPitch() {
        return this.pitch;
    }

    @Override
    public int ordinal() {
        return count;
    }

    public int getX() {
        return this.x;
    }

    @Override
    public String toString() {
        return "VPacketPlayOutEntity(id="
            + this.getId()
            + ", x="
            + this.getX()
            + ", y="
            + this.getY()
            + ", z="
            + this.getZ()
            + ", pitch="
            + this.getPitch()
            + ", yaw="
            + this.getYaw()
            + ", ground="
            + this.isGround()
            + ", look="
            + this.isLook()
            + ", pos="
            + this.isPos()
            + ")";
    }

    public byte getYaw() {
        return this.yaw;
    }

    public boolean isLook() {
        return this.look;
    }

    public IServerLocation move() {
        return new ServerLocation(this.x, this.y, this.z);
    }

    public boolean isPos() {
        return this.pos;
    }

    public boolean moves() {
        return this.pos && (this.x != 0 || this.y != 0 || this.z != 0);
    }

    @Override
    public void handle(PacketHandler listener) {
        listener.handle(this);
    }

    public int getZ() {
        return this.z;
    }
}
