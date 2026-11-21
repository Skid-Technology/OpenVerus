package me.levansj01.verus.util.location;

public class PacketLocation extends BasicPacketLocation implements IPacketLocation {
    private final boolean look;
    private final boolean ground;
    private final boolean teleport;
    private final boolean pos;

    public PacketLocation up() {
        return this.add(0.0, 1.0, 0.0);
    }

    @Override
    public boolean isPos() {
        return this.pos;
    }

    @Override
    public boolean isGround() {
        return this.ground;
    }

    public PacketLocation(double value, double value2, double value3, float value4, float value5, boolean ground, boolean pos, boolean look, boolean teleport) {
        super(value, value2, value3, value4, value5);
        this.ground = ground;
        this.pos = pos;
        this.look = look;
        this.teleport = teleport;
    }

    public PacketLocation add(double value, double value2, double value3) {
        return new PacketLocation(this.x + value, this.y + value2, this.z + value3, this.yaw, this.pitch, this.ground, true, this.look, this.teleport);
    }

    @Override
    public boolean isLook() {
        return this.look;
    }

    public boolean isTeleport() {
        return this.teleport;
    }
}
