package me.levansj01.verus.compat.v1_14_R1.packets;

import java.lang.reflect.Field;
import java.util.List;
import me.levansj01.verus.compat.packets.VPacketPlayOutEntityMetadata;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_14_R1.DataWatcherObject;
import net.minecraft.server.v1_14_R1.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_14_R1.DataWatcher.Item;

public class SPacketPlayOutEntityMetadata extends VPacketPlayOutEntityMetadata<PacketPlayOutEntityMetadata> {
    private static final Field a_field = SafeReflection.access(PacketPlayOutEntityMetadata.class, "a");
    private static final Field b_field = SafeReflection.access(PacketPlayOutEntityMetadata.class, "b");

    public void accept(PacketPlayOutEntityMetadata packet) {
        List<Item> list = SafeReflection.fetch(b_field, packet);
        if (list != null && !list.isEmpty()) {
            for (Item item : list) {
                DataWatcherObject dataWatcherObject = item.a();
                this.values.add(new VPacketPlayOutEntityMetadata.MetadataValue(dataWatcherObject.a(), item.b()));
            }
        }

        this.entityId = SafeReflection.<Integer>fetch(a_field, packet);
    }
}
