package me.levansj01.verus.type.premium.checks.scaffold;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.api.wrapper.BlockPosition;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.util.item.MaterialList;
import org.bukkit.Material;
import org.bukkit.World;

@CheckInfo(
    friendlyName = "Scaffold",
    type = CheckType.SCAFFOLD,
    subType = "A",
    version = CheckVersion.RELEASE,
    minViolations = -1.0,
    maxViolations = 5,
    schem = true
)
public class ScaffoldA extends Check implements PacketHandler {
    @Override
    public void handle(VPacketPlayInBlockPlace<?> packet) {
        if (packet.getFace() == 1 && packet.getBlockY() == 0.0) {
            World world = this.player.getWorld();
            BlockPosition position = packet.getPosition();
            this.run(() -> {
                Material typeWithAPI = NMSManager.getInstance().getTypeWithAPI(this.player, world, position);
                if (!MaterialList.FLAT.contains(typeWithAPI)) {
                    this.handleViolation(String.valueOf(typeWithAPI));
                }
            });
        } else {
            this.decreaseVL(0.05);
        }
    }
}
