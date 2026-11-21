package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;

public abstract class VPacketPlayInEntityAction<P> extends VPacket<P> {
    private static final int count = count();
    protected int value;
    protected VPacketPlayInEntityAction.PlayerAction action;

    public int getValue() {
        return this.value;
    }

    public VPacketPlayInEntityAction.PlayerAction getAction() {
        return this.action;
    }

    @Override
    public void handle(PacketHandler listener) {
        listener.handle(this);
    }

    @Override
    public int ordinal() {
        return count;
    }

    public enum PlayerAction {
        START_SNEAKING(VPacketPlayInEntityAction.PlayerAction.Type.SNEAK, true),
        STOP_SNEAKING(VPacketPlayInEntityAction.PlayerAction.Type.SNEAK, false),
        STOP_SLEEPING(false),
        START_SPRINTING(VPacketPlayInEntityAction.PlayerAction.Type.SPRINT, true),
        STOP_SPRINTING(VPacketPlayInEntityAction.PlayerAction.Type.SPRINT, false),
        RIDING_JUMP(true),
        OPEN_INVENTORY(true),
        START_FALL_FLYING(true);

        private final VPacketPlayInEntityAction.PlayerAction.Type type;
        private final boolean value;

        public boolean isValue() {
            return this.value;
        }

        public VPacketPlayInEntityAction.PlayerAction.Type getType() {
            return this.type;
        }

        PlayerAction(boolean flag) {
            this(VPacketPlayInEntityAction.PlayerAction.Type.OTHER, flag);
        }

        public boolean isSneak() {
            return this.type == VPacketPlayInEntityAction.PlayerAction.Type.SNEAK;
        }

        PlayerAction(VPacketPlayInEntityAction.PlayerAction.Type type, boolean value) {
            this.type = type;
            this.value = value;
        }

        public boolean isSprint() {
            return this.type == VPacketPlayInEntityAction.PlayerAction.Type.SPRINT;
        }

        public enum Type {
            SNEAK,
            SPRINT,
            OTHER;
        }
    }
}
