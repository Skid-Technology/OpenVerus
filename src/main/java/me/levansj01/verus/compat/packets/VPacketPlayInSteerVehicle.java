package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;

public abstract class VPacketPlayInSteerVehicle<P> extends VPacket<P> {
    private static final int count = count();
    protected double forward;
    protected double strafe;

    @Override
    public int ordinal() {
        return count;
    }

    public double getForward() {
        return this.forward;
    }

    @Override
    public void handle(PacketHandler listener) {
        listener.handle(this);
    }

    public double getStrafe() {
        return this.strafe;
    }
}
