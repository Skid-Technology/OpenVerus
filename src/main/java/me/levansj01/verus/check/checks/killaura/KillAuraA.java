package me.levansj01.verus.check.checks.killaura;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.data.transaction.tracker.PacketLocation;
import me.levansj01.verus.util.java.AtomicCappedQueue;
import me.levansj01.verus.util.java.MathUtil;
import me.levansj01.verus.util.location.PlayerLocation;

@CheckInfo(type = CheckType.KILL_AURA, subType = "A", friendlyName = "Aimbot", version = CheckVersion.RELEASE, minViolations = -2.0, maxViolations = 6)
public class KillAuraA extends Check implements PacketHandler {
    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        if (packet.isLook()
            && !this.playerData.isTeleporting(5)
            && this.playerData.isSpawned()
            && this.playerData.getLastAttackTicks() <= 20
            && this.playerData.getLastAttacked() != null) {
            PlayerLocation location = this.playerData.getLocation();
            AtomicCappedQueue atomicCappedQueue = this.playerData.getRecentMoveMap().get(this.playerData.getLastAttackedId());
            if (atomicCappedQueue != null) {
                int transactionPing = this.playerData.getTransactionPing();
                List list = atomicCappedQueue.toList();
                List<PacketLocation> list2 = new ArrayList<>();
                long diff = this.playerData.getTimestamp() - 125L - transactionPing;
                Iterator iterator = list.iterator();
                PacketLocation next = (PacketLocation)iterator.next();

                while (iterator.hasNext()) {
                    PacketLocation next2 = (PacketLocation)iterator.next();
                    long diff2 = next2.getTimestamp() - diff;
                    if (diff2 > 0L) {
                        list2.add(next);
                        if (diff2 > 100L) {
                            next = next2;
                            break;
                        }
                    }

                    next = next2;
                }

                if (list2.isEmpty()) {
                    list2.add(next);
                }

                PacketLocation location2 = MathUtil.min(list2, PacketLocation::getTimestamp);
                int value = list.indexOf(location2);
                if (value > 0) {
                    list2.add((PacketLocation)list.get(value - 1));
                }

                for (PacketLocation location3 : list2) {
                    float[] rotationFromPosition = MathUtil.getRotationFromPosition(location, location3);
                    double distanceBetweenAngles = MathUtil.getDistanceBetweenAngles(location.getYaw(), rotationFromPosition[0]);
                    double distanceBetweenAngles2 = MathUtil.getDistanceBetweenAngles(location.getPitch(), rotationFromPosition[1]);
                    if (distanceBetweenAngles == 0.0 || distanceBetweenAngles2 == 0.0) {
                        if (distanceBetweenAngles == 0.0) {
                            this.handleViolation("Yaw", 1.0);
                        }

                        return;
                    }
                }
            }

            this.decreaseVL(1.0E-4);
        }
    }
}
