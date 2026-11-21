package me.levansj01.verus.type;

public enum VerusType {
    REGULAR,
    PREMIUM,
    CUSTOM,
    ENTERPRISE,
    DEV;

    public boolean before(VerusType runnable) {
        return this.ordinal() < runnable.ordinal();
    }

    public boolean after(VerusType runnable) {
        return this.ordinal() > runnable.ordinal();
    }

    public boolean afterOrEq(VerusType verusType) {
        return this.ordinal() >= verusType.ordinal();
    }
}
