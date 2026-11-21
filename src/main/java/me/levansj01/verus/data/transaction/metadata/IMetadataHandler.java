package me.levansj01.verus.data.transaction.metadata;

import me.levansj01.verus.compat.packets.VPacketPlayOutEntityMetadata;

public interface IMetadataHandler {
    boolean isToggledElytra();

    boolean isElytraFlying();

    void handle(VPacketPlayOutEntityMetadata<?> packet);

    boolean getFlag(int value);
}
