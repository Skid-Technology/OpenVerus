package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;

public abstract class VPacketPlayOutAbilities<P> extends VPacket<P> {
    private static final int count = count();
    protected float walkSpeed;
    protected boolean isInvulnerable;
    protected float flySpeed;
    protected boolean isFlying;
    protected boolean canFly;
    protected boolean canInstantlyBuild;

    public boolean isCanFly() {
        return this.canFly;
    }

    @Override
    public int ordinal() {
        return count;
    }

    public float getFlySpeed() {
        return this.flySpeed;
    }

    public boolean isCanInstantlyBuild() {
        return this.canInstantlyBuild;
    }

    public float getWalkSpeed() {
        return this.walkSpeed;
    }

    public boolean isFlying() {
        return this.isFlying;
    }

    @Override
    public void handle(PacketHandler listener) {
        listener.handle(this);
    }

    public boolean isInvulnerable() {
        return this.isInvulnerable;
    }
}
