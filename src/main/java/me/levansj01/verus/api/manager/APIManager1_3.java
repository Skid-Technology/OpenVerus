package me.levansj01.verus.api.manager;

import java.util.Arrays;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.concurrent.TimeUnit;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.api.check.Check;
import me.levansj01.verus.api.check.InvalidCheckException;
import me.levansj01.verus.data.CheckData;
import me.levansj01.verus.data.PlayerData;
import org.bukkit.entity.Player;

public class APIManager1_3 extends APIManager {
    public int getViolations(Player player, Check check) throws InvalidCheckException, DataNotFoundException {
        return (int)Math.floor(Math.max(this.getCheck(player, check).getViolations(), 0.0));
    }

    public void resetViolations(Player player, Check check, boolean flag) throws InvalidCheckException, DataNotFoundException {
        me.levansj01.verus.check.Check check2 = this.getCheck(player, check);
        check2.setViolations(Math.min(flag ? check2.getMinViolation() : 0.0, check2.getViolations()));
    }

    public OptionalInt getPunishViolations(Check check) throws InvalidCheckException {
        int maxViolation = this.checkManager.getMaxViolation(this.getCheck(check));
        return maxViolation > 0 ? OptionalInt.of(maxViolation) : OptionalInt.empty();
    }

    public Optional<String> getBrand(Player player) throws DataNotFoundException {
        return Optional.ofNullable(this.getData(player).getBrand());
    }

    public int getTotalViolations(Player player) throws DataNotFoundException {
        return (int)Math.floor(this.getData(player).getTotalViolations());
    }

    public boolean isAlerts(Player player) throws DataNotFoundException {
        return this.getData(player).isAlerts();
    }

    public APIManager1_3(VerusPlugin plugin) {
        super(plugin);
    }

    protected me.levansj01.verus.check.Check getCheck(Player player, Check check) throws InvalidCheckException, DataNotFoundException {
        me.levansj01.verus.check.Check[] originalChecks = this.getData(player).getCheckData().getOriginalChecks();
        return Arrays.stream(originalChecks)
            .filter(Array -> Array.getType().getName().equalsIgnoreCase(check.getType()))
            .filter(Array -> Array.getSubType().equalsIgnoreCase(check.getSubType()))
            .findAny()
            .orElseThrow(() -> new InvalidCheckException(check));
    }

    protected PlayerData getData(Player player) throws DataNotFoundException {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        } else {
            PlayerData player2 = this.dataManager.getPlayer(player);
            if (player2 == null) {
                throw new DataNotFoundException(player.getUniqueId());
            } else {
                return player2;
            }
        }
    }

    public int getKeepAlivePing(Player player) throws DataNotFoundException {
        return this.getData(player).getPing();
    }

    public int getTransactionPing(Player player) throws DataNotFoundException {
        return this.getData(player).getTransactionPing();
    }

    public Optional<Check> getCheck(String str, String str2) {
        return Optional.ofNullable(this.getCheckOrNull(str, str2));
    }

    public boolean isGameFrozen(Player player, TimeUnit timeUnit, long value) throws DataNotFoundException {
        PlayerData data = this.getData(player);
        return data.getLastKeepAliveTimestamp() - data.getLastFlying() > timeUnit.toMillis(value);
    }

    public void resetViolations(Player player, boolean flag) throws DataNotFoundException {
        CheckData checkData = this.getData(player).getCheckData();

        for (me.levansj01.verus.check.Check check : checkData.getChecks()) {
            check.setViolations(Math.min(flag ? check.getMinViolation() : 0.0, check.getViolations()));
        }
    }
}
