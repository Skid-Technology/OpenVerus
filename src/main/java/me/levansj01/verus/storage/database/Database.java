package me.levansj01.verus.storage.database;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.storage.config.VerusConfiguration;
import me.levansj01.verus.storage.database.check.CheckValues;
import me.levansj01.verus.util.java.JavaV;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public abstract class Database {
    protected final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor(
        new ThreadFactoryBuilder().setPriority(3).setNameFormat(this.getClass().getSimpleName() + " Executor Thread").build()
    );

    public void getCheckBans(Check check, Consumer<Integer> consumer) {
        this.getCheckBans(check.getType().getName(), check.getSubType(), consumer);
    }

    public void getLogs(UUID uuid, Consumer<Iterable<Log>> consumer) {
        this.getLogs(uuid, 3000, consumer);
    }

    public abstract void loadAlerts(PlayerData playerData);

    public void stop() {
        this.executorService.shutdownNow().forEach(Runnable::run);
        JavaV.shutdownAndAwaitTermination(this.executorService, 5L, TimeUnit.SECONDS);
    }

    public abstract int getTotalLogs();

    public abstract void insertLog(Log log);

    public void loadCheckCommands(Map<String, CheckValues> values) {
    }

    public abstract void loadData(PlayerData playerData);

    public abstract void getLogs(UUID uuid, int limit, Consumer<Iterable<Log>> consumer);

    public void updateCheckPunish(Check id, boolean punish) {
        this.updateCheckPunish(id.identifier(), punish);
    }

    protected abstract UUID fetchUUID(String name);

    public void start() {
        if (StorageEngine.getInstance().getVerusConfig().isCleanup()) {
            this.executorService.scheduleAtFixedRate(this::cleanup, 5L, 60L, TimeUnit.MINUTES);
        }
    }

    public abstract void loadCheckValues(Check[] checks, Consumer<List<CheckValues>> consumer);

    public abstract void cleanup();

    protected long getPersistenceTime() {
        VerusConfiguration verusConfig = StorageEngine.getInstance().getVerusConfig();
        return System.currentTimeMillis() - verusConfig.getPersistenceMillis();
    }

    public abstract void updateCheckViolation(String id, int maxViolation);

    public abstract void getManualLogs(UUID uuid, int limit, Consumer<Iterable<Log>> consumer);

    public abstract void getBans(UUID uuid, int limit, Consumer<Iterable<Ban>> consumer);

    public void execute(Runnable sender, long args) {
        this.executorService.schedule(sender, args, TimeUnit.MILLISECONDS);
    }

    public abstract void connect(VerusConfiguration config);

    public abstract void insertBan(Ban ban);

    public abstract void updateCheckCommands(String id, List<String> commands);

    public void getUUID(String str, Consumer<UUID> consumer) {
        this.execute(() -> {
            Player playerExact = Bukkit.getPlayerExact(str);
            if (playerExact != null) {
                consumer.accept(playerExact.getUniqueId());
            } else {
                UUID uuid = this.fetchUUID(str);
                if (uuid == null) {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(str);
                    if (offlinePlayer != null && offlinePlayer.hasPlayedBefore()) {
                        consumer.accept(offlinePlayer.getUniqueId());
                    } else {
                        consumer.accept(null);
                    }
                } else {
                    consumer.accept(uuid);
                }
            }
        });
    }

    public abstract void updateCheckPunish(String id, boolean punish);

    public abstract void getLogs(String type, String subType, int limit, Consumer<Iterable<Log>> consumer);

    public abstract void updateAlerts(PlayerData playerData);

    public void execute(Runnable runnable) {
        this.executorService.execute(runnable);
    }

    protected boolean shouldPersist(long value) {
        VerusConfiguration verusConfig = StorageEngine.getInstance().getVerusConfig();
        return !verusConfig.isPersistence() ? false : System.currentTimeMillis() - value < verusConfig.getPersistenceMillis();
    }

    public abstract void removeData(PlayerData playerData);

    public abstract int getTotalBans();

    public void updateCheckAlerts(Check id, boolean alerts) {
        this.updateCheckAlerts(id.identifier(), alerts);
    }

    public abstract void updateData(PlayerData playerData);

    public void updateCheckViolation(Check id, int maxViolation) {
        this.updateCheckViolation(id.identifier(), maxViolation);
    }

    public abstract void updateCheckAlerts(String id, boolean alerts);

    public void getBans(UUID uuid, Consumer<Iterable<Ban>> consumer) {
        this.getBans(uuid, 10, consumer);
    }

    public abstract void getCheckBans(String type, String subType, Consumer<Integer> consumer);

    public abstract boolean isConnected();
}
