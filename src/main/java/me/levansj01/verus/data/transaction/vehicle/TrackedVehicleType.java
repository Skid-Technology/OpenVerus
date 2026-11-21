package me.levansj01.verus.data.transaction.vehicle;

import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import me.levansj01.verus.compat.ServerVersion;

public enum TrackedVehicleType {
    BOAT(new Builder<ServerVersion, Integer>().put(ServerVersion.v1_8_R3, 1).build()),
    MINE_CART(new Builder<ServerVersion, Integer>().put(ServerVersion.v1_8_R3, 10).build());

    private final Map<ServerVersion, Integer> metadataIdMap;

    public int getId(ServerVersion serverVersion) {
        return this.metadataIdMap.get(serverVersion);
    }

    TrackedVehicleType(Map<ServerVersion, Integer> metadataIdMap) {
        this.metadataIdMap = metadataIdMap;
    }

    public static TrackedVehicleType isTrackable(ServerVersion serverVersion, int value) {
        for (TrackedVehicleType trackedVehicleType : values()) {
            if (trackedVehicleType.getId(serverVersion) == value) {
                return trackedVehicleType;
            }
        }

        return null;
    }
}
