package me.levansj01.verus.type.premium.checks.aim;

import java.util.Objects;
import me.levansj01.verus.check.AimCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.util.location.PlayerLocation;

@CheckInfo(type = CheckType.AIM_ASSIST, subType = "A1", friendlyName = "Aimbot", version = CheckVersion.RELEASE, maxViolations = 10)
public class AimA1 extends AimCheck {
    private float lastYawChange;

    @Override
    public void handle(PlayerLocation location, PlayerLocation location2, long value) {
        if (this.playerData.getLastAttackTicks() < 3) {
            float deltaYaw = Math.abs(location.getYaw() - location2.getYaw());
            int value2;
            if (deltaYaw > 1.0F && (value2 = Math.round(deltaYaw)) == deltaYaw) {
                if (Objects.equals(deltaYaw, this.lastYawChange)) {
                    this.handleViolation(String.format("Y: %s", deltaYaw));
                }

                this.lastYawChange = value2;
            } else {
                this.lastYawChange = 0.0F;
            }
        }
    }
}
