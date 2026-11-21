package me.levansj01.verus.compat.v1_11_R1.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutSpawnEntityLiving;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_11_R1.PacketPlayOutSpawnEntityLiving;

public class SPacketPlayOutSpawnEntityLiving extends VPacketPlayOutSpawnEntityLiving<PacketPlayOutSpawnEntityLiving> {
    private static final Field PacketPlayOutSpawnEntityLiving_a = SafeReflection.access(PacketPlayOutSpawnEntityLiving.class, "a");

    public void accept(PacketPlayOutSpawnEntityLiving packet) {
        this.id = SafeReflection.<Integer>fetch(PacketPlayOutSpawnEntityLiving_a, packet);
    }
}
