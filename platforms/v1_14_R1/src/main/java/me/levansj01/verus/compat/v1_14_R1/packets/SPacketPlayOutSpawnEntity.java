package me.levansj01.verus.compat.v1_14_R1.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutSpawnEntity;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_14_R1.PacketPlayOutSpawnEntity;

public class SPacketPlayOutSpawnEntity extends VPacketPlayOutSpawnEntity<PacketPlayOutSpawnEntity> {
    private static final Field PacketPlayOutSpawnEntity_a = SafeReflection.access(PacketPlayOutSpawnEntity.class, "a");

    public void accept(PacketPlayOutSpawnEntity packet) {
        this.id = SafeReflection.<Integer>fetch(PacketPlayOutSpawnEntity_a, packet);
    }
}
