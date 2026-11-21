package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.api.PacketHandler;

@Deprecated
public abstract class VPacketPlayInLook<P> extends VPacketPlayInFlying<P> {
    @Override
    public void handle(PacketHandler listener) {
        listener.handle(this);
    }
}
