package me.levansj01.verus.storage;

import java.util.logging.Logger;
import me.levansj01.launcher.VerusLauncher;
import me.levansj01.verus.storage.config.VerusConfiguration;
import me.levansj01.verus.storage.database.Database;
import me.levansj01.verus.storage.database.DatabaseType;

public class StorageEngine {
    private DatabaseType type;
    private VerusConfiguration verusConfig;
    private Database database;
    private static StorageEngine engine;

    public void stop() {
        if (this.database != null) {
            this.database.stop();
        }
    }

    public DatabaseType getType() {
        return this.type;
    }

    public boolean isConnected() {
        return this.database != null && this.database.isConnected();
    }

    public void start() {
        if (this.verusConfig.isMongoEnabled()) {
            this.type = DatabaseType.MONGO;
        } else if (this.verusConfig.isSqlEnabled()) {
            this.type = DatabaseType.SQL;
        }

        if (this.type == null) {
            Logger logger = VerusLauncher.getPlugin().getLogger();
            logger.warning("No storage method found, please enable database storage in the configuration to access important features");
        } else {
            this.database = this.type.create();
            VerusLauncher.getPlugin().getLogger().info("Using storage method " + this.type.name() + " handled by " + this.database.getClass().getName());
            this.database.start();
            this.database.connect(this.verusConfig);
        }
    }

    public static StorageEngine getInstance() {
        return engine == null ? (engine = new StorageEngine()) : engine;
    }

    public Database getDatabase() {
        return this.database;
    }

    public void startConfig() {
        this.verusConfig = new VerusConfiguration();
        this.verusConfig.enable();
    }

    public void stopConfig() {
        if (this.verusConfig != null) {
            this.verusConfig.disable();
        }
    }

    public VerusConfiguration getVerusConfig() {
        return this.verusConfig;
    }
}
