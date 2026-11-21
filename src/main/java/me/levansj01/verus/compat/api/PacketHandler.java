package me.levansj01.verus.compat.api;

import java.lang.reflect.Method;
import java.util.LinkedList;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VClientboundPingPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInAbilities;
import me.levansj01.verus.compat.packets.VPacketPlayInArmAnimation;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockDig;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.compat.packets.VPacketPlayInChat;
import me.levansj01.verus.compat.packets.VPacketPlayInClientCommand;
import me.levansj01.verus.compat.packets.VPacketPlayInCloseWindow;
import me.levansj01.verus.compat.packets.VPacketPlayInCustomPayload;
import me.levansj01.verus.compat.packets.VPacketPlayInEntityAction;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInHeldItemSlot;
import me.levansj01.verus.compat.packets.VPacketPlayInKeepAlive;
import me.levansj01.verus.compat.packets.VPacketPlayInSetCreativeSlot;
import me.levansj01.verus.compat.packets.VPacketPlayInSteerVehicle;
import me.levansj01.verus.compat.packets.VPacketPlayInTeleportAccept;
import me.levansj01.verus.compat.packets.VPacketPlayInTransaction;
import me.levansj01.verus.compat.packets.VPacketPlayInUseEntity;
import me.levansj01.verus.compat.packets.VPacketPlayInUseItem;
import me.levansj01.verus.compat.packets.VPacketPlayInVehicleMove;
import me.levansj01.verus.compat.packets.VPacketPlayInWindowClick;
import me.levansj01.verus.compat.packets.VPacketPlayOutAbilities;
import me.levansj01.verus.compat.packets.VPacketPlayOutAttachEntity;
import me.levansj01.verus.compat.packets.VPacketPlayOutBlockChange;
import me.levansj01.verus.compat.packets.VPacketPlayOutEntity;
import me.levansj01.verus.compat.packets.VPacketPlayOutEntityDestroy;
import me.levansj01.verus.compat.packets.VPacketPlayOutEntityEffect;
import me.levansj01.verus.compat.packets.VPacketPlayOutEntityMetadata;
import me.levansj01.verus.compat.packets.VPacketPlayOutEntityTeleport;
import me.levansj01.verus.compat.packets.VPacketPlayOutEntityVelocity;
import me.levansj01.verus.compat.packets.VPacketPlayOutExplosion;
import me.levansj01.verus.compat.packets.VPacketPlayOutGameStateChange;
import me.levansj01.verus.compat.packets.VPacketPlayOutKeepAlive;
import me.levansj01.verus.compat.packets.VPacketPlayOutMapChunk;
import me.levansj01.verus.compat.packets.VPacketPlayOutMapChunkBulk;
import me.levansj01.verus.compat.packets.VPacketPlayOutMultiBlockChange;
import me.levansj01.verus.compat.packets.VPacketPlayOutNamedEntitySpawn;
import me.levansj01.verus.compat.packets.VPacketPlayOutOpenWindow;
import me.levansj01.verus.compat.packets.VPacketPlayOutPosition;
import me.levansj01.verus.compat.packets.VPacketPlayOutRemoveEntityEffect;
import me.levansj01.verus.compat.packets.VPacketPlayOutRespawn;
import me.levansj01.verus.compat.packets.VPacketPlayOutSetSlot;
import me.levansj01.verus.compat.packets.VPacketPlayOutSpawnEntity;
import me.levansj01.verus.compat.packets.VPacketPlayOutSpawnEntityLiving;
import me.levansj01.verus.compat.packets.VPacketPlayOutSpawnPosition;
import me.levansj01.verus.compat.packets.VPacketPlayOutTransaction;
import me.levansj01.verus.compat.packets.VPacketPlayOutUnloadChunk;
import me.levansj01.verus.compat.packets.VPacketPlayOutUpdateAttributes;
import me.levansj01.verus.compat.packets.VServerboundPongPacket;
import me.levansj01.verus.util.java.SafeReflection;

public interface PacketHandler {
    default void handle(VPacketPlayOutEntityVelocity<?> packet) {
    }

    default void handle(VPacketPlayInChat<?> packet) {
    }

    default void handle(VPacketPlayOutAttachEntity<?> packet) {
    }

    default void handle(VPacketPlayInCloseWindow<?> packet) {
    }

    default void handle(VPacketPlayOutSetSlot<?> packet) {
    }

