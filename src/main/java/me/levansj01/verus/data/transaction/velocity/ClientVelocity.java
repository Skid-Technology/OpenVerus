package me.levansj01.verus.data.transaction.velocity;

public class ClientVelocity extends BasicVelocity {
    private final long timestamp;
    private final int ticks;

    @Override
    public String toString() {
        return "ClientVelocity(ticks=" + this.getTicks() + ", timestamp=" + this.getTimestamp() + ")";
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public ClientVelocity(double value, double value2, double value3, int ticks, long timestamp, boolean flag) {
        super(value, value2, value3, flag);
        this.ticks = ticks;
        this.timestamp = timestamp;
    }

    public int getTicks() {
        return this.ticks;
    }
}
