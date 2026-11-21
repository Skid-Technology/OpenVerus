package me.levansj01.verus.type.custom.checks.inventory;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInClientCommand;
import me.levansj01.verus.compat.packets.VPacketPlayInCloseWindow;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInWindowClick;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(
    type = CheckType.INVENTORY,
    subType = "F",
    friendlyName = "Inventory Cleaner",
    version = CheckVersion.RELEASE,
    unsupportedAtleast = ClientVersion.V1_9,
    schem = true,
    minViolations = -2.0,
    maxViolations = 10
)
public class InventoryF extends Check implements PacketHandler {
    private boolean open;
    private boolean was;

    @Override
    public void handle(VPacketPlayInWindowClick<?> packet) {
        this.was = this.open;
        if (this.open) {
            this.handleViolation(() -> "Click", 1.0, true);
            this.open = false;
        } else {
            this.decreaseVL(0.25);
        }
    }

    @Override
    public void handle(VPacketPlayInCloseWindow<?> packet) {
        if (this.was) {
            this.handleViolation(() -> "Close", 1.0, true);
        } else {
            this.decreaseVL(0.25);
        }
    }

    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        this.open = this.was = false;
    }

    @Override
    public void handle(VPacketPlayInClientCommand<?> packet) {
        if (packet.getCommand() == VPacketPlayInClientCommand.ClientCommand.OPEN_INVENTORY_ACHIEVEMENT) {
            this.open = true;
        }
    }
}
