package me.levansj01.verus.compat.v1_8_R3;

import java.lang.reflect.Method;
import me.levansj01.verus.compat.api.VPacketListener;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayInAbilities;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayInArmAnimation;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayInBlockDig;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayInBlockPlace;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayInClientCommand;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayInCloseWindow;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayInCustomPayload;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayInEntityAction;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayInFlying;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayInHeldItemSlot;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayInKeepAlive;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayInSetCreativeSlot;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayInSteerVehicle;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayInTransaction;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayInUseEntity;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayInWindowClick;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutAbilities;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutAttachEntity;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutBlockChange;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutEntity;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutEntityDestroy;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutEntityEffect;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutEntityTeleport;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutEntityVelocity;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutExplosion;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutGameStateChange;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutKeepAlive;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutMapChunk;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutMapChunkBulk;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutMultiBlockChange;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutNamedEntitySpawn;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutOpenWindow;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutPosition;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutRemoveEntityEffect;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutRespawn;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutSetSlot;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutSpawnEntity;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutSpawnEntityLiving;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutSpawnPosition;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutTransaction;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutUpdateAttributes;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketHandshakingInListener;
import net.minecraft.server.v1_8_R3.PacketHandshakingInSetProtocol;
import net.minecraft.server.v1_8_R3.PacketListener;
import net.minecraft.server.v1_8_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_8_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_8_R3.PacketLoginInEncryptionBegin;
import net.minecraft.server.v1_8_R3.PacketLoginInListener;
import net.minecraft.server.v1_8_R3.PacketLoginInStart;
import net.minecraft.server.v1_8_R3.PacketLoginOutDisconnect;
import net.minecraft.server.v1_8_R3.PacketLoginOutEncryptionBegin;
import net.minecraft.server.v1_8_R3.PacketLoginOutListener;
import net.minecraft.server.v1_8_R3.PacketLoginOutSetCompression;
import net.minecraft.server.v1_8_R3.PacketLoginOutSuccess;
import net.minecraft.server.v1_8_R3.PacketPlayInAbilities;
import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockDig;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockPlace;
import net.minecraft.server.v1_8_R3.PacketPlayInChat;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import net.minecraft.server.v1_8_R3.PacketPlayInCloseWindow;
import net.minecraft.server.v1_8_R3.PacketPlayInCustomPayload;
import net.minecraft.server.v1_8_R3.PacketPlayInEnchantItem;
import net.minecraft.server.v1_8_R3.PacketPlayInEntityAction;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import net.minecraft.server.v1_8_R3.PacketPlayInHeldItemSlot;
import net.minecraft.server.v1_8_R3.PacketPlayInKeepAlive;
import net.minecraft.server.v1_8_R3.PacketPlayInResourcePackStatus;
import net.minecraft.server.v1_8_R3.PacketPlayInSetCreativeSlot;
import net.minecraft.server.v1_8_R3.PacketPlayInSettings;
import net.minecraft.server.v1_8_R3.PacketPlayInSpectate;
import net.minecraft.server.v1_8_R3.PacketPlayInSteerVehicle;
import net.minecraft.server.v1_8_R3.PacketPlayInTabComplete;
import net.minecraft.server.v1_8_R3.PacketPlayInTransaction;
import net.minecraft.server.v1_8_R3.PacketPlayInUpdateSign;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import net.minecraft.server.v1_8_R3.PacketPlayInWindowClick;
import net.minecraft.server.v1_8_R3.PacketPlayOutAbilities;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayOutAttachEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutBed;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockAction;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockBreakAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockChange;
import net.minecraft.server.v1_8_R3.PacketPlayOutCamera;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutCloseWindow;
import net.minecraft.server.v1_8_R3.PacketPlayOutCollect;
import net.minecraft.server.v1_8_R3.PacketPlayOutCombatEvent;
import net.minecraft.server.v1_8_R3.PacketPlayOutCustomPayload;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEffect;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityStatus;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityVelocity;
import net.minecraft.server.v1_8_R3.PacketPlayOutExperience;
import net.minecraft.server.v1_8_R3.PacketPlayOutExplosion;
import net.minecraft.server.v1_8_R3.PacketPlayOutGameStateChange;
import net.minecraft.server.v1_8_R3.PacketPlayOutHeldItemSlot;
import net.minecraft.server.v1_8_R3.PacketPlayOutKeepAlive;
import net.minecraft.server.v1_8_R3.PacketPlayOutKickDisconnect;
import net.minecraft.server.v1_8_R3.PacketPlayOutLogin;
import net.minecraft.server.v1_8_R3.PacketPlayOutMap;
import net.minecraft.server.v1_8_R3.PacketPlayOutMapChunk;
import net.minecraft.server.v1_8_R3.PacketPlayOutMapChunkBulk;
import net.minecraft.server.v1_8_R3.PacketPlayOutMultiBlockChange;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedSoundEffect;
import net.minecraft.server.v1_8_R3.PacketPlayOutOpenSignEditor;
import net.minecraft.server.v1_8_R3.PacketPlayOutOpenWindow;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PacketPlayOutPosition;
import net.minecraft.server.v1_8_R3.PacketPlayOutRemoveEntityEffect;
import net.minecraft.server.v1_8_R3.PacketPlayOutResourcePackSend;
import net.minecraft.server.v1_8_R3.PacketPlayOutRespawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardDisplayObjective;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardObjective;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardScore;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;
import net.minecraft.server.v1_8_R3.PacketPlayOutServerDifficulty;
import net.minecraft.server.v1_8_R3.PacketPlayOutSetCompression;
import net.minecraft.server.v1_8_R3.PacketPlayOutSetSlot;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityExperienceOrb;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityPainting;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityWeather;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnPosition;
import net.minecraft.server.v1_8_R3.PacketPlayOutStatistic;
import net.minecraft.server.v1_8_R3.PacketPlayOutTabComplete;
import net.minecraft.server.v1_8_R3.PacketPlayOutTileEntityData;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutTransaction;
import net.minecraft.server.v1_8_R3.PacketPlayOutUpdateAttributes;
import net.minecraft.server.v1_8_R3.PacketPlayOutUpdateEntityNBT;
import net.minecraft.server.v1_8_R3.PacketPlayOutUpdateHealth;
import net.minecraft.server.v1_8_R3.PacketPlayOutUpdateSign;
import net.minecraft.server.v1_8_R3.PacketPlayOutUpdateTime;
import net.minecraft.server.v1_8_R3.PacketPlayOutWindowData;
import net.minecraft.server.v1_8_R3.PacketPlayOutWindowItems;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldBorder;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldEvent;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_8_R3.PacketStatusInListener;
import net.minecraft.server.v1_8_R3.PacketStatusInPing;
import net.minecraft.server.v1_8_R3.PacketStatusInStart;
import net.minecraft.server.v1_8_R3.PacketStatusOutListener;
import net.minecraft.server.v1_8_R3.PacketStatusOutPong;
import net.minecraft.server.v1_8_R3.PacketStatusOutServerInfo;

