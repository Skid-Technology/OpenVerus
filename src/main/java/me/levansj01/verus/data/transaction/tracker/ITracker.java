package me.levansj01.verus.data.transaction.tracker;

import me.levansj01.verus.compat.packets.VPacketPlayOutAttachEntity;
import me.levansj01.verus.compat.packets.VPacketPlayOutEntity;
import me.levansj01.verus.compat.packets.VPacketPlayOutEntityDestroy;
import me.levansj01.verus.compat.packets.VPacketPlayOutEntityTeleport;
import me.levansj01.verus.compat.packets.VPacketPlayOutNamedEntitySpawn;
import me.levansj01.verus.compat.packets.VPacketPlayOutSpawnEntity;
import me.levansj01.verus.compat.packets.VPacketPlayOutSpawnEntityLiving;

public interface ITracker {
    void handle(VPacketPlayOutEntity<?> packet);

    void handle(VPacketPlayOutEntityDestroy<?> packet);

    void handle(VPacketPlayOutSpawnEntityLiving<?> packet);

    void handle(VPacketPlayOutAttachEntity<?> packet);

    void updateVersion();

    void handle(VPacketPlayOutNamedEntitySpawn<?> packet);

    ITrackedPlayer get(int value);

    void handle(VPacketPlayOutSpawnEntity<?> packet);

    void handle(VPacketPlayOutEntityTeleport<?> packet);
}
