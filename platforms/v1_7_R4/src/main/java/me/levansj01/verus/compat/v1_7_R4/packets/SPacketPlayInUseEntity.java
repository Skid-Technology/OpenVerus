package me.levansj01.verus.compat.v1_7_R4.packets;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayInUseEntity;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_7_R4.PacketPlayInUseEntity;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.entity.Entity;

public class SPacketPlayInUseEntity extends VPacketPlayInUseEntity<PacketPlayInUseEntity> {
    private static final Field id_field = SafeReflection.access(PacketPlayInUseEntity.class, "a");
    private boolean fetchedEntity;

    public void accept(PacketPlayInUseEntity packet) {
        this.id = SafeReflection.<Integer>fetch(id_field, packet);
        if (packet.c() != null) {
            this.action = VPacketPlayInUseEntity.EntityUseAction.values()[packet.c().ordinal()];
        } else {
            this.action = VPacketPlayInUseEntity.EntityUseAction.INTERACT_AT;
        }

        this.fetchedEntity = false;
    }

    @Override
    public Entity getEntity(World world) {
        if (!this.fetchedEntity) {
            net.minecraft.server.v1_7_R4.Entity entity = ((CraftWorld)world).getHandle().getEntity(this.id);
            if (entity == null) {
                this.entity = new WeakReference<>(null);
            } else {
                this.entity = new WeakReference<>(entity.getBukkitEntity());
            }

            this.fetchedEntity = true;
        }

        return this.entity == null ? null : this.entity.get();
    }
}
