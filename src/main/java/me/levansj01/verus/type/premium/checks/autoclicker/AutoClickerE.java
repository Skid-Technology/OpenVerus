package me.levansj01.verus.type.premium.checks.autoclicker;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.api.wrapper.BlockPosition;
import me.levansj01.verus.compat.api.wrapper.Direction;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockDig;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.util.java.BasicDeque;
import me.levansj01.verus.util.java.CappedQueue;
import me.levansj01.verus.util.java.MathUtil;

@CheckInfo(type = CheckType.AUTO_CLICKER, subType = "E", friendlyName = "Auto Clicker", version = CheckVersion.RELEASE, minViolations = -1.0, logData = true)
public class AutoClickerE extends Check implements PacketHandler {
    private final BasicDeque<Integer> tickList;
    private int ticks = 0;

    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        if (this.playerData.isDigging() && !this.playerData.isAbortedDigging()) {
            this.ticks++;
        }
    }

    @Override
    public void handle(VPacketPlayInBlockDig<?> packet) {
        if (packet.getType() == VPacketPlayInBlockDig.PlayerDigType.START_DESTROY_BLOCK) {
            this.ticks = 0;
        } else if (packet.getType() == VPacketPlayInBlockDig.PlayerDigType.ABORT_DESTROY_BLOCK) {
            BlockPosition blockPosition = packet.getBlockPosition();
            Direction face = packet.getFace();
            if (blockPosition.equals(this.playerData.getDiggingBlock()) && !face.equals(this.playerData.getDiggingBlockFace())) {
                this.tickList.addFirst(this.ticks);
                if (this.tickList.size() > 40) {
                    double value = MathUtil.deviation(this.tickList);
                    if (value < 0.325) {
                        this.handleViolation(String.format("D: %s", value), 1.325 - value, true);
                    } else {
                        this.decreaseVL(0.25);
                    }

                    this.tickList.clear();
                }
            }

            this.ticks = 0;
        }
    }

    public AutoClickerE() {
        this.tickList = new CappedQueue(41);
    }
}
