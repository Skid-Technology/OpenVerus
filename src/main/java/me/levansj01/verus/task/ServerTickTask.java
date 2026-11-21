package me.levansj01.verus.task;

import java.util.concurrent.atomic.AtomicBoolean;
import me.levansj01.launcher.VerusLauncher;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.data.manager.DataManager;
import me.levansj01.verus.storage.StorageEngine;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class ServerTickTask implements Runnable {
    private Runnable endTask;
    private final AtomicBoolean cancel = new AtomicBoolean();
    public static ServerTickTask instance;
    private long lastTransaction;
    private BukkitTask bukkitTask;
    private long lastTick;
    private long tick;

    public BukkitTask getBukkitTask() {
        return this.bukkitTask;
    }

    public long getTick() {
        return this.tick;
    }

    public long getLastTick() {
        return this.lastTick;
    }

    @Override
    public void run() {
        this.lastTick = this.tick;
        this.tick = System.currentTimeMillis();
        if (StorageEngine.getInstance().getVerusConfig().isMoreTransactions() || this.tick - this.lastTransaction >= 250L) {
            this.lastTransaction = this.tick;
            this.sendTransactions();
        }
    }

    public void sendTransactions() {
        if (!this.cancel.get()) {
            NMSManager nmsManager = NMSManager.getInstance();

            for (PlayerData playerData : DataManager.getInstance().getPlayerList()) {
                nmsManager.sendTransaction(playerData.getPlayer(), playerData.incrementTransactionId());
            }
        }
    }

    public void schedule() {
        this.bukkitTask = Bukkit.getScheduler().runTaskTimer(VerusLauncher.getPlugin(), this, 1L, 1L);
        if (StorageEngine.getInstance().getVerusConfig().isMoreTransactions()) {
            NMSManager nmsManager = NMSManager.getInstance();
            nmsManager.postToMainThread(() -> {
                if (!this.cancel.get()) {
                    this.endTask = nmsManager.scheduleEnd(this::sendTransactions);
                }
            });
        }
    }

    public boolean isLagging(long value) {
        return value - this.tick > 750L || this.tick - this.lastTick > 750L;
    }

    public long getLastTransaction() {
        return this.lastTransaction;
    }

    public AtomicBoolean getCancel() {
        return this.cancel;
    }

    public static ServerTickTask getInstance() {
        return instance == null ? (instance = new ServerTickTask()) : instance;
    }

    public Runnable getEndTask() {
        return this.endTask;
    }

    public void cancel() {
        this.cancel.set(true);
        if (this.bukkitTask != null) {
            this.bukkitTask.cancel();
        }

        if (this.endTask != null) {
            NMSManager.getInstance().postToMainThread(this.endTask);
        }
    }
}
