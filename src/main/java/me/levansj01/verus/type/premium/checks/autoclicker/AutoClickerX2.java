package me.levansj01.verus.type.premium.checks.autoclicker;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInArmAnimation;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.storage.StorageEngine;

@CheckInfo(
    type = CheckType.AUTO_CLICKER,
    subType = "X2",
    friendlyName = "Auto Clicker",
    version = CheckVersion.RELEASE,
    minViolations = -1.0,
    unsupportedAtleast = ClientVersion.V1_9,
    logData = true
)
public class AutoClickerX2 extends Check implements PacketHandler {
    private long lastFlying;
    private boolean place;
    private int done;
    private int swings;

    @Override
    public void handle(VPacketPlayInBlockPlace<?> packet) {
        this.place = true;
    }

    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        if (this.done++ >= 19) {
            int maxClicksPerSecond = StorageEngine.getInstance().getVerusConfig().getMaxClicksPerSecond();
            if (this.swings > maxClicksPerSecond) {
                this.handleViolation(String.format("S: %s", this.swings), (this.swings - maxClicksPerSecond) / 2.0);
            } else {
                this.decreaseVL(0.1);
            }

            this.done = this.swings = 0;
        }

        if (this.place) {
            this.place = false;
        }

        this.lastFlying = this.playerData.getTimestamp();
    }

    @Override
    public void handle(VPacketPlayInArmAnimation<?> packet) {
        if (!this.playerData.isSwingDigging() && !this.place) {
            if (this.playerData.getVersion().before(ClientVersion.V1_8) && this.playerData.getTimestamp() - this.lastFlying > 110L) {
                return;
            }

            this.swings++;
        }
    }
}
