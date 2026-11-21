package me.levansj01.verus.check.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.gui.manager.GUIManager;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.storage.config.VerusConfiguration;
import me.levansj01.verus.storage.database.check.CheckValues;
import me.levansj01.verus.storage.database.check.DBCheckManager;
import me.levansj01.verus.storage.database.check.FlatCheckManager;
import me.levansj01.verus.type.VerusTypeLoader;

public abstract class CheckManager {
    private static CheckManager instance;
    protected Check[] checks;
    protected Map<String, CheckValues> values;

    public static CheckManager getInstance() {
        if (instance == null) {
            VerusConfiguration config = StorageEngine.getInstance().getVerusConfig();
            instance = config != null && config.isDatabaseCheckPersistence() && VerusTypeLoader.isEnterprise() ? new DBCheckManager() : new FlatCheckManager();
        }

        return instance;
    }

    public abstract void loadChecks();

    public abstract void saveChecks();

    public void enable(VerusPlugin plugin) {
        this.checks = plugin.getTypeLoader().loadChecks();
        this.values = new ConcurrentHashMap<>();
        this.loadChecks();
        GUIManager.getInstance().enable(plugin);
    }

    public void disable() {
        this.checks = null;
        this.values = null;
    }

    public void setEnabled(Check check, boolean enabled) {
        CheckValues values = this.getValues(check);
        if (values != null) {
            values.setAlert(enabled);
        }
    }

    public void setAutoban(Check check, boolean autoban) {
        CheckValues values = this.getValues(check);
        if (values != null) {
            values.setPunish(autoban);
        }
    }

    public void setEnabled(String checkId, boolean enabled) {
        CheckValues values = this.getValues(checkId);
        if (values != null) {
            values.setAlert(enabled);
        }
    }

    public void setAutoban(String checkId, boolean autoban) {
        CheckValues values = this.getValues(checkId);
        if (values != null) {
            values.setPunish(autoban);
        }
    }

    public void setMaxViolation(String checkId, int violation) {
        CheckValues values = this.getValues(checkId);
        if (values != null) {
            values.setMaxViolation(violation);
        }
    }

    public void addCommand(CheckValues values, String command) {
        values.addCommand(command);
    }

    public void addCommand(String checkId, String command) {
        this.getValues(checkId).addCommand(command);
    }

    public void removeCommand(CheckValues values, int index) {
        values.removeCommand(index);
    }

    public void removeCommand(String checkId, int index) {
        this.getValues(checkId).removeCommand(index);
    }

    public boolean isEnabled(Check check) {
        CheckValues values = this.getValues(check);
        return values == null ? check.getCheckVersion() != CheckVersion.DEVELOPMENT : values.isAlert();
    }

    public boolean isAutoban(Check check) {
        CheckValues values = this.getValues(check);
        return values == null ? check.getCheckVersion() == CheckVersion.RELEASE : values.isPunish();
    }

    public boolean isDisabled(Check check) {
        CheckValues values = this.getValues(check);
        return values == null ? check.getCheckVersion() == CheckVersion.DEVELOPMENT : !values.isAlert() && !values.isPunish();
    }

    public int getMaxViolation(Check check) {
        CheckValues values = this.getValues(check);
        return values != null && values.getMaxViolation() != Integer.MAX_VALUE ? values.getMaxViolation() : check.getMaxViolation();
    }

    public List<String> getCommands(Check check) {
        CheckValues values = this.getValues(check);
        return values == null ? new ArrayList<>(0) : values.getCommands();
    }

    public CheckValues getValues(String id) {
        return this.values.get(id);
    }

    public CheckValues getValues(Check check) {
        return this.getValues(check.identifier());
    }
}
