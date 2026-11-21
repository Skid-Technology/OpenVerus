package me.levansj01.verus.util.java;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Queue;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import me.levansj01.verus.compat.api.wrapper.BlockPosition;
import me.levansj01.verus.data.transaction.tracker.PacketLocation;
import me.levansj01.verus.util.location.IVector3d;
import me.levansj01.verus.util.location.PlayerLocation;
import me.levansj01.verus.util.location.Vector3d;

public class MathUtil {
    public static double relEntityRoundPos(double value) {
        return net.minecraft.server.v1_8_R3.MathHelper.floor(value * 32.0) / 32.0;
    }

    public static int toInt(float value) {
        return (int)(new BigDecimal(value).setScale(5, RoundingMode.UP).floatValue() * 10000.0);
    }

    public static int lcd(long value, int value2, int value3) {
        return value3 <= value ? value2 : gcd(value, value3, value2 % value3);
    }

    public static double hypotSquared(double... values) {
        double value = 0.0;

        for (double value2 : values) {
            value += Math.pow(value2, 2.0);
        }

        return value;
    }

    public static double getDistanceBetweenAngles360(double value, double value2) {
        double abs = Math.abs(value % 360.0 - value2 % 360.0);
        abs = Math.min(360.0 - abs, abs);
        return Math.abs(abs);
    }

    public static double highest(Iterable<? extends Number> iterable) {
        Double doubleValue = null;

        for (Number number : iterable) {
            if (doubleValue == null || number.doubleValue() > doubleValue) {
                doubleValue = number.doubleValue();
            }
        }

        return JavaV.firstNonNull(doubleValue, 0.0);
    }

    public static double highestAbs(Number... numbers) {
        return highestAbs(Arrays.asList(numbers));
    }

    public static double positiveSmaller(Number number, Number number2) {
        return Math.abs(number.doubleValue()) < Math.abs(number2.doubleValue()) ? number.doubleValue() : number2.doubleValue();
    }

    public static double fastSqrt(double value) {
        return Double.longBitsToDouble((Double.doubleToLongBits(value) - 4503599627370496L >> 1) + 2305843009213693952L);
    }

    public static float relEntityRoundLook(float value) {
        return net.minecraft.server.v1_8_R3.MathHelper.d(value * 256.0F / 360.0F);
    }

    public static double highest(Number... numbers) {
        return highest(Arrays.asList(numbers));
    }

    public static double zeros(Queue<Float> floats) {
        double value = 0.0;

        for (float value2 : floats) {
            if (value2 == 0.0F) {
                value++;
            }
        }

        return value / (floats.size() + 1);
    }

    public static double hypot(double... values) {
        return Math.sqrt(hypotSquared(values));
    }

    public static double totalAbs(Iterable<? extends Number> iterable) {
        double value = 0.0;

        for (Number number : iterable) {
            value += Math.abs(number.doubleValue());
        }

        return value;
    }

    private static String multiply(String str, int value) {
        StringBuilder builder = new StringBuilder();

        while (value-- > 0) {
            builder.append(str);
        }

        return builder.toString();
    }

    public static int gcd(long value, int value2, int value3) {
        return value3 <= value ? value2 : gcd(value, value3, value2 % value3);
    }

    public static <T> T max(Iterable<T> iterable, Predicate<T> predicate, ToDoubleFunction<T> toDoubleFunction) {
        double MIN_VALUE = Double.MIN_VALUE;
        Object obj = null;

        for (T obj2 : iterable) {
            if (predicate.test(obj2)) {
                double value = toDoubleFunction.applyAsDouble(obj2);
                if (value > MIN_VALUE) {
                    obj = obj2;
                    MIN_VALUE = value;
                }
            }
        }

        return (T)obj;
    }

    public static <T> T min(Iterable<T> iterable, ToDoubleFunction<T> toDoubleFunction) {
        double MAX_VALUE = Double.MAX_VALUE;
        Object obj = null;

        for (T obj2 : iterable) {
            double value = toDoubleFunction.applyAsDouble(obj2);
            if (value < MAX_VALUE) {
                obj = obj2;
                MAX_VALUE = value;
            }
        }

        return (T)obj;
    }

    public static boolean onGround(double value) {
        return value % 0.015625 == 0.0;
    }

    public static double highestAbs(Iterable<? extends Number> iterable) {
        Double doubleValue = null;

        for (Number number : iterable) {
            if (doubleValue == null || Math.abs(number.doubleValue()) > Math.abs(doubleValue)) {
                doubleValue = number.doubleValue();
            }
        }

        return JavaV.firstNonNull(doubleValue, 0.0);
    }

    public static double varianceSquared(Number number, Iterable<? extends Number> iterable) {
        double value = 0.0;
        int value2 = 0;

        for (Number number2 : iterable) {
            value += Math.pow(number2.doubleValue() - number.doubleValue(), 2.0);
            value2++;
        }

        return value == 0.0 ? 0.0 : value / (value2 - 1);
    }

