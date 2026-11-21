package me.levansj01.verus.util.pastebin.api;

import org.jetbrains.annotations.Nullable;

class PasteUtil {
    /** @deprecated */
    private PasteUtil() {
    }

    public static String getPasteKeyFromUrl(@Nullable String str) {
        if (str == null) {
            return null;
        } else if (!str.contains("https://pastebin.com/")) {
            throw new IllegalArgumentException("Not a valid paste bin url!");
        } else {
            return str.substring(str.indexOf("https://pastebin.com/") + "https://pastebin.com/".length());
        }
    }
}
