package me.levansj01.verus.storage.database.impl;

import com.google.common.collect.Sets;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCommandException;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.data.CheckData;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.data.manager.DataManager;
import me.levansj01.verus.gui.impl.MainGUI;
import me.levansj01.verus.gui.manager.GUIManager;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.storage.config.VerusConfiguration;
import me.levansj01.verus.storage.database.Ban;
import me.levansj01.verus.storage.database.Database;
import me.levansj01.verus.storage.database.Log;
import me.levansj01.verus.storage.database.check.CheckValues;
import org.bson.Document;
import org.bson.conversions.Bson;

public class MongoDB extends Database {
    private MongoCollection<Document> dataCollection;
    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> logsCollection;
    private MongoClient mongoClient;
    private MongoCollection<Document> banCollection;
    private MongoCollection<Document> settingsCollection;
    private MongoCollection<Document> checkCollection;

    @Override
    public void getCheckBans(String type, String subType, Consumer<Integer> consumer) {
        this.execute(() -> {
            Bson filter = Filters.and(new Bson[]{Filters.eq("type", type), Filters.eq("subType", subType)});
            consumer.accept((int)this.banCollection.count(filter));
        });
    }

    @Override
    public void insertBan(Ban ban) {
        this.execute(() -> this.banCollection.insertOne(ban.toBson()));
    }

    public void resetLogs(UUID uuid) {
        this.execute(() -> this.logsCollection.deleteMany(Filters.eq("uuid", uuid)));
    }

    @Override
    public void getLogs(UUID uuid, int limit, Consumer<Iterable<Log>> consumer) {
        this.execute(
            () -> consumer.accept(this.logsCollection.find(Filters.eq("uuid", uuid)).limit(limit).sort(Indexes.descending(new String[]{"_id"})).map(Log::fromBson))
        );
    }

    @Override
    public boolean isConnected() {
        return this.mongoClient != null
            && this.logsCollection != null
            && this.banCollection != null
            && this.dataCollection != null
            && this.settingsCollection != null;
    }

    @Override
    public int getTotalLogs() {
        return (int)this.logsCollection.count();
    }

    @Override
    public void getLogs(String type, String subType, int limit, Consumer<Iterable<Log>> consumer) {
        this.execute(
            () -> consumer.accept(
                this.logsCollection
                    .find(Filters.and(new Bson[]{Filters.eq("type", type), Filters.eq("subType", subType)}))
                    .limit(limit)
                    .sort(Indexes.descending(new String[]{"_id"}))
                    .map(Log::fromBson)
            )
        );
    }

    @Override
    public void cleanup() {
        if (this.isConnected()) {
            VerusConfiguration verusConfig = StorageEngine.getInstance().getVerusConfig();
            long now = System.currentTimeMillis();
            long value = now - TimeUnit.DAYS.toMillis(verusConfig.getLogsCleanupDays());
            long value2 = now - TimeUnit.DAYS.toMillis(verusConfig.getBansCleanupDays());
            long value3 = now - verusConfig.getPersistenceMillis();
            Document document = (Document)this.mongoDatabase
                .runCommand(new Document("collStats", this.logsCollection.getNamespace().getCollectionName()), Document.class);
            if (!document.getBoolean("capped", false)) {
                this.logsCollection.deleteMany(Filters.lte("timestamp", value));
            }

            this.banCollection.deleteMany(Filters.lte("timestamp", value2));
            this.dataCollection.deleteMany(Filters.lte("time", value3));
        }
    }

    @Override
    public void updateCheckAlerts(String id, boolean alerts) {
        this.execute(() -> this.checkCollection.updateOne(Filters.eq("id", id), Updates.set("alerts", alerts)));
    }

    @Override
    public void insertLog(Log log) {
        this.execute(() -> this.logsCollection.insertOne(log.toBson()));
    }

