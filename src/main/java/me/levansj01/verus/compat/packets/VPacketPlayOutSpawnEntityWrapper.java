package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.data.transaction.tracker.BasicLocation;
import me.levansj01.verus.data.transaction.tracker.IServerLocation;
import me.levansj01.verus.data.transaction.tracker.PacketLocation;

public abstract class VPacketPlayOutSpawnEntityWrapper<P> extends VPacket<P> {
    private static final int count = count();
    protected double y;
    protected byte yaw;
    protected double z;
    protected double x;
    protected int id;
    protected byte pitch;

    public int getId() {
        return this.id;
    }

    public void setPitch(byte pitch) {
        this.pitch = pitch;
    }

    public PacketLocation toLocation(long value) {
        return new PacketLocation(this.x, this.y, this.z, value, Long.MAX_VALUE);
    }

    public VPacketPlayOutSpawnEntity<P> wrapSpawnEntity() {
        return new VPacketPlayOutSpawnEntityWrapper.SpawnEntityWrapper();
    }

    public abstract boolean isPlayer();

    public void setYaw(byte yaw) {
        this.yaw = yaw;
    }

    public double getZ() {
        return this.z;
    }

    @Override
    public int ordinal() {
        return count;
    }

    public void setId(int id) {
        this.id = id;
    }

    public VPacketPlayOutNamedEntitySpawn<P> wrapSpawnNamedEntity() {
        return new VPacketPlayOutSpawnEntityWrapper.SpawnNamedEntityWrapper();
    }

    public IServerLocation location() {
        return new BasicLocation(this.x, this.y, this.z);
    }

    @Override
    public void handle(PacketHandler listener) {
        if (this.isPlayer()) {
            listener.handle(this.wrapSpawnNamedEntity());
        } else {
            listener.handle(this.wrapSpawnEntity());
        }
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return this.y;
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

    public void setZ(double z) {
        this.z = z;
    }

    public double getX() {
        return this.x;
    }

    public class SpawnEntityWrapper extends VPacketPlayOutSpawnEntity<P> {
        @Override
        public void accept(P packet) {
        }

        @Override
        public int getId() {
            return VPacketPlayOutSpawnEntityWrapper.this.getId();
        }
    }

    public class SpawnNamedEntityWrapper extends VPacketPlayOutNamedEntitySpawn<P> {
        @Override
        public void setX(double x) {
            VPacketPlayOutSpawnEntityWrapper.this.setX(x);
        }

        @Override
        public double getY() {
            return VPacketPlayOutSpawnEntityWrapper.this.getY();
        }

        @Override
        public void setPitch(byte pitch) {
            VPacketPlayOutSpawnEntityWrapper.this.setPitch(pitch);
        }

        @Override
        public void accept(P packet) {
        }

        @Override
        public double getZ() {
            return VPacketPlayOutSpawnEntityWrapper.this.getZ();
        }

        @Override
        public int getId() {
            return VPacketPlayOutSpawnEntityWrapper.this.getId();
        }

        @Override
        public byte getYaw() {
            return VPacketPlayOutSpawnEntityWrapper.this.getYaw();
        }

        @Override
        public void setId(int id) {
            VPacketPlayOutSpawnEntityWrapper.this.setId(id);
        }

        @Override
        public void setZ(double z) {
            VPacketPlayOutSpawnEntityWrapper.this.setZ(z);
        }

        @Override
        public IServerLocation location() {
            return VPacketPlayOutSpawnEntityWrapper.this.location();
        }

        @Override
        public byte getPitch() {
            return VPacketPlayOutSpawnEntityWrapper.this.getPitch();
        }

        @Override
        public void setYaw(byte yaw) {
            VPacketPlayOutSpawnEntityWrapper.this.setYaw(yaw);
        }

        @Override
        public PacketLocation toLocation(long value) {
            return VPacketPlayOutSpawnEntityWrapper.this.toLocation(value);
        }

        @Override
        public double getX() {
            return VPacketPlayOutSpawnEntityWrapper.this.getX();
        }

        @Override
        public void setY(double y) {
            VPacketPlayOutSpawnEntityWrapper.this.setY(y);
        }
    }
}
