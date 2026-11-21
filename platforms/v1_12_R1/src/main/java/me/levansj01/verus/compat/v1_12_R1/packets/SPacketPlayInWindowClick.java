package me.levansj01.verus.compat.v1_12_R1.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInWindowClick;
import net.minecraft.server.v1_12_R1.PacketPlayInWindowClick;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;

public class SPacketPlayInWindowClick extends VPacketPlayInWindowClick<PacketPlayInWindowClick> {
    @Override
    public boolean isItem() {
        return this.itemStack != null;
    }

    public void accept(PacketPlayInWindowClick packet) {
        this.slot = packet.b();
        this.window = packet.a();
        this.button = packet.c();
        this.mode = packet.f().ordinal();
        this.itemStack = CraftItemStack.asBukkitCopy(packet.e());
    }

    @Override
    public boolean isShiftClick() {
        return this.isChest() && this.mode == 1 && this.button < 2;
    }

    @Override
    public boolean isChest() {
        return this.window != 0;
    }
}
