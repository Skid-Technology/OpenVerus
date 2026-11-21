package me.levansj01.verus.data.version;

public enum ClientVersion {
    V1_7("1.7"),
    V1_8("1.8"),
    V1_9("1.9"),
    V1_10("1.10"),
    V1_11("1.11"),
    V1_12("1.12"),
    V1_13("1.13"),
    V1_14("1.14"),
    V1_15("1.15"),
    V1_16("1.16"),
    V1_17("1.17"),
    V1_18("1.18"),
    V1_19("1.19"),
    VERSION_UNSUPPORTED("Unknown"),
    NONE("Error");

    private final String name;

    public boolean before(ClientVersion runnable) {
        return this.ordinal() < runnable.ordinal();
    }

    public String getName() {
        return this.name;
    }

    public ClientVersion next() {
        ClientVersion[] values2 = values();
        return values2[Math.min(this.ordinal() + 1, values2.length - 1)];
    }

    ClientVersion(String name) {
        this.name = name;
    }

    public boolean after(ClientVersion runnable) {
        return this.ordinal() > runnable.ordinal();
    }

    public boolean afterEq(ClientVersion clientVersion) {
        return this.ordinal() >= clientVersion.ordinal();
    }
}
