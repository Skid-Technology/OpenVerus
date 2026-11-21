package me.levansj01.verus.compat.v1_8_R3.packets;

import me.levansj01.verus.compat.api.wrapper.BlockPosition;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockPlace;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;

public class SPacketPlayInBlockPlace extends VPacketPlayInBlockPlace<PacketPlayInBlockPlace> {
    public void accept(PacketPlayInBlockPlace packet) {
        this.position = new BlockPosition(packet.a().getX(), packet.a().getY(), packet.a().getZ());
        this.face = packet.getFace();
        ItemStack itemStack = packet.getItemStack();
        this.itemStack = CraftItemStack.asBukkitCopy(itemStack);
        this.blockX = packet.d();
        this.blockY = packet.e();
        this.blockZ = packet.f();
        this.timestamp = packet.timestamp;
    }
}
