package me.levansj01.verus.util.pastebin.api;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import me.levansj01.verus.util.pastebin.Paste;
import me.levansj01.verus.util.pastebin.PasteExpiration;
import org.jetbrains.annotations.Nullable;

class PasteInfoUpdater {
    static BasePaste updateDate(@Nullable BasePaste basePaste) {
        if (basePaste == null) {
            return null;
        }

        if (basePaste instanceof Paste) {
            Paste paste = (Paste)basePaste;
            if (basePaste.getExpireDateTimestamp() > 0L) {
                paste.setExpiration(PasteExpiration.getBySeconds(basePaste.getExpireDateTimestamp() - basePaste.getDateTimestamp()));
            } else {
                paste.setExpiration(PasteExpiration.NEVER);
            }
        }

        if (basePaste.getDateTimestamp() > 0L) {
            ZonedDateTime zonedDateTime = new Date(basePaste.getDateTimestamp() * 1000L).toInstant().atZone(ZoneId.systemDefault());
            basePaste.setLocalPasteDate(zonedDateTime);
            basePaste.setPasteDate(zonedDateTime.toLocalDateTime());
            if (basePaste.getExpireDateTimestamp() > 0L) {
                ZonedDateTime zonedDateTime2 = new Date(basePaste.getExpireDateTimestamp() * 1000L).toInstant().atZone(ZoneId.systemDefault());
                basePaste.setLocalExpirationDate(zonedDateTime2);
                basePaste.setExpirationDate(zonedDateTime2.toLocalDateTime());
            }
        }

        return basePaste;
    }

    private PasteInfoUpdater() {
    }

    static List<Paste> updateDate(@Nullable List<Paste> pastes) {
        if (pastes != null && !pastes.isEmpty()) {
            pastes.forEach(PasteInfoUpdater::updateDate);
            return pastes;
        } else {
            return Collections.emptyList();
        }
    }
}
