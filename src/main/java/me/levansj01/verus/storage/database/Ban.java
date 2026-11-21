package me.levansj01.verus.storage.database;

import java.util.UUID;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.data.PlayerData;
import org.bson.Document;

public class Ban {
    private final UUID uuid;
    private final String type;
    private final String subType;
    private final long timestamp;
    private final String name;

    public UUID getUuid() {
        return this.uuid;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public String getType() {
        return this.type;
    }

    public String getSubType() {
        return this.subType;
    }

    public static Ban create(PlayerData playerData, Check check) {
        return new Ban(playerData.getUuid(), playerData.getName(), System.currentTimeMillis(), check.getType().getName(), check.getSubType());
    }

    public Document toBson() {
        Document document = new Document();
        document.put("uuid", this.uuid);
        document.put("name", this.name);
        document.put("timestamp", this.timestamp);
        document.put("type", this.type);
        document.put("subType", this.subType);
        return document;
    }

    public static Ban fromBson(Document document) {
        return new Ban((UUID)document.get("uuid", UUID.class), document.getString("name"), document.getLong("timestamp"), document.getString("type"), document.getString("subType"));
    }

    public String getName() {
        return this.name;
    }

    public Ban(UUID uuid, String name, long timestamp, String type, String subType) {
        this.uuid = uuid;
        this.name = name;
        this.timestamp = timestamp;
        this.type = type;
        this.subType = subType;
    }
}
