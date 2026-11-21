package me.levansj01.verus.compat.v1_11_R1.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInTransaction;
import net.minecraft.server.v1_11_R1.PacketPlayInTransaction;

public class SPacketPlayInTransaction extends VPacketPlayInTransaction<PacketPlayInTransaction> {
    public void accept(PacketPlayInTransaction packet) {
        this.id = packet.b();
    }
}
