package me.levansj01.verus.compat.v1_18_R2.packets;

import me.levansj01.verus.compat.packets.VPacketPlayOutAttachEntity;
import net.minecraft.network.protocol.game.PacketPlayOutAttachEntity;

public class SPacketPlayOutAttachEntity extends VPacketPlayOutAttachEntity<PacketPlayOutAttachEntity> {
    public void accept(PacketPlayOutAttachEntity packet) {
        this.leash = 0;
        this.entityId = packet.b();
        this.attachId = packet.c();
    }
}
