package me.levansj01.verus.util.pastebin;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum PasteExpiration {
    @XmlEnumValue("N")
    NEVER("N", 0L),
    @XmlEnumValue("10M")
    TEN_MINUTES("10M", 600L),
    @XmlEnumValue("1H")
    ONE_HOUR("1H", 3600L),
    @XmlEnumValue("1D")
    ONE_DAY("1D", 86400L),
    @XmlEnumValue("1W")
    ONE_WEEK("1W", 604800L),
    @XmlEnumValue("2W")
    TWO_WEEKS("2W", 1209600L),
    @XmlEnumValue("1m")
    ONE_MONTH("1M", -1L);

    private static final Map<Long, PasteExpiration> cacheBySeconds = new HashMap<>();
    private final String expiration;
    private final long seconds;

    public static PasteExpiration getBySeconds(long value) {
        return cacheBySeconds.getOrDefault(value, ONE_MONTH);
    }

    @Override
    public String toString() {
        return this.expiration;
    }

    static {
        for (PasteExpiration pasteExpiration : values()) {
            cacheBySeconds.put(pasteExpiration.seconds, pasteExpiration);
        }
    }

    PasteExpiration(String expiration, long seconds) {
        this.expiration = expiration;
        this.seconds = seconds;
    }
}