public class SPacketListener
    extends VPacketListener
    implements PacketListenerPlayIn,
    PacketListenerPlayOut,
    PacketStatusInListener,
    PacketStatusOutListener,
    PacketLoginInListener,
    PacketLoginOutListener,
    PacketHandshakingInListener {
    private static final Method method = SafeReflection.access(new String[]{"net.minecraft.server.v1_8_R3.Packet"}, "a", PacketListener.class);

    @Override
    public void a(PacketPlayOutTabComplete packet) {
    }

    @Override
    public void a(PacketPlayInTransaction packet) {
        this.handleIn(packet, new SPacketPlayInTransaction());
    }

    @Override
    public void a(PacketPlayOutSpawnEntityLiving packet) {
        this.handleOut(packet, new SPacketPlayOutSpawnEntityLiving());
    }

    @Override
    public void a(PacketPlayOutSetSlot packet) {
        this.handleOut(packet, new SPacketPlayOutSetSlot());
    }

    @Override
    public void a(PacketPlayOutEntityEquipment packet) {
    }

    @Override
    public void a(PacketPlayInResourcePackStatus packet) {
    }

    @Override
    public void a(PacketPlayOutAbilities packet) {
        this.handleOut(packet, new SPacketPlayOutAbilities());
    }

    @Override
    public void a(PacketPlayOutNamedSoundEffect packet) {
    }

    @Override
    public void a(PacketPlayOutKeepAlive packet) {
        this.handleOut(packet, new SPacketPlayOutKeepAlive());
    }

    @Override
    public void a(PacketPlayOutWorldParticles packet) {
    }

    @Override
    public void a(PacketPlayInFlying packet) {
        this.handleIn(packet, new SPacketPlayInFlying());
    }

    @Override
    public void a(PacketPlayInSteerVehicle packet) {
        this.handleIn(packet, new SPacketPlayInSteerVehicle());
    }

    @Override
    public void a(PacketPlayOutStatistic packet) {
    }

    @Override
    public void a(PacketPlayInUseEntity packet) {
        this.handleIn(packet, new SPacketPlayInUseEntity());
    }

    @Override
    public void a(PacketPlayOutSpawnEntityWeather packet) {
    }

    @Override
    public void a(IChatBaseComponent packet) {
    }

    @Override
    public void a(PacketPlayOutTileEntityData packet) {
    }

    @Override
    public void a(PacketPlayOutMap packet) {
    }

    @Override
    public void a(PacketPlayOutMapChunkBulk packet) {
        this.handleOut(packet, new SPacketPlayOutMapChunkBulk());
    }

    @Override
    public void process(Object obj) {
        if (obj instanceof Packet) {
            try {
                this.processTyped((Packet)obj);
            } catch (IncompatibleClassChangeError exception) {
                SafeReflection.execute(method, obj, this);
            }
        }
    }

    @Override
    public void a(PacketPlayInCloseWindow packet) {
        this.handleIn(packet, new SPacketPlayInCloseWindow());
    }

    @Override
    public void a(PacketPlayOutEntityVelocity packet) {
        this.handleOut(packet, new SPacketPlayOutEntityVelocity());
    }

    @Override
    public void a(PacketPlayOutRemoveEntityEffect packet) {
        this.handleOut(packet, new SPacketPlayOutRemoveEntityEffect());
    }

    @Override
    public void a(PacketPlayOutWindowData packet) {
    }

    @Override
    public void a(PacketPlayOutLogin packet) {
    }

    @Override
    public void a(PacketPlayOutRespawn packet) {
        this.handleOut(packet, new SPacketPlayOutRespawn());
    }

    @Override
    public void a(PacketPlayOutServerDifficulty packet) {
    }

    @Override
    public void a(PacketPlayOutMapChunk packet) {
        this.handleOut(packet, new SPacketPlayOutMapChunk());
    }

    @Override
    public void a(PacketPlayOutExperience packet) {
    }

    @Override
    public void a(PacketPlayOutEntityHeadRotation packet) {
    }

    @Override
    public void a(PacketPlayInEnchantItem packet) {
    }

    @Override
    public void a(PacketLoginInEncryptionBegin packet) {
    }

    @Override
    public void a(PacketPlayOutPosition packet) {
        this.handleOut(packet, new SPacketPlayOutPosition());
    }

    @Override
    public void a(PacketPlayOutSpawnEntity packet) {
        this.handleOut(packet, new SPacketPlayOutSpawnEntity());
    }

    @Override
    public void a(PacketPlayInChat packet) {
    }

    @Override
    public void a(PacketPlayOutScoreboardTeam packet) {
    }

    @Override
    public void a(PacketStatusOutServerInfo packet) {
    }

    @Override
    public void a(PacketPlayOutTransaction packet) {
        this.handleOut(packet, new SPacketPlayOutTransaction());
    }

    @Override
    public void a(PacketPlayOutCollect packet) {
    }

    @Override
    public void a(PacketPlayOutKickDisconnect packet) {
    }

    @Override
    public void a(PacketPlayOutResourcePackSend packet) {
    }

    @Override
    public void a(PacketPlayOutOpenWindow packet) {
        this.handleOut(packet, new SPacketPlayOutOpenWindow());
    }

    @Override
    public void a(PacketPlayOutExplosion packet) {
        this.handleOut(packet, new SPacketPlayOutExplosion());
    }

    @Override
    public void a(PacketPlayOutBed packet) {
    }

    @Override
    public void a(PacketLoginOutEncryptionBegin packet) {
    }

    @Override
    public void a(PacketPlayOutWorldBorder packet) {
    }

    @Override
    public void a(PacketPlayInSettings packet) {
    }

    @Override
    public void a(PacketPlayOutWorldEvent packet) {
    }

    @Override
    public void a(PacketPlayOutBlockChange packet) {
        this.handleOut(packet, new SPacketPlayOutBlockChange());
    }

    @Override
    public void a(PacketPlayInBlockPlace packet) {
        this.handleIn(packet, new SPacketPlayInBlockPlace());
    }

    @Override
    public void a(PacketPlayOutCustomPayload packet) {
    }

    @Override
    public void a(PacketPlayOutBlockAction packet) {
    }

    @Override
    public void a(PacketPlayOutMultiBlockChange packet) {
        this.handleOut(packet, new SPacketPlayOutMultiBlockChange());
    }

    @Override
    public void a(PacketLoginInStart packet) {
    }

    @Override
    public void a(PacketPlayOutAttachEntity packet) {
        this.handleOut(packet, new SPacketPlayOutAttachEntity());
    }

    @Override
    public void a(PacketPlayOutGameStateChange packet) {
        this.handleOut(packet, new SPacketPlayOutGameStateChange());
    }

    @Override
    public void a(PacketPlayOutNamedEntitySpawn packet) {
        this.handleOut(packet, new SPacketPlayOutNamedEntitySpawn());
    }

    @Override
    public void a(PacketPlayInAbilities packet) {
        this.handleIn(packet, new SPacketPlayInAbilities());
    }

    @Override
    public void a(PacketPlayInBlockDig packet) {
        this.handleIn(packet, new SPacketPlayInBlockDig());
    }

    @Override
    public void a(PacketPlayInArmAnimation packet) {
        this.handleIn(packet, new SPacketPlayInArmAnimation());
    }

    @Override
    public void a(PacketPlayInWindowClick packet) {
        this.handleIn(packet, new SPacketPlayInWindowClick());
    }

    @Override
    public void a(PacketPlayOutEntityMetadata packet) {
    }

    @Override
    public void a(PacketPlayOutUpdateHealth packet) {
    }

    @Override
    public void a(PacketPlayOutSpawnPosition packet) {
        this.handleOut(packet, new SPacketPlayOutSpawnPosition());
    }

    @Override
    public void a(PacketPlayInSpectate packet) {
    }

    @Override
    public void a(PacketPlayOutBlockBreakAnimation packet) {
    }

    @Override
    public void a(PacketPlayOutPlayerInfo packet) {
    }

    @Override
    public void a(PacketLoginOutSetCompression packet) {
    }

    @Override
    public void a(PacketPlayOutSpawnEntityPainting packet) {
    }

    @Override
    public void a(PacketPlayOutEntityStatus packet) {
    }

    @Override
    public void a(PacketPlayOutCloseWindow packet) {
    }

    @Override
    public void a(PacketPlayOutChat packet) {
    }

    @Override
    public void a(PacketPlayInClientCommand packet) {
        this.handleIn(packet, new SPacketPlayInClientCommand());
    }

    @Override
    public void a(PacketPlayOutCamera packet) {
    }

    @Override
    public void a(PacketPlayOutSetCompression packet) {
    }

    @Override
    public void a(PacketPlayOutEntityTeleport packet) {
        this.handleOut(packet, new SPacketPlayOutEntityTeleport());
    }

    @Override
    public void a(PacketStatusInPing packet) {
    }

    @Override
    public void a(PacketPlayOutUpdateTime packet) {
    }

    @Override
    public void a(PacketPlayInSetCreativeSlot packet) {
        this.handleIn(packet, new SPacketPlayInSetCreativeSlot());
    }

    @Override
    public void a(PacketPlayOutWindowItems packet) {
    }

    @Override
    public void a(PacketPlayOutEntityDestroy packet) {
        this.handleOut(packet, new SPacketPlayOutEntityDestroy());
    }

    @Override
    public void a(PacketPlayOutUpdateAttributes packet) {
        this.handleOut(packet, new SPacketPlayOutUpdateAttributes());
    }

    @Override
    public void a(PacketStatusInStart packet) {
    }

    @Override
    public void a(PacketPlayInTabComplete packet) {
    }

    @Override
    public void a(PacketPlayOutAnimation packet) {
    }

    @Override
    public void a(PacketLoginOutSuccess packet) {
    }

    @Override
    public void a(PacketPlayOutEntityEffect packet) {
        this.handleOut(packet, new SPacketPlayOutEntityEffect());
    }

    @Override
    public void a(PacketLoginOutDisconnect packet) {
    }

    @Override
    public void a(PacketPlayOutTitle packet) {
    }

    public SPacketListener(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void a(PacketStatusOutPong packet) {
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public <T extends PacketListener> void processTyped(Packet<T> packet) {
        ((Packet)packet).a(this);
    }

    @Override
    public void a(PacketPlayInHeldItemSlot packet) {
        this.handleIn(packet, new SPacketPlayInHeldItemSlot());
    }

    @Override
    public void a(PacketHandshakingInSetProtocol packet) {
    }

    @Override
    public void a(PacketPlayOutOpenSignEditor packet) {
    }

    @Override
    public void a(PacketPlayOutUpdateSign packet) {
    }

    @Override
    public void a(PacketPlayOutScoreboardObjective packet) {
    }

    @Override
    public void a(PacketPlayInEntityAction packet) {
        this.handleIn(packet, new SPacketPlayInEntityAction());
    }

    @Override
    public void a(PacketPlayOutPlayerListHeaderFooter packet) {
    }

    @Override
    public void a(PacketPlayOutEntity packet) {
        this.handleOut(packet, new SPacketPlayOutEntity());
    }

    @Override
    public void a(PacketPlayInCustomPayload packet) {
        this.handleIn(packet, new SPacketPlayInCustomPayload());
    }

    @Override
    public void a(PacketPlayOutScoreboardDisplayObjective packet) {
    }

    @Override
    public void a(PacketPlayOutScoreboardScore packet) {
    }

    @Override
    public void a(PacketPlayOutHeldItemSlot packet) {
    }

    @Override
    public void a(PacketPlayOutUpdateEntityNBT packet) {
    }

    @Override
    public void a(PacketPlayOutCombatEvent packet) {
    }

    @Override
    public void a(PacketPlayInUpdateSign packet) {
    }

    @Override
    public void a(PacketPlayOutSpawnEntityExperienceOrb packet) {
    }

    @Override
    public void a(PacketPlayInKeepAlive packet) {
        this.handleIn(packet, new SPacketPlayInKeepAlive());
    }
}
