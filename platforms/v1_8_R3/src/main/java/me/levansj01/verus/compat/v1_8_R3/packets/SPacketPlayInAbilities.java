package me.levansj01.verus.compat.v1_8_R3.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.api.wrapper.PlayerAbilities;
import me.levansj01.verus.compat.packets.VPacketPlayInAbilities;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_8_R3.PacketPlayInAbilities;

public class SPacketPlayInAbilities extends VPacketPlayInAbilities<PacketPlayInAbilities> {
    private static final Field e_field = SafeReflection.access(PacketPlayInAbilities.class, "e");
    private static final Field f_field = SafeReflection.access(PacketPlayInAbilities.class, "f");

    public void accept(PacketPlayInAbilities packet) {
        this.abilities = new PlayerAbilities(
            packet.a(), packet.isFlying(), packet.c(), packet.d(), SafeReflection.<Float>fetch(e_field, packet), SafeReflection.<Float>fetch(f_field, packet)
        );
    }
}
