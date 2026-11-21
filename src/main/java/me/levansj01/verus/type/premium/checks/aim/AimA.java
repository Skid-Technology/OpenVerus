package me.levansj01.verus.type.premium.checks.aim;

import me.levansj01.verus.check.AimCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.util.location.PlayerLocation;

@CheckInfo(
    type = CheckType.AIM_ASSIST,
    subType = "A",
    friendlyName = "Aimbot",
    version = CheckVersion.RELEASE,
    unsupportedAtleast = ClientVersion.V1_17,
    maxViolations = 2,
    logData = true
)
public class AimA extends AimCheck {
    @Override
    public void handle(PlayerLocation location, PlayerLocation location2, long value) {
        float deltaYaw = Math.abs(location.getYaw() - location2.getYaw());
        if (deltaYaw >= 1.0F && deltaYaw % 0.1F == 0.0F) {
            if (deltaYaw % 1.0F == 0.0F) {
                this.violations++;
            }

            if (deltaYaw % 10.0F == 0.0F) {
                this.violations++;
            }

            if (deltaYaw % 30.0F == 0.0F) {
                this.violations++;
            }

            this.handleViolation(String.format("Y: %s", deltaYaw));
        }

        float deltaPitch = Math.abs(location.getPitch() - location2.getPitch());
        if (deltaPitch >= 1.0F && deltaPitch % 0.1F == 0.0F) {
            if (deltaPitch % 1.0F == 0.0F) {
                this.violations++;
            }

            if (deltaPitch % 10.0F == 0.0F) {
                this.violations++;
            }

            if (deltaPitch % 30.0F == 0.0F) {
                this.violations++;
            }

            this.handleViolation(String.format("P: %s", deltaPitch));
        }
    }
}
