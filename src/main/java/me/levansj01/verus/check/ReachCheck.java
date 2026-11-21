package me.levansj01.verus.check;

import java.util.List;
import me.levansj01.verus.data.reach.ReachBase;

public abstract class ReachCheck extends Check {
    public void handle(ReachBase reachBase, long value) {
    }

    public void handle(List<ReachBase> reachBases, long value) {
    }
}
