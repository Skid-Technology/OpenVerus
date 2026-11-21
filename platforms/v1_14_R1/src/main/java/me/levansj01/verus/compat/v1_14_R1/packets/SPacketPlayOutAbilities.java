package me.levansj01.verus.compat.v1_14_R1.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutAbilities;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_14_R1.PacketPlayOutAbilities;

public class SPacketPlayOutAbilities extends VPacketPlayOutAbilities<PacketPlayOutAbilities> {
    private static final Field e_field = SafeReflection.access(PacketPlayOutAbilities.class, "e");
    private static final Field f_field = SafeReflection.access(PacketPlayOutAbilities.class, "f");

    public void accept(PacketPlayOutAbilities packet) {
        this.isInvulnerable = packet.a();
        this.isFlying = packet.b();
        this.canFly = packet.c();
        this.canInstantlyBuild = packet.d();
        this.walkSpeed = SafeReflection.<Float>fetch(e_field, packet);
        this.flySpeed = SafeReflection.<Float>fetch(f_field, packet);
    }
}
