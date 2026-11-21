package me.levansj01.verus.util.pastebin;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum AccountType {
    @XmlEnumValue("0")
    NORMAL,
    @XmlEnumValue("1")
    PRO;
}
