package me.levansj01.verus.type.premium.checks.autoclicker;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInArmAnimation;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.util.java.BasicDeque;
import me.levansj01.verus.util.java.CappedQueue;
import me.levansj01.verus.util.java.MathUtil;

@CheckInfo(
    type = CheckType.AUTO_CLICKER,
    subType = "A",
    friendlyName = "Auto Clicker",
    version = CheckVersion.RELEASE,
    minViolations = -5.0,
    maxViolations = 10,
    logData = true
)
public class AutoClickerA extends Check implements PacketHandler {
    private final BasicDeque<Integer> intervals = new CappedQueue(40);
    private int ticks = 0;
    private boolean place;
    private boolean swung;

    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        if (this.swung && !this.playerData.isSwingDigging() && !this.place && this.playerData.isSurvival() && this.playerData.getLastAttackTicks() < 1200) {
            if (this.ticks < 8) {
                this.intervals.addFirst(this.ticks);
                if (this.intervals.size() >= 40) {
                    double value = MathUtil.deviation(this.intervals);
                    double value2 = (0.325 - value) * 2.0 + 0.675;
                    if (value2 > 0.0) {
                        this.handleViolation(String.format("D: %s", value), value2);
                    } else {
                        this.decreaseVL(-value2);
                    }

                    this.intervals.clear();
                }
            }

            this.ticks = 0;
        }

        this.place = false;
        this.swung = false;
        this.ticks++;
    }

    @Override
    public void handle(VPacketPlayInArmAnimation<?> packet) {
        this.swung = true;
    }

    @Override
    public void handle(VPacketPlayInBlockPlace<?> packet) {
        this.place = true;
    }
}
