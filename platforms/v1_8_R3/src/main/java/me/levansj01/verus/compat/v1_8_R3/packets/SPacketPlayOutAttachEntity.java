package me.levansj01.verus.compat.v1_8_R3.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutAttachEntity;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_8_R3.PacketPlayOutAttachEntity;

public class SPacketPlayOutAttachEntity extends VPacketPlayOutAttachEntity<PacketPlayOutAttachEntity> {
    private static final Field a_field = SafeReflection.access(PacketPlayOutAttachEntity.class, "a");
    private static final Field b_field = SafeReflection.access(PacketPlayOutAttachEntity.class, "b");
    private static final Field c_field = SafeReflection.access(PacketPlayOutAttachEntity.class, "c");

    public void accept(PacketPlayOutAttachEntity packet) {
        this.leash = (byte)SafeReflection.<Integer>fetch(a_field, packet).intValue();
        this.entityId = SafeReflection.<Integer>fetch(b_field, packet);
        this.attachId = SafeReflection.<Integer>fetch(c_field, packet);
    }
}
