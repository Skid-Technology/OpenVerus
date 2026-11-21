package me.levansj01.verus.type.premium.checks.autoclicker;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInArmAnimation;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockDig;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.util.java.BasicDeque;
import me.levansj01.verus.util.java.CappedQueue;
import me.levansj01.verus.util.java.MathUtil;

@CheckInfo(
    type = CheckType.AUTO_CLICKER,
    subType = "D",
    friendlyName = "Auto Clicker",
    version = CheckVersion.RELEASE,
    minViolations = -1.0,
    maxViolations = 10,
    logData = true
)
public class AutoClickerD extends Check implements PacketHandler {
    private final BasicDeque<Integer> averageTicks = new CappedQueue(50);
    private int done;
    private boolean abortedDigging;
    private boolean digging;
    private int ticks = 0;
    private int lastTicks;
    private boolean swung;

    public AutoClickerD() {
        this.done = 0;
        this.lastTicks = 0;
    }

    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        if (this.swung && !this.digging && this.playerData.getLastAttackTicks() < 1200) {
            if (this.ticks < 7) {
                this.averageTicks.addFirst(this.ticks);
            }

            if (this.averageTicks.size() > 40) {
                double value = MathUtil.average(this.averageTicks);
                if (value < 2.5) {
                    if (this.ticks > 3 && this.ticks < 20 && this.lastTicks < 20) {
                        this.violations = this.violations - Math.min(this.violations, 0.25);
                        this.done = 0;
                    } else if (this.done++ > 600.0 / (value * 1.5)) {
                        this.handleViolation(String.format("A: %s", value), 1.0);
                        this.done = 0;
                    }
                } else {
                    this.done = 0;
                }
            }

            this.lastTicks = this.ticks;
            this.ticks = 0;
        }

        if (this.abortedDigging) {
            this.digging = false;
            this.abortedDigging = false;
        }

        this.swung = false;
        this.ticks++;
    }

    @Override
    public void handle(VPacketPlayInArmAnimation<?> packet) {
        this.swung = true;
    }

    @Override
    public void handle(VPacketPlayInBlockDig<?> packet) {
        if (packet.getType() == VPacketPlayInBlockDig.PlayerDigType.START_DESTROY_BLOCK) {
            this.digging = true;
            this.abortedDigging = false;
        } else if (packet.getType() == VPacketPlayInBlockDig.PlayerDigType.ABORT_DESTROY_BLOCK) {
            this.abortedDigging = true;
        }
    }
}
