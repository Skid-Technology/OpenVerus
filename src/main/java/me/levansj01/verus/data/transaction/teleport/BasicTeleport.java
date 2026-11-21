package me.levansj01.verus.data.transaction.teleport;

import java.util.Set;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayOutPosition;
import me.levansj01.verus.util.location.ILocationGround;

public class BasicTeleport implements ILocationGround {
    private final float yaw;
    private final double y;
    private final float pitch;
    private final double z;
    private final Set<VPacketPlayOutPosition.TeleportFlag> flags;
    private final double x;

    @Override
    public float getYaw() {
        return this.yaw;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof BasicTeleport)) {
            return false;
        }

        BasicTeleport basicTeleport = (BasicTeleport)obj;
        if (!basicTeleport.canEqual(this)) {
            return false;
        }

        if (Double.compare(this.getX(), basicTeleport.getX()) != 0) {
            return false;
        }

        if (Double.compare(this.getY(), basicTeleport.getY()) != 0) {
            return false;
        }

        if (Double.compare(this.getZ(), basicTeleport.getZ()) != 0) {
            return false;
        }

        if (Float.compare(this.getYaw(), basicTeleport.getYaw()) != 0) {
            return false;
        }

        if (Float.compare(this.getPitch(), basicTeleport.getPitch()) != 0) {
            return false;
        }

        Set flags = this.getFlags();
        Set flags2 = basicTeleport.getFlags();
        return flags == null ? flags2 == null : flags.equals(flags2);
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public float getPitch() {
        return this.pitch;
    }

    protected boolean canEqual(Object obj) {
        return obj instanceof BasicTeleport;
    }

    public boolean matches(VPacketPlayInFlying<?> packet) {
        return !packet.isGround()
            && packet.isPos()
            && packet.isLook()
            && packet.getX() == this.x
            && packet.getY() == this.y
            && packet.getZ() == this.z
            && packet.getYaw() == this.yaw
            && packet.getPitch() == this.pitch;
    }

    public BasicTeleport(double x, double y, double z, float yaw, float pitch, Set<VPacketPlayOutPosition.TeleportFlag> flags) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.flags = flags;
    }

    @Override
    public boolean isGround() {
        return false;
    }

    @Override
    public String toString() {
        return "BasicTeleport(x="
            + this.getX()
            + ", y="
            + this.getY()
            + ", z="
            + this.getZ()
            + ", yaw="
            + this.getYaw()
            + ", pitch="
            + this.getPitch()
            + ", flags="
            + this.getFlags()
            + ")";
    }

    @Override
    public double getZ() {
        return this.z;
    }

    @Override
    public int hashCode() {
        byte b = 59;
        int value = 1;
        long value2 = Double.doubleToLongBits(this.getX());
        value = value * 59 + (int)(value2 >>> 32 ^ value2);
        long value3 = Double.doubleToLongBits(this.getY());
        value = value * 59 + (int)(value3 >>> 32 ^ value3);
        long value4 = Double.doubleToLongBits(this.getZ());
        value = value * 59 + (int)(value4 >>> 32 ^ value4);
        value = value * 59 + Float.floatToIntBits(this.getYaw());
        value = value * 59 + Float.floatToIntBits(this.getPitch());
        Set flags = this.getFlags();
        return value * 59 + (flags == null ? 43 : flags.hashCode());
    }

    @Override
    public double getY() {
        return this.y;
    }

    public Set<VPacketPlayOutPosition.TeleportFlag> getFlags() {
        return this.flags;
    }
}
