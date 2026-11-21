package me.levansj01.verus.compat.packets;

import java.util.Set;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.data.transaction.teleport.BasicTeleport;
import me.levansj01.verus.data.transaction.teleport.Teleport;
import me.levansj01.verus.util.location.ILocation;
import me.levansj01.verus.util.location.PlayerLocation;

public abstract class VPacketPlayOutPosition<P> extends VPacket<P> implements ILocation {
    private static final int count = count();
    protected float pitch;
    protected Set<VPacketPlayOutPosition.TeleportFlag> flags;
    protected double z;
    protected double x;
    protected float yaw;
    protected double y;

    @Override
    public double getZ() {
        return this.z;
    }

    @Override
    public double getX() {
        return this.x;
    }

    public Teleport toTeleport(int value, long value2) {
        return new Teleport(this.x, this.y, this.z, this.yaw, this.pitch, this.flags, value, value2);
    }

    public BasicTeleport toTeleport() {
        return new BasicTeleport(this.x, this.y, this.z, this.yaw, this.pitch, this.flags);
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public float getYaw() {
        return this.yaw;
    }

    @Override
    public int ordinal() {
        return count;
    }

    @Override
    public void handle(PacketHandler listener) {
        listener.handle(this);
    }

    public Set<VPacketPlayOutPosition.TeleportFlag> getFlags() {
        return this.flags;
    }

    public PlayerLocation toLocation(PlayerData playerData) {
        return new PlayerLocation(System.currentTimeMillis(), playerData.getTotalTicks(), this.x, this.y, this.z, this.yaw, this.pitch, null, false);
    }

    @Override
    public float getPitch() {
        return this.pitch;
    }

    public enum TeleportFlag {
        X,
        Y,
        Z,
        Y_ROT,
        X_ROT;

        public int bit() {
            return 1 << this.ordinal();
        }

        public boolean contains(int value) {
            return (value & this.bit()) != 0;
        }
    }
}
