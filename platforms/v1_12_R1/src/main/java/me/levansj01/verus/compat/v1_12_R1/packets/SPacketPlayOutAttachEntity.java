package me.levansj01.verus.compat.v1_12_R1.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutAttachEntity;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_12_R1.PacketPlayOutAttachEntity;

public class SPacketPlayOutAttachEntity extends VPacketPlayOutAttachEntity<PacketPlayOutAttachEntity> {
    private static final Field a_field = SafeReflection.access(PacketPlayOutAttachEntity.class, "a");
    private static final Field b_field = SafeReflection.access(PacketPlayOutAttachEntity.class, "b");

    public void accept(PacketPlayOutAttachEntity packet) {
        this.leash = 0;
        this.entityId = SafeReflection.<Integer>fetch(a_field, packet);
        this.attachId = SafeReflection.<Integer>fetch(b_field, packet);
    }
}
