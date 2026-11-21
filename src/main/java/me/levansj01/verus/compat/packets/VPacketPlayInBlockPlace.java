package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.api.wrapper.BlockPosition;
import org.bukkit.inventory.ItemStack;

public abstract class VPacketPlayInBlockPlace<P> extends VPacket<P> {
    private static final int count = count();
    protected int hand = 0;
    protected boolean empty;
    protected float blockY;
    protected BlockPosition position;
    protected float blockX;
    protected float blockZ;
    protected ItemStack itemStack;
    protected long timestamp;
    protected int face;

    @Override
    public int ordinal() {
        return count;
    }

    public boolean isItem() {
        return this.itemStack != null && this.isUse();
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public float getBlockZ() {
        return this.blockZ;
    }

    public int getHand() {
        return this.hand;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public boolean isUse() {
        return this.position != null
            && this.position.getX() == -1
            && (this.position.getY() == -1 || this.position.getY() == 255)
            && this.position.getZ() == -1
            && this.blockX == 0.0F
            && this.blockY == 0.0F
            && this.blockZ == 0.0F
            && this.face == 255;
    }

    public float getBlockY() {
        return this.blockY;
    }

    public BlockPosition getPosition() {
        return this.position;
    }

    public boolean isEmpty() {
        return this.empty;
    }

    public float getBlockX() {
        return this.blockX;
    }

    @Override
    public void handle(PacketHandler listener) {
        listener.handle(this);
    }

    public int getFace() {
        return this.face;
    }
}
