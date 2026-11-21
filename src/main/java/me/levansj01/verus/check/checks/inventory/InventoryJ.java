package me.levansj01.verus.check.checks.inventory;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInArmAnimation;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInWindowClick;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(type = CheckType.INVENTORY, subType = "J", friendlyName = "KillAura", version = CheckVersion.RELEASE, unsupportedAtleast = ClientVersion.V1_9)
public class InventoryJ extends Check implements PacketHandler {
    private boolean click;
    private boolean swing = false;

    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        if (this.swing && this.click) {
            this.handleViolation("", 1.0, true);
        }

        this.swing = false;
        this.click = false;
    }

    public InventoryJ() {
        this.click = false;
    }

    @Override
    public void handle(VPacketPlayInWindowClick<?> packet) {
        this.click = true;
    }

    @Override
    public void handle(VPacketPlayInArmAnimation<?> packet) {
        this.swing = true;
    }
}
