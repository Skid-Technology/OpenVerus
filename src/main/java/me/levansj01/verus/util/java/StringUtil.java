package me.levansj01.verus.util.java;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import org.bukkit.ChatColor;
import org.bukkit.potion.PotionEffect;

public class StringUtil {
    public static final String LINE = ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + "-----------------------------------------";

    public static String formatEffect(PotionEffect effect) {
        String str = WordUtils.capitalize(effect.getType().getName().toLowerCase()).replaceAll("_", " ");
        int value = effect.getAmplifier() + 1;
        int value2 = effect.getDuration() * 50;
        return formatEffect(str, value, value2);
    }

    public static String plainDifferenceAsTime(long value) {
        long value2 = TimeUnit.MILLISECONDS.toDays(value);
        long value3 = TimeUnit.MILLISECONDS.toHours(value);
        return value2 == 0L ? (value3 == 0L ? TimeUnit.MILLISECONDS.toMinutes(value) + " mins" : value3 + " hrs") : value2 + " days";
    }

    public static String generateScrambledId(int value) {
        StringBuilder builder = new StringBuilder(value);

        for (int i = 0; i < value; i++) {
            int value2 = ThreadLocalRandom.current().nextBoolean() ? 97 : 65;
            char c = (char)(value2 + ThreadLocalRandom.current().nextInt(20));
            builder.append(c);
        }

        return builder.toString();
    }

    public static String formatEffect(String str, int value, long value2) {
        long value3 = value2 / 60000L;
        long value4 = value2 / 1000L % 60L;
        return String.format("%s %s %d:%02d", str, value, value3, value4);
    }

    public static String differenceAsTime(long value) {
        long value2 = TimeUnit.MILLISECONDS.toDays(value);
        long value3 = TimeUnit.HOURS.convert(value, TimeUnit.MILLISECONDS);
        return value2 == 0L
            ? (
                value3 == 0L
                    ? ChatColor.WHITE + String.valueOf(TimeUnit.MINUTES.convert(value, TimeUnit.MILLISECONDS)).concat(ChatColor.GRAY + " minutes ago")
                    : ChatColor.WHITE + String.valueOf(value3).concat(ChatColor.GRAY + " hours ago")
            )
            : ChatColor.WHITE + String.valueOf(value2).concat(ChatColor.GRAY + " days ago");
    }

    public static String generateRandomId() {
        return String.valueOf(ThreadLocalRandom.current().nextInt(1, 9)) + (char)(65 + ThreadLocalRandom.current().nextInt(0, 5));
    }
}
