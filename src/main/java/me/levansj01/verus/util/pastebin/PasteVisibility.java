package me.levansj01.verus.util.pastebin;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum PasteVisibility {
    @XmlEnumValue("0")
    PUBLIC("0"),
    @XmlEnumValue("1")
    UNLISTED("1"),
    @XmlEnumValue("2")
    PRIVATE("2");

    private final String visibility;

    @Override
    public String toString() {
        return this.visibility;
    }

    PasteVisibility(String visibility) {
        this.visibility = visibility;
    }
}
