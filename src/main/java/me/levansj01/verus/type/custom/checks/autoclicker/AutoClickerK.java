package me.levansj01.verus.type.custom.checks.autoclicker;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInArmAnimation;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.util.java.BasicDeque;
import me.levansj01.verus.util.java.CappedQueue;
import me.levansj01.verus.util.java.Moment;
import me.levansj01.verus.verus2.data.player.TickerMap;
import me.levansj01.verus.verus2.data.player.TickerType;

@CheckInfo(type = CheckType.AUTO_CLICKER, subType = "K", friendlyName = "Auto Clicker", version = CheckVersion.RELEASE, minViolations = -1.0)
public class AutoClickerK extends Check implements PacketHandler {
    private boolean swung;
    private boolean place;
    private int ticks;
    private final BasicDeque<Byte> intervals = new CappedQueue(200);
    private int lastTicks;

    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        if (this.swung && !this.playerData.isSwingDigging() && !this.place && this.playerData.isSurvival()) {
            if (this.ticks < 9) {
                if (this.lastTicks > 0) {
                    byte b = (byte)(this.ticks - this.lastTicks);
                    this.intervals.addFirst(b);
                    if (this.intervals.size() == 200) {
                        Moment moment = new Moment(this.intervals);
                        if (moment.getKurtosis() < 0.1) {
                            TickerMap tickerMap = this.playerData.getTickerMap();
                            int value = tickerMap.get(TickerType.DOUBLE_SWING) - tickerMap.get(TickerType.LEGIT_SWING);
                            if (value > 20) {
                                this.handleViolation(
                                    String.format("1: %.3f 2: %.3f 3: %.3f 4: %.3f", moment.getMean(), moment.getVariance(), moment.getSkewness(), moment.getKurtosis()),
                                    moment.getKurtosis() < -0.1 ? 2.0 : 1.0,
                                    true
                                );
                            }
                        } else {
                            this.decreaseVL(0.1);
                        }

                        this.intervals.clear();
                    }
                }

                this.lastTicks = this.ticks;
            }

            this.ticks = 0;
        }

        this.place = false;
        this.swung = false;
        this.ticks++;
    }

    @Override
    public void handle(VPacketPlayInBlockPlace<?> packet) {
        this.place = true;
    }

    @Override
    public void handle(VPacketPlayInArmAnimation<?> packet) {
        this.swung = true;
    }

    public AutoClickerK() {
        this.ticks = 0;
        this.lastTicks = 0;
    }
}
