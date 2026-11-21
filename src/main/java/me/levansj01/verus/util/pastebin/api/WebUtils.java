package me.levansj01.verus.util.pastebin.api;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class WebUtils {
    private static final String USER_AGENT = "Mozilla/5.0";

    static String encodeUTF8(@NotNull String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException exception) {
            return "";
        }
    }

    static String doRequest(@NotNull String str, @NotNull HttpURLConnection httpURLConnection, @Nullable String str2, boolean flag) throws IOException {
        if (StringUtils.isNotNullNorEmpty(str2)) {
            httpURLConnection.setDoOutput(true);

            try (DataOutputStream out = new DataOutputStream(httpURLConnection.getOutputStream())) {
                out.writeBytes(str2);
            }
        }

        int responseCode = httpURLConnection.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException(String.format("Error posting to %s using the params: %s", str, str2));
        }

        String str3;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), Charset.forName("UTF-8")))) {
            str3 = reader.lines().collect(Collectors.joining(flag ? "\n" : ""));
        }

        return str3;
    }

    public static Optional<String> post(@NotNull String str, @NotNull Map<Object, Object> map) {
        try {
            URL url = new URL(str);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.addRequestProperty("User-Agent", "Mozilla/5.0");
            httpURLConnection.addRequestProperty("Accept", Locale.getDefault().getLanguage());
            httpURLConnection.setConnectTimeout(30000);
            String params = getParams(map);
            String str2 = doRequest(str, httpURLConnection, params, false);
            return Optional.of(str2);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static Optional<String> get(@NotNull String str, @NotNull Map<Object, Object> map) {
        return get(str, map, false);
    }

    public static Optional<String> get(@NotNull String x, @NotNull Map<Object, Object> y, boolean z) {
        try {
            URL url = new URL(x + "?" + getParams(y));
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.addRequestProperty("User-Agent", "Mozilla/5.0");
            httpURLConnection.addRequestProperty("Accept", Locale.getDefault().getLanguage());
            httpURLConnection.setConnectTimeout(30000);
            String str = doRequest(x, httpURLConnection, null, z);
            return Optional.of(str);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    static String getParams(@NotNull Map<Object, Object> map) {
        return map.entrySet()
            .stream()
            .map(entry -> String.format("%s=%s", encodeUTF8(entry.getKey().toString()), encodeUTF8(entry.getValue().toString())))
            .collect(Collectors.joining("&"));
    }
}
