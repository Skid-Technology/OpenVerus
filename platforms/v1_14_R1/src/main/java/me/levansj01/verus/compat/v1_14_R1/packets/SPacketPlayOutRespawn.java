package me.levansj01.verus.compat.v1_14_R1.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutRespawn;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_14_R1.EnumGamemode;
import net.minecraft.server.v1_14_R1.PacketPlayOutRespawn;
import org.bukkit.GameMode;

public class SPacketPlayOutRespawn extends VPacketPlayOutRespawn<PacketPlayOutRespawn> {
    private static final Field b_field = SafeReflection.access(PacketPlayOutRespawn.class, "b");

    public void accept(PacketPlayOutRespawn packet) {
        EnumGamemode enumGamemode = SafeReflection.fetch(b_field, packet);
        this.gameMode = GameMode.getByValue(enumGamemode.getId());
    }
}
