package me.levansj01.verus.compat.v1_19_R3.packets;

import me.levansj01.verus.compat.packets.VPacketPlayOutUpdateAttributes;
import net.minecraft.network.protocol.game.PacketPlayOutUpdateAttributes;
import net.minecraft.network.protocol.game.PacketPlayOutUpdateAttributes.AttributeSnapshot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class SPacketPlayOutUpdateAttributes extends VPacketPlayOutUpdateAttributes<PacketPlayOutUpdateAttributes> {
    public void accept(PacketPlayOutUpdateAttributes packet) {
        this.entityId = packet.a();

        for (AttributeSnapshot attributeSnapshot : packet.c()) {
            VPacketPlayOutUpdateAttributes.Snapshot snapshot = new VPacketPlayOutUpdateAttributes.Snapshot(attributeSnapshot.a().c(), attributeSnapshot.b());

            for (AttributeModifier attributeModifier : (Iterable<AttributeModifier>)attributeSnapshot.c()) {
                snapshot.getModifiers().add(new VPacketPlayOutUpdateAttributes.Snapshot.Modifier(attributeModifier.b(), attributeModifier.d(), attributeModifier.c().a()));
            }

            this.snapshots.add(snapshot);
        }
    }
}
