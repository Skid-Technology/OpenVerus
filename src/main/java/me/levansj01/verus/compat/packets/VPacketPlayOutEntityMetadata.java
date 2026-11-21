package me.levansj01.verus.compat.packets;

import java.util.ArrayList;
import java.util.List;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;

public abstract class VPacketPlayOutEntityMetadata<P> extends VPacket<P> {
    private static final int count = count();
    protected int entityId;
    protected List<VPacketPlayOutEntityMetadata.MetadataValue> values = new ArrayList<>();

    @Override
    public void handle(PacketHandler listener) {
        listener.handle(this);
    }

    public List<VPacketPlayOutEntityMetadata.MetadataValue> getValues() {
        return this.values;
    }

    @Override
    public int ordinal() {
        return count;
    }

    public int getEntityId() {
        return this.entityId;
    }

    public static class MetadataValue {
        private final int index;
        private final Object object;

        public Object getObject() {
            return this.object;
        }

        public int getIndex() {
            return this.index;
        }

        public MetadataValue(int index, Object object) {
            this.index = index;
            this.object = object;
        }
    }
}