    public static Double getMinimumAngle(PacketLocation location, PlayerLocation... locations) {
        Double doubleValue = null;

        for (PlayerLocation location2 : locations) {
            double deltaX = location.getX() - location2.getX();
            double deltaZ = location.getZ() - location2.getZ();
            float value = (float)(Math.atan2(deltaZ, deltaX) * 180.0 / Math.PI) - 90.0F;
            double distanceBetweenAngles360 = getDistanceBetweenAngles360(location2.getYaw(), value);
            if (doubleValue == null || doubleValue > distanceBetweenAngles360) {
                doubleValue = distanceBetweenAngles360;
            }
        }

        return doubleValue;
    }

    public static float[] getBlockRotations(PlayerLocation location, BlockPosition blockPosition) {
        double value = blockPosition.getX() + 0.5 - location.getX();
        double value2 = blockPosition.getY() + 0.5 - (location.getY() + 1.62F);
        double value3 = blockPosition.getZ() + 0.5 - location.getZ();
        float distance = (float)Math.sqrt(value * value + value3 * value3);
        float value4 = (float)Math.toDegrees(Math.atan2(value, value3)) - 90.0F;
        float value5 = (float)(-Math.toDegrees(Math.atan2(value2, distance)));
        return new float[]{value4, value5};
    }

    public static int lowestInt(int... values) {
        Integer integer = null;

        for (int value : values) {
            if (integer == null || value < integer) {
                integer = value;
            }
        }

        return JavaV.firstNonNull(integer, 0);
    }

    public static double range(Iterable<? extends Number> iterable) {
        return highest(iterable) - lowest(iterable);
    }

    public static double deviation(Iterable<? extends Number> iterable) {
        return Math.sqrt(deviationSquared(iterable));
    }

    public static double variance(Number number, Iterable<? extends Number> iterable) {
        return Math.sqrt(varianceSquared(number, iterable));
    }

    public static <T> T max(Iterable<T> iterable, ToDoubleFunction<T> toDoubleFunction) {
        double MIN_VALUE = Double.MIN_VALUE;
        Object obj = null;

        for (T obj2 : iterable) {
            double value = toDoubleFunction.applyAsDouble(obj2);
            if (value > MIN_VALUE) {
                obj = obj2;
                MIN_VALUE = value;
            }
        }

        return (T)obj;
    }

    public static double highest(double value, double value2, double value3) {
        return value > value2 ? (value > value3 ? value : value3) : (value2 > value3 ? value2 : value3);
    }

    public static int getLog(double value) {
        if (value == 0.0) {
            return 0;
        }

        int value2;
        for (value2 = 0; value < 1.0; value2++) {
            value *= 10.0;
        }

        return value2;
    }

    public static float[] getRotationFromPosition(PlayerLocation location, PacketLocation location2) {
        double deltaX = location2.getX() - location.getX();
        double deltaZ = location2.getZ() - location.getZ();
        double value = location2.getY() + 0.81 - location.getY() - 1.2;
        float distance = (float)Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
        float value2 = (float)(Math.atan2(deltaZ, deltaX) * 180.0 / Math.PI) - 90.0F;
        float value3 = (float)(-(Math.atan2(value, distance) * 180.0 / Math.PI));
        return new float[]{value2, value3};
    }

    public static double common(Queue<Float> floats) {
        HashSet set = new HashSet();
        double value = 0.0;

        for (float value2 : floats) {
            if (!set.add(value2)) {
                value++;
            }
        }

        return value / (floats.size() + 1);
    }

    public static float getHeight(PlayerLocation location, PlayerLocation location2) {
        return (float)location.distanceXZ(location2) * (float)Math.cos(Math.toRadians(location.getPitch()));
    }

    public static double getDistanceBetweenAngles(float value, float value2) {
        float value3 = Math.abs(value - value2) % 360.0F;
        if (value3 > 180.0F) {
            value3 = 360.0F - value3;
        }

        return value3;
    }

    public static double kurtosis(Iterable<? extends Number> iterable) {
        double value = 0.0;
        double value2 = 0.0;

        for (Number number : iterable) {
            value += number.doubleValue();
            value2++;
        }

        if (value2 < 3.0) {
            return 0.0;
        }

        double value3 = value2 * (value2 + 1.0) / ((value2 - 1.0) * (value2 - 2.0) * (value2 - 3.0));
        double value4 = 3.0 * Math.pow(value2 - 1.0, 2.0) / ((value2 - 2.0) * (value2 - 3.0));
        value /= value2;
        double value5 = 0.0;
        double value6 = 0.0;

        for (Number number : iterable) {
            value5 += Math.pow(value - number.doubleValue(), 2.0);
            value6 += Math.pow(value - number.doubleValue(), 4.0);
        }

        value5 /= value2;
        value6 /= Math.pow(value5, 2.0);
        return value3 * value6 - value4;
    }

