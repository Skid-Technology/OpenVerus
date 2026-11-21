package me.levansj01.verus.compat.v1_7_R4;

import me.levansj01.verus.compat.api.VPacketListener;
import me.levansj01.verus.compat.v1_7_R4.packets.SPacketPlayInAbilities;
import me.levansj01.verus.compat.v1_7_R4.packets.SPacketPlayInArmAnimation;
import me.levansj01.verus.compat.v1_7_R4.packets.SPacketPlayInBlockDig;
import me.levansj01.verus.compat.v1_7_R4.packets.SPacketPlayInBlockPlace;
import me.levansj01.verus.compat.v1_7_R4.packets.SPacketPlayInClientCommand;
import me.levansj01.verus.compat.v1_7_R4.packets.SPacketPlayInCloseWindow;
import me.levansj01.verus.compat.v1_7_R4.packets.SPacketPlayInCustomPayload;
import me.levansj01.verus.compat.v1_7_R4.packets.SPacketPlayInEntityAction;
import me.levansj01.verus.compat.v1_7_R4.packets.SPacketPlayInFlying;
import me.levansj01.verus.compat.v1_7_R4.packets.SPacketPlayInHeldItemSlot;
import me.levansj01.verus.compat.v1_7_R4.packets.SPacketPlayInKeepAlive;
import me.levansj01.verus.compat.v1_7_R4.packets.SPacketPlayInSetCreativeSlot;
import me.levansj01.verus.compat.v1_7_R4.packets.SPacketPlayInSteerVehicle;
import me.levansj01.verus.compat.v1_7_R4.packets.SPacketPlayInTransaction;
import me.levansj01.verus.compat.v1_7_R4.packets.SPacketPlayInUseEntity;
import me.levansj01.verus.compat.v1_7_R4.packets.SPacketPlayInWindowClick;
import me.levansj01.verus.compat.v1_7_R4.packets.SPacketPlayOutAbilities;
import me.levansj01.verus.compat.v1_7_R4.packets.SPacketPlayOutAttachEntity;
import me.levansj01.verus.compat.v1_7_R4.packets.SPacketPlayOutEntity;
import me.levansj01.verus.compat.v1_7_R4.packets.SPacketPlayOutEntityDestroy;
import me.levansj01.verus.compat.v1_7_R4.packets.SPacketPlayOutEntityEffect;
import me.levansj01.verus.compat.v1_7_R4.packets.SPacketPlayOutEntityTeleport;
import me.levansj01.verus.compat.v1_7_R4.packets.SPacketPlayOutEntityVelocity;
import me.levansj01.verus.compat.v1_7_R4.packets.SPacketPlayOutExplosion;
import me.levansj01.verus.compat.v1_7_R4.packets.SPacketPlayOutGameStateChange;
import me.levansj01.verus.compat.v1_7_R4.packets.SPacketPlayOutKeepAlive;
import me.levansj01.verus.compat.v1_7_R4.packets.SPacketPlayOutNamedEntitySpawn;
import me.levansj01.verus.compat.v1_7_R4.packets.SPacketPlayOutOpenWindow;
import me.levansj01.verus.compat.v1_7_R4.packets.SPacketPlayOutPosition;
import me.levansj01.verus.compat.v1_7_R4.packets.SPacketPlayOutRemoveEntityEffect;
import me.levansj01.verus.compat.v1_7_R4.packets.SPacketPlayOutRespawn;
import me.levansj01.verus.compat.v1_7_R4.packets.SPacketPlayOutSetSlot;
import me.levansj01.verus.compat.v1_7_R4.packets.SPacketPlayOutSpawnEntity;
import me.levansj01.verus.compat.v1_7_R4.packets.SPacketPlayOutSpawnEntityLiving;
import me.levansj01.verus.compat.v1_7_R4.packets.SPacketPlayOutSpawnPosition;
import me.levansj01.verus.compat.v1_7_R4.packets.SPacketPlayOutTransaction;
import me.levansj01.verus.compat.v1_7_R4.packets.SPacketPlayOutUpdateAttributes;
import me.levansj01.verus.data.PlayerData;
import net.minecraft.server.v1_7_R4.EnumProtocol;
import net.minecraft.server.v1_7_R4.IChatBaseComponent;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketHandshakingInListener;
import net.minecraft.server.v1_7_R4.PacketHandshakingInSetProtocol;
import net.minecraft.server.v1_7_R4.PacketLoginInEncryptionBegin;
import net.minecraft.server.v1_7_R4.PacketLoginInListener;
import net.minecraft.server.v1_7_R4.PacketLoginInStart;
import net.minecraft.server.v1_7_R4.PacketLoginOutDisconnect;
import net.minecraft.server.v1_7_R4.PacketLoginOutEncryptionBegin;
import net.minecraft.server.v1_7_R4.PacketLoginOutListener;
import net.minecraft.server.v1_7_R4.PacketLoginOutSuccess;
import net.minecraft.server.v1_7_R4.PacketPlayInAbilities;
import net.minecraft.server.v1_7_R4.PacketPlayInArmAnimation;
import net.minecraft.server.v1_7_R4.PacketPlayInBlockDig;
import net.minecraft.server.v1_7_R4.PacketPlayInBlockPlace;
import net.minecraft.server.v1_7_R4.PacketPlayInChat;
import net.minecraft.server.v1_7_R4.PacketPlayInClientCommand;
import net.minecraft.server.v1_7_R4.PacketPlayInCloseWindow;
import net.minecraft.server.v1_7_R4.PacketPlayInCustomPayload;
import net.minecraft.server.v1_7_R4.PacketPlayInEnchantItem;
import net.minecraft.server.v1_7_R4.PacketPlayInEntityAction;
import net.minecraft.server.v1_7_R4.PacketPlayInFlying;
import net.minecraft.server.v1_7_R4.PacketPlayInHeldItemSlot;
import net.minecraft.server.v1_7_R4.PacketPlayInKeepAlive;
import net.minecraft.server.v1_7_R4.PacketPlayInListener;
import net.minecraft.server.v1_7_R4.PacketPlayInSetCreativeSlot;
import net.minecraft.server.v1_7_R4.PacketPlayInSettings;
import net.minecraft.server.v1_7_R4.PacketPlayInSteerVehicle;
import net.minecraft.server.v1_7_R4.PacketPlayInTabComplete;
import net.minecraft.server.v1_7_R4.PacketPlayInTransaction;
import net.minecraft.server.v1_7_R4.PacketPlayInUpdateSign;
import net.minecraft.server.v1_7_R4.PacketPlayInUseEntity;
import net.minecraft.server.v1_7_R4.PacketPlayInWindowClick;
import net.minecraft.server.v1_7_R4.PacketPlayOutAbilities;
import net.minecraft.server.v1_7_R4.PacketPlayOutAnimation;
import net.minecraft.server.v1_7_R4.PacketPlayOutAttachEntity;
import net.minecraft.server.v1_7_R4.PacketPlayOutBed;
import net.minecraft.server.v1_7_R4.PacketPlayOutBlockAction;
import net.minecraft.server.v1_7_R4.PacketPlayOutBlockBreakAnimation;
import net.minecraft.server.v1_7_R4.PacketPlayOutBlockChange;
import net.minecraft.server.v1_7_R4.PacketPlayOutChat;
import net.minecraft.server.v1_7_R4.PacketPlayOutCloseWindow;
import net.minecraft.server.v1_7_R4.PacketPlayOutCollect;
import net.minecraft.server.v1_7_R4.PacketPlayOutCustomPayload;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntity;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityEffect;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityStatus;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityVelocity;
import net.minecraft.server.v1_7_R4.PacketPlayOutExperience;
import net.minecraft.server.v1_7_R4.PacketPlayOutExplosion;
import net.minecraft.server.v1_7_R4.PacketPlayOutGameStateChange;
import net.minecraft.server.v1_7_R4.PacketPlayOutHeldItemSlot;
import net.minecraft.server.v1_7_R4.PacketPlayOutKeepAlive;
import net.minecraft.server.v1_7_R4.PacketPlayOutKickDisconnect;
import net.minecraft.server.v1_7_R4.PacketPlayOutListener;
import net.minecraft.server.v1_7_R4.PacketPlayOutLogin;
import net.minecraft.server.v1_7_R4.PacketPlayOutMap;
import net.minecraft.server.v1_7_R4.PacketPlayOutMapChunk;
import net.minecraft.server.v1_7_R4.PacketPlayOutMapChunkBulk;
import net.minecraft.server.v1_7_R4.PacketPlayOutMultiBlockChange;
import net.minecraft.server.v1_7_R4.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_7_R4.PacketPlayOutNamedSoundEffect;
import net.minecraft.server.v1_7_R4.PacketPlayOutOpenSignEditor;
import net.minecraft.server.v1_7_R4.PacketPlayOutOpenWindow;
import net.minecraft.server.v1_7_R4.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_7_R4.PacketPlayOutPosition;
import net.minecraft.server.v1_7_R4.PacketPlayOutRemoveEntityEffect;
import net.minecraft.server.v1_7_R4.PacketPlayOutRespawn;
import net.minecraft.server.v1_7_R4.PacketPlayOutScoreboardDisplayObjective;
import net.minecraft.server.v1_7_R4.PacketPlayOutScoreboardObjective;
import net.minecraft.server.v1_7_R4.PacketPlayOutScoreboardScore;
import net.minecraft.server.v1_7_R4.PacketPlayOutScoreboardTeam;
import net.minecraft.server.v1_7_R4.PacketPlayOutSetSlot;
import net.minecraft.server.v1_7_R4.PacketPlayOutSpawnEntity;
import net.minecraft.server.v1_7_R4.PacketPlayOutSpawnEntityExperienceOrb;
import net.minecraft.server.v1_7_R4.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_7_R4.PacketPlayOutSpawnEntityPainting;
import net.minecraft.server.v1_7_R4.PacketPlayOutSpawnEntityWeather;
import net.minecraft.server.v1_7_R4.PacketPlayOutSpawnPosition;
import net.minecraft.server.v1_7_R4.PacketPlayOutStatistic;
import net.minecraft.server.v1_7_R4.PacketPlayOutTabComplete;
import net.minecraft.server.v1_7_R4.PacketPlayOutTileEntityData;
import net.minecraft.server.v1_7_R4.PacketPlayOutTransaction;
import net.minecraft.server.v1_7_R4.PacketPlayOutUpdateAttributes;
import net.minecraft.server.v1_7_R4.PacketPlayOutUpdateHealth;
import net.minecraft.server.v1_7_R4.PacketPlayOutUpdateSign;
import net.minecraft.server.v1_7_R4.PacketPlayOutUpdateTime;
import net.minecraft.server.v1_7_R4.PacketPlayOutWindowData;
import net.minecraft.server.v1_7_R4.PacketPlayOutWindowItems;
import net.minecraft.server.v1_7_R4.PacketPlayOutWorldEvent;
import net.minecraft.server.v1_7_R4.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_7_R4.PacketStatusInListener;
import net.minecraft.server.v1_7_R4.PacketStatusInPing;
import net.minecraft.server.v1_7_R4.PacketStatusInStart;
import net.minecraft.server.v1_7_R4.PacketStatusOutListener;
import net.minecraft.server.v1_7_R4.PacketStatusOutPong;
import net.minecraft.server.v1_7_R4.PacketStatusOutServerInfo;

