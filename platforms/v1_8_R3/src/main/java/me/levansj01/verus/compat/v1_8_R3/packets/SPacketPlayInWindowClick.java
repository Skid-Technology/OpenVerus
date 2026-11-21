package me.levansj01.verus.compat.v1_8_R3.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInWindowClick;
import net.minecraft.server.v1_8_R3.PacketPlayInWindowClick;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;

public class SPacketPlayInWindowClick extends VPacketPlayInWindowClick<PacketPlayInWindowClick> {
    @Override
    public boolean isItem() {
        return this.itemStack != null;
    }

    @Override
    public boolean isShiftClick() {
        return this.isChest() && this.mode == 1 && this.button < 2;
    }

    public void accept(PacketPlayInWindowClick packet) {
        this.slot = packet.b();
        this.window = packet.a();
        this.button = packet.c();
        this.mode = packet.f();
        this.itemStack = CraftItemStack.asBukkitCopy(packet.e());
    }

    @Override
    public boolean isChest() {
        return this.window != 0;
    }
}
