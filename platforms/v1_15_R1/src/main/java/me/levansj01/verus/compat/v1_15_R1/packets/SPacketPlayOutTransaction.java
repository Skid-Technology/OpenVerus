package me.levansj01.verus.compat.v1_15_R1.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutTransaction;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_15_R1.PacketPlayOutTransaction;

public class SPacketPlayOutTransaction extends VPacketPlayOutTransaction<PacketPlayOutTransaction> {
    private static final Field window_field = SafeReflection.access(PacketPlayOutTransaction.class, "a");
    private static final Field id_field = SafeReflection.access(PacketPlayOutTransaction.class, "b");
    private static final Field accepted_field = SafeReflection.access(PacketPlayOutTransaction.class, "c");

    public void accept(PacketPlayOutTransaction packet) {
        this.window = SafeReflection.<Integer>fetch(window_field, packet);
        this.id = SafeReflection.<Short>fetch(id_field, packet);
        this.accepted = SafeReflection.<Boolean>fetch(accepted_field, packet);
    }
}
