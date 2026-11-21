package me.levansj01.verus.type.premium.checks.killaura;

import java.util.Objects;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInUseEntity;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(
    type = CheckType.KILL_AURA,
    subType = "O",
    friendlyName = "MultiAura",
    version = CheckVersion.RELEASE,
    maxViolations = 5,
    unsupportedAtleast = ClientVersion.V1_9
)
public class KillAuraO extends Check implements PacketHandler {
    private int entityId;
    private boolean ticked;

    @Override
    public void handle(VPacketPlayInUseEntity<?> packet) {
        if (packet.getAction().equals(VPacketPlayInUseEntity.EntityUseAction.ATTACK) && this.playerData.isSurvival() && this.playerData.getTotalTicks() > 200) {
            if (!this.ticked && !Objects.equals(this.entityId, packet.getId())) {
                this.handleViolation();
            } else {
                this.decreaseVL(0.25);
            }

            this.entityId = packet.getId();
            this.ticked = false;
        }
    }

    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        this.ticked = true;
        this.decreaseVL(0.025);
    }
}
