package me.levansj01.verus.data.transaction.velocity;

import java.util.Queue;
import me.levansj01.verus.compat.packets.VPacketPlayOutEntityVelocity;
import me.levansj01.verus.compat.packets.VPacketPlayOutExplosion;
import me.levansj01.verus.data.CheckLocalQueue;

public interface IVelocityHandler {
    boolean possibleVelocity();

    CheckLocalQueue<TickVelocity> getTickData();

    void handle(VPacketPlayOutExplosion<?> packet);

    int getTicks();

    void handle(VPacketPlayOutEntityVelocity<?> packet);

    Queue<BasicVelocity> getQueue();
}
