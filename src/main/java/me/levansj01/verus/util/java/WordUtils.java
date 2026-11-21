package me.levansj01.verus.util.java;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;

public class WordUtils {
    public static String capitalize(String str) {
        return capitalize(str, null);
    }

    public static String wrap(String str, int value, String str2, boolean flag) {
        if (str == null) {
            return null;
        }

        if (str2 == null) {
            str2 = SystemUtils.LINE_SEPARATOR;
        }

        if (value < 1) {
            value = 1;
        }

        int length = str.length();
        int value2 = 0;
        StringBuffer buffer = new StringBuffer(length + 32);

        while (length - value2 > value) {
            if (str.charAt(value2) == ' ') {
                value2++;
            } else {
                int value3 = str.lastIndexOf(32, value + value2);
                if (value3 >= value2) {
                    buffer.append(str.substring(value2, value3));
                    buffer.append(str2);
                    value2 = value3 + 1;
                } else if (flag) {
                    buffer.append(str.substring(value2, value + value2));
                    buffer.append(str2);
                    value2 += value;
                } else {
                    value3 = str.indexOf(32, value + value2);
                    if (value3 >= 0) {
                        buffer.append(str.substring(value2, value3));
                        buffer.append(str2);
                        value2 = value3 + 1;
                    } else {
                        buffer.append(str.substring(value2));
                        value2 = length;
                    }
                }
            }
        }

        buffer.append(str.substring(value2));
        return buffer.toString();
    }

    public static String initials(String str, char[] chars) {
        if (str != null && str.length() != 0) {
            if (chars != null && chars.length == 0) {
                return "";
            }

            int length = str.length();
            char[] chars2 = new char[length / 2 + 1];
            int value = 0;
            boolean flag = true;

            for (int i = 0; i < length; i++) {
                char c = str.charAt(i);
                if (isDelimiter(c, chars)) {
                    flag = true;
                } else if (flag) {
                    chars2[value++] = c;
                    flag = false;
                }
            }

            return new String(chars2, 0, value);
        } else {
            return str;
        }
    }

    public static String uncapitalize(String str, char[] chars) {
        int value = chars == null ? -1 : chars.length;
        if (str != null && str.length() != 0 && value != 0) {
            int length = str.length();
            StringBuffer buffer = new StringBuffer(length);
            boolean flag = true;

            for (int i = 0; i < length; i++) {
                char c = str.charAt(i);
                if (isDelimiter(c, chars)) {
                    buffer.append(c);
                    flag = true;
                } else if (flag) {
                    buffer.append(Character.toLowerCase(c));
                    flag = false;
                } else {
                    buffer.append(c);
                }
            }

            return buffer.toString();
        } else {
            return str;
        }
    }

    public static String swapCase(String str) {
        int value;
        if (str != null && (value = str.length()) != 0) {
            StringBuffer buffer = new StringBuffer(value);
            boolean flag = true;
            char c = '\u0000';
            char c2 = '\u0000';

            for (int i = 0; i < value; i++) {
                c = str.charAt(i);
                if (Character.isUpperCase(c)) {
                    c2 = Character.toLowerCase(c);
                } else if (Character.isTitleCase(c)) {
                    c2 = Character.toLowerCase(c);
                } else if (Character.isLowerCase(c)) {
                    if (flag) {
                        c2 = Character.toTitleCase(c);
                    } else {
                        c2 = Character.toUpperCase(c);
                    }
                } else {
                    c2 = c;
                }

                buffer.append(c2);
                flag = Character.isWhitespace(c);
            }

            return buffer.toString();
        } else {
            return str;
        }
    }

    public static String initials(String str) {
        return initials(str, null);
    }

    private static boolean isDelimiter(char c, char[] chars) {
        if (chars == null) {
            return Character.isWhitespace(c);
        }

        int value = 0;

        for (int i = chars.length; value < i; value++) {
            if (c == chars[value]) {
                return true;
            }
        }

        return false;
    }

    public static String abbreviate(String str, int value, int value2, String str2) {
        if (str == null) {
            return null;
        }

        if (str.length() == 0) {
            return "";
        }

        if (value > str.length()) {
            value = str.length();
        }

        if (value2 == -1 || value2 > str.length()) {
            value2 = str.length();
        }

        if (value2 < value) {
            value2 = value;
        }

        StringBuffer buffer = new StringBuffer();
        int value3 = StringUtils.indexOf(str, " ", value);
        if (value3 == -1) {
            buffer.append(str.substring(0, value2));
            if (value2 != str.length()) {
                buffer.append(StringUtils.defaultString(str2));
            }
        } else if (value3 > value2) {
            buffer.append(str.substring(0, value2));
            buffer.append(StringUtils.defaultString(str2));
        } else {
            buffer.append(str.substring(0, value3));
            buffer.append(StringUtils.defaultString(str2));
        }

        return buffer.toString();
    }

    public static String capitalizeFully(String str, char[] chars) {
        int value = chars == null ? -1 : chars.length;
        if (str != null && str.length() != 0 && value != 0) {
            str = str.toLowerCase();
            return capitalize(str, chars);
        } else {
            return str;
        }
    }

    public static String uncapitalize(String str) {
        return uncapitalize(str, null);
    }

    public static String wrap(String str, int value) {
        return wrap(str, value, null, false);
    }

    public static String capitalizeFully(String str) {
        return capitalizeFully(str, null);
    }

    public static String capitalize(String str, char[] chars) {
        int value = chars == null ? -1 : chars.length;
        if (str != null && str.length() != 0 && value != 0) {
            int length = str.length();
            StringBuffer buffer = new StringBuffer(length);
            boolean flag = true;

            for (int i = 0; i < length; i++) {
                char c = str.charAt(i);
                if (isDelimiter(c, chars)) {
                    buffer.append(c);
                    flag = true;
                } else if (flag) {
                    buffer.append(Character.toTitleCase(c));
                    flag = false;
                } else {
                    buffer.append(c);
                }
            }

            return buffer.toString();
        } else {
            return str;
        }
    }
}
