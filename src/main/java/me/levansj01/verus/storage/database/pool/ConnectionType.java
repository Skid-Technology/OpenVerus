package me.levansj01.verus.storage.database.pool;

public enum ConnectionType {
    MYSQL("com.mysql.jdbc.Driver", "com.mysql.jdbc.jdbc2.optional.MysqlDataSource", "port", "mysql"),
    POSTGRESQL("org.postgresql.ds.PGSimpleDataSource", "org.postgresql.ds.PGSimpleDataSource", "portNumber", "postgresql");

    private final String hikariCP;
    private final String uri;
    private final String port;
    private final String className;

    public String getClassName() {
        return this.className;
    }

    public String getHikariCP() {
        return this.hikariCP;
    }

    public String getUri() {
        return this.uri;
    }

    ConnectionType(String className, String hikariCP, String port, String uri) {
        this.className = className;
        this.hikariCP = hikariCP;
        this.port = port;
        this.uri = uri;
    }

    public String getPort() {
        return this.port;
    }
}
