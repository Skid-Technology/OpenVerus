package me.levansj01.verus.compat.v1_17_R1;

import me.levansj01.verus.compat.api.VPacketListener;
import me.levansj01.verus.compat.v1_17_R1.packets.SClientboundPingPacket;
import me.levansj01.verus.compat.v1_17_R1.packets.SPacketPlayInAbilities;
import me.levansj01.verus.compat.v1_17_R1.packets.SPacketPlayInArmAnimation;
import me.levansj01.verus.compat.v1_17_R1.packets.SPacketPlayInBlockDig;
import me.levansj01.verus.compat.v1_17_R1.packets.SPacketPlayInBlockPlace;
import me.levansj01.verus.compat.v1_17_R1.packets.SPacketPlayInClientCommand;
import me.levansj01.verus.compat.v1_17_R1.packets.SPacketPlayInCloseWindow;
import me.levansj01.verus.compat.v1_17_R1.packets.SPacketPlayInCustomPayload;
import me.levansj01.verus.compat.v1_17_R1.packets.SPacketPlayInEntityAction;
import me.levansj01.verus.compat.v1_17_R1.packets.SPacketPlayInFlying;
import me.levansj01.verus.compat.v1_17_R1.packets.SPacketPlayInHeldItemSlot;
import me.levansj01.verus.compat.v1_17_R1.packets.SPacketPlayInKeepAlive;
import me.levansj01.verus.compat.v1_17_R1.packets.SPacketPlayInSetCreativeSlot;
import me.levansj01.verus.compat.v1_17_R1.packets.SPacketPlayInSteerVehicle;
import me.levansj01.verus.compat.v1_17_R1.packets.SPacketPlayInTeleportAccept;
import me.levansj01.verus.compat.v1_17_R1.packets.SPacketPlayInUseEntity;
import me.levansj01.verus.compat.v1_17_R1.packets.SPacketPlayInUseItem;
import me.levansj01.verus.compat.v1_17_R1.packets.SPacketPlayInVehicleMove;
import me.levansj01.verus.compat.v1_17_R1.packets.SPacketPlayInWindowClick;
import me.levansj01.verus.compat.v1_17_R1.packets.SPacketPlayOutAbilities;
import me.levansj01.verus.compat.v1_17_R1.packets.SPacketPlayOutAttachEntity;
import me.levansj01.verus.compat.v1_17_R1.packets.SPacketPlayOutEntity;
import me.levansj01.verus.compat.v1_17_R1.packets.SPacketPlayOutEntityDestroy;
import me.levansj01.verus.compat.v1_17_R1.packets.SPacketPlayOutEntityEffect;
import me.levansj01.verus.compat.v1_17_R1.packets.SPacketPlayOutEntityMetadata;
import me.levansj01.verus.compat.v1_17_R1.packets.SPacketPlayOutEntityTeleport;
import me.levansj01.verus.compat.v1_17_R1.packets.SPacketPlayOutEntityVelocity;
import me.levansj01.verus.compat.v1_17_R1.packets.SPacketPlayOutExplosion;
import me.levansj01.verus.compat.v1_17_R1.packets.SPacketPlayOutGameStateChange;
import me.levansj01.verus.compat.v1_17_R1.packets.SPacketPlayOutKeepAlive;
import me.levansj01.verus.compat.v1_17_R1.packets.SPacketPlayOutNamedEntitySpawn;
import me.levansj01.verus.compat.v1_17_R1.packets.SPacketPlayOutOpenWindow;
import me.levansj01.verus.compat.v1_17_R1.packets.SPacketPlayOutPosition;
import me.levansj01.verus.compat.v1_17_R1.packets.SPacketPlayOutRemoveEntityEffect;
import me.levansj01.verus.compat.v1_17_R1.packets.SPacketPlayOutRespawn;
import me.levansj01.verus.compat.v1_17_R1.packets.SPacketPlayOutSetSlot;
import me.levansj01.verus.compat.v1_17_R1.packets.SPacketPlayOutSpawnEntity;
import me.levansj01.verus.compat.v1_17_R1.packets.SPacketPlayOutSpawnEntityLiving;
import me.levansj01.verus.compat.v1_17_R1.packets.SPacketPlayOutSpawnPosition;
import me.levansj01.verus.compat.v1_17_R1.packets.SPacketPlayOutUpdateAttributes;
import me.levansj01.verus.compat.v1_17_R1.packets.SServerboundPongPacket;
import me.levansj01.verus.data.PlayerData;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketListener;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddVibrationSignalPacket;
import net.minecraft.network.protocol.game.ClientboundClearTitlesPacket;
import net.minecraft.network.protocol.game.ClientboundInitializeBorderPacket;
import net.minecraft.network.protocol.game.ClientboundPingPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatEndPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatEnterPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatKillPacket;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderCenterPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderLerpSizePacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderSizePacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderWarningDelayPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderWarningDistancePacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.network.protocol.game.PacketListenerPlayIn;
import net.minecraft.network.protocol.game.PacketListenerPlayOut;
import net.minecraft.network.protocol.game.PacketPlayInAbilities;
import net.minecraft.network.protocol.game.PacketPlayInAdvancements;
import net.minecraft.network.protocol.game.PacketPlayInArmAnimation;
import net.minecraft.network.protocol.game.PacketPlayInAutoRecipe;
import net.minecraft.network.protocol.game.PacketPlayInBEdit;
import net.minecraft.network.protocol.game.PacketPlayInBeacon;
import net.minecraft.network.protocol.game.PacketPlayInBlockDig;
import net.minecraft.network.protocol.game.PacketPlayInBlockPlace;
import net.minecraft.network.protocol.game.PacketPlayInBoatMove;
import net.minecraft.network.protocol.game.PacketPlayInChat;
import net.minecraft.network.protocol.game.PacketPlayInClientCommand;
import net.minecraft.network.protocol.game.PacketPlayInCloseWindow;
import net.minecraft.network.protocol.game.PacketPlayInCustomPayload;
import net.minecraft.network.protocol.game.PacketPlayInDifficultyChange;
import net.minecraft.network.protocol.game.PacketPlayInDifficultyLock;
import net.minecraft.network.protocol.game.PacketPlayInEnchantItem;
import net.minecraft.network.protocol.game.PacketPlayInEntityAction;
import net.minecraft.network.protocol.game.PacketPlayInEntityNBTQuery;
import net.minecraft.network.protocol.game.PacketPlayInFlying;
import net.minecraft.network.protocol.game.PacketPlayInHeldItemSlot;
import net.minecraft.network.protocol.game.PacketPlayInItemName;
import net.minecraft.network.protocol.game.PacketPlayInJigsawGenerate;
import net.minecraft.network.protocol.game.PacketPlayInKeepAlive;
import net.minecraft.network.protocol.game.PacketPlayInPickItem;
import net.minecraft.network.protocol.game.PacketPlayInRecipeDisplayed;
import net.minecraft.network.protocol.game.PacketPlayInRecipeSettings;
import net.minecraft.network.protocol.game.PacketPlayInResourcePackStatus;
import net.minecraft.network.protocol.game.PacketPlayInSetCommandBlock;
import net.minecraft.network.protocol.game.PacketPlayInSetCommandMinecart;
import net.minecraft.network.protocol.game.PacketPlayInSetCreativeSlot;
import net.minecraft.network.protocol.game.PacketPlayInSetJigsaw;
import net.minecraft.network.protocol.game.PacketPlayInSettings;
import net.minecraft.network.protocol.game.PacketPlayInSpectate;
import net.minecraft.network.protocol.game.PacketPlayInSteerVehicle;
import net.minecraft.network.protocol.game.PacketPlayInStruct;
import net.minecraft.network.protocol.game.PacketPlayInTabComplete;
import net.minecraft.network.protocol.game.PacketPlayInTeleportAccept;
import net.minecraft.network.protocol.game.PacketPlayInTileNBTQuery;
import net.minecraft.network.protocol.game.PacketPlayInTrSel;
import net.minecraft.network.protocol.game.PacketPlayInUpdateSign;
import net.minecraft.network.protocol.game.PacketPlayInUseEntity;
import net.minecraft.network.protocol.game.PacketPlayInUseItem;
import net.minecraft.network.protocol.game.PacketPlayInVehicleMove;
import net.minecraft.network.protocol.game.PacketPlayInWindowClick;
import net.minecraft.network.protocol.game.PacketPlayOutAbilities;
import net.minecraft.network.protocol.game.PacketPlayOutAdvancements;
import net.minecraft.network.protocol.game.PacketPlayOutAnimation;
import net.minecraft.network.protocol.game.PacketPlayOutAttachEntity;
import net.minecraft.network.protocol.game.PacketPlayOutAutoRecipe;
import net.minecraft.network.protocol.game.PacketPlayOutBlockAction;
import net.minecraft.network.protocol.game.PacketPlayOutBlockBreak;
import net.minecraft.network.protocol.game.PacketPlayOutBlockBreakAnimation;
import net.minecraft.network.protocol.game.PacketPlayOutBlockChange;
import net.minecraft.network.protocol.game.PacketPlayOutBoss;
import net.minecraft.network.protocol.game.PacketPlayOutCamera;
import net.minecraft.network.protocol.game.PacketPlayOutChat;
import net.minecraft.network.protocol.game.PacketPlayOutCloseWindow;
import net.minecraft.network.protocol.game.PacketPlayOutCollect;
import net.minecraft.network.protocol.game.PacketPlayOutCommands;
import net.minecraft.network.protocol.game.PacketPlayOutCustomPayload;
import net.minecraft.network.protocol.game.PacketPlayOutCustomSoundEffect;
import net.minecraft.network.protocol.game.PacketPlayOutEntity;
import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy;
import net.minecraft.network.protocol.game.PacketPlayOutEntityEffect;
import net.minecraft.network.protocol.game.PacketPlayOutEntityEquipment;
import net.minecraft.network.protocol.game.PacketPlayOutEntityHeadRotation;
import net.minecraft.network.protocol.game.PacketPlayOutEntityMetadata;
import net.minecraft.network.protocol.game.PacketPlayOutEntitySound;
import net.minecraft.network.protocol.game.PacketPlayOutEntityStatus;
import net.minecraft.network.protocol.game.PacketPlayOutEntityTeleport;
import net.minecraft.network.protocol.game.PacketPlayOutEntityVelocity;
import net.minecraft.network.protocol.game.PacketPlayOutExperience;
import net.minecraft.network.protocol.game.PacketPlayOutExplosion;
import net.minecraft.network.protocol.game.PacketPlayOutGameStateChange;
import net.minecraft.network.protocol.game.PacketPlayOutHeldItemSlot;
import net.minecraft.network.protocol.game.PacketPlayOutKeepAlive;
import net.minecraft.network.protocol.game.PacketPlayOutKickDisconnect;
import net.minecraft.network.protocol.game.PacketPlayOutLightUpdate;
import net.minecraft.network.protocol.game.PacketPlayOutLogin;
import net.minecraft.network.protocol.game.PacketPlayOutLookAt;
import net.minecraft.network.protocol.game.PacketPlayOutMap;
import net.minecraft.network.protocol.game.PacketPlayOutMapChunk;
import net.minecraft.network.protocol.game.PacketPlayOutMount;
import net.minecraft.network.protocol.game.PacketPlayOutMultiBlockChange;
import net.minecraft.network.protocol.game.PacketPlayOutNBTQuery;
import net.minecraft.network.protocol.game.PacketPlayOutNamedEntitySpawn;
import net.minecraft.network.protocol.game.PacketPlayOutNamedSoundEffect;
import net.minecraft.network.protocol.game.PacketPlayOutOpenBook;
import net.minecraft.network.protocol.game.PacketPlayOutOpenSignEditor;
import net.minecraft.network.protocol.game.PacketPlayOutOpenWindow;
import net.minecraft.network.protocol.game.PacketPlayOutOpenWindowHorse;
import net.minecraft.network.protocol.game.PacketPlayOutOpenWindowMerchant;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.network.protocol.game.PacketPlayOutPosition;
import net.minecraft.network.protocol.game.PacketPlayOutRecipeUpdate;
import net.minecraft.network.protocol.game.PacketPlayOutRecipes;
import net.minecraft.network.protocol.game.PacketPlayOutRemoveEntityEffect;
import net.minecraft.network.protocol.game.PacketPlayOutResourcePackSend;
import net.minecraft.network.protocol.game.PacketPlayOutRespawn;
import net.minecraft.network.protocol.game.PacketPlayOutScoreboardDisplayObjective;
import net.minecraft.network.protocol.game.PacketPlayOutScoreboardObjective;
import net.minecraft.network.protocol.game.PacketPlayOutScoreboardScore;
import net.minecraft.network.protocol.game.PacketPlayOutScoreboardTeam;
import net.minecraft.network.protocol.game.PacketPlayOutSelectAdvancementTab;
import net.minecraft.network.protocol.game.PacketPlayOutServerDifficulty;
import net.minecraft.network.protocol.game.PacketPlayOutSetCooldown;
import net.minecraft.network.protocol.game.PacketPlayOutSetSlot;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntityExperienceOrb;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntityLiving;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntityPainting;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnPosition;
import net.minecraft.network.protocol.game.PacketPlayOutStatistic;
import net.minecraft.network.protocol.game.PacketPlayOutStopSound;
import net.minecraft.network.protocol.game.PacketPlayOutTabComplete;
import net.minecraft.network.protocol.game.PacketPlayOutTags;
import net.minecraft.network.protocol.game.PacketPlayOutTileEntityData;
import net.minecraft.network.protocol.game.PacketPlayOutUnloadChunk;
import net.minecraft.network.protocol.game.PacketPlayOutUpdateAttributes;
import net.minecraft.network.protocol.game.PacketPlayOutUpdateHealth;
import net.minecraft.network.protocol.game.PacketPlayOutUpdateTime;
import net.minecraft.network.protocol.game.PacketPlayOutVehicleMove;
import net.minecraft.network.protocol.game.PacketPlayOutViewCentre;
import net.minecraft.network.protocol.game.PacketPlayOutViewDistance;
import net.minecraft.network.protocol.game.PacketPlayOutWindowData;
import net.minecraft.network.protocol.game.PacketPlayOutWindowItems;
import net.minecraft.network.protocol.game.PacketPlayOutWorldEvent;
import net.minecraft.network.protocol.game.PacketPlayOutWorldParticles;
import net.minecraft.network.protocol.game.ServerboundPongPacket;
import net.minecraft.network.protocol.handshake.PacketHandshakingInListener;
import net.minecraft.network.protocol.handshake.PacketHandshakingInSetProtocol;
import net.minecraft.network.protocol.login.PacketLoginInCustomPayload;
import net.minecraft.network.protocol.login.PacketLoginInEncryptionBegin;
import net.minecraft.network.protocol.login.PacketLoginInListener;
import net.minecraft.network.protocol.login.PacketLoginInStart;
import net.minecraft.network.protocol.login.PacketLoginOutCustomPayload;
import net.minecraft.network.protocol.login.PacketLoginOutDisconnect;
import net.minecraft.network.protocol.login.PacketLoginOutEncryptionBegin;
import net.minecraft.network.protocol.login.PacketLoginOutListener;
import net.minecraft.network.protocol.login.PacketLoginOutSetCompression;
import net.minecraft.network.protocol.login.PacketLoginOutSuccess;
import net.minecraft.network.protocol.status.PacketStatusInListener;
import net.minecraft.network.protocol.status.PacketStatusInPing;
import net.minecraft.network.protocol.status.PacketStatusInStart;
import net.minecraft.network.protocol.status.PacketStatusOutListener;
import net.minecraft.network.protocol.status.PacketStatusOutPong;
import net.minecraft.network.protocol.status.PacketStatusOutServerInfo;

