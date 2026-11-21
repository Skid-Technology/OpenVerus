package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;

@CheckInfo(type = CheckType.BAD_PACKETS, subType = "A", friendlyName = "Timer", version = CheckVersion.RELEASE, maxViolations = 5)
public class BadPacketsA extends Check implements PacketHandler {
    private int packets;

    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        if (!this.playerData.isVehicle() && !packet.isPos()) {
            if (++this.packets > 20) {
                int packets = this.packets;
                this.handleViolation(() -> String.format("P: %s | %s", packets, this.playerData.getMaxPingTicks()), packets > 22 ? 2.0 : 0.2);
            } else {
                this.decreaseVL(1.0E-5);
            }
        } else {
            this.packets = 0;
        }
    }
}
