package me.levansj01.verus.api.wrapper;

public enum BanResult {
    ANNOUNCE(true, true),
    SILENT(true, false),
    CANCEL(false, false);

    private final boolean ban;
    private final boolean announce;

    public boolean isAnnounce() {
        return this.announce;
    }

    BanResult(boolean ban, boolean announce) {
        this.ban = ban;
        this.announce = announce;
    }

    public boolean isBan() {
        return this.ban;
    }
}
