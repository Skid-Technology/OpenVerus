package me.levansj01.verus.storage.database;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.function.Supplier;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.lang.EnumMessage;
import org.bson.Document;

public class Log {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM HH:mm");
    private final String type;
    private final UUID uuid;
    private final int violations;
    private final String data;
    private final long timestamp;
    private final String name;
    private final String subType;
    private final int ping;
    private final int lag;

    public String getName() {
        return this.name;
    }

    public String toString(boolean flag) {
        String message = EnumMessage.LOG_FORMAT
            .get()
            .replace("{date}", DATE_FORMAT.format(new Date(this.timestamp)))
            .replace("{timestamp}", String.valueOf(this.timestamp))
            .replace("{player}", this.name)
            .replace("{checkType}", this.type)
            .replace("{subType}", this.subType)
            .replace("{vl}", String.valueOf(this.violations))
            .replace("{ping}", String.valueOf(this.ping))
            .replace("{lag}", String.valueOf(this.lag));
        if (this.data != null && !this.data.isEmpty() && flag) {
            message = message + "| " + this.data;
        }

        return message + "\n";
    }

    private static Log create(PlayerData playerData, Check check, String str, int value) {
        return new Log(
            playerData.getUuid(),
            playerData.getName(),
            System.currentTimeMillis(),
            check.getType().getName(),
            check.getSubType(),
            value,
            playerData.getTransactionPing(),
            playerData.getLag(),
            str
        );
    }

    public Document toBson() {
        Document document = new Document();
        document.put("uuid", this.uuid);
        document.put("name", this.name);
        document.put("timestamp", this.timestamp);
        document.put("type", this.type);
        document.put("subType", this.subType);
        document.put("violations", this.violations);
        document.put("ping", this.ping);
        document.put("lag", this.lag);
        if (this.data != null) {
            document.put("data", this.data);
        }

        return document;
    }

    public int getPing() {
        return this.ping;
    }

    public String getType() {
        return this.type;
    }

    public int getViolations() {
        return this.violations;
    }

    public static Log create(PlayerData playerData, Check check, Supplier<String> supplier, int value) {
        String str = null;
        if (check.isLogData() && supplier != null) {
            str = (String)supplier.get();
            if (str.isEmpty()) {
                str = null;
            }
        }

        return create(playerData, check, str, value);
    }

    public static Log fromBson(Document document) {
        return new Log(
            (UUID)document.get("uuid", UUID.class),
            document.getString("name"),
            document.getLong("timestamp"),
            document.getString("type"),
            document.getString("subType"),
            document.getInteger("violations"),
            document.getInteger("ping"),
            document.getInteger("lag"),
            document.getString("data")
        );
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public String getData() {
        return this.data;
    }

    public Log(UUID uuid, String name, long timestamp, String type, String subType, int violations, int ping, int lag, String data) {
        this.uuid = uuid;
        this.name = name;
        this.timestamp = timestamp;
        this.type = type;
        this.subType = subType;
        this.violations = violations;
        this.ping = ping;
        this.lag = lag;
        this.data = data;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public String getSubType() {
        return this.subType;
    }

    public int getLag() {
        return this.lag;
    }
}
