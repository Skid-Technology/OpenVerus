package me.levansj01.verus.type.custom.checks.inventory;

import me.levansj01.verus.check.MovementCheck2;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.util.java.DoubleWrapper;
import me.levansj01.verus.util.java.MathUtil;
import me.levansj01.verus.util.location.PacketLocation;
import me.levansj01.verus.verus2.data.player.TickerType;

@CheckInfo(type = CheckType.INVENTORY, subType = "C2", version = CheckVersion.RELEASE, friendlyName = "Inventory", schem = true, logData = true)
public class InventoryC2 extends MovementCheck2 {
    @Override
    public void handle(PacketLocation location, PacketLocation location2, long value) {
        if (!this.playerData.isTeleporting(5)
            && !this.playerData.isTeleportingV2()
            && this.playerData.getTickerMap().get(TickerType.WINDOW_CLICK) <= 1
            && this.playerData.getVelocityTicks() > this.playerData.getMaxPingTicks() * 2
            && !this.playerData.isFallFlying()
            && !this.playerData.isGliding()) {
            double distance = MathUtil.hypot(location2.getX() - location.getX(), location2.getZ() - location.getZ());
            boolean flag = !location.isGround() || !location2.isGround();
            DoubleWrapper doubleWrapper = new DoubleWrapper(0.215);
            doubleWrapper.addAndGet(Math.max((Integer)this.playerData.getSpeedLevel().get(), 0) * (location2.isGround() ? 0.0573 : 0.0175));
            if (distance > doubleWrapper.get()) {
                boolean flag2 = this.playerData.hasLag() || this.playerData.hasFast();
                boolean sprinting = this.playerData.isSprinting(true);
                this.handleViolation(String.format("%.3f %s %s %s", distance - doubleWrapper.get(), sprinting, flag2, flag), !flag2 && !flag ? 1.0 : 0.2);
            }
        }
    }
}
