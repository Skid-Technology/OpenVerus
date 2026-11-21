package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;

public abstract class VPacketPlayOutAttachEntity<P> extends VPacket<P> {
    private static final int count = count();
    protected int attachId;
    protected int entityId;
    protected byte leash;

    @Override
    public void handle(PacketHandler listener) {
        listener.handle(this);
    }

    public byte getLeash() {
        return this.leash;
    }

    @Override
    public int ordinal() {
        return count;
    }

    public int getEntityId() {
        return this.entityId;
    }

    public int getAttachId() {
        return this.attachId;
    }
}
