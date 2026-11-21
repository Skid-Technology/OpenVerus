package me.levansj01.verus.compat.v1_7_R4.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutEntityTeleport;
import me.levansj01.verus.data.transaction.tracker.IServerLocation;
import me.levansj01.verus.data.transaction.tracker.ServerLocation;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityTeleport;

public class SPacketPlayOutEntityTeleport extends VPacketPlayOutEntityTeleport<PacketPlayOutEntityTeleport> {
    private static final Field id_field = SafeReflection.access(PacketPlayOutEntityTeleport.class, "a");
    private static final Field x_field = SafeReflection.access(PacketPlayOutEntityTeleport.class, "b");
    private static final Field y_field = SafeReflection.access(PacketPlayOutEntityTeleport.class, "c");
    private static final Field z_field = SafeReflection.access(PacketPlayOutEntityTeleport.class, "d");
    private static final Field yaw_field = SafeReflection.access(PacketPlayOutEntityTeleport.class, "e");
    private static final Field pitch_field = SafeReflection.access(PacketPlayOutEntityTeleport.class, "f");
    private static final Field ground_field = SafeReflection.access(PacketPlayOutEntityTeleport.class, "onGround");
    protected int iX;
    protected int iY;
    protected int iZ;

    @Override
    public IServerLocation location() {
        return new ServerLocation(this.iX, this.iY, this.iZ);
    }

    public void accept(PacketPlayOutEntityTeleport packet) {
        this.id = SafeReflection.<Integer>fetch(id_field, packet);
        this.iX = SafeReflection.<Integer>fetch(x_field, packet);
        this.iY = SafeReflection.<Integer>fetch(y_field, packet);
        this.iZ = SafeReflection.<Integer>fetch(z_field, packet);
        this.x = this.iX / 32.0;
        this.y = this.iY / 32.0;
        this.z = this.iZ / 32.0;
        this.yaw = SafeReflection.<Byte>fetch(yaw_field, packet);
        this.pitch = SafeReflection.<Byte>fetch(pitch_field, packet);
        this.ground = SafeReflection.<Boolean>fetch(ground_field, packet);
    }
}
