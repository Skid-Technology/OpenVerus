package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInUseEntity;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(type = CheckType.BAD_PACKETS, subType = "P", friendlyName = "KillAura", version = CheckVersion.RELEASE, unsupportedAtleast = ClientVersion.V1_9)
public class BadPacketsP extends Check implements PacketHandler {
    private boolean interactAt;
    private boolean interact;
    private boolean attack;

    @Override
    public void handle(VPacketPlayInUseEntity<?> packet) {
        if (packet.getAction().isAttack()) {
            if (!this.attack && (this.interact || this.interactAt)) {
                this.handleViolation("Attack [" + (this.interactAt ? "Interact At " : "") + (this.interact ? "Interact" : "") + "]", 1.0, true);
                this.interact = this.interactAt = false;
            }

            this.attack = true;
        } else if (packet.getAction().isInteract()) {
            this.interact = true;
        } else if (packet.getAction().isInteractAt()) {
            if (!this.interactAt && this.interact) {
                this.handleViolation("Interact", 1.0, true);
                this.interact = false;
            }

            this.interactAt = true;
        }
    }

    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        this.attack = this.interactAt = this.interact = false;
    }
}