public class SPacketListener
    extends VPacketListener
    implements PacketListenerPlayIn,
    PacketListenerPlayOut,
    PacketStatusInListener,
    PacketStatusOutListener,
    PacketLoginInListener,
    PacketLoginOutListener,
    PacketHandshakingInListener {
    public void a(IChatBaseComponent packet) {
    }

    public void a(PacketPlayOutHeldItemSlot packet) {
    }

    public void a(PacketPlayOutUpdateHealth packet) {
    }

    public void a(PacketPlayOutChat packet) {
    }

    public void a(PacketPlayInTabComplete packet) {
    }

    public void a(PacketPlayOutNBTQuery packet) {
    }

    public void a(PacketLoginOutDisconnect packet) {
    }

    public void a(ClientboundSetTitleTextPacket packet) {
    }

    public void a(PacketPlayOutUnloadChunk packet) {
    }

    public void a(PacketPlayInEnchantItem packet) {
    }

    public void a(PacketPlayOutTags packet) {
    }

    public void a(PacketPlayOutEntity packet) {
        this.handleOut(packet, new SPacketPlayOutEntity());
    }

    public void a(PacketPlayInBlockPlace packet) {
        this.handleIn(packet, new SPacketPlayInBlockPlace());
    }

    public void a(PacketStatusInPing packet) {
    }

    public void a(PacketPlayOutRespawn packet) {
        this.handleOut(packet, new SPacketPlayOutRespawn());
    }

    public void a(PacketPlayInHeldItemSlot packet) {
        this.handleIn(packet, new SPacketPlayInHeldItemSlot());
    }

    public void a(PacketPlayOutPlayerListHeaderFooter packet) {
    }

    public void a(PacketPlayInAutoRecipe packet) {
    }

    public void a(PacketPlayInTrSel packet) {
    }

    public void a(PacketPlayOutCollect packet) {
    }

    public void a(ClientboundSetBorderLerpSizePacket packet) {
    }

    public void a(PacketPlayOutAttachEntity packet) {
        this.handleOut(packet, new SPacketPlayOutAttachEntity());
    }

    public void a(PacketPlayOutScoreboardObjective packet) {
    }

    public void a(PacketPlayOutViewDistance packet) {
    }

    public void a(PacketPlayInCloseWindow packet) {
        this.handleIn(packet, new SPacketPlayInCloseWindow());
    }

    public void a(PacketPlayOutExplosion packet) {
        this.handleOut(packet, new SPacketPlayOutExplosion());
    }

    public void a(PacketPlayOutScoreboardDisplayObjective packet) {
    }

    public void a(PacketLoginOutEncryptionBegin packet) {
    }

    public void a(PacketLoginOutCustomPayload packet) {
    }

    public void a(PacketPlayOutBlockBreakAnimation packet) {
    }

    public void a(PacketPlayOutWindowItems packet) {
    }

    public void a(PacketPlayInBoatMove packet) {
    }

    public void a(PacketPlayOutNamedEntitySpawn packet) {
        this.handleOut(packet, new SPacketPlayOutNamedEntitySpawn());
    }

    public void a(PacketPlayOutCommands packet) {
    }

    public void a(PacketStatusOutServerInfo packet) {
    }

    public void a(ClientboundSetBorderWarningDistancePacket packet) {
    }

    public void a(PacketPlayOutViewCentre packet) {
    }

    public void a(PacketPlayOutPosition packet) {
        this.handleOut(packet, new SPacketPlayOutPosition());
    }

    public void a(PacketPlayOutSpawnEntity packet) {
        this.handleOut(packet, new SPacketPlayOutSpawnEntity());
    }

    public void a(PacketPlayOutUpdateAttributes packet) {
        this.handleOut(packet, new SPacketPlayOutUpdateAttributes());
    }

    public void a(PacketPlayInCustomPayload packet) {
        this.handleIn(packet, new SPacketPlayInCustomPayload());
    }

    public void a(PacketPlayOutAbilities packet) {
        this.handleOut(packet, new SPacketPlayOutAbilities());
    }

    public void a(PacketPlayInBeacon packet) {
    }

    public void a(PacketPlayInVehicleMove packet) {
        this.handleIn(packet, new SPacketPlayInVehicleMove());
    }

    public void a(PacketPlayOutCustomSoundEffect packet) {
    }

    public void a(PacketPlayOutLogin packet) {
    }

    public void a(PacketPlayInResourcePackStatus packet) {
    }

    public void a(PacketPlayOutUpdateTime packet) {
    }

    public void a(PacketPlayOutStatistic packet) {
    }

    public void a(PacketPlayInSteerVehicle packet) {
        this.handleIn(packet, new SPacketPlayInSteerVehicle());
    }

    public void a(PacketPlayOutEntityTeleport packet) {
        this.handleOut(packet, new SPacketPlayOutEntityTeleport());
    }

    public void a(PacketPlayOutTabComplete packet) {
    }

    public void a(PacketPlayInUseItem packet) {
        this.handleIn(packet, new SPacketPlayInUseItem());
    }

    public void a(PacketPlayOutSpawnPosition packet) {
        this.handleOut(packet, new SPacketPlayOutSpawnPosition());
    }

    public void a(PacketPlayOutWorldParticles packet) {
    }

    public void a(PacketPlayOutGameStateChange packet) {
        this.handleOut(packet, new SPacketPlayOutGameStateChange());
    }

    public void a(ServerboundPongPacket packet) {
        this.handleIn(packet, new SServerboundPongPacket());
    }

    public void a(PacketPlayInSetJigsaw packet) {
    }

    public void a(PacketPlayOutOpenWindow packet) {
        this.handleOut(packet, new SPacketPlayOutOpenWindow());
    }

    public void a(PacketPlayOutAnimation packet) {
    }

    public void a(PacketPlayOutMapChunk packet) {
    }

    public void a(PacketPlayInTileNBTQuery packet) {
    }

    public void a(PacketPlayOutScoreboardScore packet) {
    }

    public void a(PacketPlayInPickItem packet) {
    }

    public void a(ClientboundSetTitlesAnimationPacket packet) {
    }

    public void a(PacketPlayOutEntityHeadRotation packet) {
    }

    public void a(PacketPlayInDifficultyChange packet) {
    }

    public void a(PacketPlayOutSelectAdvancementTab packet) {
    }

    public void a(PacketPlayInRecipeSettings packet) {
    }

    public void a(ClientboundInitializeBorderPacket packet) {
    }

    public void a(PacketStatusInStart packet) {
    }

    public void a(PacketPlayOutEntityVelocity packet) {
        this.handleOut(packet, new SPacketPlayOutEntityVelocity());
    }

    public void a(PacketPlayInSetCommandMinecart packet) {
    }

    public void a(PacketPlayOutMap packet) {
    }

    public void a(PacketPlayInStruct packet) {
    }

    public <T extends PacketListener> void processTyped(Packet<T> packet) {
        if (packet instanceof PacketPlayInCustomPayload) {
            this.a((PacketPlayInCustomPayload)packet);
        } else {
            packet.a(this);
        }
    }

    public void a(PacketPlayOutNamedSoundEffect packet) {
    }

    public void a(PacketPlayOutResourcePackSend packet) {
    }

    public void a(PacketPlayOutOpenBook packet) {
    }

    public void a(PacketPlayOutEntityDestroy packet) {
        this.handleOut(packet, new SPacketPlayOutEntityDestroy());
    }

    public void a(PacketPlayInKeepAlive packet) {
        this.handleIn(packet, new SPacketPlayInKeepAlive());
    }

    public void a(PacketPlayOutOpenSignEditor packet) {
    }

    public void a(PacketPlayOutCustomPayload packet) {
    }

    public void a(PacketPlayInAbilities packet) {
        this.handleIn(packet, new SPacketPlayInAbilities());
    }

    public void a(PacketPlayInItemName packet) {
    }

    public void a(PacketPlayOutBlockAction packet) {
    }

    public void a(PacketPlayOutVehicleMove packet) {
    }

    public void a(PacketPlayOutOpenWindowMerchant packet) {
    }

    public void a(PacketPlayInWindowClick packet) {
        this.handleIn(packet, new SPacketPlayInWindowClick());
    }

    public void a(PacketPlayOutEntityEffect packet) {
        this.handleOut(packet, new SPacketPlayOutEntityEffect());
    }

    public void a(PacketPlayOutExperience packet) {
    }

    public void a(PacketPlayOutTileEntityData packet) {
    }

    public void a(PacketLoginInStart packet) {
    }

    public SPacketListener(PlayerData playerData) {
        super(playerData);
    }

    public void a(PacketPlayOutCloseWindow packet) {
    }

    public void a(PacketPlayInChat packet) {
    }

    public void a(PacketPlayOutSpawnEntityExperienceOrb packet) {
    }

    public void a(PacketPlayInTeleportAccept packet) {
        this.handleIn(packet, new SPacketPlayInTeleportAccept());
    }

    public void a(ClientboundPlayerCombatEndPacket packet) {
    }

    public void a(PacketPlayOutBoss packet) {
    }

    public void a(ClientboundSetActionBarTextPacket packet) {
    }

    public void a(ClientboundPlayerCombatEnterPacket packet) {
    }

    public void a(ClientboundSetBorderWarningDelayPacket packet) {
    }

    public void a(PacketLoginInCustomPayload packet) {
    }

    public NetworkManager a() {
        return null;
    }

    public void a(PacketLoginOutSuccess packet) {
    }

    public void a(PacketLoginOutSetCompression packet) {
    }

    public void a(ClientboundSetBorderCenterPacket packet) {
    }

    public void a(ClientboundClearTitlesPacket packet) {
    }

    public void a(PacketPlayOutBlockChange packet) {
    }

    public void a(PacketPlayOutSetSlot packet) {
        this.handleOut(packet, new SPacketPlayOutSetSlot());
    }

    public void a(PacketPlayOutEntityStatus packet) {
    }

    public void a(PacketPlayInJigsawGenerate packet) {
    }

    public void a(PacketPlayOutStopSound packet) {
    }

    public void a(PacketPlayOutCamera packet) {
    }

    @Override
    public void process(Object obj) {
        if (obj instanceof Packet) {
            this.processTyped((Packet)obj);
        }
    }

    public void a(PacketPlayOutWindowData packet) {
    }

    public void a(PacketPlayOutSpawnEntityLiving packet) {
        this.handleOut(packet, new SPacketPlayOutSpawnEntityLiving());
    }

    public void a(PacketPlayOutSetCooldown packet) {
    }

    public void a(PacketPlayInSetCommandBlock packet) {
    }

    public void a(PacketPlayOutAdvancements packet) {
    }

    public void a(PacketPlayInEntityAction packet) {
        this.handleIn(packet, new SPacketPlayInEntityAction());
    }

    public void a(PacketPlayOutSpawnEntityPainting packet) {
    }

    public void a(ClientboundSetSubtitleTextPacket packet) {
    }

    public void a(PacketPlayInClientCommand packet) {
        this.handleIn(packet, new SPacketPlayInClientCommand());
    }

    public void a(PacketPlayOutRemoveEntityEffect packet) {
        this.handleOut(packet, new SPacketPlayOutRemoveEntityEffect());
    }

    public void a(ClientboundAddVibrationSignalPacket packet) {
    }

    public void a(PacketStatusOutPong packet) {
    }

    public void a(PacketPlayInSpectate packet) {
    }

    public void a(PacketPlayInSettings packet) {
    }

    public void a(PacketHandshakingInSetProtocol packet) {
    }

    public void a(PacketPlayInRecipeDisplayed packet) {
    }

    public void a(PacketPlayInFlying packet) {
        this.handleIn(packet, new SPacketPlayInFlying());
    }

    public void a(PacketPlayInBEdit packet) {
    }

    public void a(PacketPlayInArmAnimation packet) {
        this.handleIn(packet, new SPacketPlayInArmAnimation());
    }

    public void a(PacketPlayOutServerDifficulty packet) {
    }

    public void a(PacketPlayInUpdateSign packet) {
    }

    public void a(PacketPlayOutLookAt packet) {
    }

    public void a(PacketPlayOutKickDisconnect packet) {
    }

    public void a(PacketPlayOutAutoRecipe packet) {
    }

    public void a(ClientboundPingPacket packet) {
        this.handleOut(packet, new SClientboundPingPacket());
    }

    public void a(PacketPlayOutRecipes packet) {
    }

    public void a(ClientboundPlayerCombatKillPacket packet) {
    }

    public void a(PacketPlayInAdvancements packet) {
    }

    public void a(PacketPlayOutMultiBlockChange packet) {
    }

    public void a(PacketPlayInBlockDig packet) {
        this.handleIn(packet, new SPacketPlayInBlockDig());
    }

    public void a(PacketPlayInUseEntity packet) {
        this.handleIn(packet, new SPacketPlayInUseEntity());
    }

    public void a(ClientboundSetBorderSizePacket packet) {
    }

    public void a(PacketPlayInEntityNBTQuery packet) {
    }

    public void a(PacketLoginInEncryptionBegin packet) {
    }

    public void a(PacketPlayOutOpenWindowHorse packet) {
    }

    public void a(PacketPlayOutEntityMetadata packet) {
        this.handleOut(packet, new SPacketPlayOutEntityMetadata());
    }

    public void a(PacketPlayOutMount packet) {
    }

    public void a(PacketPlayOutPlayerInfo packet) {
    }

    public void a(PacketPlayOutWorldEvent packet) {
    }

    public void a(PacketPlayOutKeepAlive packet) {
        this.handleOut(packet, new SPacketPlayOutKeepAlive());
    }

    public void a(PacketPlayOutLightUpdate packet) {
    }

    public void a(PacketPlayInSetCreativeSlot packet) {
        this.handleIn(packet, new SPacketPlayInSetCreativeSlot());
    }

    public void a(PacketPlayOutEntitySound packet) {
    }

    public void a(PacketPlayOutBlockBreak packet) {
    }

    public void a(PacketPlayInDifficultyLock packet) {
    }

    public void a(PacketPlayOutScoreboardTeam packet) {
    }

    public void a(PacketPlayOutRecipeUpdate packet) {
    }

    public void a(PacketPlayOutEntityEquipment packet) {
    }
}
