package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;
import org.bukkit.GameMode;

public abstract class VPacketPlayOutRespawn<P> extends VPacket<P> {
    private static final int count = count();
    protected GameMode gameMode;

    @Override
    public void handle(PacketHandler listener) {
        listener.handle(this);
    }

    @Override
    public int ordinal() {
        return count;
    }

    public GameMode getGameMode() {
        return this.gameMode;
    }
}
