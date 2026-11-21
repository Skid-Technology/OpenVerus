package me.levansj01.verus.type.custom.checks.misc;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockDig;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.type.Loader;

@CheckInfo(
    type = CheckType.MISC,
    subType = "F",
    friendlyName = "Nuker",
    minViolations = -1.0,
    version = CheckVersion.RELEASE,
    unsupportedAtleast = ClientVersion.V1_9
)
public class MiscF extends Check implements PacketHandler {
    private boolean sent;

    @Override
    public void handle(VPacketPlayInBlockDig<?> packet) {
        switch (packet.getType()) {
            case START_DESTROY_BLOCK:
                this.sent = false;
                break;
            case STOP_DESTROY_BLOCK:
                if (!this.playerData.isTeleportingV2() && !this.playerData.isTeleporting(2) && this.playerData.isSurvival()) {
                    if (this.sent) {
                        this.handleViolation();
                    } else {
                        this.decreaseVL(0.2);
                        this.sent = true;
                    }
                }
        }
    }

    public MiscF() {
        if (Loader.getUsername().equals("UniversoCraft")) {
            this.setMaxViolation(50);
        }
    }

    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        if (this.playerData.isTeleportingV2() || this.playerData.isTeleporting(2)) {
            this.sent = false;
        }
    }
}
