package me.levansj01.verus.data.transaction.teleport;

import java.util.Set;
import me.levansj01.verus.compat.packets.VPacketPlayOutPosition;

public class Teleport extends BasicTeleport {
    private final long time;
    private final int ticks;

    public long getTime() {
        return this.time;
    }

    public Teleport(double value, double value2, double value3, float value4, float value5, Set<VPacketPlayOutPosition.TeleportFlag> teleportFlags, int ticks, long time) {
        super(value, value2, value3, value4, value5, teleportFlags);
        this.ticks = ticks;
        this.time = time;
    }

    public int getTicks() {
        return this.ticks;
    }
}
