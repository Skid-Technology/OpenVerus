package me.levansj01.verus.check;

import me.levansj01.verus.util.location.PlayerLocation;

public abstract class AimCheck extends Check {
    public abstract void handle(PlayerLocation location, PlayerLocation location2, long value);
}
