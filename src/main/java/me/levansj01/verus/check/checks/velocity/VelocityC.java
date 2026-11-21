package me.levansj01.verus.check.checks.velocity;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.util.location.PlayerLocation;

@CheckInfo(type = CheckType.VELOCITY, subType = "C", friendlyName = "NoVelocity", version = CheckVersion.RELEASE, minViolations = -2.0)
public class VelocityC extends Check implements PacketHandler {
    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        PlayerLocation lastLastLocation = this.playerData.getLastLastLocation();
        PlayerLocation location = this.playerData.getLocation();
        if (this.playerData.getVerticalVelocityTicks() > this.playerData.getMoveTicks() - 1
            && this.playerData.getLastVelY() > 0.0
            && this.playerData.getVerticalVelocityTicks() > this.playerData.getMaxPingTicks()) {
            double deltaY = location.getY() - lastLastLocation.getY();
            if (deltaY > 0.0) {
                this.decreaseVL(1.0);
            } else {
                this.handleViolation(String.format("T: %s | %s", this.playerData.getVerticalVelocityTicks(), this.playerData.getMaxPingTicks()));
            }

            this.playerData.setResetVelocity(true);
        }
    }
}
