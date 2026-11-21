package me.levansj01.verus.compat.v1_18_R2.packets;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.lang.reflect.Field;
import java.util.function.Function;
import me.levansj01.verus.compat.packets.VPacketPlayOutEntityDestroy;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy;

public class SPacketPlayOutEntityDestroy extends VPacketPlayOutEntityDestroy<PacketPlayOutEntityDestroy> {
    private static final Field a_field = SafeReflection.access(PacketPlayOutEntityDestroy.class, "a");
    private static final Class<?> type = a_field.getType();
    private static final Function<PacketPlayOutEntityDestroy, int[]> a_extractor;

    public void accept(PacketPlayOutEntityDestroy packet) {
        this.ids = a_extractor.apply(packet);
    }

    static {
        if (type == int.class) {
            a_extractor = entry -> new int[]{SafeReflection.<Integer>fetch(a_field, entry)};
        } else if (type == IntArrayList.class) {
            a_extractor = entry -> SafeReflection.<IntArrayList>fetch(a_field, entry).toIntArray();
        } else {
            if (type != IntList.class) {
                throw new UnsupportedOperationException(type + " is not supported for " + a_field.getClass().getName() + "." + a_field.getName());
            }

            a_extractor = entry -> SafeReflection.<IntList>fetch(a_field, entry).toIntArray();
        }
    }
}
