package me.levansj01.verus.compat.v1_15_R1.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.api.wrapper.PlayerAbilities;
import me.levansj01.verus.compat.packets.VPacketPlayInAbilities;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_15_R1.PacketPlayInAbilities;

public class SPacketPlayInAbilities extends VPacketPlayInAbilities<PacketPlayInAbilities> {
    private static final Field e_field = SafeReflection.access(PacketPlayInAbilities.class, "e");
    private static final Field f_field = SafeReflection.access(PacketPlayInAbilities.class, "f");

    public void accept(PacketPlayInAbilities packet) {
        this.abilities = new PlayerAbilities(
            packet.b(), packet.isFlying(), packet.d(), packet.e(), SafeReflection.<Float>fetch(e_field, packet), SafeReflection.<Float>fetch(f_field, packet)
        );
    }
}
