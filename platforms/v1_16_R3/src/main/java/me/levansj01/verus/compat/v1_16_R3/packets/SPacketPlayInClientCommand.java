package me.levansj01.verus.compat.v1_16_R3.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInClientCommand;
import net.minecraft.server.v1_16_R3.PacketPlayInClientCommand;

public class SPacketPlayInClientCommand extends VPacketPlayInClientCommand<PacketPlayInClientCommand> {
    public void accept(PacketPlayInClientCommand packet) {
        this.command = VPacketPlayInClientCommand.ClientCommand.values()[packet.b().ordinal()];
    }
}
