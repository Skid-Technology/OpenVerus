package me.levansj01.verus.compat.v1_18_R2.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInCloseWindow;
import net.minecraft.network.protocol.game.PacketPlayInCloseWindow;

public class SPacketPlayInCloseWindow extends VPacketPlayInCloseWindow<PacketPlayInCloseWindow> {
    public void accept(PacketPlayInCloseWindow packet) {
        this.window = packet.b();
    }
}
