package me.levansj01.verus.type.premium.checks.autoclicker;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInArmAnimation;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockDig;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.verus2.data.player.TickerMap;
import me.levansj01.verus.verus2.data.player.TickerType;

@CheckInfo(
    type = CheckType.AUTO_CLICKER,
    subType = "B",
    friendlyName = "Auto Clicker",
    version = CheckVersion.RELEASE,
    unsupportedAtleast = ClientVersion.V1_9,
    minViolations = -5.0,
    maxViolations = 20,
    logData = true,
    butterfly = true
)
public class AutoClickerB extends Check implements PacketHandler {
    private boolean swing;
    private int stage;
    private boolean first;
    private int ticks;

    @Override
    public void handle(VPacketPlayInArmAnimation<?> packet) {
        if (this.stage == 1) {
            this.stage = 2;
        } else if (this.stage == 3 || this.stage == 0) {
            this.swing = true;
        }
    }

    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        if (this.stage == 2) {
            this.stage = 3;
        }

        this.swing = false;
        this.ticks++;
    }

    @Override
    public void handle(VPacketPlayInBlockPlace<?> packet) {
        this.ticks = 0;
    }

    @Override
    public void handle(VPacketPlayInBlockDig<?> packet) {
        if (packet.getType() == VPacketPlayInBlockDig.PlayerDigType.START_DESTROY_BLOCK) {
            if (this.stage == 0 && this.ticks > 1) {
                this.stage = 1;
                this.first = this.swing;
            } else {
                this.stage = 0;
            }
        } else if (packet.getType() == VPacketPlayInBlockDig.PlayerDigType.ABORT_DESTROY_BLOCK) {
            if (this.stage == 3) {
                if (this.swing) {
                    TickerMap tickerMap = this.playerData.getTickerMap();
                    int value = tickerMap.get(TickerType.DOUBLE_SWING) - tickerMap.get(TickerType.LEGIT_SWING);
                    if (this.ticks > 1 && value > 20) {
                        this.handleViolation(String.valueOf(value), 1.0, true);
                    }
                } else if (this.first) {
                    this.decreaseVL(0.3);
                }
            }

            this.stage = 0;
        }
    }
}
