package me.levansj01.verus.util.pastebin;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import me.levansj01.verus.util.pastebin.api.BasePaste;
import me.levansj01.verus.util.pastebin.api.PasteBinApiFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Paste extends BasePaste {
    @XmlElement(name = "paste_format_short")
    private PasteHighLight highLight;
    private PasteExpiration expiration;
    @XmlElement(name = "paste_private")
    private PasteVisibility visibility;
    @XmlElement(name = "paste_url")
    private String url;
    @XmlElement(name = "paste_title")
    private String title;
    @XmlElement(name = "paste_size")
    private long size;
    @XmlElement(name = "paste_hits")
    private long hits;
    @XmlElement(name = "paste_key")
    private String key;
    private String content;

    public LocalDateTime getExpirationDate() {
        return this.expirationDate;
    }

    public LocalDateTime getPasteDate() {
        return this.pasteDate;
    }

    public String fetchContent() {
        this.content = PasteBinApiFactory.createDefaultImplementation().getPasteContent(this);
        return this.content;
    }

    public PasteVisibility getVisibility() {
        return this.visibility;
    }

    public String getKey() {
        return this.key;
    }

    public long getHits() {
        return this.hits;
    }

    public String getUrl() {
        return this.url;
    }

    public void setVisibility(PasteVisibility visibility) {
        this.visibility = visibility;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static Paste.Builder newBuilder() {
        return new Paste.Builder();
    }

    public void setHighLight(PasteHighLight highLight) {
        this.highLight = highLight;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ZonedDateTime getLocalExpirationDate() {
        return this.localExpirationDate;
    }

    public static Paste.Builder newBuilder(Paste paste) {
        Paste.Builder builder = new Paste.Builder();
        builder.title = paste.title;
        builder.content = paste.content;
        builder.highLight = paste.highLight;
        builder.expiration = paste.expiration;
        builder.visibility = paste.visibility;
        return builder;
    }

    public Paste() {
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return this.title;
    }

    public PasteHighLight getHighLight() {
        return this.highLight;
    }

    public long getSize() {
        return this.size;
    }

    public Paste(@Nullable String title, @NotNull String content, @Nullable PasteHighLight highLight, @Nullable PasteExpiration expiration, @Nullable PasteVisibility visibility) {
        Objects.requireNonNull(content);
        this.title = title;
        this.content = content;
        this.expiration = expiration;
        this.visibility = visibility;
        this.highLight = highLight;
    }

    public String getContent() {
        return this.content;
    }

    private Paste(Paste.Builder builder) {
        this.setTitle(builder.title);
        this.setContent(builder.content);
        this.setHighLight(builder.highLight);
        this.setExpiration(builder.expiration);
        this.setVisibility(builder.visibility);
    }

    public String paste(@NotNull PasteBin pasteBin) {
        return pasteBin.createPaste(this);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Paste{");
        builder.append("key='").append(this.key).append('\'');
        builder.append(", title='").append(this.title).append('\'');
        builder.append(", highLight=").append(this.highLight);
        builder.append(", visibility=").append(this.visibility);
        builder.append(", expiration=").append(this.expiration);
        builder.append(", localExpirationDate=").append(this.getLocalExpirationDate());
        builder.append(", localPasteDate=").append(this.getLocalPasteDate());
        builder.append(", url='").append(this.url).append('\'');
        builder.append(", size=").append(this.size);
        builder.append(", hits=").append(this.hits);
        builder.append('}');
        return builder.toString();
    }

    public void setExpiration(PasteExpiration expiration) {
        this.expiration = expiration;
    }

    public PasteExpiration getExpiration() {
        return this.expiration;
    }

    public Paste(@NotNull String key) {
        Objects.requireNonNull(key);
        this.key = key;
    }

    public String paste(@NotNull AccountCredentials credentials) {
        return PasteBinApiFactory.createDefaultImplementation().createPaste(credentials, this);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ZonedDateTime getLocalPasteDate() {
        return this.localPasteDate;
    }

    public static final class Builder {
        private PasteExpiration expiration;
        private String title;
        private PasteVisibility visibility;
        private PasteHighLight highLight;
        private String content;

        public Paste.Builder withHighLight(PasteHighLight highLight) {
            this.highLight = highLight;
            return this;
        }

        private Builder() {
        }

        public Paste.Builder withExpiration(PasteExpiration expiration) {
            this.expiration = expiration;
            return this;
        }

        public Paste.Builder withContent(String content) {
            this.content = content;
            return this;
        }

        public Paste.Builder withVisibility(PasteVisibility visibility) {
            this.visibility = visibility;
            return this;
        }

        public Paste.Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Paste build() {
            return new Paste(this);
        }
    }
}
