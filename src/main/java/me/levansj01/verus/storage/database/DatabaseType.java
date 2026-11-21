package me.levansj01.verus.storage.database;

import java.util.function.Supplier;
import me.levansj01.verus.storage.database.impl.MongoDB;
import me.levansj01.verus.storage.database.impl.SQLDB;

public enum DatabaseType {
    MONGO(MongoDB::new),
    SQL(SQLDB::new);

    private final Supplier<Database> supplier;

    DatabaseType(Supplier<Database> supplier) {
        this.supplier = supplier;
    }

    public Database create() {
        return this.supplier.get();
    }
}
