package me.levansj01.verus.compat.v1_15_R1.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutGameStateChange;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_15_R1.PacketPlayOutGameStateChange;

public class SPacketPlayOutGameStateChange extends VPacketPlayOutGameStateChange<PacketPlayOutGameStateChange> {
    private static final Field b_field = SafeReflection.access(PacketPlayOutGameStateChange.class, "b");
    private static final Field c_field = SafeReflection.access(PacketPlayOutGameStateChange.class, "c");

    public void accept(PacketPlayOutGameStateChange packet) {
        this.setting = SafeReflection.<Integer>fetch(b_field, packet);
        this.value = SafeReflection.<Float>fetch(c_field, packet);
    }
}
