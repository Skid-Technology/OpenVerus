package me.levansj01.verus.check.checks.scaffold;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.api.wrapper.BlockPosition;
import me.levansj01.verus.compat.api.wrapper.Direction;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.item.MaterialList;
import me.levansj01.verus.util.location.PlayerLocation;
import org.bukkit.inventory.ItemStack;

@CheckInfo(
    friendlyName = "Scaffold",
    type = CheckType.SCAFFOLD,
    subType = "G",
    version = CheckVersion.RELEASE,
    schem = true,
    minViolations = -1.0,
    maxViolations = 10
)
public class ScaffoldG extends Check implements PacketHandler {
    private static final Cuboid CUBOID = new Cuboid();

    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        this.decreaseVL(0.05);
    }

    @Override
    public void handle(VPacketPlayInBlockPlace<?> packet) {
        int face = packet.getFace();
        if (face != 255 && !this.playerData.isTeleportingV2()) {
            ItemStack itemStack = packet.getItemStack();
            if (itemStack != null && itemStack.getType() != MaterialList.AIR) {
                BlockPosition position = packet.getPosition();
                if (position != null) {
                    PlayerLocation location = this.playerData.getLocation().clone();
                    location.setY(location.getY() + this.player.getEyeHeight());
                    CUBOID.setValues(location);
                    if (!CUBOID.containsBlock(this.player.getWorld(), position)) {
                        Direction direction = Direction.values()[packet.getFace()];
                        switch (direction) {
                            case EAST:
                                double deltaX = location.getX() - position.getX();
                                if (deltaX < 0.3) {
                                    this.handleViolation(() -> String.format("%s %.2f", direction.name(), deltaX));
                                }
                                break;
                            case SOUTH:
                                double deltaZ = location.getZ() - position.getZ();
                                if (deltaZ < 0.3) {
                                    this.handleViolation(() -> String.format("%s %.2f", direction.name(), deltaZ));
                                }
                                break;
                            case WEST:
                                double deltaX2 = location.getX() - position.getX();
                                if (deltaX2 > 0.7) {
                                    this.handleViolation(() -> String.format("%s %.2f", direction.name(), deltaX2));
                                }
                                break;
                            case NORTH:
                                double deltaZ2 = location.getZ() - position.getZ();
                                if (deltaZ2 > 0.7) {
                                    this.handleViolation(() -> String.format("%s %.2f", direction.name(), deltaZ2));
                                }
                                break;
                            case DOWN:
                                double deltaY = location.getY() - position.getY();
                                if (deltaY > 2.0) {
                                    this.handleViolation(() -> String.format("%s %.2f", direction.name(), deltaY));
                                }
                        }
                    }
                }
            }
        }
    }
}
