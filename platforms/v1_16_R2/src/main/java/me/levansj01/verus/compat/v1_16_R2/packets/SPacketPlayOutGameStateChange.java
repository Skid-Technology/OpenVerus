package me.levansj01.verus.compat.v1_16_R2.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutGameStateChange;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_16_R2.PacketPlayOutGameStateChange;
import net.minecraft.server.v1_16_R2.PacketPlayOutGameStateChange.a;

public class SPacketPlayOutGameStateChange extends VPacketPlayOutGameStateChange<PacketPlayOutGameStateChange> {
    private static final Field m_field = SafeReflection.access(PacketPlayOutGameStateChange.class, "m");
    private static final Field n_field = SafeReflection.access(PacketPlayOutGameStateChange.class, "n");
    private static final Field b_field = SafeReflection.access(a.class, "b");

    public void accept(PacketPlayOutGameStateChange packet) {
        a a2 = SafeReflection.fetch(m_field, packet);
        this.setting = SafeReflection.<Integer>fetch(b_field, a2);
        this.value = SafeReflection.<Float>fetch(n_field, packet);
    }
}
