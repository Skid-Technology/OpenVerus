package me.levansj01.verus.check.checks.payload;

import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInCustomPayload;

@CheckInfo(type = CheckType.PAYLOAD, subType = "A", friendlyName = "Hacked Client", version = CheckVersion.RELEASE, logData = true, maxViolations = 1)
public class CustomPayloadA extends Check implements PacketHandler {
    private static final Map<String, String> INVALID_CHANNELS = new Builder<String, String>()
        .put("LOLIMAHCKER", "Cracked Vape")
        .put("CPS_BAN_THIS_NIGGER", "Cracked Vape")
        .put("EROUAXWASHERE", "Cracked Vape")
        .put("EARWAXWASHERE", "Cracked Vape")
        .put("#unbanearwax", "Cracked Vape")
        .put("1946203560", "Vape v3")
        .put("cock", "Reach Mod")
        .put("lmaohax", "Reach Mod")
        .put("reach", "Reach Mod")
        .put("gg", "Reach Mod")
        .put("customGuiOpenBspkrs", "Bspkrs Client")
        .put("0SO1Lk2KASxzsd", "Bspkrs Client")
        .put("MCnetHandler", "Misplace")
        .put("n", "Misplace")
        .put("CRYSTAL|KZ1LM9TO", "CrystalWare")
        .put("CRYSTAL|6LAKS0TRIES", "CrystalWare")
        .put("BLC|M", "Remix")
        .put("XDSMKDKFDKSDAKDFkEJF", "Cracked Moon")
        .build();

    @Override
    public void handle(VPacketPlayInCustomPayload<?> packet) {
        String channel = packet.getChannel();
        if (channel.startsWith("CRYSTAL|")) {
            this.handleViolation("CrystalWare");
            this.handleBan(true);
        } else {
            String str = INVALID_CHANNELS.get(channel);
            if (str != null) {
                this.handleViolation(str);
                this.handleBan(true);
            }
        }
    }
}
