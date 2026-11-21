package me.levansj01.verus.util.pastebin.api;

public class PasteBinApiFactory {
    private static PasteBinApi defaultApi;

    public static PasteBinApi createDefaultImplementation() {
        if (defaultApi == null) {
            defaultApi = new PasteBinApiImpl();
        }

        return defaultApi;
    }

    private PasteBinApiFactory() {
    }
}
