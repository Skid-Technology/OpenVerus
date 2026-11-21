package me.levansj01.verus.compat.v1_15_R1.packets;

import java.lang.reflect.Field;
import java.util.List;
import me.levansj01.verus.compat.packets.VPacketPlayOutUpdateAttributes;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_15_R1.AttributeModifier;
import net.minecraft.server.v1_15_R1.PacketPlayOutUpdateAttributes;
import net.minecraft.server.v1_15_R1.PacketPlayOutUpdateAttributes.AttributeSnapshot;

public class SPacketPlayOutUpdateAttributes extends VPacketPlayOutUpdateAttributes<PacketPlayOutUpdateAttributes> {
    private static final Field a_field = SafeReflection.access(PacketPlayOutUpdateAttributes.class, "a");
    private static final Field b_field = SafeReflection.access(PacketPlayOutUpdateAttributes.class, "b");

    public void accept(PacketPlayOutUpdateAttributes packet) {
        this.entityId = SafeReflection.<Integer>fetch(a_field, packet);

        for (AttributeSnapshot attributeSnapshot : (List<AttributeSnapshot>)SafeReflection.fetch(b_field, packet)) {
            VPacketPlayOutUpdateAttributes.Snapshot snapshot = new VPacketPlayOutUpdateAttributes.Snapshot(attributeSnapshot.a(), attributeSnapshot.b());

            for (AttributeModifier attributeModifier : (Iterable<AttributeModifier>)attributeSnapshot.c()) {
                snapshot.getModifiers().add(new VPacketPlayOutUpdateAttributes.Snapshot.Modifier(attributeModifier.getName(), attributeModifier.getAmount(), attributeModifier.getOperation().a()));
            }

            this.snapshots.add(snapshot);
        }
    }
}
