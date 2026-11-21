package me.levansj01.verus.type.custom.checks.fly;

import me.levansj01.verus.check.MovementCheck2;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.util.location.PacketLocation;
import me.levansj01.verus.verus2.data.player.TickerType;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;

@CheckInfo(type = CheckType.FLY, subType = "K2", friendlyName = "Fly", version = CheckVersion.RELEASE, minViolations = -1.0, maxViolations = 10, logData = true)
public class FlyK2 extends MovementCheck2 {
    private double lastYDiff;
    private int lastBypassTick = -10;

    @Override
    public void handle(PacketLocation location, PacketLocation location2, long value) {
        if (this.playerData.getTotalTicks() - 100 > this.lastBypassTick) {
            double deltaY = location.getY() - location2.getY();
            if (!location.isGround()
                || location.getY() % 0.5 * 16.0 % 1.0 == 0.0
                || location.getY() % 0.5 * 64.0 == 1.0
                || location.getY() % 0.5 == 0.0
                || location2.getY() % 1.0 == 0.42F
                || location2.getY() % 1.0 == 0.0
                || !(Math.abs(location.getY() % 0.5 - 0.015555072702202466) > 1.0E-12)
                || location.getY() % 1.0 == 0.09375
                || this.playerData.getTickerMap().get(TickerType.TELEPORT) <= 2
                || this.playerData.getVelocityTicks() <= this.playerData.getPingTicks()
                || this.playerData.isVehicle()
                || !this.playerData.isSurvival()
                || this.playerData.canFly()
                || this.playerData.hasPlacedBlock(true)) {
                this.decreaseVL(0.001);
            } else if (!location2.isGround() && deltaY < 0.078 && this.lastYDiff > 0.08) {
                double lastYDiff = this.lastYDiff;
                this.run(() -> {
                    if (this.playerData.getTotalTicks() - 100 > this.lastBypassTick) {
                        for (Entity entity : this.player.getNearbyEntities(2.5, 2.5, 2.5)) {
                            if (entity instanceof Boat || entity instanceof Minecart) {
                                this.lastBypassTick = this.playerData.getTotalTicks();
                                return;
                            }
                        }

                        this.handleViolation(String.format("L: %.3f N: %.3f", lastYDiff, deltaY));
                    }
                });
            }

            this.lastYDiff = deltaY;
        }
    }
}
