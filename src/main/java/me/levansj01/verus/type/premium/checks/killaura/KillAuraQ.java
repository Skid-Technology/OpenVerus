package me.levansj01.verus.type.premium.checks.killaura;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import me.levansj01.verus.check.AimCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.data.transaction.tracker.PacketLocation;
import me.levansj01.verus.util.java.AtomicCappedQueue;
import me.levansj01.verus.util.java.MathUtil;
import me.levansj01.verus.util.location.PlayerLocation;

@CheckInfo(type = CheckType.KILL_AURA, subType = "Q", friendlyName = "KillAura", version = CheckVersion.DEVELOPMENT, minViolations = -5.0, maxViolations = 20)
public class KillAuraQ extends AimCheck {
    private final Queue<Double> values = new LinkedList<>();

    @Override
    public void handle(PlayerLocation location, PlayerLocation location2, long value) {
        if (this.playerData.getLastAttackTicks() <= 1
            && this.playerData.getLastAttacked() != null
            && Math.min(Math.abs(location.getYaw() - location2.getYaw()), Math.abs(location.getPitch() - location2.getPitch())) > 0.5) {
            AtomicCappedQueue atomicCappedQueue = this.playerData.getRecentMoveMap().get(this.playerData.getLastAttackedId());
            if (atomicCappedQueue != null && atomicCappedQueue.size() > 5) {
                ArrayList<PacketLocation> list = new ArrayList<>();
                int transactionPing = this.playerData.getTransactionPing();
                long value2 = value - 125L - transactionPing;
                Iterator iterator = atomicCappedQueue.iterator();
                PacketLocation next = (PacketLocation)iterator.next();

                while (iterator.hasNext()) {
                    PacketLocation next2 = (PacketLocation)iterator.next();
                    long diff = next2.getTimestamp() - value2;
                    if (diff > 0L) {
                        list.add(next);
                        if (diff > 75L) {
                            next = next2;
                            break;
                        }
                    }

                    next = next2;
                }

                if (list.isEmpty()) {
                    list.add(next);
                }

                Stream<Double> stream = list.stream().map(entry -> MathUtil.getLuckyAura(location2, entry));
                if (this.values.size() > 10) {
                    double value3 = MathUtil.average(this.values);
                    Double doubleValue = stream.min(Comparator.comparingDouble(entry -> Math.abs(entry - value3) + Math.abs(entry / 2.0))).orElse(null);
                    if (doubleValue != null) {
                        this.values.add(doubleValue);
                        double value4 = MathUtil.deviation(this.values);
                        if (value4 < 0.1 && value3 < 0.3) {
                            this.handleViolation(String.format("AVG: %.2f DEV: %.3f", value3, value4), 1.25 - value4 * 10.0);
                        } else {
                            this.decreaseVL(0.1);
                        }

                        this.values.poll();
                    }
                } else {
                    this.values.add(MathUtil.average(stream.collect(Collectors.toList())));
                }
            }
        }
    }
}
