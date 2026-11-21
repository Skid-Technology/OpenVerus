package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;
import org.bukkit.inventory.ItemStack;

public abstract class VPacketPlayInWindowClick<P> extends VPacket<P> {
    private static final int count = count();
    protected Integer mode;
    protected Integer slot;
    protected Integer button;
    protected Integer window;
    protected ItemStack itemStack;

    public Integer getButton() {
        return this.button;
    }

    public Integer getMode() {
        return this.mode;
    }

    public abstract boolean isShiftClick();

    public Integer getWindow() {
        return this.window;
    }

    @Override
    public void handle(PacketHandler listener) {
        listener.handle(this);
    }

    @Override
    public int ordinal() {
        return count;
    }

    public Integer getSlot() {
        return this.slot;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public abstract boolean isChest();

    public abstract boolean isItem();
}
