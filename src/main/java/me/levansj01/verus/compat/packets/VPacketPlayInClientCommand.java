package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;

public abstract class VPacketPlayInClientCommand<P> extends VPacket<P> {
    private static final int count = count();
    protected VPacketPlayInClientCommand.ClientCommand command;

    public VPacketPlayInClientCommand.ClientCommand getCommand() {
        return this.command;
    }

    @Override
    public void handle(PacketHandler listener) {
        listener.handle(this);
    }

    @Override
    public int ordinal() {
        return count;
    }

    public enum ClientCommand {
        PERFORM_RESPAWN,
        REQUEST_STATS,
        OPEN_INVENTORY_ACHIEVEMENT;
    }
}
