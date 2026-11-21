package me.levansj01.verus.util.java;

import java.util.Random;
import java.util.UUID;

public class MathHelper {
    public static final float field_180189_a = sqrt_float(2.0F);
    private static final float[] SIN_TABLE_FAST = new float[4096];
    public static boolean fastMath = false;
    private static final float[] SIN_TABLE = new float[65536];
    private static final double field_181163_d = Double.longBitsToDouble(4805340802404319232L);
    private static final double[] field_181164_e = new double[257];
    private static final double[] field_181165_f = new double[257];
    private static final int[] multiplyDeBruijnBitPosition;
    private static final float degFull = 360.0F;
    private static final int SIN_BITS = 12;
    private static final float degToIndex = 11.377778F;
    private static final String __OBFID = "CL_00001496";
    public static final float PI2 = (float) (Math.PI * 2);
    private static final float radToIndex = 651.8986F;
    private static final int SIN_MASK = 4095;
    private static final int SIN_COUNT = 4096;
    private static final float radFull = (float) (Math.PI * 2);
    public static final float deg2Rad = (float) (Math.PI / 180.0);
    public static final float PId2 = (float) (Math.PI / 2);
    public static final float PI = (float) Math.PI;

    private static boolean isPowerOfTwo(int value) {
        return value != 0 && (value & value - 1) == 0;
    }

    public static double clamp_double(double value, double value2, double value3) {
        return value < value2 ? value2 : (value > value3 ? value3 : value);
    }

    public static long func_180187_c(int value, int value2, int value3) {
        long value4 = value * 3129871 ^ value3 * 116129781L ^ value2;
        return value4 * value4 * 42317861L + value4 * 11L;
    }

    public static double average(long[] values) {
        long value = 0L;

        for (long value2 : values) {
            value += value2;
        }

        return value / values.length;
    }

    public static double square(double value) {
        return value * value;
    }

    public static int bucketInt(int value, int value2) {
        return value < 0 ? -((-value - 1) / value2) - 1 : value / value2;
    }

    public static float abs(float value) {
        return value >= 0.0F ? value : -value;
    }

    public static int calculateLogBaseTwo(int value) {
        return calculateLogBaseTwoDeBruijn(value) - (isPowerOfTwo(value) ? 0 : 1);
    }

    public static double atan2(double value, double value2) {
        double value3 = value2 * value2 + value * value;
        if (Double.isNaN(value3)) {
            return Double.NaN;
        }

        boolean flag = value < 0.0;
        if (flag) {
            value = -value;
        }

        boolean flag2 = value2 < 0.0;
        if (flag2) {
            value2 = -value2;
        }

        boolean flag3 = value > value2;
        if (flag3) {
            double value4 = value2;
            value2 = value;
            value = value4;
        }

        double value4 = func_181161_i(value3);
        value2 *= value4;
        value *= value4;
        double value5 = field_181163_d + value;
        int value6 = (int)Double.doubleToRawLongBits(value5);
        double value7 = field_181164_e[value6];
        double value8 = field_181165_f[value6];
        double value9 = value5 - field_181163_d;
        double value10 = value * value8 - value2 * value9;
        double value11 = (6.0 + value10 * value10) * value10 * 0.16666666666666666;
        double value12 = value7 + value11;
        if (flag3) {
            value12 = (Math.PI / 2) - value12;
        }

        if (flag2) {
            value12 = Math.PI - value12;
        }

        if (flag) {
            value12 = -value12;
        }

        return value12;
    }

    public static double denormalizeClamp(double value, double value2, double value3) {
        return value3 < 0.0 ? value : (value3 > 1.0 ? value2 : value + (value2 - value) * value3);
    }

    public static int func_154353_e(double value) {
        return (int)(value >= 0.0 ? value : -value + 1.0);
    }

    public static double func_181161_i(double value) {
        double value2 = 0.5 * value;
        long value3 = Double.doubleToRawLongBits(value);
        value3 = 6910469410427058090L - (value3 >> 1);
        value = Double.longBitsToDouble(value3);
        return value * (1.5 - value2 * value * value);
    }

    public static int ceiling_double_int(double value) {
        int value2 = (int)value;
        return value > value2 ? value2 + 1 : value2;
    }

    public static float sin(float value) {
        return fastMath ? SIN_TABLE_FAST[(int)(value * 651.8986F) & 4095] : SIN_TABLE[(int)(value * 10430.378F) & 65535];
    }

    public static float clamp_float(float value, float value2, float value3) {
        return value < value2 ? value2 : (value > value3 ? value3 : value);
    }

    public static double getRandomDoubleInRange(Random random, double value, double value2) {
        return value >= value2 ? value : random.nextDouble() * (value2 - value) + value;
    }

    public static long floor_double_long(double value) {
        long value2 = (long)value;
        return value < value2 ? value2 - 1L : value2;
    }

    public static UUID func_180182_a(Random random) {
        long value = random.nextLong() & -61441L | 16384L;
        long value2 = random.nextLong() & 4611686018427387903L | Long.MIN_VALUE;
        return new UUID(value, value2);
    }

    public static int func_154354_b(int value, int value2) {
        if (value2 == 0) {
            return 0;
        }

        if (value == 0) {
            return value2;
        }

        if (value < 0) {
            value2 *= -1;
        }

        int value3 = value % value2;
        return value3 == 0 ? value : value + value2 - value3;
    }

