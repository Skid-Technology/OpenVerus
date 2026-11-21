package me.levansj01.verus.compat.v1_20_R3.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutGameStateChange;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.network.protocol.game.PacketPlayOutGameStateChange;
import net.minecraft.network.protocol.game.PacketPlayOutGameStateChange.a;

public class SPacketPlayOutGameStateChange extends VPacketPlayOutGameStateChange<PacketPlayOutGameStateChange> {
    private static final Field b_field = SafeReflection.access(a.class, "b");

    public void accept(PacketPlayOutGameStateChange packet) {
        a a2 = packet.a();
        this.setting = SafeReflection.<Integer>fetch(b_field, a2);
        this.value = packet.d();
    }
}
