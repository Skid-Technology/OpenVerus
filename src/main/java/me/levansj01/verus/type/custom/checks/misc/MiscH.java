package me.levansj01.verus.type.custom.checks.misc;

import java.util.Objects;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.api.wrapper.BlockPosition;
import me.levansj01.verus.compat.packets.VPacketPlayInArmAnimation;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockDig;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.util.item.MaterialList;
import org.bukkit.Material;
import org.bukkit.World;

@CheckInfo(
    type = CheckType.MISC,
    subType = "H",
    friendlyName = "FastBreak",
    minViolations = -1.0,
    version = CheckVersion.RELEASE,
    unsupportedAtleast = ClientVersion.V1_9,
    maxViolations = 20
)
public class MiscH extends Check implements PacketHandler {
    private int digging;
    private boolean sentarm;
    private BlockPosition position;
    private float damage;

    @Override
    public void handle(VPacketPlayInBlockDig<?> packet) {
        switch (packet.getType()) {
            case START_DESTROY_BLOCK:
                if (this.sentarm) {
                    this.digging++;
                }

                BlockPosition blockPosition = packet.getBlockPosition();
                World world = this.player.getWorld();
                this.run(() -> {
                    NMSManager nmsManager = NMSManager.getInstance();
                    boolean flag = nmsManager.setOnGround(this.player, true);
                    boolean flag2 = nmsManager.setInWater(this.player, false);
                    this.damage = nmsManager.getDamage(this.player, world, blockPosition);
                    nmsManager.setOnGround(this.player, flag);
                    nmsManager.setInWater(this.player, flag2);
                    this.position = blockPosition;
                });
                break;
            case STOP_DESTROY_BLOCK:
                if (this.playerData.isSurvival() && this.digging > 0) {
                    BlockPosition blockPosition2 = packet.getBlockPosition();
                    if (Objects.equals(blockPosition2, this.position)) {
                        float damage = this.damage;
                        int digging = this.digging;
                        int value = Math.round(1.0F / damage);
                        int value2 = value - digging;
                        World world2 = this.player.getWorld();
                        if (value2 > 0) {
                            this.run(
                                () -> {
                                    if (this.playerData.getVersion() == ClientVersion.V1_7
                                        && NMSManager.getInstance().getServerVersion().after(ServerVersion.v1_7_R4)) {
                                        Material typeWithAPI = NMSManager.getInstance().getTypeWithAPI(this.player, world2, blockPosition2);
                                        if (typeWithAPI == MaterialList.BARRIER) {
                                            return;
                                        }
                                    }

                                    this.player.updateInventory();
                                    this.handleViolation(String.format("D: %.4f T: %s, %s ", damage, digging, value), Math.min(value2 / 2.0, 2.0));
                                }
                            );
                        } else {
                            this.decreaseVL(0.1);
                        }
                    }
                }
            case ABORT_DESTROY_BLOCK:
                this.digging = 0;
        }
    }

    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        if (this.playerData.isDigging()) {
            this.digging++;
        } else {
            this.digging = 0;
        }

        this.sentarm = false;
    }

    @Override
    public void handle(VPacketPlayInArmAnimation<?> packet) {
        this.sentarm = true;
    }
}