    @Override
    public void updateCheckCommands(String id, List<String> commands) {
        this.execute(() -> this.checkCollection.updateOne(Filters.eq("id", id), Updates.set("commands", commands)));
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void removeData(PlayerData playerData) {
        this.execute(() -> this.dataCollection.deleteMany(Filters.eq("_id", playerData.getUuid())));
    }

    @Override
    protected UUID fetchUUID(String name) {
        Pattern pattern = Pattern.compile("^" + name + "$", 2);

        for (MongoCollection collection : Arrays.asList(this.logsCollection, this.banCollection)) {
            Document document = (Document)collection.find(Filters.eq("name", pattern)).projection(Projections.include(new String[]{"uuid"})).limit(1).first();
            if (document != null) {
                return (UUID)document.get("uuid", UUID.class);
            }
        }

        return null;
    }

    @Override
    public void loadData(PlayerData playerData) {
        this.execute(() -> {
            FindIterable findIterable = this.dataCollection.find(Filters.eq("_id", playerData.getUuid())).limit(1);
            Document document = (Document)findIterable.first();
            if (document != null && playerData.isEnabled()) {
                Document document2 = (Document)document.get("checkData", Document.class);
                if (document2 != null) {
                    long longValue = document.getLong("time");
                    if (this.shouldPersist(longValue)) {
                        CheckData checkData = playerData.getCheckData();

                        for (Check check : checkData.getOriginalChecks()) {
                            Number number = (Number)document2.get(check.identifier(), Number.class);
                            if (number != null) {
                                checkData.load(check, number.doubleValue());
                            }
                        }
                    }
                }
            }
        }, 100L);
    }

    @Override
    public void loadAlerts(PlayerData playerData) {
        this.execute(
            () -> {
                FindIterable findIterable = this.settingsCollection
                    .find(Filters.eq("_id", playerData.getUuid()))
                    .projection(Projections.include(new String[]{"alerts"}))
                    .limit(1);
                Document document = (Document)findIterable.first();
                if (document != null) {
                    playerData.setAlerts(document.getBoolean("alerts", playerData.isAlerts()));
                }
            }
        );
    }

    @Override
    public void updateCheckPunish(String id, boolean punish) {
        this.execute(() -> this.checkCollection.updateOne(Filters.eq("id", id), Updates.set("punish", punish)));
    }

    @Override
    public void stop() {
        super.stop();
        if (this.isConnected()) {
            this.mongoClient.close();
            this.mongoClient = null;
        }
    }

    @Override
    public void updateCheckViolation(String id, int maxViolation) {
        this.execute(() -> this.checkCollection.updateOne(Filters.eq("id", id), Updates.set("maxViolation", maxViolation)));
    }

    @Override
    public void connect(VerusConfiguration config) {
        this.execute(() -> {
            ServerAddress serverAddress = new ServerAddress(config.getMongoHost(), config.getMongoPort());
            MongoClientOptions mongoClientOptions = MongoClientOptions.builder().build();
            if (config.isMongoAuthEnabled()) {
                this.mongoClient = new MongoClient(serverAddress, Collections.singletonList(config.getMongoCredential()), mongoClientOptions);
            } else {
                this.mongoClient = new MongoClient(serverAddress, mongoClientOptions);
            }

            this.mongoDatabase = this.mongoClient.getDatabase(config.getMongoDatabase());
            if (!((HashSet)this.mongoDatabase.listCollectionNames().into(Sets.newHashSet())).contains("logs")) {
                this.mongoDatabase.createCollection("logs", new CreateCollectionOptions().capped(true).maxDocuments(1000000L).sizeInBytes(500000000L));
            }

            this.logsCollection = this.mongoDatabase.getCollection("logs");
            this.banCollection = this.mongoDatabase.getCollection("bans");
            this.dataCollection = this.mongoDatabase.getCollection("playerdata");
            this.settingsCollection = this.mongoDatabase.getCollection("settings");
            this.checkCollection = this.mongoDatabase.getCollection(StorageEngine.getInstance().getVerusConfig().getCheckDatabaseName());
            this.logsCollection.createIndex(Indexes.ascending(new String[]{"uuid"}));
            this.logsCollection.createIndex(Indexes.text("name"));

            try {
                this.logsCollection.createIndex(Indexes.descending(new String[]{"_id"}));
            } catch (MongoCommandException exception) {
                this.logsCollection.createIndex(Indexes.ascending(new String[]{"_id"}));
            }

            this.banCollection.createIndex(Indexes.ascending(new String[]{"uuid"}));
            this.banCollection.createIndex(Indexes.ascending(new String[]{"_id"}));
            this.banCollection.createIndex(Indexes.text("name"));
            this.dataCollection.createIndex(Indexes.ascending(new String[]{"_id"}));
            this.settingsCollection.createIndex(Indexes.ascending(new String[]{"_id"}));
            this.checkCollection.createIndex(Indexes.ascending(new String[]{"_id"}));
            MainGUI mainGui = GUIManager.getInstance().getMainGui();
            if (mainGui != null) {
                mainGui.updateInfoStack();
            }

            DataManager dataManager = DataManager.getInstance();
            if (dataManager != null) {
                dataManager.getPlayers().forEach(PlayerData::loadData);
            }
        });
    }

    @Override
    public void updateData(PlayerData playerData) {
        this.execute(() -> {
            Document document = new Document("_id", playerData.getUuid());
            Document document2 = new Document();
            playerData.getCheckData().update().forEach(check -> document2.put(check.identifier(), check.getViolations()));
            if (!document2.isEmpty()) {
                document.put("time", System.currentTimeMillis());
                document.put("checkData", document2);
                this.dataCollection.replaceOne(Filters.eq("_id", playerData.getUuid()), document, new UpdateOptions().upsert(true));
            } else {
                this.dataCollection.deleteOne(Filters.eq("_id", playerData.getUuid()));
            }
        });
    }

    @Override
    public void loadCheckValues(Check[] checks, Consumer<List<CheckValues>> consumer) {
        if (consumer != null) {
            this.execute(
                () -> {
                    ArrayList list = new ArrayList();

                    for (Check check : checks) {
                        Document document = (Document)this.checkCollection.find(Filters.eq("id", check.identifier())).first();
                        CheckValues checkValues = new CheckValues(check);
                        if (document == null) {
                            this.checkCollection
                                .insertOne(
                                    new Document("id", check.identifier())
                                        .append("alerts", true)
                                        .append("punish", true)
                                        .append("maxViolation", check.getMaxViolation())
                                        .append("commands", new ArrayList())
                                );
                            list.add(checkValues);
                        } else {
                            checkValues.setAlert(document.getBoolean("alerts", true));
                            checkValues.setPunish(document.getBoolean("punish", true));
                            checkValues.setMaxViolation(document.getInteger("maxViolation", check.getMaxViolation()));
                            checkValues.setCommands((List<String>)document.get("commands", new ArrayList()));
                            list.add(checkValues);
                        }
                    }

                    consumer.accept(list);
                }
            );
        }
    }

    @Override
    public int getTotalBans() {
        return (int)this.banCollection.count();
    }

    @Override
    public void getManualLogs(UUID uuid, int limit, Consumer<Iterable<Log>> consumer) {
        this.execute(
            () -> consumer.accept(
                this.logsCollection
                    .find(Filters.and(new Bson[]{Filters.eq("uuid", uuid), Filters.eq("type", "Manual")}))
                    .limit(limit)
                    .sort(Indexes.descending(new String[]{"_id"}))
                    .map(Log::fromBson)
            )
        );
    }

    @Override
    public void getBans(UUID uuid, int limit, Consumer<Iterable<Ban>> consumer) {
        this.execute(
            () -> consumer.accept(this.banCollection.find(Filters.eq("uuid", uuid)).limit(limit).sort(Indexes.descending(new String[]{"_id"})).map(Ban::fromBson))
        );
    }

    @Override
    public void updateAlerts(PlayerData playerData) {
        this.execute(() -> {
            if (playerData.isAlerts()) {
                Document document = new Document("_id", playerData.getUuid());
                document.put("alerts", playerData.isAlerts());
                this.settingsCollection.replaceOne(Filters.eq("_id", playerData.getUuid()), document, new UpdateOptions().upsert(true));
            } else {
                this.settingsCollection.deleteOne(Filters.eq("_id", playerData.getUuid()));
            }
        });
    }
}
