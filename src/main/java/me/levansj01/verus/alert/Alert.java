package me.levansj01.verus.alert;

import java.util.UUID;
import java.util.function.Supplier;
import me.levansj01.verus.check.Check;

public class Alert {
    private int lag;
    private UUID uuid;
    private final Supplier<String> data;
    private int ping;
    private final int vl;
    private final long timestamp = System.currentTimeMillis();
    private String name;
    private final Check check;

    public int getPing() {
        return this.ping;
    }

    public String getName() {
        return this.name;
    }

    public Check getCheck() {
        return this.check;
    }

    public void setPing(int ping) {
        this.ping = ping;
    }

    public Supplier<String> getData() {
        return this.data;
    }

    public void setLag(int lag) {
        this.lag = lag;
    }

    public Alert(Check check, Supplier<String> data, int vl) {
        this.check = check;
        this.data = data;
        this.vl = vl;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public int getVl() {
        return this.vl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public int getLag() {
        return this.lag;
    }

    public UUID getUuid() {
        return this.uuid;
    }
}
