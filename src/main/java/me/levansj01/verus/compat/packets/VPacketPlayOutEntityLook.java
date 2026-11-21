package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.api.PacketHandler;

@Deprecated
public abstract class VPacketPlayOutEntityLook<P> extends VPacketPlayOutEntity<P> {
    @Override
    public void handle(PacketHandler listener) {
        listener.handle(this);
    }
}
