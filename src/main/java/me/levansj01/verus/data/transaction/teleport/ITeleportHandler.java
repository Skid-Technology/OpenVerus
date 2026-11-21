package me.levansj01.verus.data.transaction.teleport;

import me.levansj01.verus.compat.packets.VPacketPlayOutPosition;
import me.levansj01.verus.util.location.IPacketLocation;

public interface ITeleportHandler {
    void handle(VPacketPlayOutPosition<?> packet);

    boolean isTeleport(IPacketLocation packetLocation);

    boolean isTeleporting();
}
