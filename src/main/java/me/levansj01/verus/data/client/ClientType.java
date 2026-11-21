package me.levansj01.verus.data.client;

public enum ClientType {
    UNKNOWN("Unknown"),
    VANILLA("Vanilla"),
    FORGE("Forge"),
    LITE_LOADER("LiteLoader"),
    FABRIC("Fabric"),
    BADLION("Badlion"),
    LUNAR("Lunar"),
    BEDROCK("Bedrock");

    private final String display;

    public boolean isUnknown() {
        return this == UNKNOWN;
    }

    public String getDisplay() {
        return this.display;
    }

    ClientType(String display) {
        this.display = display;
    }
}
