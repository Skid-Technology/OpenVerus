package me.levansj01.verus.check.checks.crasher;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;

@CheckInfo(type = CheckType.SERVER_CRASHER, subType = "G", friendlyName = "Server Crasher", version = CheckVersion.EXPERIMENTAL)
public class ServerCrasherG extends Check implements PacketHandler {
    private long lastFlying;
    private int number;

    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        if (this.playerData.getTimestamp() - this.lastFlying > 1000L) {
            this.lastFlying = this.playerData.getTimestamp();
            this.number = 0;
        }

        if (this.number++ > 1200) {
            this.handleViolation();
            if (this.number > 2400) {
                this.playerData.fuckOff();
            }
        }
    }
}
