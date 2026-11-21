package me.levansj01.verus.api.manager;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.api.check.Check;
import me.levansj01.verus.api.check.InvalidCheckException;
import me.levansj01.verus.api.log.Ban;
import me.levansj01.verus.api.log.Log;
import me.levansj01.verus.api.log.StorageNotConnectedException;
import me.levansj01.verus.check.manager.CheckManager;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.data.manager.DataManager;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.storage.database.Database;
import me.levansj01.verus.util.java.WrappingIterator;

public abstract class APIManager implements VerusManager {
    protected final Map<Check, me.levansj01.verus.check.Check> checkMap;
    protected final CheckManager checkManager;
    protected final DataManager dataManager;
    protected final List<Check> checks;
    protected final StorageEngine storageEngine = StorageEngine.getInstance();
    protected final HashBasedTable<CheckType, String, Check> checkTable;

    private Ban toAPI(me.levansj01.verus.storage.database.Ban ban) {
        Check uncheckedCheck = this.getUncheckedCheck(ban.getType(), ban.getSubType());
        return new Ban(uncheckedCheck, ban.getTimestamp());
    }

    public boolean isValid(Check check) {
        if (check.getType() != null && check.getSubType() != null) {
            CheckType byString = CheckType.getByString(check.getType());
            return byString == null ? false : this.checkMap.containsKey(check);
        } else {
            return false;
        }
    }

    protected Check getCheckOrNull(String str, String str2) {
        CheckType byString = CheckType.getByString(str);
        return byString != null ? this.checkTable.get(byString, str2.toLowerCase()) : null;
    }

    public boolean isConnected() {
        return this.storageEngine.isConnected();
    }

    public boolean isEnabled() {
        return true;
    }

    public void fetchLogs(UUID uuid, int value, Consumer<Iterable<Log>> consumer) throws StorageNotConnectedException {
        if (!this.isConnected()) {
            throw new StorageNotConnectedException();
        }

        Database database = this.storageEngine.getDatabase();
        database.getLogs(uuid, value, entry -> consumer.accept(() -> new WrappingIterator<>(entry.iterator(), this::toAPI)));
    }

    public void fetchBans(UUID uuid, int value, Consumer<Iterable<Ban>> consumer) throws StorageNotConnectedException {
        if (!this.isConnected()) {
            throw new StorageNotConnectedException();
        }

        Database database = this.storageEngine.getDatabase();
        database.getBans(uuid, value, entry -> consumer.accept(() -> new WrappingIterator<>(entry.iterator(), this::toAPI)));
    }

    public APIManager(VerusPlugin plugin) {
        this.checkManager = CheckManager.getInstance();
        this.dataManager = DataManager.getInstance();
        me.levansj01.verus.check.Check[] checks = plugin.getTypeLoader().loadChecks();
        this.checks = Arrays.stream(checks).map(this::toAPI).collect(Collectors.toList());
        this.checkMap = Maps.newHashMapWithExpectedSize(checks.length);
        this.checkTable = HashBasedTable.create(CheckType.values().length, checks.length / CheckType.values().length);

        for (me.levansj01.verus.check.Check check : checks) {
            this.checkMap.put(this.toAPI(check), check);
            this.checkTable.put(check.getType(), check.getSubType().toLowerCase(), this.toAPI(check));
        }
    }

    public boolean isAlert(Check check) throws InvalidCheckException {
        return this.checkManager.isEnabled(this.getCheck(check));
    }

    public boolean setPunish(Check check, boolean flag) throws InvalidCheckException {
        me.levansj01.verus.check.Check check2 = this.getCheck(check);
        boolean autoban = this.checkManager.isAutoban(check2);
        if (autoban != flag) {
            this.checkManager.setAutoban(check2, flag);
            return true;
        } else {
            return false;
        }
    }

    protected me.levansj01.verus.check.Check getCheck(Check check) throws InvalidCheckException {
        me.levansj01.verus.check.Check check2 = this.checkMap.get(check);
        if (check2 == null) {
            throw new InvalidCheckException(check);
        } else {
            return check2;
        }
    }

    public List<Check> getChecks() {
        return ImmutableList.copyOf(this.checks);
    }

    private Log toAPI(me.levansj01.verus.storage.database.Log log) {
        Check uncheckedCheck = this.getUncheckedCheck(log.getType(), log.getSubType());
        return new Log(uncheckedCheck, log.getTimestamp(), log.getViolations(), log.getPing(), log.getLag());
    }

    private Check toAPI(me.levansj01.verus.check.Check check) {
        return new Check(check.getType().getName(), check.getSubType(), check.getFriendlyName());
    }

    public boolean isPunish(Check check) throws InvalidCheckException {
        return this.checkManager.isAutoban(this.getCheck(check));
    }

    private Check getUncheckedCheck(String str, String str2) {
        Check checkOrNull = this.getCheckOrNull(str, str2);
        return checkOrNull == null ? new Check(str, str2) : checkOrNull;
    }

    public boolean setAlert(Check check, boolean flag) throws InvalidCheckException {
        me.levansj01.verus.check.Check check2 = this.getCheck(check);
        boolean enabled = this.checkManager.isEnabled(check2);
        if (enabled != flag) {
            this.checkManager.setEnabled(check2, flag);
            return true;
        } else {
            return false;
        }
    }
}
