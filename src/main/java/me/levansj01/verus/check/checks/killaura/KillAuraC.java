package me.levansj01.verus.check.checks.killaura;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;

@CheckInfo(type = CheckType.KILL_AURA, subType = "C", friendlyName = "Aimbot", version = CheckVersion.RELEASE, minViolations = -1.0, maxViolations = 5)
public class KillAuraC extends Check implements PacketHandler {
    private Float lastYaw = null;
    private Float yawSpeed = 360.0F;

    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        if (packet.isLook()) {
            if (this.lastYaw != null && !this.playerData.isTeleporting()) {
                float deltaYaw = Math.abs(this.lastYaw - packet.getYaw());
                if (this.yawSpeed < 45.0F) {
                    if (deltaYaw > 345.0F && deltaYaw < 375.0F) {
                        float abs = Math.abs(360.0F - (deltaYaw + Math.abs(180.0F - Math.abs(packet.getYaw() % 180.0F - this.lastYaw % 180.0F))));
                        if (Math.abs(abs) == 0.0) {
                            this.handleViolation(String.format("D: %s", deltaYaw), 1.0, true);
                        }
                    } else if (deltaYaw > 172.5 && deltaYaw < 187.5) {
                        float abs = Math.abs(180.0F - (deltaYaw + Math.abs(90.0F - Math.abs(packet.getYaw() % 90.0F - this.lastYaw % 90.0F))));
                        if (Math.abs(abs) == 0.0) {
                            this.handleViolation(String.format("D: %s", deltaYaw), 0.75, true);
                        }
                    }

                    this.decreaseVL(0.005);
                }

                this.yawSpeed = this.yawSpeed * 3.0F;
                this.yawSpeed = this.yawSpeed + deltaYaw;
                this.yawSpeed = this.yawSpeed / 4.0F;
            }

            this.lastYaw = packet.getYaw();
        }
    }
}
