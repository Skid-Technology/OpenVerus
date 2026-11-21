package me.levansj01.verus.storage.database.check;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.manager.CheckManager;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.storage.database.Database;

public class DBCheckManager extends CheckManager {
    @Deprecated
    @Override
    public void saveChecks() {
    }

    @Override
    public void setAutoban(Check check, boolean autoban) {
        this.setAutoban(check.identifier(), autoban);
    }

    @Override
    public void addCommand(CheckValues values, String command) {
    }

    private void updateCommands(CheckValues checkValues) {
    }

    @Override
    public void setEnabled(String checkId, boolean enabled) {
        super.setEnabled(checkId, enabled);
        StorageEngine.getInstance().getDatabase().updateCheckAlerts(checkId, enabled);
    }

    @Override
    public void setMaxViolation(String checkId, int violation) {
        super.setMaxViolation(checkId, violation);
        StorageEngine.getInstance().getDatabase().updateCheckViolation(checkId, violation);
    }

    @Override
    public void loadChecks() {
        StorageEngine storageEngine = StorageEngine.getInstance();
        Database database = storageEngine.getDatabase();
        database.loadCheckValues(this.checks, entry -> {
            for (CheckValues checkValues : entry) {
                this.values.put(checkValues.getCheckId(), checkValues);
            }
        });
    }

    @Override
    public void setEnabled(Check check, boolean enabled) {
        this.setEnabled(check.identifier(), enabled);
    }

    @Override
    public void setAutoban(String checkId, boolean autoban) {
        super.setAutoban(checkId, autoban);
        StorageEngine.getInstance().getDatabase().updateCheckPunish(checkId, autoban);
    }

    @Override
    public void removeCommand(CheckValues values, int index) {
    }
}
