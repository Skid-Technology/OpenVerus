package me.levansj01.verus.util.pastebin.api;

import java.io.StringReader;
import javax.xml.bind.JAXB;

class XMLUtils {
    public static <T> T unMarshal(String str, Class<T> clazz) {
        return (T)JAXB.unmarshal(new StringReader(str), clazz);
    }
}
