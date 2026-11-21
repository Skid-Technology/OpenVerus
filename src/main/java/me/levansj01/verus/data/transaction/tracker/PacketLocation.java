package me.levansj01.verus.data.transaction.tracker;

public class PacketLocation extends BasicLocation {
    private long timestamp;
    private long nextOffset;

    public PacketLocation(double value, double value2, double value3, long timestamp, long nextOffset) {
        super(value, value2, value3);
        this.timestamp = timestamp;
        this.nextOffset = nextOffset;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setNextOffset(long nextOffset) {
        this.nextOffset = nextOffset;
    }

    public long getNextOffset() {
        return this.nextOffset;
    }

    public PacketLocation clone() {
        return new PacketLocation(this.x, this.y, this.z, this.timestamp, this.nextOffset);
    }

    public long getTime() {
        return this.nextOffset - this.timestamp;
    }

    @Override
    protected boolean canEqual(Object obj) {
        return obj instanceof PacketLocation;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof PacketLocation)) {
            return false;
        } else {
            PacketLocation location = (PacketLocation)obj;
            if (!location.canEqual(this)) {
                return false;
            } else if (!super.equals(obj)) {
                return false;
            } else {
                return this.getTimestamp() != location.getTimestamp() ? false : this.getNextOffset() == location.getNextOffset();
            }
        }
    }

    public PacketLocation interpolateFrom(PacketLocation location, int value) {
        double value2 = value / 3.0;
        return new PacketLocation(
            location.x + (this.x - location.x) * value2, location.y + (this.y - location.y) * value2, location.z + (this.z - location.z) * value2, this.timestamp, this.nextOffset
        );
    }

    public PacketLocation add(double value, double value2, double value3) {
        return new PacketLocation(this.x + value, this.y + value2, this.z + value3, this.timestamp, this.nextOffset);
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int hashCode() {
        byte b = 59;
        int value = super.hashCode();
        long timestamp = this.getTimestamp();
        value = value * 59 + (int)(timestamp >>> 32 ^ timestamp);
        long nextOffset = this.getNextOffset();
        return value * 59 + (int)(nextOffset >>> 32 ^ nextOffset);
    }
}
