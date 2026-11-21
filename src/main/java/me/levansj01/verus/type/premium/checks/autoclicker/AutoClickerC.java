package me.levansj01.verus.type.premium.checks.autoclicker;

import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInArmAnimation;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockDig;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.verus2.data.player.TickerMap;
import me.levansj01.verus.verus2.data.player.TickerType;

@CheckInfo(
    type = CheckType.AUTO_CLICKER,
    subType = "C",
    friendlyName = "Auto Clicker",
    version = CheckVersion.RELEASE,
    minViolations = -5.0,
    maxViolations = 30,
    logData = true,
    butterfly = true
)
public class AutoClickerC extends PacketCheck {
    private int stage = 0;
    private int flags = 0;
    private int normal = 0;

    public void run() {
        int diff = this.normal - this.flags;
        if (diff != 1 && this.flags > 0 || this.flags > 5) {
            if (diff <= 2 && diff >= -2) {
                this.decreaseVL(0.5);
            } else {
                TickerMap tickerMap = this.playerData.getTickerMap();
                int value = tickerMap.get(TickerType.DOUBLE_SWING) - tickerMap.get(TickerType.LEGIT_SWING);
                if (value > 20) {
                    this.handleViolation(String.format("D: %s", diff), 0.75, true);
                }
            }
        }
    }

    @Override
    public void handle(VPacket packet, long value) {
        if (this.stage == 0) {
            if (packet instanceof VPacketPlayInArmAnimation) {
                this.stage = 1;
            }
        } else if (this.stage == 1) {
            if (packet instanceof VPacketPlayInBlockDig) {
                this.stage = 2;
            } else {
                this.stage = 0;
            }
        } else if (this.stage == 2) {
            if (packet instanceof VPacketPlayInBlockDig && ((VPacketPlayInBlockDig)packet).getType() == VPacketPlayInBlockDig.PlayerDigType.ABORT_DESTROY_BLOCK) {
                this.flags++;
                this.run();
                this.stage = 0;
            } else if (packet instanceof VPacketPlayInArmAnimation) {
                this.stage = 3;
            } else {
                this.stage = this.flags = this.normal = 0;
            }
        } else if (this.stage == 3) {
            if (packet instanceof VPacketPlayInFlying) {
                this.stage = 4;
            } else {
                this.stage = this.flags = this.normal = 0;
            }
        } else if (this.stage == 4) {
            if (packet instanceof VPacketPlayInBlockDig && ((VPacketPlayInBlockDig)packet).getType() == VPacketPlayInBlockDig.PlayerDigType.ABORT_DESTROY_BLOCK) {
                this.normal++;
                this.run();
                this.stage = 0;
            } else {
                this.stage = this.flags = this.normal = 0;
            }
        }
    }
}
