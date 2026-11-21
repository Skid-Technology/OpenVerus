package me.levansj01.verus.compat.v1_18_R1.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInClientCommand;
import net.minecraft.network.protocol.game.PacketPlayInClientCommand;

public class SPacketPlayInClientCommand extends VPacketPlayInClientCommand<PacketPlayInClientCommand> {
    public void accept(PacketPlayInClientCommand packet) {
        this.command = VPacketPlayInClientCommand.ClientCommand.values()[packet.b().ordinal()];
    }
}
