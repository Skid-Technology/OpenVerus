package me.levansj01.verus.data.transaction.vehicle;

import me.levansj01.verus.compat.packets.VPacketPlayOutEntityDestroy;
import me.levansj01.verus.compat.packets.VPacketPlayOutEntityTeleport;
import me.levansj01.verus.util.location.PacketLocation;

public interface IVehicleTracker {
    boolean isNearby(PacketLocation location);

    void handle(VPacketPlayOutEntityDestroy<?> packet);

    void handle(VPacketPlayOutEntityTeleport<?> packet);
}
