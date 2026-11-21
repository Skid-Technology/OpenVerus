package me.levansj01.verus.type.premium.checks.inventory;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInWindowClick;

@CheckInfo(type = CheckType.INVENTORY, subType = "C", friendlyName = "Inventory", version = CheckVersion.RELEASE, maxViolations = 5, schem = true)
public class InventoryC extends Check implements PacketHandler {
    private int ticks = 0;

    @Override
    public void handle(VPacketPlayInWindowClick<?> packet) {
        if (packet.getSlot() != -999 && !this.playerData.isTeleportingV2() && this.playerData.isSpawned() && this.playerData.isMoved() && this.ticks > 3) {
            this.handleViolation(String.format("T: %s", this.ticks));
            this.run(this.playerData::closeInventory);
        }
    }

    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        if (this.playerData.isSprinting(true) && this.playerData.isInventoryOpen()) {
            this.ticks++;
        } else {
            this.ticks = 0;
            this.decreaseVL(0.001);
        }
    }
}
