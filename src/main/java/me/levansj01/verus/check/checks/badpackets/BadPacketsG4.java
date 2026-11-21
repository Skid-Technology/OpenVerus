package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.data.transaction.teleport.Teleport;

@CheckInfo(type = CheckType.BAD_PACKETS, subType = "G4", friendlyName = "Ping Spoof", version = CheckVersion.RELEASE, minViolations = -1.0)
public class BadPacketsG4 extends Check {
    private int threshold = 0;

    public void accept(long value, Teleport teleport) {
        long value2 = value - teleport.getTime();
        int transactionPing = this.playerData.getTransactionPing();
        if (value2 * 10L + 100L < transactionPing && this.playerData.getTotalTicks() > 100) {
            if (this.threshold++ > 3) {
                this.handleViolation(() -> String.format("Tr: %s K: %s Te: %s", transactionPing, this.playerData.getPing(), value2), this.threshold / 4.0);
                this.playerData.setTransactionPing((int)value2);
            }
        } else {
            this.threshold = 0;
        }
    }
}
