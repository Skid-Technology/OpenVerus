package me.levansj01.verus.compat.v1_19_R3.packets;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayInUseEntity;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.network.protocol.game.PacketPlayInUseEntity;
import net.minecraft.network.protocol.game.PacketPlayInUseEntity.c;
import net.minecraft.world.EnumHand;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.entity.Entity;

public class SPacketPlayInUseEntity extends VPacketPlayInUseEntity<PacketPlayInUseEntity> {
    private static final Field id_field = SafeReflection.access(PacketPlayInUseEntity.class, "a", "id");
    private boolean fetchedEntity;

    public void accept(PacketPlayInUseEntity packet) {
        this.id = SafeReflection.<Integer>fetch(id_field, packet);
        packet.a(new c() {
            public void a() {
                SPacketPlayInUseEntity.this.action = VPacketPlayInUseEntity.EntityUseAction.ATTACK;
                SPacketPlayInUseEntity.this.bodyX = SPacketPlayInUseEntity.this.bodyY = SPacketPlayInUseEntity.this.bodyZ = 0.0;
            }

            public void a(EnumHand packet2) {
                SPacketPlayInUseEntity.this.action = VPacketPlayInUseEntity.EntityUseAction.INTERACT;
                SPacketPlayInUseEntity.this.bodyX = SPacketPlayInUseEntity.this.bodyY = SPacketPlayInUseEntity.this.bodyZ = 0.0;
            }

            public void a(EnumHand hand, Vec3D vec) {
                SPacketPlayInUseEntity.this.action = VPacketPlayInUseEntity.EntityUseAction.INTERACT_AT;
                SPacketPlayInUseEntity.this.bodyX = vec.a();
                SPacketPlayInUseEntity.this.bodyY = vec.b();
                SPacketPlayInUseEntity.this.bodyZ = vec.c();
            }
        });
        this.fetchedEntity = false;
    }

    @Override
    public Entity getEntity(World world) {
        if (!this.fetchedEntity) {
            net.minecraft.world.entity.Entity entity = ((CraftWorld)world).getHandle().a(this.id);
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
