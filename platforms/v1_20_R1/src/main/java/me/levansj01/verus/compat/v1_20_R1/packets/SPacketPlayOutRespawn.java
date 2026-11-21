package me.levansj01.verus.compat.v1_20_R1.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutRespawn;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.network.protocol.game.PacketPlayOutRespawn;
import net.minecraft.world.level.EnumGamemode;
import org.bukkit.GameMode;

public class SPacketPlayOutRespawn extends VPacketPlayOutRespawn<PacketPlayOutRespawn> {
    private static final Field g_field = SafeReflection.access(PacketPlayOutRespawn.class, "g");

    public void accept(PacketPlayOutRespawn packet) {
        EnumGamemode enumGamemode = SafeReflection.fetch(g_field, packet);
        this.gameMode = GameMode.getByValue(enumGamemode.a());
    }
}
