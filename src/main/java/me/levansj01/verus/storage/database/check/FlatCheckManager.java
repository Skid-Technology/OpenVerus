package me.levansj01.verus.storage.database.check;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.manager.CheckManager;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.storage.config.VerusConfiguration;
import me.levansj01.verus.type.VerusTypeLoader;
import org.bukkit.configuration.file.YamlConfiguration;

public class FlatCheckManager extends CheckManager {
    @Override
    public void addCommand(CheckValues values, String command) {
    }

    @Override
    public void setAutoban(String checkId, boolean autoban) {
        super.setAutoban(checkId, autoban);
        this.saveChecks();
    }

    @Override
    public void setAutoban(Check check, boolean autoban) {
        this.setAutoban(check.identifier(), autoban);
    }

    @Override
    public void removeCommand(CheckValues values, int index) {
    }

    @Override
    public void loadChecks() {
        YamlConfiguration checkConfiguration = StorageEngine.getInstance().getVerusConfig().getCheckConfiguration();

        for (Check check : this.checks) {
            this.values.compute(check.identifier(), (first, second) -> {
                if (second == null) {
                    second = new CheckValues(check);
                }

                String str = check.getType().getName() + "." + check.getSubType();
                second.setAlert(checkConfiguration.getBoolean(str + ".enabled", true));
                second.setPunish(checkConfiguration.getBoolean(str + ".autoban", true));
                if (VerusTypeLoader.isCustom()) {
                    second.setMaxViolation(checkConfiguration.getInt(str + ".maxViolation", check.getMaxViolation()));
                    second.setCommands(checkConfiguration.getStringList(str + ".commands"));
                }

                return (CheckValues)second;
            });
        }
    }

    @Override
    public void addCommand(String checkId, String command) {
    }

    @Override
    public void setEnabled(String checkId, boolean enabled) {
        super.setEnabled(checkId, enabled);
        this.saveChecks();
    }

    @Override
    public void removeCommand(String checkId, int index) {
    }

    @Override
    public void setMaxViolation(String checkId, int violation) {
        super.setMaxViolation(checkId, violation);
        this.saveChecks();
    }

    @Override
    public void setEnabled(Check check, boolean enabled) {
        this.setEnabled(check.identifier(), enabled);
    }

    @Override
    public void saveChecks() {
        VerusConfiguration verusConfig = StorageEngine.getInstance().getVerusConfig();
        YamlConfiguration checkConfiguration = verusConfig.getCheckConfiguration();

        for (Check check : this.checks) {
            String str = check.getType().getName() + "." + check.getSubType();
            checkConfiguration.set(str + ".enabled", this.isEnabled(check));
            checkConfiguration.set(str + ".autoban", this.isAutoban(check));
            if (VerusTypeLoader.isCustom()) {
                checkConfiguration.set(str + ".maxViolation", this.getMaxViolation(check));
                checkConfiguration.set(str + ".commands", this.getCommands(check));
            }
        }

        verusConfig.saveConfig(checkConfiguration, verusConfig.getCheckFile());
    }
}
