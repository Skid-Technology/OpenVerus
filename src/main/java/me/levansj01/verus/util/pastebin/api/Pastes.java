package me.levansj01.verus.util.pastebin.api;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import me.levansj01.verus.util.pastebin.Paste;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class Pastes {
    @XmlElement(name = "paste")
    private List<Paste> pastes;

    public List<Paste> getPastes() {
        return this.pastes;
    }
}
