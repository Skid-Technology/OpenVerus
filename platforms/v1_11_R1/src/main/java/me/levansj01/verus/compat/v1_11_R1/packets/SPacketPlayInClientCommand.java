package me.levansj01.verus.compat.v1_11_R1.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInClientCommand;
import net.minecraft.server.v1_11_R1.PacketPlayInClientCommand;

public class SPacketPlayInClientCommand extends VPacketPlayInClientCommand<PacketPlayInClientCommand> {
    public void accept(PacketPlayInClientCommand packet) {
        this.command = VPacketPlayInClientCommand.ClientCommand.values()[packet.a().ordinal()];
    }
}
