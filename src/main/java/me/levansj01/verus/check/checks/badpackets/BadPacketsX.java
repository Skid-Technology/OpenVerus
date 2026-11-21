package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockDig;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(type = CheckType.BAD_PACKETS, subType = "X", friendlyName = "FastBreak", version = CheckVersion.RELEASE, minViolations = -0.5, maxViolations = 10)
public class BadPacketsX extends Check implements PacketHandler {
    private int stage = 0;
    private int ticks = 0;

    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        if (this.stage == 2) {
            this.decreaseVL(0.01);
        }

        if (this.stage == 1) {
            this.ticks++;
            this.stage = 2;
        } else {
            this.stage = 0;
        }
    }

    @Override
    public void handle(VPacketPlayInBlockDig<?> packet) {
        if (packet.getType() == VPacketPlayInBlockDig.PlayerDigType.STOP_DESTROY_BLOCK) {
            this.stage = 1;
            this.decreaseVL(1.0E-4);
        } else if (packet.getType() == VPacketPlayInBlockDig.PlayerDigType.START_DESTROY_BLOCK) {
            if (this.stage == 2 && (this.ticks != 1 || this.playerData.getVersion().before(ClientVersion.V1_9))) {
                this.handleViolation(String.format("T: %s", this.ticks), 0.25);
            }

            this.stage = 0;
            this.ticks = 0;
        }
    }
}
