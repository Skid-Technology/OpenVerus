package me.levansj01.verus.compat.v1_8_R3.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutBlockChange;
import me.levansj01.verus.compat.v1_8_R3.NMSManager;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockChange;

public class SPacketPlayOutBlockChange extends VPacketPlayOutBlockChange<PacketPlayOutBlockChange> {
    private static final Field a_field = SafeReflection.access(PacketPlayOutBlockChange.class, "a");
    private static final Field block_field = SafeReflection.access(PacketPlayOutBlockChange.class, "block");

    public void accept(PacketPlayOutBlockChange packet) {
        BlockPosition blockPosition = SafeReflection.fetch(a_field, packet);
        this.x = blockPosition.getX();
        this.y = blockPosition.getY();
        this.z = blockPosition.getZ();
        IBlockData blockData = SafeReflection.fetch(block_field, packet);
        this.data = NMSManager.getInstance().toLazy(blockData);
    }
}
