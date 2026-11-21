package me.levansj01.verus.type.custom.checks.inventory;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInWindowClick;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(type = CheckType.INVENTORY, subType = "L", friendlyName = "Inventory", version = CheckVersion.RELEASE, unsupportedAtleast = ClientVersion.V1_9)
public class InventoryL extends Check implements PacketHandler {
    private int slot;
    private int button;
    private boolean pressed = false;

    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        this.pressed = false;
    }

    @Override
    public void handle(VPacketPlayInWindowClick<?> packet) {
        int slot = packet.getSlot();
        int button = packet.getButton();
        if (packet.getMode() == 2 && packet.getWindow() != 0) {
            if (this.pressed && this.slot != slot && this.button == button) {
                this.handleViolation();
                this.run(this.playerData::closeInventory);
            } else {
                this.decreaseVL(0.025);
            }

            this.pressed = true;
        }

        this.slot = slot;
        this.button = button;
    }

    public InventoryL() {
        this.button = -1;
        this.slot = -1;
    }
}
