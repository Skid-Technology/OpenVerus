package me.levansj01.verus.compat.v1_16_R3.packets;

import java.lang.reflect.Field;
import java.math.BigInteger;
import me.levansj01.verus.compat.packets.VPacketPlayOutKeepAlive;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_16_R3.PacketPlayOutKeepAlive;

public class SPacketPlayOutKeepAlive extends VPacketPlayOutKeepAlive<PacketPlayOutKeepAlive> {
    private static final Field id_field = SafeReflection.access(PacketPlayOutKeepAlive.class, "a");

    public void accept(PacketPlayOutKeepAlive packet) {
        this.id = BigInteger.valueOf(SafeReflection.<Long>fetch(id_field, packet));
    }
}
