package me.levansj01.verus.type.premium.checks.aim;

import me.levansj01.verus.check.AimCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.util.location.PlayerLocation;

@CheckInfo(type = CheckType.AIM_ASSIST, subType = "H", friendlyName = "Aim", version = CheckVersion.EXPERIMENTAL, minViolations = -1.0)
public class AimH extends AimCheck {
    @Override
    public void handle(PlayerLocation location, PlayerLocation location2, long value) {
        float deltaYaw = Math.abs(location.getYaw() - location2.getYaw());
        float deltaPitch = Math.abs(location.getPitch() - location2.getPitch());
        if (this.playerData.getLastAttackTicks() < 3 && deltaYaw > 0.0F && deltaYaw < 0.8 && deltaPitch > 0.279 && deltaPitch < 0.28090858) {
            this.handleViolation(String.format("Y: %s P: %s", deltaYaw, deltaPitch));
        } else {
            this.decreaseVL(1.0E-4);
        }
    }
}
