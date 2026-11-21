package me.levansj01.verus.check.checks.crasher;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInArmAnimation;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInHeldItemSlot;
import me.levansj01.verus.compat.packets.VPacketPlayInWindowClick;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.storage.StorageEngine;

@CheckInfo(
    type = CheckType.SERVER_CRASHER,
    subType = "B",
    friendlyName = "Server Crasher",
    version = CheckVersion.RELEASE,
    maxViolations = 1,
    unsupportedAtleast = ClientVersion.V1_9
)
public class ServerCrasherB extends Check implements PacketHandler {
    private long lastSwitch;
    private int places;
    private int clicks;
    private int switches;
    private int swings;

    public void handle(int value, int value2) {
        if (StorageEngine.getInstance().getVerusConfig().isSchemBans() && this.playerData.isSurvival()) {
            this.handleViolation(String.format("T: %s A: %s", value, value2));
            this.playerData.fuckOff();
        }
    }

    @Override
    public void handle(VPacketPlayInFlying<?> packet) {
        this.clicks = this.swings = this.places = 0;
    }

    @Override
    public void handle(VPacketPlayInHeldItemSlot<?> packet) {
        if (this.playerData.getTimestamp() - this.lastSwitch > 100L) {
            this.switches = 0;
            this.lastSwitch = this.playerData.getTimestamp();
        }

        if (this.switches++ > 400) {
            this.handle(3, this.switches);
        }
    }

    @Override
    public void handle(VPacketPlayInArmAnimation<?> packet) {
        if (this.swings++ > 200) {
            this.handle(1, this.swings);
        }
    }

    @Override
    public void handle(VPacketPlayInWindowClick<?> packet) {
        if (this.clicks++ > 200) {
            this.handle(2, this.clicks);
        }
    }

    @Override
    public void handle(VPacketPlayInBlockPlace<?> packet) {
        if (this.places++ > 200) {
            this.handle(2, this.places);
        }
    }
}
