package me.levansj01.verus.data.manager;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.api.API;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.data.client.ClientType;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.storage.config.VerusConfiguration;
import me.levansj01.verus.util.java.JavaV;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.geysermc.floodgate.api.FloodgateApi;

public class DataManager {
    private static DataManager instance;
    private final Map<UUID, PlayerData> players = new ConcurrentHashMap<>();
    private final Map<Integer, WeakReference<PlayerData>> playersById;
    private final ExecutorService executorService;
    private final List<PlayerData> playerList = new CopyOnWriteArrayList<>();
    public final VerusPlugin plugin;

    public boolean isPlayer(int value) {
        return this.playersById.containsKey(value);
    }

    public static void disable() {
        if (instance != null) {
            JavaV.shutdownAndAwaitTermination(instance.executorService, 5L, TimeUnit.SECONDS);
            Thread thread = new Thread(() -> Bukkit.getOnlinePlayers().forEach(instance::uninject));
            thread.start();

            try {
                thread.join(TimeUnit.SECONDS.toMillis(5L));
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
            }

            for (PlayerData playerData : instance.getPlayers()) {
                playerData.release();
            }
        }

        instance = null;
    }

    public DataManager(VerusPlugin plugin) {
        this.playersById = new ConcurrentHashMap<>();
        this.executorService = NMSManager.getInstance().getDataExecutorService("Data Manager Thread %s");
        this.plugin = plugin;
    }

    public List<PlayerData> getMostViolations() {
        return this.players
            .values()
            .stream()
            .sorted(Collections.reverseOrder(Comparator.comparingDouble(PlayerData::getTotalViolations)))
            .collect(Collectors.toList());
    }

    public PlayerData getPlayer(Player player) {
        return this.getPlayer(player.getUniqueId());
    }

    public ExecutorService getExecutorService() {
        return this.executorService;
    }

    public void uninject(Player playerData) {
        PlayerData playerData2 = this.players.remove(playerData.getUniqueId());

        try {
            WeakReference weakReference = this.playersById.remove(playerData.getEntityId());
            if (weakReference != null) {
                weakReference.clear();
            }

            if (playerData2 != null) {
                playerData2.saveData();
                playerData2.setEnabled(false);
                NMSManager.getInstance().uninject(playerData2);
            }
        } finally {
            if (playerData2 != null) {
                playerData2.release();
            }
        }

        if (playerData2 != null) {
            this.playerList.remove(playerData2);
        }
    }

    public PlayerData getPlayer(UUID uuid) {
        return this.players.get(uuid);
    }

    public static DataManager getInstance() {
        return instance;
    }

    public void inject(Player playerData) {
        if (playerData != null && !playerData.hasMetadata("fake")) {
            PlayerData playerData2 = new PlayerData(playerData, this.plugin.getTypeLoader().loadChecks());
            StorageEngine storageEngine = StorageEngine.getInstance();
            VerusConfiguration verusConfig = storageEngine.getVerusConfig();
            NMSManager nmsManager = NMSManager.getInstance();
            API api = API.getAPI();
            if (api != null && api.fireInitEvent(playerData2)
                || verusConfig.isGeyser() && playerData.getName().startsWith(verusConfig.getGeyserPrefix())
                || nmsManager.isFloodGateAPI() && FloodgateApi.getInstance().isFloodgatePlayer(playerData.getUniqueId())) {
                playerData2.setEnabled(false);
                playerData2.getClientData().setType(ClientType.BEDROCK);
            }

            PlayerData playerData3 = this.players.put(playerData.getUniqueId(), playerData2);
            if (playerData3 != null) {
                this.playerList.remove(playerData3);
            }

            this.playersById.put(playerData.getEntityId(), new WeakReference<>(playerData2));
            this.playerList.add(playerData2);
            if (playerData2.isEnabled()) {
                nmsManager.inject(playerData2);
            }

            if (storageEngine.isConnected()) {
                playerData2.loadData();
            }
        }
    }

    public Map<Integer, WeakReference<PlayerData>> getPlayersById() {
        return this.playersById;
    }

    public Collection<PlayerData> getPlayers() {
        return this.players.values();
    }

    public List<PlayerData> getPlayerList() {
        return this.playerList;
    }

    public static void enable(VerusPlugin plugin) {
        instance = new DataManager(plugin);
        NMSManager.getInstance().postToMainThread(() -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                NMSManager.getInstance().setAsyncPotionEffects(player);
            }
        });
        instance.getExecutorService().execute(() -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                try {
                    instance.inject(player);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
    }

    public PlayerData getPlayer(int value) {
        WeakReference weakReference = this.playersById.get(value);
        return weakReference == null ? null : (PlayerData)weakReference.get();
    }

    public VerusPlugin getPlugin() {
        return this.plugin;
    }
}
