package me.levansj01.verus.check.checks.speed;

import me.levansj01.verus.check.MovementCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.util.MutableBlockLocation;
import me.levansj01.verus.util.item.MaterialList;
import me.levansj01.verus.util.location.PlayerLocation;
import org.bukkit.Material;
import org.bukkit.World;

@CheckInfo(type = CheckType.SPEED, subType = "B", friendlyName = "FastLadder", version = CheckVersion.RELEASE, maxViolations = 25, logData = true)
public class SpeedB extends MovementCheck {
    private boolean ladder = false;
    private double lastDiff;
    private int threshold;
    private int lastLadderCheck;

    @Override
    public void handle(PlayerLocation location, PlayerLocation location2, long value) {
        if (!(location2.getY() > location.getY())
            || location2.getGround()
            || location.getGround()
            || this.playerData.isFlying()
            || this.playerData.getVelocityTicks() <= this.playerData.getPingTicks() * 2
            || this.playerData.isLevitating()) {
            this.ladder = false;
        } else if (this.ladder) {
            double deltaY = location2.getY() - location.getY();
            if (deltaY > 0.118) {
                if (this.threshold++ > 1 && deltaY >= this.lastDiff * 0.95) {
                    this.threshold = 0;
                    PlayerLocation location3 = location2.clone();
                    World world = this.player.getWorld();
                    this.run(
                        () -> {
                            MutableBlockLocation mutableBlockLocation = new MutableBlockLocation(
                                (int)Math.floor(location3.getX()), (int)Math.floor(location3.getY() + 1.0), (int)Math.floor(location3.getZ())
                            );
                            Material typeWithAPI = NMSManager.getInstance().getTypeWithAPI(this.player, world, mutableBlockLocation);
                            boolean flag = mutableBlockLocation.isWaterLogged(world) || mutableBlockLocation.add(0, -1, 0).isWaterLogged(world) || mutableBlockLocation.add(0, 2, 0).isWaterLogged(world);
                            if (!flag && MaterialList.CLIMBABLE.contains(typeWithAPI)) {
                                this.handleViolation(String.format("D: %s", deltaY));
                            } else {
                                this.ladder = false;
                            }
                        }
                    );
                }
            } else {
                this.threshold = 0;
            }

            this.lastDiff = deltaY;
        } else if (this.lastLadderCheck++ > 9) {
            this.lastLadderCheck = 0;
            PlayerLocation location3 = location2.clone();
            World world = this.player.getWorld();
            this.run(
                () -> {
                    MutableBlockLocation mutableBlockLocation = new MutableBlockLocation(
                        (int)Math.floor(location3.getX()), (int)Math.floor(location3.getY() + 1.0), (int)Math.floor(location3.getZ())
                    );
                    Material typeWithAPI = NMSManager.getInstance().getTypeWithAPI(this.player, world, mutableBlockLocation);
                    if (MaterialList.CLIMBABLE.contains(typeWithAPI)) {
                        this.ladder = true;
                    }
                }
            );
        }
    }
}
