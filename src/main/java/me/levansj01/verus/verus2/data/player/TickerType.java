package me.levansj01.verus.verus2.data.player;

import java.util.Arrays;

public enum TickerType {
    TOTAL(true),
    TELEPORT(true),
    SPRINT(true),
    VELOCITY(true),
    VEHICLE,
    LEGIT_SWING(true),
    DOUBLE_SWING(true),
    POSSIBLE_SWINGS,
    ATTACKS,
    ATTACKS_IN_LAST,
    NOT_MOVING(true),
    MANUAL_TELEPORT(true),
    PLACED_BLOCK(true, 1200),
    PLACED_BLOCK_UNDER(true, 1200),
    WINDOW_CLICK(true, 1200),
    SUFFOCATING(true, 100),
    GLIDING(true, 1200),
    RIPTIDE(true, 1200),
    ELYTRA_BOOST(true, 1200),
    FALL_FLYING(true, 1200),
    ELYTRA_EXIT(true, 1200),
    WATER_BUCKET(true, 1200),
    HOOKED(true, 1200),
    EXPLOSION(true, 1200),
    MOVES_SINCE_TELEPORT(false, 0),
    LAST_RECEIVED_TRANSACTION(false, 0),
    LAST_SENT_TRANSACTION(false, 0),
    ATTRIBUTES(true, 1200),
    SPEED_BOOST(true, 1200),
    JUMP_BOOST(true, 1200),
    LEVITATION(true, 1200),
    SLIME_PUSH(true, 1200);

    private static final TickerType[] autos = Arrays.stream(values()).filter(TickerType::isAuto).toArray(TickerType[]::new);
    private final int starting;
    private final boolean auto;

    TickerType() {
        this(false, 0);
    }

    public int getStarting() {
        return this.starting;
    }

    TickerType(int value) {
        this(false, value);
    }

    TickerType(boolean auto, int starting) {
        this.auto = auto;
        this.starting = starting;
    }

    public static TickerType[] getAutos() {
        return autos;
    }

    public boolean isAuto() {
        return this.auto;
    }

    TickerType(boolean flag) {
        this(flag, 0);
    }
}
