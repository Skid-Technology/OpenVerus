package me.levansj01.verus.type.custom.checks.aim;

import java.util.LinkedList;
import me.levansj01.verus.alert.manager.AlertManager;
import me.levansj01.verus.check.AimCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.util.location.PlayerLocation;

@CheckInfo(type = CheckType.AIM_ASSIST, subType = "S", friendlyName = "Aim Assist", version = CheckVersion.EXPERIMENTAL, maxViolations = 0)
public class AimS extends AimCheck {
    private final LinkedList<Double> yawDeltas = new LinkedList<>();
    private static final int ROT_WINDOW_MS = 200;
    private long lastRotationTime;
    private double buffer;
    private final LinkedList<Double> pitchDeltas = new LinkedList<>();

    private double computeGridness(LinkedList<Double> doubles, LinkedList<Double> doubles2) {
        double value = 0.0;
        double value2 = 0.0;

        for (double value3 : doubles) {
            value += value3;
        }

        for (double value3 : doubles2) {
            value2 += value3;
        }

        double value3 = value / doubles.size();
        double value4 = value2 / doubles2.size();
        double value5 = 0.0;

        for (int i = 0; i < doubles.size(); i++) {
            double value6 = (Double)doubles.get(i);
            double value7 = (Double)doubles2.get(i);
            value5 += Math.abs(value6 % value3 - value7 % value4);
        }

        return value5 / doubles.size();
    }

    @Override
    public void handle(PlayerLocation location, PlayerLocation location2, long value) {
        double deltaYaw = Math.abs(location2.getYaw() - location.getYaw());
        double deltaPitch = Math.abs(location2.getPitch() - location.getPitch());
        double abs = Math.abs(deltaYaw);
        double abs2 = Math.abs(deltaPitch);
        if (this.playerData.getLastAttackTicks() <= 10) {
            long now = System.currentTimeMillis();
            if (this.lastRotationTime != -1L && now - this.lastRotationTime > 200L) {
                this.yawDeltas.clear();
                this.pitchDeltas.clear();
            }

            this.lastRotationTime = now;
            this.yawDeltas.add(abs);
            this.pitchDeltas.add(abs2);
            if (this.yawDeltas.size() == 40 && this.pitchDeltas.size() == 40) {
                double value2 = this.computeGridness(this.yawDeltas, this.pitchDeltas);
                AimS.LinearRegression linearRegression = new AimS.LinearRegression(this.yawDeltas.toArray(new Double[0]), this.pitchDeltas.toArray(new Double[0]));
                double value3 = linearRegression.R2();
                double value4 = linearRegression.interceptStdErr();
                AlertManager.getInstance()
                    .handleDebug(this.playerData, this, this.getViolations(), () -> String.format("Grid %.1f R2 %.1f ERR %.1f", value2, value3, value4));
                if (value2 <= 3.0 && value3 <= 0.2 && value4 <= 0.01) {
                    this.buffer++;
                    if (this.buffer > 2.0) {
                        this.handleViolation(String.format("Grid %.1f R2 %.1f ERR %.1f", value2, value3, value4));
                    }
                } else {
                    this.buffer = Math.max(0.0, this.buffer - 0.15);
                }

                this.yawDeltas.clear();
                this.pitchDeltas.clear();
            }
        }
    }

    public AimS() {
        this.lastRotationTime = -1L;
    }

    public static final class LinearRegression {
        private final double slope;
        private final double r2;
        private final double svar1;
        private final double svar0;
        private final double intercept;

        public double R2() {
            return this.r2;
        }

        public double intercept() {
            return this.intercept;
        }

        public double interceptStdErr() {
            return Math.sqrt(this.svar0);
        }

        public double slopeStdErr() {
            return Math.sqrt(this.svar1);
        }

        public LinearRegression(Double[] doubles, Double[] doubles2) {
            if (doubles.length != doubles2.length) {
                throw new IllegalArgumentException("array lengths are not equal");
            }

            int length = doubles.length;
            double value = 0.0;
            double value2 = 0.0;
            double value3 = 0.0;

            for (int i = 0; i < length; i++) {
                value += doubles[i];
                value3 += doubles[i] * doubles[i];
                value2 += doubles2[i];
            }

            double value4 = value / length;
            double value5 = value2 / length;
            double value6 = 0.0;
            double value7 = 0.0;
            double value8 = 0.0;

            for (int i = 0; i < length; i++) {
                value6 += (doubles[i] - value4) * (doubles[i] - value4);
                value7 += (doubles2[i] - value5) * (doubles2[i] - value5);
                value8 += (doubles[i] - value4) * (doubles2[i] - value5);
            }

            this.slope = value8 / value6;
            this.intercept = value5 - this.slope * value4;
            double value9 = 0.0;
            double value10 = 0.0;

            for (int i = 0; i < length; i++) {
                double value11 = this.slope * doubles[i] + this.intercept;
                value9 += (value11 - doubles2[i]) * (value11 - doubles2[i]);
                value10 += (value11 - value5) * (value11 - value5);
            }

            int value11 = length - 2;
            this.r2 = value10 / value7;
            double value12 = value9 / value11;
            this.svar1 = value12 / value6;
            this.svar0 = value12 / length + value4 * value4 * this.svar1;
        }

        public double predict(double value) {
            return this.slope * value + this.intercept;
        }

        public double slope() {
            return this.slope;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append(String.format("%.2f n + %.2f", this.slope(), this.intercept()));
            builder.append("  (R^2 = " + String.format("%.3f", this.R2()) + ")");
            return builder.toString();
        }
    }
}
