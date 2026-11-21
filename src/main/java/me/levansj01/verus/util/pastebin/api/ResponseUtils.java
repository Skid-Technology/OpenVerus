package me.levansj01.verus.util.pastebin.api;

import java.util.Optional;

class ResponseUtils {
    private ResponseUtils() {
    }

    public static void validateResponse(Optional<String> optional) {
        if (optional != null && optional.isPresent()) {
            String str = (String)optional.get();
            if (str.toLowerCase().contains("bad api request,")) {
                throw new RuntimeException("Error: " + str);
            }
        } else {
            throw new RuntimeException("Empty response");
        }
    }

    public static Optional<String> requiresValidResponse(Optional<String> optional) {
        validateResponse(optional);
        return optional;
    }
}
