package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.api.PacketHandler;

@Deprecated
public abstract class VPacketPlayInPosition<P> extends VPacketPlayInFlying<P> {
    @Override
    public void handle(PacketHandler listener) {
        listener.handle(this);
    }
}
