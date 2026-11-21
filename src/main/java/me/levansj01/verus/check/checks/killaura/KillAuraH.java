package me.levansj01.verus.check.checks.killaura;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInUseEntity;

@CheckInfo(type = CheckType.KILL_AURA, subType = "H", friendlyName = "KillAura", version = CheckVersion.RELEASE, minViolations = -1.0, maxViolations = 5)
public class KillAuraH extends Check implements PacketHandler {
    private boolean attack;

    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        if (this.attack) {
            if (this.playerData.getTimestamp() - this.playerData.getLastLastFlying() > 40L) {
                this.handleViolation("", 0.5);
            } else {
                this.decreaseVL(0.05);
            }

            this.attack = false;
        }
    }

    @Override
    public void handle(VPacketPlayInUseEntity<?> packet) {
        if (packet.getAction().isAttack()) {
            long lastFlying = this.playerData.getLastFlying();
            if (this.playerData.getTimestamp() - lastFlying < 5L) {
                this.attack = true;
            } else {
                this.decreaseVL(0.1);
            }
        }
    }
}
