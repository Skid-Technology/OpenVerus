package me.levansj01.verus.check.type;

import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;

public enum CheckType {
    AIM_ASSIST("AimAssist", "1"),
    AUTO_CLICKER("AutoClicker", "2"),
    BAD_PACKETS("BadPackets", "3"),
    FLY("Fly", "4"),
    INVENTORY("Inventory", "5"),
    KILL_AURA("KillAura", "6"),
    PAYLOAD("Payload", "7"),
    PHASE("Phase", "8"),
    REACH("Reach", "9"),
    SPEED("Speed", "10"),
    TIMER("Timer", "11"),
    VELOCITY("Velocity", "12"),
    MISC("Misc", "13"),
    SCAFFOLD("Scaffold", "14"),
    SERVER_CRASHER("Server Crasher", "15"),
    MANUAL("Manual", "100");

    private static final Map<String, CheckType> nameMap;
    private final String name;
    private final String suffix;

    public static CheckType getByString(String str) {
        return nameMap.get(str);
    }

    public CheckType next() {
        int value = this.ordinal() + 1;
        if (value > 14) {
            value = 14;
        } else if (value == 7) {
            value = 8;
        }

        return values()[value];
    }

    public boolean ignore() {
        return this.equals(PHASE) || this.equals(MANUAL);
    }

    public CheckType previous() {
        int diff = this.ordinal() - 1;
        if (diff < 0) {
            diff = 0;
        } else if (diff == 7) {
            diff = 6;
        }

        return values()[diff];
    }

    CheckType(String name, String suffix) {
        this.name = name;
        this.suffix = suffix;
    }

    public String getName() {
        return this.name;
    }

    static {
        Builder builder = new Builder();

        for (CheckType type : values()) {
            builder.put(type.name.toLowerCase(), type);
        }

        nameMap = builder.build();
    }

    public String getSuffix() {
        return this.suffix;
    }

    public boolean valid() {
        return !this.ignore();
    }
}
