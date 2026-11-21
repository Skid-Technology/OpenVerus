package me.levansj01.verus.compat.v1_20_R3.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInWindowClick;
import net.minecraft.network.protocol.game.PacketPlayInWindowClick;
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftItemStack;

public class SPacketPlayInWindowClick extends VPacketPlayInWindowClick<PacketPlayInWindowClick> {
    @Override
    public boolean isItem() {
        return this.itemStack != null;
    }

    @Override
    public boolean isChest() {
        return this.window != 0;
    }

    public void accept(PacketPlayInWindowClick packet) {
        this.slot = packet.d();
        this.window = packet.a();
        this.button = packet.e();
        this.mode = packet.h().ordinal();
        this.itemStack = CraftItemStack.asBukkitCopy(packet.f());
    }

    @Override
    public boolean isShiftClick() {
        return this.isChest() && this.mode == 1 && this.button < 2;
    }
}
