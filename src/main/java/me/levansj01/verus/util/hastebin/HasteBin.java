package me.levansj01.verus.util.hastebin;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import me.levansj01.verus.util.java.SafeReflection;

public class HasteBin {
    public static final String[] ENDPOINTS = new String[]{
        "https://paste.verus.ac/", "https://paste.verusdev.xyz/", "https://pastie.io/", "https://hastebin.com/", "https://hasteb.in/"
    };
    private static final Class<?> jsonParserClass = SafeReflection.findClass(
        "com.google.gson.JsonParser",
        "org.bukkit.craftbukkit.libs.com.google.gson.JsonParser",
        "net.minecraft.util.com.google.gson.JsonParser",
        "us.myles.viaversion.libs.gson.JsonParser"
    );
    private static final Object jsonParser = SafeReflection.fetchConstructor(SafeReflection.constructor(jsonParserClass));
    private static final Method parseMethod = SafeReflection.access(jsonParserClass, "parse", String.class);

    public static String paste(String str, String str2) throws IOException {
        return new HTTPRequest(str2 + "documents")
            .open()
            .requestMethod("POST")
            .setConnectTimeout(750)
            .setReadTimeout(750)
            .setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0")
            .setRequestProperty("Content-Type", "text/plain")
            .doOutput(true)
            .outputStream(entry -> new DataOutputStream(entry).writeBytes(str))
            .disconnectAndReturn(entry -> str2 + parseAndGet(toRaw(entry.getHttpURLConnection().getInputStream()), "key"));
    }

    public static String getPaste(String str, String str2) throws IOException {
        return new HTTPRequest(str2 + "raw/" + str).open().doOutput(true).disconnectAndReturn(entry -> {
            BufferedReader reader = new BufferedReader(new InputStreamReader(entry.getHttpURLConnection().getInputStream()));
            String str3 = "";

            while (reader.ready()) {
                String str4 = reader.readLine();
                if (!str4.contains("package")) {
                    if (str3.equals("")) {
                        str3 = str4;
                    } else {
                        str3 = str3 + "\n" + str4;
                    }
                }
            }

            return str3;
        });
    }

    private static String parseAndGet(String str, String str2) {
        Object obj = SafeReflection.execute(parseMethod, jsonParser, str);
        Method method = SafeReflection.access(obj.getClass(), "get", String.class);
        obj = SafeReflection.execute(method, obj, str2);
        method = SafeReflection.access(obj.getClass(), "getAsString", new Class[0]);
        obj = SafeReflection.execute(method, obj);
        return (String)obj;
    }

    private static String toRaw(InputStream in) throws IOException {
        StringBuilder builder = new StringBuilder();

        String str;
        try (
            InputStreamReader inputStreamReader = new InputStreamReader(in);
            BufferedReader reader = new BufferedReader(inputStreamReader);
        ) {
            while ((str = reader.readLine()) != null) {
                builder.append(str);
            }
        }

        return builder.toString();
    }
}
