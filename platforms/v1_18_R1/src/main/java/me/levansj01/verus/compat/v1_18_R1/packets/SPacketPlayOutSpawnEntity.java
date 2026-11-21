package me.levansj01.verus.compat.v1_18_R1.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutSpawnEntity;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity;

public class SPacketPlayOutSpawnEntity extends VPacketPlayOutSpawnEntity<PacketPlayOutSpawnEntity> {
    private static final Field PacketPlayOutSpawnEntity_c = SafeReflection.access(PacketPlayOutSpawnEntity.class, "c");

    public void accept(PacketPlayOutSpawnEntity packet) {
        this.id = SafeReflection.<Integer>fetch(PacketPlayOutSpawnEntity_c, packet);
    }
}
