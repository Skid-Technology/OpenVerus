package me.levansj01.verus.check.checks.scaffold;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.util.item.MaterialList;
import org.bukkit.inventory.ItemStack;

@CheckInfo(type = CheckType.SCAFFOLD, subType = "E", friendlyName = "Scaffold", version = CheckVersion.RELEASE, schem = true, maxViolations = 1)
public class ScaffoldE extends Check implements PacketHandler {
    @Override
    public void handle(VPacketPlayInBlockPlace<?> packet) {
        if (packet.getFace() != 255) {
            ItemStack itemStack = packet.getItemStack();
            if (itemStack != null && itemStack.getType() != MaterialList.AIR) {
                float blockX = packet.getBlockX();
                float blockY = packet.getBlockY();
                float blockZ = packet.getBlockZ();
                if (blockX > 1.0F || blockY > 1.0F || blockZ > 1.0F) {
                    this.handleViolation(String.format("X: %.2f Y: %.2f Z: %.2f", blockX, blockY, blockZ));
                }
            }
        }
    }
}
