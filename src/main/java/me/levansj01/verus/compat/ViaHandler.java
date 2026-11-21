package me.levansj01.verus.compat;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketTracker;
import java.lang.reflect.Field;
import java.util.UUID;
import me.levansj01.verus.util.java.SafeReflection;

public class ViaHandler {
    private final Field PacketTracker_intervalPackets;
    private final Field PacketTracker_receivedPackets = SafeReflection.access(PacketTracker.class, "receivedPackets");

    public ViaHandler.PlayerHandler get(UUID uuid) {
        return new ViaHandler.PlayerHandler(uuid);
    }

    public ViaHandler() {
        this.PacketTracker_intervalPackets = SafeReflection.access(PacketTracker.class, "intervalPackets");
    }

    public class PlayerHandler {
        private final UUID uuid;
        private UserConnection connection;

        public void decrease() {
            if (this.connection == null) {
                this.connection = Via.getAPI().getConnection(this.uuid);
            }

            if (this.connection != null) {
                PacketTracker packetTracker = this.connection.getPacketTracker();
                long value = SafeReflection.<Long>fetch(ViaHandler.this.PacketTracker_receivedPackets, packetTracker);
                SafeReflection.set(ViaHandler.this.PacketTracker_receivedPackets, packetTracker, value - 1L);
                long value2 = SafeReflection.<Long>fetch(ViaHandler.this.PacketTracker_intervalPackets, packetTracker);
                SafeReflection.set(ViaHandler.this.PacketTracker_intervalPackets, packetTracker, value2 - 1L);
            }
        }

        private PlayerHandler(UUID uuid) {
            this.uuid = uuid;
        }
    }
}
