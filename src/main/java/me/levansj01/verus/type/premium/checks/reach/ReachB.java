package me.levansj01.verus.type.premium.checks.reach;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInUseEntity;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(
    type = CheckType.REACH,
    subType = "B",
    friendlyName = "Reach",
    version = CheckVersion.RELEASE,
    unsupportedAtleast = ClientVersion.V1_9,
    maxViolations = 4
)
public class ReachB extends Check implements PacketHandler {
    public static final double MAX_YL = -0.1001001001001001;
    public static final double MAX_X = 0.4004004004004004;
    public static final double V = 0.999;
    public static final double MAX_YH = 1.901901901901902;

    @Override
    public void handle(VPacketPlayInUseEntity<?> packet) {
        if (packet.getAction() == VPacketPlayInUseEntity.EntityUseAction.INTERACT_AT) {
            double bodyX = packet.getBodyX();
            double bodyY = packet.getBodyY();
            double bodyZ = packet.getBodyZ();
            if ((Math.abs(bodyX) > 0.4004004004004004 || Math.abs(bodyZ) > 0.4004004004004004 || bodyY > 1.901901901901902 || bodyY < -0.1001001001001001)
                && packet.isPlayer()
                && (this.playerData.getTracker() == null || this.playerData.getTracker().get(packet.getId()) != null)) {
                this.handleViolation(String.format("X: %s Y: %s Z: %s", bodyX, bodyY, bodyZ), 1.0, true);
            }
        }
    }
}
