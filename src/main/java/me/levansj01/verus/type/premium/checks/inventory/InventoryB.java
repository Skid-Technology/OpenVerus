package me.levansj01.verus.type.premium.checks.inventory;

import java.util.Objects;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInWindowClick;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(
    type = CheckType.INVENTORY,
    subType = "B",
    friendlyName = "Inventory",
    version = CheckVersion.RELEASE,
    unsupportedAtleast = ClientVersion.V1_9,
    maxViolations = 5
)
public class InventoryB extends Check implements PacketHandler {
    private int slot;
    private int stage = 0;

    @Override
    public void handle(VPacketPlayInWindowClick<?> packet) {
        int slot = packet.getSlot();
        if (!packet.isShiftClick() || packet.getButton() != 0 || this.playerData.hasLag()) {
            this.stage = 0;
        } else if (this.stage == 0 && packet.isItem()) {
            this.stage = 1;
        } else if (this.stage == 1 && !packet.isItem() && Objects.equals(this.slot, slot)) {
            this.handleViolation("", 1.0, true);
            this.stage = 0;
        } else {
            this.stage = 0;
        }

        this.slot = slot;
    }

    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        this.stage = 0;
        this.decreaseVL(0.025);
    }

    public InventoryB() {
        this.slot = -999;
    }
}