    public static <T> T min(ToDoubleFunction<T> toDoubleFunction, T... ts) {
        double MAX_VALUE = Double.MAX_VALUE;
        Object obj = null;

        for (T obj2 : ts) {
            double value = toDoubleFunction.applyAsDouble(obj2);
            if (value < MAX_VALUE) {
                obj = obj2;
                MAX_VALUE = value;
            }
        }

        return (T)obj;
    }

    public static int gcd(int value, int value2) {
        return BigInteger.valueOf(value).gcd(BigInteger.valueOf(value2)).intValueExact();
    }

    public static double lowest(Number... numbers) {
        return lowest(Arrays.asList(numbers));
    }

    public static boolean isScientificNotation(String str) {
        try {
            new BigDecimal(str);
        } catch (NumberFormatException exception) {
            return false;
        }

        return str.toUpperCase().contains("E");
    }

    public static double lowestAbs(Iterable<? extends Number> iterable) {
        Double doubleValue = null;

        for (Number number : iterable) {
            if (doubleValue == null || Math.abs(number.doubleValue()) < Math.abs(doubleValue)) {
                doubleValue = number.doubleValue();
            }
        }

        return JavaV.firstNonNull(doubleValue, 0.0);
    }

    public static double lowest(Iterable<? extends Number> iterable) {
        Double doubleValue = null;

        for (Number number : iterable) {
            if (doubleValue == null || number.doubleValue() < doubleValue) {
                doubleValue = number.doubleValue();
            }
        }

        return JavaV.firstNonNull(doubleValue, 0.0);
    }

    public static double average(Iterable<? extends Number> iterable) {
        double value = 0.0;
        int value2 = 0;

        for (Number number : iterable) {
            value += number.doubleValue();
            value2++;
        }

        return value / value2;
    }

    public static double lowestAbs(Number... numbers) {
        return lowestAbs(Arrays.asList(numbers));
    }

    public static int lcd(int value, int value2) {
        return BigInteger.valueOf(value).gcd(BigInteger.valueOf(value2)).intValueExact();
    }

    public static float[] getRotationsBlockOld(double value, PlayerLocation location, BlockPosition blockPosition) {
        double deltaX = blockPosition.getX() - location.getX();
        double deltaY = blockPosition.getY() - (location.getY() + value);
        double deltaZ = blockPosition.getZ() - location.getZ();
        float distance = (float)Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
        float value2 = (float)(Math.atan2(deltaZ, deltaX) * 180.0 / Math.PI) - 90.0F;
        float value3 = (float)(-(Math.atan2(deltaY, distance) * 180.0 / Math.PI));
        return new float[]{value2, value3};
    }

    public static double deviationSquared(Iterable<? extends Number> iterable) {
        double value = 0.0;
        int value2 = 0;

        for (Number number : iterable) {
            value += number.doubleValue();
            value2++;
        }

        double value3 = value / value2;
        double value4 = 0.0;

        for (Number number : iterable) {
            value4 += Math.pow(number.doubleValue() - value3, 2.0);
        }

        return value4 == 0.0 ? 0.0 : value4 / (value2 - 1);
    }

    public static double average(Number... numbers) {
        return average(Arrays.asList(numbers));
    }

    public static double getLuckyAura(PlayerLocation location, IVector3d vector3d) {
        float pitch = location.getPitch();
        double value = location.distanceXZ(vector3d);
        return Math.tan(Math.toRadians(pitch)) * value - location.getY() + vector3d.getY();
    }

    public static float[][] getRotationFromPositions(Double[] doubles, Vector3d[] vector3ds, Vector3d[] vector3ds2) {
        float[][] valueses = new float[doubles.length * vector3ds.length * vector3ds2.length][];
        int value = 0;

        for (Vector3d vector3d : vector3ds2) {
            for (Vector3d vector3d2 : vector3ds) {
                Double[] doubles2 = doubles;
                int length = doubles2.length;

                for (int i = 0; i < length; i++) {
                    double value2 = doubles2[i];
                    double deltaX = vector3d.getX() - vector3d2.getX();
                    double deltaZ = vector3d.getZ() - vector3d2.getZ();
                    double deltaY = vector3d.getY() - (vector3d2.getY() + value2);
                    float distance = (float)Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
                    float value3 = (float)(Math.atan2(deltaZ, deltaX) * 180.0 / Math.PI) - 90.0F;
                    float value4 = (float)(-(Math.atan2(deltaY, distance) * 180.0 / Math.PI));
                    valueses[value++] = new float[]{value3, value4};
                }
            }
        }

        return valueses;
    }

    public static double total(Iterable<? extends Number> iterable) {
        double value = 0.0;

        for (Number number : iterable) {
            value += number.doubleValue();
        }

        return value;
    }
}
