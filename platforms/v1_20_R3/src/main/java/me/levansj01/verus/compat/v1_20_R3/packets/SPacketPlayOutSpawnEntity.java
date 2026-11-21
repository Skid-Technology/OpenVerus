package me.levansj01.verus.compat.v1_20_R3.packets;

import me.levansj01.verus.compat.packets.VPacketPlayOutSpawnEntityWrapper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity;
import net.minecraft.world.entity.EntityTypes;

public class SPacketPlayOutSpawnEntity extends VPacketPlayOutSpawnEntityWrapper<PacketPlayOutSpawnEntity> {
    private static final int PLAYER_TYPE = BuiltInRegistries.g.a(EntityTypes.bv);
    private int entityType;

    public void accept(PacketPlayOutSpawnEntity packet) {
        this.id = packet.a();
        this.x = packet.f();
        this.y = packet.g();
        this.z = packet.h();
        this.yaw = (byte)packet.l();
        this.pitch = (byte)packet.m();
        this.entityType = BuiltInRegistries.g.a(packet.e());
    }

    @Override
    public boolean isPlayer() {
        return this.entityType == PLAYER_TYPE;
    }
}
