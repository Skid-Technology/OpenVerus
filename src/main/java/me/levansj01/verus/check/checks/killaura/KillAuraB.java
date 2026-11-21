package me.levansj01.verus.check.checks.killaura;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInUseEntity;

@CheckInfo(type = CheckType.KILL_AURA, subType = "B", friendlyName = "KillAura", version = CheckVersion.RELEASE, minViolations = -1.0, maxViolations = 5)
public class KillAuraB extends Check implements PacketHandler {
    private int ticks;
    private int totalTicks;
    private int invalidTicks;
    private int lastTicks;

    @Override
    public void handle(VPacketPlayInUseEntity<?> packet) {
        if (packet.getAction().isAttack()) {
            if (this.ticks <= 8) {
                if (this.lastTicks == this.ticks) {
                    this.invalidTicks++;
                }

                if (this.totalTicks++ >= 25) {
                    if (this.invalidTicks > 22) {
                        this.handleViolation(String.format("%s/%s", this.invalidTicks, this.totalTicks), 1.0 + (this.invalidTicks - 22) / 6.0);
                    } else {
                        this.violations = this.violations - Math.min(this.violations + 1.0, 1.0);
                    }

                    this.invalidTicks = this.totalTicks = 0;
                }

                this.lastTicks = this.ticks;
            }

            this.ticks = 0;
        }
    }

    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        this.ticks++;
    }
}
