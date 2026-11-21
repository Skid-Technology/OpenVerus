package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockDig;

@CheckInfo(type = CheckType.BAD_PACKETS, subType = "D", friendlyName = "KillAura", version = CheckVersion.RELEASE, maxViolations = 1)
public class BadPacketsD extends Check implements PacketHandler {
    @Override
    public void handle(VPacketPlayInBlockDig<?> packet) {
        if (packet.getType() == VPacketPlayInBlockDig.PlayerDigType.RELEASE_USE_ITEM) {
            switch (packet.getFace()) {
                case UP:
                case NORTH:
                case EAST:
                case WEST:
                    this.handleViolation(() -> String.format("F: %s", packet.getFace().name()), 1.0, this.violations < 2.0);
            }
        }
    }
}
