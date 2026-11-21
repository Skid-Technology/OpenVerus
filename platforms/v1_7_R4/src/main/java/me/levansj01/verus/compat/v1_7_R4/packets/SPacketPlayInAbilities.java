package me.levansj01.verus.compat.v1_7_R4.packets;

import me.levansj01.verus.compat.api.wrapper.PlayerAbilities;
import me.levansj01.verus.compat.packets.VPacketPlayInAbilities;
import net.minecraft.server.v1_7_R4.PacketPlayInAbilities;

public class SPacketPlayInAbilities extends VPacketPlayInAbilities<PacketPlayInAbilities> {
    public void accept(PacketPlayInAbilities packet) {
        this.abilities = new PlayerAbilities(packet.c(), packet.isFlying(), packet.e(), packet.f(), packet.g(), packet.h());
    }
}
