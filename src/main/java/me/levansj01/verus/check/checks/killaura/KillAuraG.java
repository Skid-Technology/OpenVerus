package me.levansj01.verus.check.checks.killaura;

import java.util.Comparator;
import me.levansj01.verus.check.ReachCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.data.reach.DistanceData;
import me.levansj01.verus.data.reach.ReachBase;

@CheckInfo(
    type = CheckType.KILL_AURA,
    subType = "G",
    friendlyName = "KillAura",
    version = CheckVersion.RELEASE,
    minViolations = -3.0,
    maxViolations = 20,
    logData = true
)
public class KillAuraG extends ReachCheck {
    private int threshold = 0;

    @Override
    public void handle(ReachBase reachBase, long value) {
        DistanceData distanceData = reachBase.getDistanceData();
        double value2 = reachBase.getDistanceList().stream().min(Comparator.comparingDouble(DistanceData::getExtra)).get().getExtra() / distanceData.getHorizontal();
        double value3 = distanceData.getX() * value2;
        double value4 = distanceData.getZ() * value2;
        if (Math.max(Math.abs(value3), Math.abs(value4)) > 0.5) {
            if (this.threshold++ > 2 + this.playerData.getPingTicks() / 3) {
                this.handleViolation(String.format("X: %.2f Z: %.2f E: %.2f", value3, value4, distanceData.getExtra()), 1.0, true);
            }
        } else {
            this.decreaseVL(0.2);
            this.threshold = 0;
        }
    }
}
