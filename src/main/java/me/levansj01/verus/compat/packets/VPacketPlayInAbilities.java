package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.api.wrapper.PlayerAbilities;

public abstract class VPacketPlayInAbilities<P> extends VPacket<P> {
    private static final int count = count();
    protected PlayerAbilities abilities;

    public PlayerAbilities getAbilities() {
        return this.abilities;
    }

    @Override
    public int ordinal() {
        return count;
    }

    @Override
    public void handle(PacketHandler listener) {
        listener.handle(this);
    }
}
