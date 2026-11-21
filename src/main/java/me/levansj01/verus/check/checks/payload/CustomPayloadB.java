package me.levansj01.verus.check.checks.payload;

import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.data.client.ClientType;

@CheckInfo(type = CheckType.PAYLOAD, subType = "B", friendlyName = "Hacked Client", version = CheckVersion.RELEASE, logData = true, maxViolations = 1)
public class CustomPayloadB extends Check {
    private static final Map<String, String> INVALID_BRANDS = new Builder<String, String>()
        .put("Vanilla", "Jigsaw")
        .put("\u0007Vanilla", "Jigsaw")
        .put("Synergy", "Synergy")
        .put("\u0007Synergy", "Synergy")
        .put("Created By ", "Vape")
        .put("\u0007Created By ", "Vape")
        .build();
    private static final Map<String, ClientType> CLIENT_TYPES = new Builder<String, ClientType>()
        .put("vanilla", ClientType.VANILLA)
        .put("fml,forge", ClientType.FORGE)
        .put("LiteLoader", ClientType.LITE_LOADER)
        .put("fabric", ClientType.FABRIC)
        .put("\u0007vanilla", ClientType.VANILLA)
        .put("\u0007fml,forge", ClientType.FORGE)
        .put("\u0007LiteLoader", ClientType.LITE_LOADER)
        .put("\u0006fabric", ClientType.FABRIC)
        .build();

    public void handle(String brand) {
        String str = INVALID_BRANDS.get(brand);
        if (str != null) {
            this.handleViolation(String.format("T: %s", str));
            this.handleBan(true);
        } else {
            ClientType clientType = CLIENT_TYPES.get(brand);
            if (clientType != null) {
                this.playerData.getClientData().setType(clientType);
            } else {
                this.debug(brand);
            }

            this.playerData.setBrand(brand);
        }
    }
}
