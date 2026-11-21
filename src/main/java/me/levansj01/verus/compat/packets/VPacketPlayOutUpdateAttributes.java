package me.levansj01.verus.compat.packets;

import java.util.ArrayList;
import java.util.List;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;

public abstract class VPacketPlayOutUpdateAttributes<P> extends VPacket<P> {
    private static final int count = count();
    protected final List<VPacketPlayOutUpdateAttributes.Snapshot> snapshots = new ArrayList<>();
    protected int entityId;

    @Override
    public int ordinal() {
        return count;
    }

    @Override
    public void handle(PacketHandler listener) {
        listener.handle(this);
    }

    public int getEntityId() {
        return this.entityId;
    }

    public List<VPacketPlayOutUpdateAttributes.Snapshot> getSnapshots() {
        return this.snapshots;
    }

    public static class Snapshot {
        private final List<VPacketPlayOutUpdateAttributes.Snapshot.Modifier> modifiers = new ArrayList<>();
        private final String name;
        private final double baseValue;

        public String getName() {
            return this.name;
        }

        public double getBaseValue() {
            return this.baseValue;
        }

        public List<VPacketPlayOutUpdateAttributes.Snapshot.Modifier> getModifiers() {
            return this.modifiers;
        }

        public Snapshot(String name, double baseValue) {
            this.name = name;
            this.baseValue = baseValue;
        }

        public static class Modifier {
            private final double amount;
            private final int operation;
            private final String name;

            public double getAmount() {
                return this.amount;
            }

            public int getOperation() {
                return this.operation;
            }

            public String getName() {
                return this.name;
            }

            public Modifier(String name, double amount, int operation) {
                this.name = name;
                this.amount = amount;
                this.operation = operation;
            }
        }
    }
}
