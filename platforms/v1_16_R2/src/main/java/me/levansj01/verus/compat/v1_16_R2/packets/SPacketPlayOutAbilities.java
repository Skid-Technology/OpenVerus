package me.levansj01.verus.compat.v1_16_R2.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutAbilities;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_16_R2.PacketPlayOutAbilities;

public class SPacketPlayOutAbilities extends VPacketPlayOutAbilities<PacketPlayOutAbilities> {
    private static final Field a_field = SafeReflection.access(PacketPlayOutAbilities.class, "a");
    private static final Field b_field = SafeReflection.access(PacketPlayOutAbilities.class, "b");
    private static final Field c_field = SafeReflection.access(PacketPlayOutAbilities.class, "c");
    private static final Field d_field = SafeReflection.access(PacketPlayOutAbilities.class, "d");
    private static final Field e_field = SafeReflection.access(PacketPlayOutAbilities.class, "e");
    private static final Field f_field = SafeReflection.access(PacketPlayOutAbilities.class, "f");

    public void accept(PacketPlayOutAbilities packet) {
        this.isInvulnerable = SafeReflection.<Boolean>fetch(a_field, packet);
        this.isFlying = SafeReflection.<Boolean>fetch(b_field, packet);
        this.canFly = SafeReflection.<Boolean>fetch(c_field, packet);
        this.canInstantlyBuild = SafeReflection.<Boolean>fetch(d_field, packet);
        this.walkSpeed = SafeReflection.<Float>fetch(e_field, packet);
        this.flySpeed = SafeReflection.<Float>fetch(f_field, packet);
    }
}