public class SPacketListener
    extends VPacketListener
    implements PacketPlayInListener,
    PacketPlayOutListener,
    PacketStatusInListener,
    PacketStatusOutListener,
    PacketLoginInListener,
    PacketLoginOutListener,
    PacketHandshakingInListener {
    public void a(PacketPlayOutStatistic packet) {
    }

    public void a(PacketPlayOutBlockBreakAnimation packet) {
    }

    public void a(PacketPlayOutEntity packet) {
        this.handleOut(packet, new SPacketPlayOutEntity());
    }

    public void a(PacketPlayInCustomPayload packet) {
        this.handleIn(packet, new SPacketPlayInCustomPayload());
    }

    public SPacketListener(PlayerData playerData) {
        super(playerData);
    }

    public void a(PacketStatusOutPong packet) {
    }

    public void a(PacketPlayOutOpenWindow packet) {
        this.handleOut(packet, new SPacketPlayOutOpenWindow());
    }

    public void a(PacketPlayOutOpenSignEditor packet) {
    }

    public void a(PacketPlayInUseEntity packet) {
        this.handleIn(packet, new SPacketPlayInUseEntity());
    }

    public void a(PacketPlayOutMapChunkBulk packet) {
    }

    public void a(PacketPlayOutUpdateHealth packet) {
    }

    public void a(PacketPlayOutBlockChange packet) {
    }

    public void a(PacketPlayOutTabComplete packet) {
    }

    public void a(PacketPlayInTabComplete packet) {
    }

    public void a(PacketPlayOutScoreboardObjective packet) {
    }

    public void a(PacketPlayOutUpdateAttributes packet) {
        this.handleOut(packet, new SPacketPlayOutUpdateAttributes());
    }

    public void a(PacketPlayOutScoreboardScore packet) {
    }

    public void a(PacketHandshakingInSetProtocol packet) {
    }

    public void a(PacketPlayOutSpawnEntityPainting packet) {
    }

    public void a(PacketPlayInUpdateSign packet) {
    }

    public void a(PacketPlayOutCloseWindow packet) {
    }

    public void a(PacketPlayInTransaction packet) {
        this.handleIn(packet, new SPacketPlayInTransaction());
    }

    public void a(PacketPlayOutTransaction packet) {
        this.handleOut(packet, new SPacketPlayOutTransaction());
    }

    public void a(PacketPlayOutSpawnEntityLiving packet) {
        this.handleOut(packet, new SPacketPlayOutSpawnEntityLiving());
    }

    public void a(PacketPlayInBlockDig packet) {
        this.handleIn(packet, new SPacketPlayInBlockDig());
    }

    public void a(PacketPlayInSetCreativeSlot packet) {
        this.handleIn(packet, new SPacketPlayInSetCreativeSlot());
    }

    public void a(PacketPlayInHeldItemSlot packet) {
        this.handleIn(packet, new SPacketPlayInHeldItemSlot());
    }

    public void a(PacketPlayOutLogin packet) {
    }

    public void a(PacketPlayOutScoreboardTeam packet) {
    }

    public void a(PacketPlayOutEntityVelocity packet) {
        this.handleOut(packet, new SPacketPlayOutEntityVelocity());
    }

    public void a(PacketPlayOutWindowData packet) {
    }

    public void a(PacketPlayInArmAnimation packet) {
        this.handleIn(packet, new SPacketPlayInArmAnimation());
    }

    public void a(PacketPlayOutRespawn packet) {
        this.handleOut(packet, new SPacketPlayOutRespawn());
    }

    public void a(PacketPlayOutSpawnEntityWeather packet) {
    }

    public void a(PacketPlayOutWorldEvent packet) {
    }

    public void a(PacketPlayOutNamedEntitySpawn packet) {
        this.handleOut(packet, new SPacketPlayOutNamedEntitySpawn());
    }

    public void a(PacketPlayInBlockPlace packet) {
        this.handleIn(packet, new SPacketPlayInBlockPlace());
    }

    public void a(PacketLoginOutSuccess packet) {
    }

    public void a(PacketPlayInWindowClick packet) {
        this.handleIn(packet, new SPacketPlayInWindowClick());
    }

    public void a(PacketLoginInStart packet) {
    }

    public void a(PacketPlayOutSetSlot packet) {
        this.handleOut(packet, new SPacketPlayOutSetSlot());
    }

    public void a(PacketPlayOutEntityDestroy packet) {
        this.handleOut(packet, new SPacketPlayOutEntityDestroy());
    }

    public void a(PacketPlayInKeepAlive packet) {
        this.handleIn(packet, new SPacketPlayInKeepAlive());
    }

    public void a(PacketPlayOutMap packet) {
    }

    public void a(PacketPlayInClientCommand packet) {
        this.handleIn(packet, new SPacketPlayInClientCommand());
    }

    public void a(PacketPlayInChat packet) {
    }

    public void a(PacketPlayOutWindowItems packet) {
    }

    public void a(PacketStatusInStart packet) {
    }

    public void a(PacketPlayOutEntityMetadata packet) {
    }

    public void a(PacketPlayOutSpawnEntityExperienceOrb packet) {
    }

    public void a(PacketPlayOutWorldParticles packet) {
    }

    public void a(PacketPlayOutEntityHeadRotation packet) {
    }

    public void a(PacketPlayOutScoreboardDisplayObjective packet) {
    }

    public void a(PacketPlayOutChat packet) {
    }

    public void a(PacketPlayOutMapChunk packet) {
    }

    public void a(PacketPlayOutEntityTeleport packet) {
        this.handleOut(packet, new SPacketPlayOutEntityTeleport());
    }

    public void a(PacketStatusInPing packet) {
    }

    public void a(PacketPlayOutUpdateSign packet) {
    }

    public void a(PacketPlayOutKeepAlive packet) {
        this.handleOut(packet, new SPacketPlayOutKeepAlive());
    }

    public void a(PacketPlayOutEntityEffect packet) {
        this.handleOut(packet, new SPacketPlayOutEntityEffect());
    }

    public void a(PacketPlayOutTileEntityData packet) {
    }

    public void a(PacketPlayOutSpawnPosition packet) {
        this.handleOut(packet, new SPacketPlayOutSpawnPosition());
    }

    public void a(PacketPlayOutBlockAction packet) {
    }

    public void a(PacketLoginOutDisconnect packet) {
    }

    public void a(PacketPlayOutAttachEntity packet) {
        this.handleOut(packet, new SPacketPlayOutAttachEntity());
    }

    public void a(PacketPlayOutEntityStatus packet) {
    }

    public void a(PacketPlayOutUpdateTime packet) {
    }

    public void a(PacketPlayOutCustomPayload packet) {
    }

    public void a(PacketPlayOutRemoveEntityEffect packet) {
        this.handleOut(packet, new SPacketPlayOutRemoveEntityEffect());
    }

    public void a(PacketPlayInFlying packet) {
        this.handleIn(packet, new SPacketPlayInFlying());
    }

    public void a(PacketStatusOutServerInfo packet) {
    }

    public void a(PacketPlayInEnchantItem packet) {
    }

    public void a() {
    }

    public void a(PacketPlayOutGameStateChange packet) {
        this.handleOut(packet, new SPacketPlayOutGameStateChange());
    }

    public void a(PacketPlayOutExperience packet) {
    }

    public void a(PacketPlayOutEntityEquipment packet) {
    }

    public void a(PacketPlayOutExplosion packet) {
        this.handleOut(packet, new SPacketPlayOutExplosion());
    }

    public void a(PacketPlayOutCollect packet) {
    }

    public void a(PacketPlayOutPlayerInfo packet) {
    }

    public void a(PacketPlayInSteerVehicle packet) {
        this.handleIn(packet, new SPacketPlayInSteerVehicle());
    }

    public void a(PacketLoginInEncryptionBegin packet) {
    }

    public void a(PacketPlayOutBed packet) {
    }

    public void a(PacketPlayOutPosition packet) {
        this.handleOut(packet, new SPacketPlayOutPosition());
    }

    public void a(PacketPlayOutSpawnEntity packet) {
        this.handleOut(packet, new SPacketPlayOutSpawnEntity());
    }

    public void a(PacketPlayInAbilities packet) {
        this.handleIn(packet, new SPacketPlayInAbilities());
    }

    public void a(PacketPlayOutHeldItemSlot packet) {
    }

    public void a(PacketPlayInSettings packet) {
    }

    public void a(PacketPlayOutMultiBlockChange packet) {
    }

    public void a(PacketLoginOutEncryptionBegin packet) {
    }

    public void a(PacketPlayOutNamedSoundEffect packet) {
    }

    public void a(PacketPlayOutKickDisconnect packet) {
    }

    public void a(EnumProtocol enumProtocol, EnumProtocol enumProtocol2) {
    }

    public void a(PacketPlayInEntityAction packet) {
        this.handleIn(packet, new SPacketPlayInEntityAction());
    }

    public void a(PacketPlayInCloseWindow packet) {
        this.handleIn(packet, new SPacketPlayInCloseWindow());
    }

    @Override
    public void process(Object obj) {
        if (obj instanceof Packet) {
            ((Packet)obj).handle(this);
        }
    }

    public void a(IChatBaseComponent packet) {
    }

    public void a(PacketPlayOutAbilities packet) {
        this.handleOut(packet, new SPacketPlayOutAbilities());
    }

    public void a(PacketPlayOutAnimation packet) {
    }
}
