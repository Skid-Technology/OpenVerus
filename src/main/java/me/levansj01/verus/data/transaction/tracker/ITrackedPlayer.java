package me.levansj01.verus.data.transaction.tracker;

import me.levansj01.verus.util.Cuboid;

public interface ITrackedPlayer {
    Cuboid[] getLocations();

    boolean hasVehicle();

    ILocationData getLast();
}
