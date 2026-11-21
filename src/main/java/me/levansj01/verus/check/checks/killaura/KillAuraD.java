package me.levansj01.verus.check.checks.killaura;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.util.java.BasicDeque;
import me.levansj01.verus.util.java.CappedQueue;
import me.levansj01.verus.util.java.MathUtil;

@CheckInfo(type = CheckType.KILL_AURA, subType = "D", friendlyName = "KillAura", version = CheckVersion.RELEASE, minViolations = -1.0, logData = true)
public class KillAuraD extends Check implements PacketHandler {
    private final BasicDeque<Double> pitchList = new CappedQueue(20);
    private Float lastPitch = null;

    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        if (packet.isLook()) {
            if (this.lastPitch != null && this.playerData.getLastAttackTicks() <= 3 && this.playerData.getLastAttacked() != null) {
                double deltaPitch = Math.abs(packet.getPitch() - this.lastPitch);
                this.pitchList.addFirst(deltaPitch);
                if (this.pitchList.size() >= 20) {
                    double value = MathUtil.deviation(this.pitchList);
                    double value2 = MathUtil.average(this.pitchList);
                    boolean flag = value2 > 17.5 && value > 15.0 || value2 > 22.5 && value > 12.5;
                    if (flag) {
                        this.handleViolation(String.format("AVG: %s DEV: %s", value2, value));
                    } else {
                        this.decreaseVL(0.025);
                    }

                    this.pitchList.clear();
                }
            }

            this.lastPitch = packet.getPitch();
        }
    }
}
