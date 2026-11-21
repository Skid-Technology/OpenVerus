package me.levansj01.verus.type.premium.checks.reach;

import java.util.List;
import me.levansj01.verus.check.ReachCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.data.reach.DistanceData;
import me.levansj01.verus.data.reach.ReachBase;
import me.levansj01.verus.util.java.MathUtil;

@CheckInfo(type = CheckType.REACH, subType = "C", friendlyName = "Reach", version = CheckVersion.RELEASE, minViolations = -1.5, maxViolations = 15)
public class ReachC extends ReachCheck {
    @Override
    public void handle(List<ReachBase> reachBases, long value) {
        ReachBase reachBase = MathUtil.max(reachBases, ReachBase::getUncertaintyReachValue);
        if (reachBase != null) {
            DistanceData distanceData = reachBase.getDistanceData();
            double uncertaintyReachValue = reachBase.getUncertaintyReachValue();
            double horizontal = distanceData.getHorizontal();
            double extra = distanceData.getExtra();
            if (uncertaintyReachValue > 3.0 && horizontal < 6.0 && extra < 5.0) {
                this.handleViolation(String.format("R: %.2f H: %.2f E: %.2f", distanceData.getReach(), horizontal, extra), Math.max(Math.ceil(horizontal - 3.0), 1.0));
            } else {
                this.decreaseVL(0.035);
            }
        }
    }
}