    default void handle(VPacketPlayOutExplosion<?> packet) {
    }

    default void handle(VPacketPlayInBlockPlace<?> packet) {
    }

    default void handle(VPacketPlayOutEntity<?> packet) {
    }

    default void handle(VPacketPlayOutKeepAlive<?> packet) {
    }

    default void handle(VPacketPlayOutNamedEntitySpawn<?> packet) {
    }

    default void handle(VPacketPlayOutOpenWindow<?> packet) {
    }

    default void handle(VPacketPlayInHeldItemSlot<?> packet) {
    }

    default void handle(VPacketPlayInAbilities<?> packet) {
    }

    default void handle(VPacketPlayInBlockDig<?> packet) {
    }

    default void handle(VPacketPlayOutUpdateAttributes<?> packet) {
    }

    default void handle(VPacketPlayOutEntityTeleport<?> packet) {
    }

    default void handle(VPacketPlayInCustomPayload<?> packet) {
    }

    default void handle(VPacketPlayOutAbilities<?> packet) {
    }

    default int[] parse() {
        LinkedList list = new LinkedList();

        for (Method method : this.getClass().getDeclaredMethods()) {
            if (method.getParameterCount() == 1) {
                Class clazz = method.getParameterTypes()[0];
                if (clazz == Transactionable.class) {
                    switch (method.getName()) {
                        case "handleIn":
                            list.add(VPacketPlayInTransaction.class);
                            list.add(VServerboundPongPacket.class);
                            break;
                        case "handleOut":
                            list.add(VPacketPlayOutTransaction.class);
                            list.add(VClientboundPingPacket.class);
                    }
                } else if (VPacket.class.isAssignableFrom(clazz) && method.getName().equals("handle")) {
                    list.add(clazz);
                }
            }
        }

        int[] values = new int[list.size()];
        int value = 0;

        for (Object item : list) {
            Class<?> clazz = (Class<?>)item;
            values[value++] = SafeReflection.<Integer>getLocalField(clazz, null, new String[]{"count"});
        }

        return values;
    }

    default void handle(VPacketPlayInKeepAlive<?> packet) {
    }

    default void handle(VPacketPlayInSteerVehicle<?> packet) {
    }

    default void handleOut(Transactionable transactionable) {
    }

    default void handle(VPacketPlayOutMapChunk<?> packet) {
    }

    default void handle(VPacketPlayInVehicleMove<?> packet) {
    }

    default void handle(VPacketPlayOutBlockChange<?> packet) {
    }

    default void handle(VPacketPlayOutMapChunkBulk<?> packet) {
    }

    default void handle(VPacketPlayOutMultiBlockChange<?> packet) {
    }

    default void handle(VPacketPlayInTeleportAccept<?> packet) {
    }

    default void handle(VPacketPlayInEntityAction<?> packet) {
    }

    default void handle(VPacketPlayInUseEntity<?> packet) {
    }

    default void handle(VPacketPlayInArmAnimation<?> packet) {
    }

    default void handle(VPacketPlayOutGameStateChange<?> packet) {
    }

    default void handle(VPacketPlayOutSpawnEntityLiving<?> packet) {
    }

    default void handle(VPacketPlayInWindowClick<?> packet) {
    }

    default void handle(VPacketPlayInClientCommand<?> packet) {
    }

    default void handle(VPacketPlayOutRespawn<?> packet) {
    }

    default void handle(VPacketPlayOutEntityDestroy<?> packet) {
    }

    default void handle(VPacketPlayOutEntityMetadata<?> packet) {
    }

    default void handle(VPacketPlayInSetCreativeSlot<?> packet) {
    }

    default void handle(VPacketPlayOutRemoveEntityEffect<?> packet) {
    }

    default void handle(VPacketPlayOutPosition<?> packet) {
    }

    default void handle(VPacketPlayOutSpawnPosition<?> packet) {
    }

    default void handle(VPacketPlayOutSpawnEntity<?> packet) {
    }

    default void handleIn(Transactionable transactionable) {
    }

    default void handle(VPacketPlayInFlying<?> packet) {
    }

    default void handle(VPacketPlayOutEntityEffect<?> packet) {
    }

    default void handle(VPacketPlayInUseItem<?> packet) {
    }

    default void handle(VPacketPlayOutUnloadChunk<?> packet) {
    }
}
