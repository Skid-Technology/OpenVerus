package me.levansj01.verus.type.custom.checks.badpackets;

import java.util.function.Function;
import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.Transactionable;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.verus2.data.player.TickerType;

public abstract class TransactionCheck extends PacketCheck {
    private boolean sent;
    private final Function<VPacket<?>, Boolean> packetFunction;
    private boolean reduce;

    @Override
    public void handle(VPacket<?> packet, long value) {
        if (packet instanceof VPacketPlayInFlying) {
            if (this.reduce && this.sent) {
                this.decreaseVL(0.1);
            }

            this.sent = this.reduce = false;
        } else if (packet instanceof Transactionable) {
            if (this.sent && !this.player.isDead() && this.playerData.isSurvival()) {
                int value2 = this.playerData.getTickerMap().get(TickerType.TELEPORT);
                if (value2 > 1) {
                    this.handleViolation();
                }
            }

            this.reduce = true;
        } else if (this.packetFunction.apply(packet)) {
            this.sent = true;
        }
    }

    public TransactionCheck(Function<VPacket<?>, Boolean> packetFunction) {
        this.packetFunction = packetFunction;
    }
}
