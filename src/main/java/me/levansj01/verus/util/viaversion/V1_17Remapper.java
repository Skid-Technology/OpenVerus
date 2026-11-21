package me.levansj01.verus.util.viaversion;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ServerboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ClientboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;
import java.util.logging.Level;
import me.levansj01.launcher.VerusLauncher;

public class V1_17Remapper {
    public static boolean remap() {
        Protocol protocol = Via.getManager().getProtocolManager().getProtocol(ProtocolVersion.v1_16_4, ProtocolVersion.v1_17);
        if (protocol == null) {
            return false;
        }

        try {
            protocol.registerClientbound(
                State.PLAY, ClientboundPackets1_17.PING.getId(), ClientboundPackets1_16_2.WINDOW_CONFIRMATION.getId(), new PacketRemapper() {
                    public void registerMap() {
                        this.handler(thi -> {
                            int value = (Integer)thi.read(Type.INT);
                            short s = (short)value;
                            if (value == s) {
                                thi.write(Type.UNSIGNED_BYTE, (short)0);
                                thi.write(Type.SHORT, s);
                                thi.write(Type.BOOLEAN, false);
                            } else {
                                thi.cancel();
                            }
                        });
                    }
                }, true
            );
            protocol.registerServerbound(
                State.PLAY, ServerboundPackets1_16_2.WINDOW_CONFIRMATION.getId(), ServerboundPackets1_17.PONG.getId(), new PacketRemapper() {
                    public void registerMap() {
                        this.handler(thi -> {
                            byte b = (Byte)thi.read(Type.BYTE);
                            short s = (Short)thi.read(Type.SHORT);
                            boolean flag = (Byte)thi.read(Type.BYTE) != 0;
                            if (b == 0 && flag) {
                                thi.write(Type.INT, Integer.valueOf(s));
                            } else {
                                thi.cancel();
                            }
                        });
                    }
                }, true
            );
            return true;
        } catch (Throwable throwable) {
            VerusLauncher.getPlugin().getLogger().log(Level.SEVERE, "Failed to register ViaBackwards patch, ", throwable);
            return false;
        }
    }
}
