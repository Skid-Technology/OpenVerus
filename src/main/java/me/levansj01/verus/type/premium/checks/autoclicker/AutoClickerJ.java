package me.levansj01.verus.type.premium.checks.autoclicker;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.api.wrapper.BlockPosition;
import me.levansj01.verus.compat.api.wrapper.Direction;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockDig;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;

@CheckInfo(type = CheckType.AUTO_CLICKER, subType = "J", friendlyName = "Auto Clicker", version = CheckVersion.RELEASE, minViolations = -1.0)
public class AutoClickerJ extends Check implements PacketHandler {
    private int placeTicks;
    private int lastticks;
    private double value;
    private int threshold;
    private int ticks = 0;
    private int abortTicks = 0;

    @Override
    public void handle(VPacketPlayInBlockDig<?> packet) {
        if (this.placeTicks > 1) {
            if (packet.getType() == VPacketPlayInBlockDig.PlayerDigType.START_DESTROY_BLOCK) {
                this.ticks = 0;
            } else if (packet.getType() == VPacketPlayInBlockDig.PlayerDigType.ABORT_DESTROY_BLOCK) {
                BlockPosition blockPosition = packet.getBlockPosition();
                Direction face = packet.getFace();
                if (blockPosition.equals(this.playerData.getDiggingBlock()) && !face.equals(this.playerData.getDiggingBlockFace()) && !this.playerData.isPlacing()) {
                    this.decreaseVL(0.001);
                    if (this.ticks == this.lastticks && this.abortTicks < 4) {
                        this.value = this.value + (0.3 + this.threshold * 0.2);
                        if (this.value > 20.0) {
                            this.handleViolation();
                            this.value = 0.0;
                        }

                        this.threshold++;
                    } else {
                        this.value = this.value - Math.min(this.value, 1.0);
                        this.threshold = 0;
                    }

                    this.lastticks = this.ticks;
                }

                this.abortTicks = 0;
                this.ticks = 0;
            }
        }
    }

    public AutoClickerJ() {
        this.lastticks = -1;
        this.threshold = 0;
        this.placeTicks = 0;
        this.value = 0.0;
    }

    @Override
    public void handle(VPacketPlayInBlockPlace<?> packet) {
        this.placeTicks = 0;
    }

    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        if (this.playerData.isDigging() && !this.playerData.isAbortedDigging()) {
            this.ticks++;
        }

        this.abortTicks++;
    }
}
