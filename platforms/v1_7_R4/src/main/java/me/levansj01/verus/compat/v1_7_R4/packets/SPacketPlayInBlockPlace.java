package me.levansj01.verus.compat.v1_7_R4.packets;

import me.levansj01.verus.compat.api.wrapper.BlockPosition;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import net.minecraft.server.v1_7_R4.PacketPlayInBlockPlace;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;

public class SPacketPlayInBlockPlace extends VPacketPlayInBlockPlace<PacketPlayInBlockPlace> {
    public void accept(PacketPlayInBlockPlace packet) {
        this.position = new BlockPosition(packet.c(), packet.d(), packet.e());
        this.face = packet.getFace();
        this.itemStack = CraftItemStack.asBukkitCopy(packet.getItemStack());
        this.blockX = packet.h();
        this.blockY = packet.i();
        this.blockZ = packet.j();
        this.timestamp = packet.timestamp;
    }
}
