package me.levansj01.verus.data.transaction.attribute;

import me.levansj01.verus.compat.packets.VPacketPlayOutUpdateAttributes;

public interface IAttributeHandler {
    void handle(VPacketPlayOutUpdateAttributes<?> packet);

    double getWalkSpeed();
}
