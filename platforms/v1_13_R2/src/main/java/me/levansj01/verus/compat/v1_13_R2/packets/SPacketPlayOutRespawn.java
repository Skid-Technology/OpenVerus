package me.levansj01.verus.compat.v1_13_R2.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutRespawn;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_13_R2.EnumGamemode;
import net.minecraft.server.v1_13_R2.PacketPlayOutRespawn;
import org.bukkit.GameMode;

public class SPacketPlayOutRespawn extends VPacketPlayOutRespawn<PacketPlayOutRespawn> {
    private static final Field c_field = SafeReflection.access(PacketPlayOutRespawn.class, "c");

    public void accept(PacketPlayOutRespawn packet) {
        EnumGamemode enumGamemode = SafeReflection.fetch(c_field, packet);
        this.gameMode = GameMode.getByValue(enumGamemode.getId());
    }
}
