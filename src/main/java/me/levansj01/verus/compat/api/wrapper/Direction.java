package me.levansj01.verus.compat.api.wrapper;

public enum Direction {
    DOWN,
    UP,
    NORTH,
    SOUTH,
    WEST,
    EAST;

    public static Direction byFace(int value) {
        Direction[] directions;
        return value >= 0 && value < (directions = values()).length ? directions[value] : null;
    }
}
