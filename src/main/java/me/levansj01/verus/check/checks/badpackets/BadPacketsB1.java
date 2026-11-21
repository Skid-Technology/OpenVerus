package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInSteerVehicle;

@CheckInfo(friendlyName = "InvalidSteer", type = CheckType.BAD_PACKETS, subType = "B1", version = CheckVersion.RELEASE, maxViolations = 5, minViolations = -2.0)
public class BadPacketsB1 extends Check implements PacketHandler {
    @Override
    public void handle(VPacketPlayInSteerVehicle<?> packet) {
        if (Math.abs(packet.getForward()) > 0.98F || Math.abs(packet.getStrafe()) > 0.98F) {
            this.handleViolation();
        }
    }
}
