package me.levansj01.verus.data.transaction.tracker;

import me.levansj01.verus.compat.packets.VPacketPlayOutEntity;

public class ServerLocation implements IServerLocation {
    private final int iz;
    private final double x;
    private final double z;
    private final double y;
    private final int ix;
    private final int iy;

    public int getIy() {
        return this.iy;
    }

    public int getIz() {
        return this.iz;
    }

    @Override
    public double getY() {
        return this.y;
    }

    public ServerLocation(int ix, int iy, int iz) {
        this.ix = ix;
        this.iy = iy;
        this.iz = iz;
        this.x = ix / VPacketPlayOutEntity.DIVIDER;
        this.y = iy / VPacketPlayOutEntity.DIVIDER;
        this.z = iz / VPacketPlayOutEntity.DIVIDER;
    }

    public ServerLocation add(int value, int value2, int value3) {
        return new ServerLocation(this.ix + value, this.iy + value2, this.iz + value3);
    }

    public int getIx() {
        return this.ix;
    }

    @Override
    public IServerLocation add(IServerLocation serverLocation) {
        if (serverLocation instanceof ServerLocation) {
            ServerLocation serverLocation2 = (ServerLocation)serverLocation;
            return this.add(serverLocation2.getIx(), serverLocation2.getIy(), serverLocation2.getIz());
        } else {
            return serverLocation.add(this);
        }
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getZ() {
        return this.z;
    }
}
