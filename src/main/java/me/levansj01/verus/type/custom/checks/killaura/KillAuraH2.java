package me.levansj01.verus.type.custom.checks.killaura;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInArmAnimation;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInUseEntity;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(type = CheckType.KILL_AURA, subType = "H2", friendlyName = "KillAura", version = CheckVersion.RELEASE, minViolations = -4.0)
public class KillAuraH2 extends Check implements PacketHandler {
    private Long lastFlying;
    private Long arm;
    private Long attack;

    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        if (this.lastFlying != null && this.attack != null && this.arm != null && Math.abs(this.attack - this.arm) <= 2L) {
            if (!this.playerData.getVersion().before(ClientVersion.V1_9) && this.playerData.getTimestamp() - this.lastFlying > 60L) {
                return;
            }

            if (this.playerData.getTimestamp() - this.attack > 40L && this.playerData.isSurvival()) {
                this.handleViolation(String.format("F: %s DIFF: %s", this.playerData.getTimestamp() - this.arm, this.attack - this.arm));
            } else {
                this.decreaseVL(1.2);
            }
        }

        this.lastFlying = this.playerData.getLastFlying();
        this.arm = this.attack = null;
    }

    @Override
    public void handle(VPacketPlayInArmAnimation<?> packet) {
        this.arm = this.playerData.getTimestamp();
    }

    @Override
    public void handle(VPacketPlayInUseEntity<?> packet) {
        if (packet.getAction() == VPacketPlayInUseEntity.EntityUseAction.ATTACK) {
            this.attack = this.playerData.getTimestamp();
        }
    }
}
