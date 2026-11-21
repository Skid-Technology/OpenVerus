package me.levansj01.verus.compat.v1_19_R2.packets;

import java.lang.reflect.Field;
import java.util.List;
import me.levansj01.verus.compat.packets.VPacketPlayOutEntityMetadata;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.network.protocol.game.PacketPlayOutEntityMetadata;
import net.minecraft.network.syncher.DataWatcher.b;

public class SPacketPlayOutEntityMetadata extends VPacketPlayOutEntityMetadata<PacketPlayOutEntityMetadata> {
    private static final Field b_field = SafeReflection.access(PacketPlayOutEntityMetadata.class, "b");
    private static final Field c_field = SafeReflection.access(PacketPlayOutEntityMetadata.class, "c");

    public void accept(PacketPlayOutEntityMetadata packet) {
        List<b> list = SafeReflection.fetch(c_field, packet);
        if (list != null && !list.isEmpty()) {
            for (b b2 : list) {
                this.values.add(new VPacketPlayOutEntityMetadata.MetadataValue(b2.a(), b2.c()));
            }
        }

        this.entityId = SafeReflection.<Integer>fetch(b_field, packet);
    }
}
