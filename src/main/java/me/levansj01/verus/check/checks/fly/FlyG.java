package me.levansj01.verus.check.checks.fly;

import me.levansj01.verus.alert.manager.AlertManager;
import me.levansj01.verus.check.MovementCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.item.MaterialList;
import me.levansj01.verus.util.location.PlayerLocation;
import org.bukkit.World;

@CheckInfo(type = CheckType.FLY, subType = "G", friendlyName = "Fly", version = CheckVersion.RELEASE, minViolations = -2.5, maxViolations = 20, logData = true)
public class FlyG extends MovementCheck {
    private int jump;
    private boolean ignoring = false;

    public FlyG() {
        this.jump = 0;
    }

    @Override
    public void handle(PlayerLocation location, PlayerLocation location2, long value) {
        if (this.ignoring) {
            if (location2.getGround()) {
                this.ignoring = false;
            }
        } else if (location2.getY() > location.getY()
            && this.playerData.getVelocityTicks() > (this.playerData.getPingTicks() + 1) * 2
            && !this.playerData.isVehicle()
            && !this.playerData.canFly()
            && !this.playerData.isFallFlying()
            && !this.playerData.isGliding()
            && !this.playerData.isRiptiding()
            && !this.playerData.isHooked()
            && !this.playerData.isTeleportingV2()
            && this.playerData.isSurvival()
            && this.playerData.isSpawned()
            && !this.playerData.isLevitating()
            && !this.playerData.hasPlacedBlock(true)
            && !this.playerData.hadJumpBoost()
            && !this.playerData.hadLevitation()) {
            double deltaY = location2.getY() - Math.max(0.0, location.getY());
            int value2 = (Integer)this.playerData.getJumpLevel().get();
            double value3 = 0.41999998688699;
            double value4 = Math.max(!location2.getGround() ? value3 : 0.5, value3 + Math.max(this.jump, value2) * 0.2);
            double value5 = deltaY - value4;
            if (location.getGround()) {
                this.jump = value2;
            }

            if (this.playerData.getVersion() != ClientVersion.V1_7 && location2.getGround() && location.getGround() && (value5 == 0.0625 || value5 == 0.100000024F)) {
                return;
            }

            if (deltaY > value4 && Math.abs(deltaY - 0.5) > 1.0E-12) {
                if (deltaY > 100000.0 || this.violations > 300.0) {
                    AlertManager.getInstance().handleBan(this.playerData, this, false);
                    this.playerData.fuckOff();
                }

                World world = this.player.getWorld();
                Cuboid cuboid = new Cuboid(location).move(0.0, -1.5, 0.0).expand(0.5, 2.0, 0.5);
                this.run(
                    () -> {
                        if (cuboid.checkBlocks(
                            this.player,
                            world,
                            entry -> !MaterialList.INVALID_JUMP.contains(entry)
                                && !MaterialList.SHULKER_BOX.contains(entry)
                                && entry != MaterialList.PURPLE_FUCKING_SHULKER
                        )) {
                            this.handleViolation(String.format("D: %s", value5), this.playerData.isTeleporting() ? 0.25 : Math.min(10.0, 0.5 + value5));
                        } else {
                            this.ignoring = true;
                        }
                    }
                );
            } else {
                this.violations = this.violations - Math.min(this.violations + 2.5, 0.025);
            }
        }
    }
}
