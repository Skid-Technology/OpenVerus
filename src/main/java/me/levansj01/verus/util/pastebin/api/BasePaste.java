package me.levansj01.verus.util.pastebin.api;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import javax.xml.bind.annotation.XmlElement;

public abstract class BasePaste {
    protected ZonedDateTime localExpirationDate;
    protected LocalDateTime pasteDate;
    @XmlElement(name = "paste_date")
    private long dateTimestamp;
    protected LocalDateTime expirationDate;
    @XmlElement(name = "paste_expire_date")
    private long expireDateTimestamp;
    protected ZonedDateTime localPasteDate;

    void setLocalExpirationDate(ZonedDateTime localExpirationDate) {
        this.localExpirationDate = localExpirationDate;
    }

    void setPasteDate(LocalDateTime pasteDate) {
        this.pasteDate = pasteDate;
    }

    long getExpireDateTimestamp() {
        return this.expireDateTimestamp;
    }

    void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    void setExpireDateTimestamp(long expireDateTimestamp) {
        this.expireDateTimestamp = expireDateTimestamp;
    }

    void setLocalPasteDate(ZonedDateTime localPasteDate) {
        this.localPasteDate = localPasteDate;
    }

    long getDateTimestamp() {
        return this.dateTimestamp;
    }
}
