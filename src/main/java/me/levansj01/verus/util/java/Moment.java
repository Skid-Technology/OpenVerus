package me.levansj01.verus.util.java;

public class Moment {
    private int n;
    private double kurtosis;
    private double std;
    private double mean;
    private double variance;
    private double skewness;

    public int getN() {
        return this.n;
    }

    public double getSkewness() {
        return this.skewness;
    }

    public double getMean() {
        return this.mean;
    }

    public double getKurtosis() {
        return this.kurtosis;
    }

    public Moment(Iterable<? extends Number> iterable) {
        for (Number number : iterable) {
            this.n++;
            this.mean = this.mean + number.doubleValue();
        }

        double value = this.n;
        this.mean /= value;
        double value2 = 0.0;
        double value3 = 0.0;

        for (Number number : iterable) {
            this.variance = this.variance + Math.pow(this.mean - number.doubleValue(), 2.0);
            value2 += Math.pow(this.mean - number.doubleValue(), 3.0);
            value3 += Math.pow(this.mean - number.doubleValue(), 4.0);
        }

        this.variance /= value;
        this.std = Math.sqrt(this.variance);
        if (value >= 2.0) {
            value2 /= this.variance * this.std;
            this.skewness = value / ((value - 1.0) * (value - 2.0)) * value2;
            if (value >= 3.0) {
                value3 /= Math.pow(this.variance, 2.0);
                double value4 = value * (value + 1.0) / ((value - 1.0) * (value - 2.0) * (value - 3.0));
                double value5 = 3.0 * Math.pow(value - 1.0, 2.0) / ((value - 2.0) * (value - 3.0));
                this.kurtosis = value4 * value3 - value5;
            }
        }
    }

    public double getStd() {
        return this.std;
    }

    public double getVariance() {
        return this.variance;
    }
}
