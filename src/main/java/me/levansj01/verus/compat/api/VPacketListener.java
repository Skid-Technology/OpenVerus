package me.levansj01.verus.compat.api;

import java.util.function.Consumer;
import java.util.logging.Level;
import me.levansj01.launcher.VerusLauncher;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.data.PlayerData;

public abstract class VPacketListener<I, O> implements Consumer<Object> {
    protected final PlayerData data;

    public VPacketListener(PlayerData data) {
        this.data = data;
    }

    protected <T extends I> void handleIn(Object obj, VPacket<T> packet) {
        packet.accept((T)obj);
        this.data.preProcess(packet);
        packet.handle(this.data);
        this.data.handlePacketListeners(packet);
        this.data.postProcess(packet);
    }

    @Override
    public void accept(Object packet) {
        if (this.data.isEnabled()) {
            try {
                this.process(packet);
            } catch (Throwable throwable) {
                VerusLauncher.getPlugin().getLogger().log(Level.SEVERE, "Failed to handle outgoing " + packet.getClass().getSimpleName() + ": ", throwable);
            }
        }
    }

    protected abstract void process(Object obj);

    protected <T extends O> void handleOut(Object obj, VPacket<T> packet) {
        packet.accept((T)obj);
        packet.handle(this.data);
    }
}
