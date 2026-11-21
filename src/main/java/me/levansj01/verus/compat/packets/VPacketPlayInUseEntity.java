package me.levansj01.verus.compat.packets;

import java.lang.ref.WeakReference;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.data.manager.DataManager;
import org.bukkit.World;
import org.bukkit.entity.Entity;

public abstract class VPacketPlayInUseEntity<P> extends VPacket<P> {
    private static final int count = count();
    protected WeakReference<Entity> entity;
    protected double bodyX;
    protected VPacketPlayInUseEntity.EntityUseAction action;
    protected int id;
    protected double bodyY;
    protected double bodyZ;

    public boolean isPlayer() {
        DataManager dataManager = DataManager.getInstance();
        return dataManager == null ? false : dataManager.isPlayer(this.id);
    }

    public abstract Entity getEntity(World world);

    @Override
    public int ordinal() {
        return count;
    }

    public PlayerData getPlayerData() {
        DataManager dataManager = DataManager.getInstance();
        return dataManager == null ? null : dataManager.getPlayer(this.id);
    }

    @Override
    public void handle(PacketHandler listener) {
        listener.handle(this);
    }

    public double getBodyY() {
        return this.bodyY;
    }

    public double getBodyX() {
        return this.bodyX;
    }

    public double getBodyZ() {
        return this.bodyZ;
    }

    public VPacketPlayInUseEntity.EntityUseAction getAction() {
        return this.action;
    }

    public int getId() {
        return this.id;
    }

    public enum EntityUseAction {
        INTERACT,
        ATTACK,
        INTERACT_AT;

        public boolean isAttack() {
            return this == ATTACK;
        }

        public boolean isInteract() {
            return this == INTERACT;
        }

        public boolean isInteractAt() {
            return this == INTERACT_AT;
        }
    }
}
