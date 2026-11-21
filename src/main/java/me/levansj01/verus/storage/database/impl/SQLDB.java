package me.levansj01.verus.storage.database.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Spliterators;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.Spliterators.AbstractSpliterator;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.stream.Collectors;
import me.levansj01.launcher.VerusLauncher;
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
import me.levansj01.verus.storage.database.pool.ConnectionPool;
import me.levansj01.verus.storage.database.pool.ConnectionType;
import me.levansj01.verus.util.java.JavaV;

public class SQLDB extends Database {
    private final LinkedBlockingQueue<Log> logQueue = new LinkedBlockingQueue<>();
    private final LinkedBlockingQueue<Ban> banQueue = new LinkedBlockingQueue<>();
    private AtomicInteger totalBans;
    private AtomicInteger totalLogs;
    private ConnectionPool connectionPool;
    private ConnectionType type;

    private PreparedStatement getLogStatement(Connection connection) throws SQLException {
        return this.type == ConnectionType.MYSQL
            ? connection.prepareStatement(
                "insert into logs (uuid, name, timestamp, checkType, subType, violations, ping, `lag`, data) values (?, ?, ?, ?, ?, ?, ?, ?, ?)"
            )
            : connection.prepareStatement(
                "insert into logs (uuid, name, timestamp, checkType, subType, violations, ping, lag, data) values (?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );
    }

    public void pushLogs() {
        if ((!this.logQueue.isEmpty() || !this.banQueue.isEmpty()) && this.isConnected()) {
            try (
                Connection connection = this.connectionPool.getConnection();
                PreparedStatement logStatement = this.getLogStatement(connection);
                PreparedStatement banStatement = connection.prepareStatement(
                    "insert into bans (uuid, name, timestamp, checkType, subType) values (?, ?, ?, ?, ?)"
                );
            ) {
                Log log;
                while ((log = this.logQueue.poll()) != null) {
                    logStatement.setString(1, log.getUuid().toString());
                    logStatement.setString(2, log.getName());
                    logStatement.setLong(3, log.getTimestamp());
                    logStatement.setString(4, log.getType());
                    logStatement.setString(5, log.getSubType());
                    logStatement.setInt(6, Math.min(log.getViolations(), 32767));
                    logStatement.setInt(7, Math.min(log.getPing(), 32767));
                    logStatement.setInt(8, Math.min(log.getLag(), 32767));
                    logStatement.setString(9, JavaV.firstNonNull(log.getData(), ""));
                    logStatement.addBatch();
                }

                logStatement.executeBatch();

                Ban ban;
                while ((ban = this.banQueue.poll()) != null) {
                    banStatement.setString(1, ban.getUuid().toString());
                    banStatement.setString(2, ban.getName());
                    banStatement.setLong(3, ban.getTimestamp());
                    banStatement.setString(4, ban.getType());
                    banStatement.setString(5, ban.getSubType());
                    banStatement.addBatch();
                }

                banStatement.executeBatch();
                connection.commit();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void start() {
        super.start();
        this.executorService.scheduleAtFixedRate(this::pushLogs, 1L, StorageEngine.getInstance().getVerusConfig().getSqlPushSeconds(), TimeUnit.SECONDS);
    }

    @Override
    protected UUID fetchUUID(String name) {
        try (Connection connection = this.connectionPool.getConnection()) {
            try (PreparedStatement logsStatement = connection.prepareStatement("select uuid from logs where LOWER(name) = LOWER(?) LIMIT 1")) {
                logsStatement.setString(1, name);

                try (ResultSet resultSet = logsStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return UUID.fromString(resultSet.getString(1));
                    }
                }
            }

            try (PreparedStatement bansStatement = connection.prepareStatement("select uuid from bans where LOWER(name) = LOWER(?) LIMIT 1")) {
                bansStatement.setString(1, name);

                try (ResultSet resultSet = bansStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return UUID.fromString(resultSet.getString(1));
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return null;
    }

    @Override
    public void stop() {
        if (!this.logQueue.isEmpty()) {
            this.pushLogs();
        }

        super.stop();
        if (this.connectionPool != null) {
            this.connectionPool.close();
        }
    }

    @Override
    public void connect(VerusConfiguration config) {
        try {
            this.connectionPool = new ConnectionPool(
                config.getSqlPool(),
                config.getSqlUsername(),
                config.getSqlPassword(),
                config.getSqlHost(),
                config.getSqlDatabase(),
                config.getSqlPort(),
                config.getSqlConnectionType()
            );
            this.type = config.getSqlConnectionType();
            this.execute(
                () -> {
                    try (Connection connection = this.connectionPool.getConnection()) {
                        switch (this.type) {
                            case MYSQL:
                                connection.prepareStatement(
                                        "create table if not exists logs ( `uuid` VARCHAR(36) NOT NULL , `name` VARCHAR(16) NOT NULL , `timestamp` BIGINT NOT NULL , `checkType` VARCHAR(32) NOT NULL , `subType` VARCHAR(32) NOT NULL , `violations` SMALLINT NOT NULL , `ping` SMALLINT NOT NULL , `lag` SMALLINT NOT NULL , `data` VARCHAR(128) , INDEX (uuid, name))"
                                    )
                                    .execute();
                                connection.prepareStatement(
                                        "create table if not exists bans ( `uuid` VARCHAR(36) NOT NULL , `name` VARCHAR(16) NOT NULL , `timestamp` BIGINT NOT NULL , `checkType` VARCHAR(32) NOT NULL , `subType` VARCHAR(32) NOT NULL , INDEX (uuid, name))"
                                    )
                                    .execute();
                                connection.prepareStatement(
                                        "create table if not exists playerdata ( `uuid` VARCHAR(36) NOT NULL PRIMARY KEY , `alerts` TINYINT NOT NULL , UNIQUE INDEX (uuid))"
                                    )
                                    .execute();
                                if (config.isPersistence()) {
                                    connection.prepareStatement(
                                            "create table if not exists persistenceId ( `uuid` VARCHAR(36) NOT NULL PRIMARY KEY , `id` int NOT NULL AUTO_INCREMENT UNIQUE KEY , `timestamp` BIGINT NOT NULL , INDEX (uuid, id))"
                                        )
                                        .execute();
                                    connection.prepareStatement(
                                            "create table if not exists persistenceData ( `id` int NOT NULL , `checkId` VARCHAR(8) NOT NULL , `violations` DOUBLE NOT NULL , INDEX (id))"
                                        )
                                        .execute();
                                }

                                if (config.isDatabaseCheckPersistence()) {
                                    PreparedStatement statement = connection.prepareStatement(
                                        "create table if not exists ? ( `id` VARCHAR(3) NOT NULL PRIMARY KEY , `alert` BOOLEAN NOT NULL , `punish` BOOLEAN NOT NULL , `maxViolation` int NOT NULL , UNIQUE INDEX (id))"
                                    );
                                    statement.setString(1, config.getCheckDatabaseName());
                                    statement.execute();
                                }
                                break;
                            default:
                                connection.prepareStatement(
                                        "create table if not exists logs ( uuid VARCHAR(36) NOT NULL , name VARCHAR(16) NOT NULL , timestamp BIGINT NOT NULL , checkType VARCHAR(32) NOT NULL , subType VARCHAR(32) NOT NULL , violations SMALLINT NOT NULL , ping SMALLINT NOT NULL , lag SMALLINT NOT NULL , data VARCHAR(128))"
                                    )
                                    .execute();
                                connection.prepareStatement("create INDEX IF NOT EXISTS logs_uuid_index ON logs (uuid)").execute();
                                connection.prepareStatement("create INDEX IF NOT EXISTS logs_name_index ON logs (name)").execute();
                                connection.prepareStatement(
                                        "create table if not exists bans ( uuid VARCHAR(36) NOT NULL , name VARCHAR(16) NOT NULL , timestamp BIGINT NOT NULL , checkType VARCHAR(32) NOT NULL , subType VARCHAR(32) NOT NULL )"
                                    )
                                    .execute();
                                connection.prepareStatement("create INDEX IF NOT EXISTS bans_uuid_index ON bans (uuid)").execute();
                                connection.prepareStatement("create INDEX IF NOT EXISTS bans_name_index ON bans (name)").execute();
                                connection.prepareStatement(
                                        "create table if not exists playerdata ( uuid VARCHAR(36) NOT NULL PRIMARY KEY , alerts BOOLEAN NOT NULL )"
                                    )
                                    .execute();
                                if (config.isPersistence()) {
                                    connection.prepareStatement("create UNIQUE INDEX IF NOT EXISTS playerdata_uuid_index ON playerdata (uuid)").execute();
                                    connection.prepareStatement(
                                            "create table if not exists persistenceId ( uuid VARCHAR(36) NOT NULL PRIMARY KEY , id int NOT NULL SERIAL UNIQUE KEY , timestamp BIGINT NOT NULL )"
                                        )
                                        .execute();
                                    connection.prepareStatement("create UNIQUE INDEX IF NOT EXISTS persistenceId_uuid_index ON persistenceId (uuid)").execute();
                                    connection.prepareStatement("create UNIQUE INDEX IF NOT EXISTS persistenceId_id_index ON persistenceId (id)").execute();
                                    connection.prepareStatement(
                                            "create table if not exists persistenceData ( id int NOT NULL , checkId VARCHAR(8) NOT NULL , violations DOUBLE NOT NULL )"
                                        )
                                        .execute();
                                    connection.prepareStatement("create INDEX IF NOT EXISTS persistenceData_id_index ON persistenceData (id)").execute();
                                }

                                if (config.isDatabaseCheckPersistence()) {
                                    PreparedStatement statement = connection.prepareStatement(
                                        "create table if not exists ? ( id VARCHAR(3) NOT NULL PRIMARY KEY , alert BOOLEAN NOT NULL , punish BOOLEAN NOT NULL , maxViolation int NOT NULL )"
                                    );
                                    statement.setString(1, config.getCheckDatabaseName());
                                    statement.execute();
                                    statement = connection.prepareStatement("create UNIQUE INDEX IF NOT EXISTS check_id_index ON ? (id)");
                                    statement.setString(1, config.getCheckDatabaseName());
                                    statement.execute();
                                }
                        }

                        try (ResultSet resultSet = connection.prepareStatement("select COUNT(*) from logs").executeQuery()) {
                            if (resultSet.next()) {
                                this.totalLogs = new AtomicInteger(resultSet.getInt(1));
                            }
                        }

                        try (ResultSet resultSet = connection.prepareStatement("SELECT COUNT(*) from bans").executeQuery()) {
                            if (resultSet.next()) {
                                this.totalBans = new AtomicInteger(resultSet.getInt(1));
                            }
                        }

                        connection.commit();
                        MainGUI mainGUI = GUIManager.getInstance().getMainGui();
                        if (mainGUI != null) {
                            mainGUI.updateInfoStack();
                        }

                        DataManager dataManager = DataManager.getInstance();
                        if (dataManager != null) {
                            dataManager.getPlayers().forEach(PlayerData::loadData);
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            );
        } catch (Throwable throwable) {
            VerusLauncher.getPlugin()
                .getLogger()
                .log(Level.SEVERE, "Failed to connect to " + config.getSqlConnectionType().name() + ", please check your database details: ", throwable);
        }
    }

    @Override
    public void insertLog(Log log) {
        this.logQueue.add(log);
        if (this.totalLogs != null) {
            this.totalLogs.incrementAndGet();
        }
    }

    @Override
    public void insertBan(Ban ban) {
        this.banQueue.add(ban);
        if (this.totalBans != null) {
            this.totalBans.incrementAndGet();
        }
    }

    public void getLogs(SQLDB.SQLExecutor function, UUID uuid, int limit, Consumer<Iterable<Log>> consumer) {
        this.execute(
            () -> {
                try (Connection connection = this.connectionPool.getConnection()) {
                    PreparedStatement preparedStatement = function.apply(connection);

                    try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                        Iterator<Log> iterator = Spliterators.iterator(
                            new AbstractSpliterator<Log>(limit, 16) {
                                @Override
                                public boolean tryAdvance(Consumer<? super Log> consumer2) {
                                    try {
                                        if (!resultSet.next()) {
                                            return false;
                                        }

                                        consumer2.accept(
                                            new Log(
                                                uuid == null ? UUID.fromString(resultSet.getString(1)) : uuid,
                                                resultSet.getString(2),
                                                resultSet.getLong(3),
                                                resultSet.getString(4),
                                                resultSet.getString(5),
                                                resultSet.getInt(6),
                                                resultSet.getInt(7),
                                                resultSet.getInt(8),
                                                resultSet.getString(9)
                                            )
                                        );
                                        return true;
                                    } catch (SQLException exception) {
                                        exception.printStackTrace();
                                        return false;
                                    }
                                }
                            }
                        );
                        consumer.accept(() -> iterator);
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        );
    }

    @Override
    public void getLogs(UUID uuid, int limit, Consumer<Iterable<Log>> consumer) {
        this.getLogs(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from logs where uuid=? order by timestamp desc limit ?");
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setInt(2, limit);
            return preparedStatement;
        }, uuid, limit, consumer);
    }

    @Override
    public void getLogs(String type, String subType, int limit, Consumer<Iterable<Log>> consumer) {
        this.getLogs(
            connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from logs where checkType=? and subType=? order by timestamp desc limit ?"
                );
                preparedStatement.setString(1, type);
                preparedStatement.setString(2, subType);
                preparedStatement.setInt(3, limit);
                return preparedStatement;
            },
            null,
            limit,
            consumer
        );
    }

    @Override
    public void getManualLogs(UUID uuid, int limit, Consumer<Iterable<Log>> consumer) {
        this.getLogs(
            connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from logs where uuid=? and checkType=? order by timestamp desc limit ?"
                );
                preparedStatement.setString(1, uuid.toString());
                preparedStatement.setString(2, "Manual");
                preparedStatement.setInt(3, limit);
                return preparedStatement;
            },
            null,
            limit,
            consumer
        );
    }

    @Override
    public void getBans(UUID uuid, int limit, Consumer<Iterable<Ban>> consumer) {
        this.execute(() -> {
            try (
                Connection connection = this.connectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("select * from bans where uuid=? order by timestamp desc limit ?");
            ) {
                preparedStatement.setString(1, uuid.toString());
                preparedStatement.setInt(2, limit);

                try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                    Iterator<Ban> iterator = Spliterators.iterator(new AbstractSpliterator<Ban>(limit, 16) {
                        @Override
                        public boolean tryAdvance(Consumer<? super Ban> consumer2) {
                            try {
                                if (!resultSet.next()) {
                                    return false;
                                }

                                consumer2.accept(new Ban(uuid, resultSet.getString(2), resultSet.getLong(3), resultSet.getString(4), resultSet.getString(5)));
                                return true;
                            } catch (SQLException exception) {
                                exception.printStackTrace();
                                return false;
                            }
                        }
                    });
                    consumer.accept(() -> iterator);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    @Override
    public int getTotalLogs() {
        return this.totalLogs == null ? 0 : this.totalLogs.get();
    }

    @Override
    public int getTotalBans() {
        return this.totalBans == null ? 0 : this.totalBans.get();
    }

    @Override
    public void getCheckBans(String type, String subType, Consumer<Integer> consumer) {
        this.execute(() -> {
            try (
                Connection connection = this.connectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM bans WHERE checkType=? AND subType=?");
            ) {
                preparedStatement.setString(1, type);
                preparedStatement.setString(2, subType);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        consumer.accept(resultSet.getInt(1));
                    } else {
                        consumer.accept(0);
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    @Override
    public void updateCheckAlerts(String id, boolean alerts) {
        this.execute(() -> {
            try (
                Connection connection = this.connectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("REPLACE INTO ? (alert) VALUES (?) WHERE id=?");
            ) {
                preparedStatement.setString(1, StorageEngine.getInstance().getVerusConfig().getCheckDatabaseName());
                preparedStatement.setBoolean(2, alerts);
                preparedStatement.setString(3, id);
                preparedStatement.execute();
                connection.commit();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    @Override
    public void updateCheckPunish(String id, boolean punish) {
        this.execute(() -> {
            try (
                Connection connection = this.connectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("REPLACE INTO ? (punish) VALUES (?) WHERE id=?");
            ) {
                preparedStatement.setString(1, StorageEngine.getInstance().getVerusConfig().getCheckDatabaseName());
                preparedStatement.setBoolean(2, punish);
                preparedStatement.setString(3, id);
                preparedStatement.execute();
                connection.commit();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    @Override
    public void updateCheckViolation(String id, int maxViolation) {
        this.execute(() -> {
            try (
                Connection connection = this.connectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("REPLACE INTO ? (maxViolation) VALUES (?) WHERE id=?");
            ) {
                preparedStatement.setString(1, StorageEngine.getInstance().getVerusConfig().getCheckDatabaseName());
                preparedStatement.setInt(2, maxViolation);
                preparedStatement.setString(3, id);
                preparedStatement.execute();
                connection.commit();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    @Override
    public void updateCheckCommands(String id, List<String> commands) {
        this.execute(() -> {
            try (
                Connection connection = this.connectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("REPLACE INTO ? (commands) VALUES (?) WHERE id=?");
            ) {
                preparedStatement.setString(1, StorageEngine.getInstance().getVerusConfig().getCheckCommandsDatabaseName());
                preparedStatement.setString(2, String.join(",", commands));
                preparedStatement.setString(3, id);
                preparedStatement.execute();
                connection.commit();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    @Override
    public void loadCheckCommands(Map<String, CheckValues> values) {
        this.execute(() -> {
            try (
                Connection connection = this.connectionPool.getConnection();
                PreparedStatement selectStatement = connection.prepareStatement("SELECT commands FROM ? WHERE id=?");
                PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO ? (id, commands) VALUES (?, ?)");
            ) {
                String database = StorageEngine.getInstance().getVerusConfig().getCheckCommandsDatabaseName();
                selectStatement.setString(1, database);
                insertStatement.setString(1, database);

                for (Entry<String, CheckValues> entry : values.entrySet()) {
                    String checkId = entry.getKey();
                    CheckValues value = entry.getValue();
                    selectStatement.setString(2, checkId);

                    try (ResultSet resultSet = selectStatement.executeQuery()) {
                        if (resultSet.next()) {
                            value.setCommands(Arrays.stream(resultSet.getString("commands").split(",")).collect(Collectors.toList()));
                        } else {
                            insertStatement.setString(2, value.getCheckId());
                            insertStatement.setString(3, "");
                            insertStatement.addBatch();
                        }
                    }
                }

                insertStatement.executeBatch();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        });
    }

    @Override
    public void loadCheckValues(Check[] checks, Consumer<List<CheckValues>> consumer) {
        if (consumer != null) {
            this.execute(() -> {
                List<CheckValues> list = new ArrayList<>();

                try (
                    Connection connection = this.connectionPool.getConnection();
                    PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM ? WHERE id=?");
                    PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO ? (id, alert, punish, maxViolation) VALUES (?, ?, ?, ?)");
                ) {
                    selectStatement.setString(1, StorageEngine.getInstance().getVerusConfig().getCheckDatabaseName());
                    insertStatement.setString(1, StorageEngine.getInstance().getVerusConfig().getCheckDatabaseName());

                    for (Check check : checks) {
                        selectStatement.setString(2, check.identifier());

                        try (ResultSet resultSet = selectStatement.executeQuery()) {
                            if (resultSet.next()) {
                                CheckValues values = new CheckValues(check);
                                values.setAlert(resultSet.getBoolean("alert"));
                                values.setPunish(resultSet.getBoolean("punish"));
                                values.setMaxViolation(resultSet.getInt("maxViolation"));
                                list.add(values);
                            } else {
                                insertStatement.setString(2, check.identifier());
                                insertStatement.setBoolean(3, true);
                                insertStatement.setBoolean(4, true);
                                insertStatement.setInt(5, check.getMaxViolation());
                                insertStatement.addBatch();
                                list.add(new CheckValues(check));
                            }
                        }
                    }

                    insertStatement.executeBatch();
                    consumer.accept(list);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            });
        }
    }

    @Override
    public void loadData(PlayerData playerData) {
        this.execute(
            () -> {
                try (
                    Connection connection = this.connectionPool.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(
                        "select persistenceData.checkId, persistenceData.violations from persistenceId inner join persistenceData on persistenceId.id = persistenceData.id where uuid=? and timestamp > ?"
                    );
                ) {
                    preparedStatement.setString(1, playerData.getUuid().toString());
                    preparedStatement.setLong(2, this.getPersistenceTime());
                    CheckData data = playerData.getCheckData();

                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        while (resultSet.next()) {
                            String identifier = resultSet.getString(1);
                            double vl = resultSet.getDouble(2);
                            Check check = data.getByIdentifier().get(identifier);
                            if (check != null) {
                                data.load(check, vl);
                            }
                        }
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            },
            100L
        );
    }

    @Override
    public void updateData(PlayerData playerData) {
        List<Check> checkList = playerData.getCheckData().update().collect(Collectors.toList());
        if (checkList.isEmpty()) {
            this.removeData(playerData);
        } else {
            this.execute(
                () -> {
                    try (
                        Connection connection = this.connectionPool.getConnection();
                        PreparedStatement deleteStatement = connection.prepareStatement(
                            "delete persistenceData from persistenceId inner join persistenceData on persistenceId.id = persistenceData.id where uuid=?"
                        );
                        PreparedStatement idStatement = connection.prepareStatement("replace into persistenceId (uuid, timestamp) values(?, ?)", 1);
                        PreparedStatement insertStatement = connection.prepareStatement("insert into persistenceData (id, checkId, violations) values(?, ?, ?)");
                    ) {
                        deleteStatement.setString(1, playerData.getUuid().toString());
                        deleteStatement.execute();
                        idStatement.setString(1, playerData.getUuid().toString());
                        idStatement.setLong(2, System.currentTimeMillis());
                        idStatement.executeUpdate();

                        int id;
                        try (ResultSet resultSet = idStatement.getGeneratedKeys()) {
                            if (!resultSet.next()) {
                                return;
                            }

                            id = resultSet.getInt(1);
                        }

                        for (Check check : checkList) {
                            insertStatement.setInt(1, id);
                            insertStatement.setString(2, check.identifier());
                            insertStatement.setDouble(3, check.getViolations());
                            insertStatement.addBatch();
                        }

                        insertStatement.executeBatch();
                        connection.commit();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            );
        }
    }

    @Override
    public void removeData(PlayerData playerData) {
        this.execute(
            () -> {
                try (
                    Connection connection = this.connectionPool.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(
                        "delete persistenceData, persistenceId from persistenceId inner join persistenceData on persistenceId.id = persistenceData.id where uuid=?"
                    );
                ) {
                    preparedStatement.setString(1, playerData.getUuid().toString());
                    preparedStatement.execute();
                    connection.commit();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        );
    }

    @Override
    public void loadAlerts(PlayerData playerData) {
        this.execute(() -> {
            try (
                Connection connection = this.connectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("select * from playerdata where uuid=? LIMIT 1");
            ) {
                preparedStatement.setString(1, playerData.getUuid().toString());

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        playerData.setAlerts(resultSet.getBoolean(2));
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    @Override
    public void updateAlerts(PlayerData playerData) {
        this.execute(
            () -> {
                try (
                    Connection connection = this.connectionPool.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(
                        this.type == ConnectionType.POSTGRESQL
                            ? "INSERT INTO playerdata (uuid, alerts) VALUES (?, ?) ON CONFLICT (uuid) DO UPDATE SET alerts = excluded.alerts"
                            : "replace into playerdata (uuid, alerts) values (?, ?)"
                    );
                ) {
                    preparedStatement.setString(1, playerData.getUuid().toString());
                    preparedStatement.setBoolean(2, playerData.isAlerts());
                    preparedStatement.execute();
                    connection.commit();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        );
    }

    @Override
    public void cleanup() {
        if (this.isConnected()) {
            VerusConfiguration config = StorageEngine.getInstance().getVerusConfig();
            long now = System.currentTimeMillis();
            long logs = now - TimeUnit.DAYS.toMillis(config.getLogsCleanupDays());
            long bans = now - TimeUnit.DAYS.toMillis(config.getBansCleanupDays());
            long persistence = now - config.getPersistenceMillis();

            try (
                Connection connection = this.connectionPool.getConnection();
                PreparedStatement logsStatement = connection.prepareStatement("delete from logs where timestamp<? limit 100000");
                PreparedStatement bansStatement = connection.prepareStatement("delete from bans where timestamp<? limit 100000");
                PreparedStatement persistenceStatement = connection.prepareStatement(
                    "delete persistenceData, persistenceId from persistenceId inner join persistenceData on persistenceId.id = persistenceData.id where timestamp<?"
                );
            ) {
                logsStatement.setLong(1, logs);
                logsStatement.executeUpdate();
                connection.commit();
                bansStatement.setLong(1, bans);
                bansStatement.executeUpdate();
                connection.commit();
                if (config.isPersistence()) {
                    persistenceStatement.setLong(1, persistence);
                    persistenceStatement.executeUpdate();
                }

                connection.commit();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public boolean isConnected() {
        return this.connectionPool != null;
    }

    private interface SQLExecutor {
        PreparedStatement apply(Connection connection) throws SQLException;
    }
}
