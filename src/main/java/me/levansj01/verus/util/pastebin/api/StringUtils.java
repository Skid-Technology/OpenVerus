package me.levansj01.verus.util.pastebin.api;

public class StringUtils {
    private StringUtils() {
    }

    public static boolean isNotNullNorEmpty(String str) {
        return str != null && str.length() > 0;
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.length() == 0;
    }
}
