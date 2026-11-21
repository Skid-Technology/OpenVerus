package me.levansj01.verus.type.premium.checks.aim;

import me.levansj01.verus.check.AimCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.util.location.PlayerLocation;

@CheckInfo(type = CheckType.AIM_ASSIST, subType = "C", friendlyName = "Aim", version = CheckVersion.RELEASE, minViolations = -2.5)
public class AimC extends AimCheck {
    @Override
    public void handle(PlayerLocation location, PlayerLocation location2, long value) {
        if (this.playerData.getLastAttackTicks() <= 100) {
            float deltaYaw = Math.abs(location2.getYaw() - location.getYaw());
            float deltaPitch = Math.abs(location2.getPitch() - location.getPitch());
            if (deltaYaw > 0.0F && deltaYaw < 0.01 && deltaPitch > 0.2) {
                this.handleViolation(String.format("Y: %s P: %s", deltaYaw, deltaPitch));
            } else {
                this.violations = this.violations - Math.min(this.violations + 2.5, 0.05);
            }
        }
    }
}
