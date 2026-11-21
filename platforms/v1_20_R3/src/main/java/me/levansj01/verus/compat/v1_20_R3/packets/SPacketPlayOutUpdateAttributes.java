package me.levansj01.verus.compat.v1_20_R3.packets;

import me.levansj01.verus.compat.packets.VPacketPlayOutUpdateAttributes;
import net.minecraft.network.protocol.game.PacketPlayOutUpdateAttributes;
import net.minecraft.network.protocol.game.PacketPlayOutUpdateAttributes.AttributeSnapshot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class SPacketPlayOutUpdateAttributes extends VPacketPlayOutUpdateAttributes<PacketPlayOutUpdateAttributes> {
    public void accept(PacketPlayOutUpdateAttributes packet) {
        this.entityId = packet.a();

        for (AttributeSnapshot attributeSnapshot : packet.d()) {
            VPacketPlayOutUpdateAttributes.Snapshot snapshot = new VPacketPlayOutUpdateAttributes.Snapshot(attributeSnapshot.a().c(), attributeSnapshot.b());

            for (AttributeModifier attributeModifier : (Iterable<AttributeModifier>)attributeSnapshot.c()) {
                snapshot.getModifiers().add(new VPacketPlayOutUpdateAttributes.Snapshot.Modifier(attributeModifier.e, attributeModifier.c(), attributeModifier.d().a()));
            }

            this.snapshots.add(snapshot);
        }
    }
}
