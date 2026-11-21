package me.levansj01.verus.compat.v1_17_R1.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutRespawn;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.network.protocol.game.PacketPlayOutRespawn;
import net.minecraft.world.level.EnumGamemode;
import org.bukkit.GameMode;

public class SPacketPlayOutRespawn extends VPacketPlayOutRespawn<PacketPlayOutRespawn> {
    private static final Field d_field = SafeReflection.access(PacketPlayOutRespawn.class, "d");

    public void accept(PacketPlayOutRespawn packet) {
        EnumGamemode enumGamemode = SafeReflection.fetch(d_field, packet);
        this.gameMode = GameMode.getByValue(enumGamemode.getId());
    }
}
