package me.levansj01.verus.type.custom.checks.killaura;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInUseEntity;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.type.Loader;

@CheckInfo(
    type = CheckType.KILL_AURA,
    subType = "O2",
    friendlyName = "SwitchAura",
    version = CheckVersion.RELEASE,
    minViolations = -1.0,
    unsupportedAtleast = ClientVersion.V1_9,
    unsupportedServerAtleast = ServerVersion.v1_17_R1
)
public class KillAuraO2 extends Check implements PacketHandler {
    private int ticks;
    private int entityId;

    public KillAuraO2() {
        if (Loader.getUsername().equalsIgnoreCase("UniversoCraft")) {
            this.setMaxViolation(5);
        }
    }

    @Override
    public void handle(VPacketPlayInUseEntity<?> packet) {
        if (packet.getAction().isAttack() && packet.isPlayer()) {
            int id = packet.getId();
            if (this.entityId == id) {
                this.decreaseVL(0.05);
            } else {
                if (this.ticks <= 1 && this.playerData.isSurvival()) {
                    this.handleViolation();
                } else {
                    this.decreaseVL(0.05);
                }

                this.ticks = 0;
            }

            this.entityId = id;
        }
    }

    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        this.ticks++;
    }
}
