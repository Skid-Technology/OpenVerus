package me.levansj01.verus.type.premium.checks.velocity;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.util.java.MathUtil;
import me.levansj01.verus.util.location.PlayerLocation;
import me.levansj01.verus.verus2.data.player.TickerType;

@CheckInfo(type = CheckType.VELOCITY, subType = "A", friendlyName = "vVelocity", version = CheckVersion.RELEASE, minViolations = -0.5, maxViolations = 5)
public class VelocityA extends Check implements PacketHandler {
    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        PlayerLocation lastLastLocation = this.playerData.getLastLastLocation();
        PlayerLocation location = this.playerData.getLocation();
        if (this.playerData.getVerticalVelocityTicks() > this.playerData.getMoveTicks() - 1 && this.playerData.getLastVelY() > 0.0) {
            double deltaY = location.getY() - lastLastLocation.getY();
            if (deltaY > 0.0) {
                deltaY = Math.ceil(deltaY * 8000.0) / 8000.0;
                if (deltaY < 0.42F
                    && lastLastLocation.getGround()
                    && !location.getGround()
                    && MathUtil.onGround(lastLastLocation.getY())
                    && !MathUtil.onGround(location.getY())
                    && this.playerData.getTickerMap().get(TickerType.EXPLOSION) > this.playerData.getMaxPingTicks() + 2) {
                    double value = deltaY / this.playerData.getLastVelY();
                    if (value < 0.995) {
                        this.handleViolation(String.format("P: %.1f.", value * 100.0));
                    }
                }

                this.playerData.setResetVelocity(true);
            } else {
                this.decreaseVL(0.01);
            }
        }
    }
}
