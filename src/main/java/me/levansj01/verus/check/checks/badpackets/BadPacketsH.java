package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(
    type = CheckType.BAD_PACKETS,
    subType = "H",
    friendlyName = "Invalid Direction",
    version = CheckVersion.RELEASE,
    minViolations = -1.0,
    maxViolations = 15,
    unsupportedAtleast = ClientVersion.V1_9
)
public class BadPacketsH extends Check implements PacketHandler {
    private Float oldYaw;
    private Float lastYaw = null;
    private Float lastPitch = null;
    private Float oldPitch;

    public BadPacketsH() {
        this.oldYaw = null;
        this.oldPitch = null;
    }

    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        if (packet.isLook()) {
            float yaw = packet.getYaw();
            float pitch = packet.getPitch();
            if (!this.playerData.isTeleportingV2()
                && !this.playerData.isTeleporting(5)
                && !this.playerData.hasRecentTeleport(10, this.playerData.getLastLocation(), this.playerData.getLocation())
                && !this.playerData.isVehicle()
                && !this.playerData.hasFast(this.playerData.getTimestamp() - this.playerData.getTransactionPing())
                && this.playerData.isSpawned()
                && !this.playerData.isSuffocating()) {
                if (this.oldYaw == null || this.oldYaw != yaw || this.oldPitch == null || this.oldPitch != pitch) {
                    this.oldPitch = this.oldYaw = null;
                    if (this.lastYaw != null && this.lastPitch != null && this.lastYaw == yaw && this.lastPitch == pitch) {
                        this.handleViolation(() -> String.format("%f %f", this.lastYaw, this.lastPitch));
                    } else {
                        this.decreaseVL(0.05);
                    }
                }

                this.lastYaw = yaw;
                this.lastPitch = pitch;
            } else {
                this.oldYaw = yaw;
                this.oldPitch = pitch;
            }
        }
    }
}
