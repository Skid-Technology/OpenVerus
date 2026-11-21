package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInHeldItemSlot;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(
    type = CheckType.BAD_PACKETS,
    subType = "U",
    friendlyName = "Invalid Inventory",
    version = CheckVersion.RELEASE,
    minViolations = -1.5,
    maxViolations = 5,
    schem = true
)
public class BadPacketsU extends Check implements PacketHandler {
    private Long swapped;
    private Long lastFlying;

    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        if (this.lastFlying != null && this.swapped != null) {
            long timestamp = this.playerData.getTimestamp();
            if (this.playerData.getVersion().after(ClientVersion.V1_8) && timestamp - this.lastFlying > 60L) {
                return;
            }

            if (timestamp - this.playerData.getLastLastFlying() > 45L && timestamp - this.swapped > 40L) {
                this.handleViolation(String.format("S: %s F: %s", timestamp - this.swapped, timestamp - this.lastFlying), 0.5);
            } else {
                this.decreaseVL(0.05);
            }
        }

        this.lastFlying = this.playerData.getLastFlying();
        this.swapped = null;
    }

    @Override
    public void handle(VPacketPlayInHeldItemSlot<?> packet) {
        if (this.playerData.isSurvival()) {
            long timestamp = this.playerData.getTimestamp();
            if (timestamp - this.playerData.getLastFlying() < 5L) {
                this.swapped = timestamp;
            } else {
                this.decreaseVL(0.1);
            }
        }
    }
}
