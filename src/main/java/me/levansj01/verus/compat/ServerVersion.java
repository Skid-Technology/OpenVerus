package me.levansj01.verus.compat;

public enum ServerVersion {
    v1_7_R4,
    v1_8_R3,
    v1_11_R1,
    v1_12_R1,
    v1_13_R2,
    v1_14_R1,
    v1_15_R1,
    v1_16_R1,
    v1_16_R2,
    v1_16_R3,
    v1_17_R1,
    v1_18_R1,
    v1_18_R2,
    v1_19_R1,
    v1_19_R2,
    v1_19_R3,
    v1_20_R1,
    v1_20_R3,
    NONE;

    public boolean beforeEq(ServerVersion serverVersion) {
        return serverVersion.ordinal() >= this.ordinal();
    }

    public boolean after(ServerVersion runnable) {
        return this.ordinal() > runnable.ordinal();
    }

    public boolean afterEq(ServerVersion serverVersion) {
        return this.ordinal() >= serverVersion.ordinal();
    }

    public boolean before(ServerVersion runnable) {
        return runnable.ordinal() > this.ordinal();
    }
}
