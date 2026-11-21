package me.levansj01.verus.storage.database.pool;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool implements AutoCloseable {
    private final HikariDataSource connectionPool;

    public Connection getConnection() throws SQLException {
        return this.connectionPool.getConnection();
    }

    public HikariDataSource getConnectionPool() {
        return this.connectionPool;
    }

    @Override
    public void close() {
        if (this.connectionPool != null) {
            this.connectionPool.close();
        }
    }

    public ConnectionPool(int maximumPoolSize, String username, String password, String serverName, String databaseName, int value, ConnectionType connectionType) throws Exception {
        Class.forName(connectionType.getClassName());
        HikariConfig config = new HikariConfig();
        config.setMaximumPoolSize(maximumPoolSize);
        if (connectionType.equals(ConnectionType.MYSQL)) {
            config.setJdbcUrl(String.format("jdbc:mysql://%s:%s/%s?useSSL=false&useUnicode=true&characterEncoding=utf8", serverName, value, databaseName));
        } else {
            config.setDataSourceClassName(connectionType.getHikariCP());
            config.addDataSourceProperty("serverName", serverName);
            config.addDataSourceProperty("databaseName", databaseName);
            config.addDataSourceProperty(connectionType.getPort(), value);
        }

        config.setUsername(username);
        config.setPassword(password);
        switch (connectionType) {
            case MYSQL:
                config.addDataSourceProperty("rewriteBatchedStatements", "true");
                config.addDataSourceProperty("cachePrepStmts", "true");
                config.addDataSourceProperty("prepStmtCacheSize", "250");
                config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
                config.addDataSourceProperty("useServerPrepStmts", "true");
                config.addDataSourceProperty("cacheServerConfiguration", "true");
                config.addDataSourceProperty("useLocalSessionState", "true");
                config.addDataSourceProperty("maintainTimeStats", "false");
            default:
                config.setAutoCommit(false);
                this.connectionPool = new HikariDataSource(config);
        }
    }
}