    static {
        for (int i = 0; i < 65536; i++) {
            SIN_TABLE[i] = (float)Math.sin(i * Math.PI * 2.0 / 65536.0);
        }

        multiplyDeBruijnBitPosition = new int[]{
            0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9
        };

        for (int i = 0; i < 4096; i++) {
            SIN_TABLE_FAST[i] = (float)Math.sin((i + 0.5F) / 4096.0F * (float) (Math.PI * 2));
        }

        for (byte i = 0; i < 360; i += 90) {
            SIN_TABLE_FAST[(int)(i * 11.377778F) & 4095] = (float)Math.sin(i * (float) (Math.PI / 180.0));
        }
    }

    public static int roundUpToPowerOfTwo(int value) {
        int value2 = value - 1;
        value2 |= value2 >> 1;
        value2 |= value2 >> 2;
        value2 |= value2 >> 4;
        value2 |= value2 >> 8;
        value2 |= value2 >> 16;
        return value2 + 1;
    }

    public static float sqrt_float(float value) {
        return (float)Math.sqrt(value);
    }

    public static float sqrt_double(double value) {
        return (float)Math.sqrt(value);
    }

    public static double parseDoubleWithDefaultAndMax(String str, double value, double value2) {
        return Math.max(value2, parseDoubleWithDefault(str, value));
    }

    public static int floor_double(double value) {
        int value2 = (int)value;
        return value < value2 ? value2 - 1 : value2;
    }

    public static int floor_float(float value) {
        int value2 = (int)value;
        return value < value2 ? value2 - 1 : value2;
    }

    public static int clamp_int(int value, int value2, int value3) {
        return value < value2 ? value2 : (value > value3 ? value3 : value);
    }

    public static float randomFloatClamp(Random random, float value, float value2) {
        return value >= value2 ? value : random.nextFloat() * (value2 - value) + value;
    }

    public static double abs_max(double value, double value2) {
        if (value < 0.0) {
            value = -value;
        }

        if (value2 < 0.0) {
            value2 = -value2;
        }

        return value > value2 ? value : value2;
    }

    public static int func_180183_b(float value, float value2, float value3) {
        return func_180181_b(floor_float(value * 255.0F), floor_float(value2 * 255.0F), floor_float(value3 * 255.0F));
    }

    public static double wrapAngleTo180_double(double value) {
        value %= 360.0;
        if (value >= 180.0) {
            value -= 360.0;
        }

        if (value < -180.0) {
            value += 360.0;
        }

        return value;
    }

    private static int calculateLogBaseTwoDeBruijn(int value) {
        value = isPowerOfTwo(value) ? value : roundUpToPowerOfTwo(value);
        return multiplyDeBruijnBitPosition[(int)(value * 125613361L >> 27) & 31];
    }

    public static int ceiling_float_int(float value) {
        int value2 = (int)value;
        return value > value2 ? value2 + 1 : value2;
    }

    public static double parseDoubleWithDefault(String str, double value) {
        try {
            return Double.parseDouble(str);
        } catch (Throwable throwable) {
            return value;
        }
    }

    public static float cos(float value) {
        return fastMath ? SIN_TABLE_FAST[(int)((value + (float) (Math.PI / 2)) * 651.8986F) & 4095] : SIN_TABLE[(int)(value * 10430.378F + 16384.0F) & 65535];
    }

    public static int parseIntWithDefaultAndMax(String str, int value, int value2) {
        return Math.max(value2, parseIntWithDefault(str, value));
    }

    public static int func_180181_b(int value, int value2, int value3) {
        int value4 = (value << 8) + value2;
        return (value4 << 8) + value3;
    }

    public static int getRandomIntegerInRange(Random random, int value, int value2) {
        return value >= value2 ? value : random.nextInt(value2 - value + 1) + value;
    }

    public static int parseIntWithDefault(String str, int value) {
        try {
            return Integer.parseInt(str);
        } catch (Throwable throwable) {
            return value;
        }
    }

    public static int func_180184_b(int value, int value2) {
        return (value % value2 + value2) % value2;
    }

    public static int func_180188_d(int value, int value2) {
        int value3 = (value & 0xFF0000) >> 16;
        int value4 = (value2 & 0xFF0000) >> 16;
        int value5 = (value & 0xFF00) >> 8;
        int value6 = (value2 & 0xFF00) >> 8;
        int value7 = (value & 0xFF) >> 0;
        int value8 = (value2 & 0xFF) >> 0;
        int value9 = (int)(value3 * value4 / 255.0F);
        int value10 = (int)(value5 * value6 / 255.0F);
        int value11 = (int)(value7 * value8 / 255.0F);
        return value & 0xFF000000 | value9 << 16 | value10 << 8 | value11;
    }

    public static boolean func_180185_a(float value, float value2) {
        return abs(value2 - value) < 1.0E-5F;
    }

    public static int truncateDoubleToInt(double value) {
        return (int)(value + 1024.0) - 1024;
    }

    public static int abs_int(int value) {
        return value >= 0 ? value : -value;
    }

    public static float wrapAngleTo180_float(float value) {
        value %= 360.0F;
        if (value >= 180.0F) {
            value -= 360.0F;
        }

        if (value < -180.0F) {
            value += 360.0F;
        }

        return value;
    }
}
