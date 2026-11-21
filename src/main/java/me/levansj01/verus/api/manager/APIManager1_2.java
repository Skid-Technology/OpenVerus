package me.levansj01.verus.api.manager;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.api.check.Check;
import me.levansj01.verus.api.check.InvalidCheckException;
import me.levansj01.verus.data.CheckData;
import me.levansj01.verus.data.PlayerData;
import org.bukkit.entity.Player;

public class APIManager1_2 extends APIManager {
    public void resetViolations(Player player, Check check, boolean flag) throws InvalidCheckException {
        me.levansj01.verus.check.Check check2 = this.getCheck(player, check);
        check2.setViolations(Math.min(flag ? check2.getMinViolation() : 0.0, check2.getViolations()));
    }

    protected PlayerData getData(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        } else {
            PlayerData player2 = this.dataManager.getPlayer(player);
            if (player2 == null) {
                throw new IllegalStateException("Player data not found");
            } else {
                return player2;
            }
        }
    }

    public boolean isGameFrozen(Player player, TimeUnit timeUnit, long value) {
        PlayerData data = this.getData(player);
        return data.getLastKeepAliveTimestamp() - data.getLastFlying() > timeUnit.toMillis(value);
    }

    public boolean isAlerts(Player player) {
        return this.getData(player).isAlerts();
    }

    public boolean isBan(Check check) throws InvalidCheckException {
        return this.isPunish(check);
    }

    public int getViolations(Player player, Check check) throws InvalidCheckException {
        return (int)Math.floor(Math.max(this.getCheck(player, check).getViolations(), 0.0));
    }

    public int getTotalViolations(Player player) {
        return (int)Math.floor(this.getData(player).getTotalViolations());
    }

    public APIManager1_2(VerusPlugin plugin) {
        super(plugin);
    }

    public void resetViolations(Player player, boolean flag) {
        CheckData checkData = this.getData(player).getCheckData();

        for (me.levansj01.verus.check.Check check : checkData.getChecks()) {
            check.setViolations(Math.min(flag ? check.getMinViolation() : 0.0, check.getViolations()));
        }
    }

    public boolean setBan(Check check, boolean flag) throws InvalidCheckException {
        return this.setPunish(check, flag);
    }

    protected me.levansj01.verus.check.Check getCheck(Player player, Check check) throws InvalidCheckException {
        me.levansj01.verus.check.Check[] originalChecks = this.getData(player).getCheckData().getOriginalChecks();
        return Arrays.stream(originalChecks)
            .filter(Array -> Array.getType().getName().equalsIgnoreCase(check.getType()))
            .filter(Array -> Array.getSubType().equalsIgnoreCase(check.getSubType()))
            .findAny()
            .orElseThrow(() -> new InvalidCheckException(check));
    }
}
