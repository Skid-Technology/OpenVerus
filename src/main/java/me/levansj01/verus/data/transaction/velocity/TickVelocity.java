package me.levansj01.verus.data.transaction.velocity;

public class TickVelocity extends BasicVelocity {
    protected Double hypotSquaredXZ;
    protected Integer start;
    protected Integer end;
    protected Double hypotXZ;

    public Integer getStart() {
        return this.start;
    }

    public double getHypotXZ() {
        if (this.hypotXZ == null) {
            this.hypotXZ = Math.sqrt(this.getHypotSquaredXZ());
        }

        return this.hypotXZ;
    }

    public double getHypotSquaredXZ() {
        if (this.hypotSquaredXZ == null) {
            this.hypotSquaredXZ = Math.pow(this.getRoundedX(), 2.0) + Math.pow(this.getRoundedZ(), 2.0);
        }

        return this.hypotSquaredXZ;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public Integer getEnd() {
        return this.end;
    }

    public TickVelocity(double value, double value2, double value3, boolean flag) {
        super(value, value2, value3, flag);
    }

    public void setStart(Integer start) {
        this.start = start;
    }
}
