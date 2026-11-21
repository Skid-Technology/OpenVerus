package me.levansj01.verus.compat.v1_7_R4.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInWindowClick;
import net.minecraft.server.v1_7_R4.PacketPlayInWindowClick;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;

public class SPacketPlayInWindowClick extends VPacketPlayInWindowClick<PacketPlayInWindowClick> {
    @Override
    public boolean isItem() {
        return this.itemStack != null;
    }

    @Override
    public boolean isChest() {
        return this.window != 0;
    }

    @Override
    public boolean isShiftClick() {
        return this.isChest() && this.mode == 1 && this.button < 2;
    }

    public void accept(PacketPlayInWindowClick packet) {
        this.slot = packet.d();
        this.window = packet.c();
        this.button = packet.e();
        this.mode = packet.h();
        this.itemStack = CraftItemStack.asBukkitCopy(packet.g());
    }
}
