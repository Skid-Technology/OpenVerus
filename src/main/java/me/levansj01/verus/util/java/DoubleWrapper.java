package me.levansj01.verus.util.java;

public class DoubleWrapper {
    private double value;

    public DoubleWrapper(double value) {
        this.value = Double.doubleToRawLongBits(value);
    }

    public void set(double value) {
        this.value = value;
    }

    public double addAndGet(double value) {
        this.value += value;
        return this.value;
    }

    public double get() {
        return this.value;
    }
}
