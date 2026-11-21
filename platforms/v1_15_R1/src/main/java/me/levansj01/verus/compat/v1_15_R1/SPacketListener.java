package me.levansj01.verus.compat.v1_15_R1;

import me.levansj01.verus.compat.api.VPacketListener;
import me.levansj01.verus.compat.v1_15_R1.packets.SPacketPlayInAbilities;
import me.levansj01.verus.compat.v1_15_R1.packets.SPacketPlayInArmAnimation;
import me.levansj01.verus.compat.v1_15_R1.packets.SPacketPlayInBlockDig;
import me.levansj01.verus.compat.v1_15_R1.packets.SPacketPlayInBlockPlace;
import me.levansj01.verus.compat.v1_15_R1.packets.SPacketPlayInClientCommand;
import me.levansj01.verus.compat.v1_15_R1.packets.SPacketPlayInCloseWindow;
import me.levansj01.verus.compat.v1_15_R1.packets.SPacketPlayInCustomPayload;
import me.levansj01.verus.compat.v1_15_R1.packets.SPacketPlayInEntityAction;
import me.levansj01.verus.compat.v1_15_R1.packets.SPacketPlayInFlying;
import me.levansj01.verus.compat.v1_15_R1.packets.SPacketPlayInHeldItemSlot;
import me.levansj01.verus.compat.v1_15_R1.packets.SPacketPlayInKeepAlive;
import me.levansj01.verus.compat.v1_15_R1.packets.SPacketPlayInSetCreativeSlot;
import me.levansj01.verus.compat.v1_15_R1.packets.SPacketPlayInSteerVehicle;
import me.levansj01.verus.compat.v1_15_R1.packets.SPacketPlayInTeleportAccept;
import me.levansj01.verus.compat.v1_15_R1.packets.SPacketPlayInTransaction;
import me.levansj01.verus.compat.v1_15_R1.packets.SPacketPlayInUseEntity;
import me.levansj01.verus.compat.v1_15_R1.packets.SPacketPlayInUseItem;
import me.levansj01.verus.compat.v1_15_R1.packets.SPacketPlayInVehicleMove;
import me.levansj01.verus.compat.v1_15_R1.packets.SPacketPlayInWindowClick;
import me.levansj01.verus.compat.v1_15_R1.packets.SPacketPlayOutAbilities;
import me.levansj01.verus.compat.v1_15_R1.packets.SPacketPlayOutAttachEntity;
import me.levansj01.verus.compat.v1_15_R1.packets.SPacketPlayOutEntity;
import me.levansj01.verus.compat.v1_15_R1.packets.SPacketPlayOutEntityDestroy;
import me.levansj01.verus.compat.v1_15_R1.packets.SPacketPlayOutEntityEffect;
import me.levansj01.verus.compat.v1_15_R1.packets.SPacketPlayOutEntityMetadata;
import me.levansj01.verus.compat.v1_15_R1.packets.SPacketPlayOutEntityTeleport;
import me.levansj01.verus.compat.v1_15_R1.packets.SPacketPlayOutEntityVelocity;
import me.levansj01.verus.compat.v1_15_R1.packets.SPacketPlayOutExplosion;
import me.levansj01.verus.compat.v1_15_R1.packets.SPacketPlayOutGameStateChange;
import me.levansj01.verus.compat.v1_15_R1.packets.SPacketPlayOutKeepAlive;
import me.levansj01.verus.compat.v1_15_R1.packets.SPacketPlayOutNamedEntitySpawn;
import me.levansj01.verus.compat.v1_15_R1.packets.SPacketPlayOutOpenWindow;
import me.levansj01.verus.compat.v1_15_R1.packets.SPacketPlayOutPosition;
import me.levansj01.verus.compat.v1_15_R1.packets.SPacketPlayOutRemoveEntityEffect;
import me.levansj01.verus.compat.v1_15_R1.packets.SPacketPlayOutRespawn;
import me.levansj01.verus.compat.v1_15_R1.packets.SPacketPlayOutSetSlot;
import me.levansj01.verus.compat.v1_15_R1.packets.SPacketPlayOutSpawnEntity;
import me.levansj01.verus.compat.v1_15_R1.packets.SPacketPlayOutSpawnEntityLiving;
import me.levansj01.verus.compat.v1_15_R1.packets.SPacketPlayOutSpawnPosition;
import me.levansj01.verus.compat.v1_15_R1.packets.SPacketPlayOutTransaction;
import me.levansj01.verus.compat.v1_15_R1.packets.SPacketPlayOutUpdateAttributes;
import me.levansj01.verus.data.PlayerData;
import net.minecraft.server.v1_15_R1.IChatBaseComponent;
import net.minecraft.server.v1_15_R1.NetworkManager;
import net.minecraft.server.v1_15_R1.Packet;
import net.minecraft.server.v1_15_R1.PacketHandshakingInListener;
import net.minecraft.server.v1_15_R1.PacketHandshakingInSetProtocol;
import net.minecraft.server.v1_15_R1.PacketListener;
import net.minecraft.server.v1_15_R1.PacketListenerPlayIn;
import net.minecraft.server.v1_15_R1.PacketListenerPlayOut;
import net.minecraft.server.v1_15_R1.PacketLoginInCustomPayload;
import net.minecraft.server.v1_15_R1.PacketLoginInEncryptionBegin;
import net.minecraft.server.v1_15_R1.PacketLoginInListener;
import net.minecraft.server.v1_15_R1.PacketLoginInStart;
import net.minecraft.server.v1_15_R1.PacketLoginOutCustomPayload;
import net.minecraft.server.v1_15_R1.PacketLoginOutDisconnect;
import net.minecraft.server.v1_15_R1.PacketLoginOutEncryptionBegin;
import net.minecraft.server.v1_15_R1.PacketLoginOutListener;
import net.minecraft.server.v1_15_R1.PacketLoginOutSetCompression;
import net.minecraft.server.v1_15_R1.PacketLoginOutSuccess;
import net.minecraft.server.v1_15_R1.PacketPlayInAbilities;
import net.minecraft.server.v1_15_R1.PacketPlayInAdvancements;
import net.minecraft.server.v1_15_R1.PacketPlayInArmAnimation;
import net.minecraft.server.v1_15_R1.PacketPlayInAutoRecipe;
import net.minecraft.server.v1_15_R1.PacketPlayInBEdit;
import net.minecraft.server.v1_15_R1.PacketPlayInBeacon;
import net.minecraft.server.v1_15_R1.PacketPlayInBlockDig;
import net.minecraft.server.v1_15_R1.PacketPlayInBlockPlace;
import net.minecraft.server.v1_15_R1.PacketPlayInBoatMove;
import net.minecraft.server.v1_15_R1.PacketPlayInChat;
import net.minecraft.server.v1_15_R1.PacketPlayInClientCommand;
import net.minecraft.server.v1_15_R1.PacketPlayInCloseWindow;
import net.minecraft.server.v1_15_R1.PacketPlayInCustomPayload;
import net.minecraft.server.v1_15_R1.PacketPlayInDifficultyChange;
import net.minecraft.server.v1_15_R1.PacketPlayInDifficultyLock;
import net.minecraft.server.v1_15_R1.PacketPlayInEnchantItem;
import net.minecraft.server.v1_15_R1.PacketPlayInEntityAction;
import net.minecraft.server.v1_15_R1.PacketPlayInEntityNBTQuery;
import net.minecraft.server.v1_15_R1.PacketPlayInFlying;
import net.minecraft.server.v1_15_R1.PacketPlayInHeldItemSlot;
import net.minecraft.server.v1_15_R1.PacketPlayInItemName;
import net.minecraft.server.v1_15_R1.PacketPlayInKeepAlive;
import net.minecraft.server.v1_15_R1.PacketPlayInPickItem;
import net.minecraft.server.v1_15_R1.PacketPlayInRecipeDisplayed;
import net.minecraft.server.v1_15_R1.PacketPlayInResourcePackStatus;
import net.minecraft.server.v1_15_R1.PacketPlayInSetCommandBlock;
import net.minecraft.server.v1_15_R1.PacketPlayInSetCommandMinecart;
import net.minecraft.server.v1_15_R1.PacketPlayInSetCreativeSlot;
import net.minecraft.server.v1_15_R1.PacketPlayInSetJigsaw;
import net.minecraft.server.v1_15_R1.PacketPlayInSettings;
import net.minecraft.server.v1_15_R1.PacketPlayInSpectate;
import net.minecraft.server.v1_15_R1.PacketPlayInSteerVehicle;
import net.minecraft.server.v1_15_R1.PacketPlayInStruct;
import net.minecraft.server.v1_15_R1.PacketPlayInTabComplete;
import net.minecraft.server.v1_15_R1.PacketPlayInTeleportAccept;
import net.minecraft.server.v1_15_R1.PacketPlayInTileNBTQuery;
import net.minecraft.server.v1_15_R1.PacketPlayInTrSel;
import net.minecraft.server.v1_15_R1.PacketPlayInTransaction;
import net.minecraft.server.v1_15_R1.PacketPlayInUpdateSign;
import net.minecraft.server.v1_15_R1.PacketPlayInUseEntity;
import net.minecraft.server.v1_15_R1.PacketPlayInUseItem;
import net.minecraft.server.v1_15_R1.PacketPlayInVehicleMove;
import net.minecraft.server.v1_15_R1.PacketPlayInWindowClick;
import net.minecraft.server.v1_15_R1.PacketPlayOutAbilities;
import net.minecraft.server.v1_15_R1.PacketPlayOutAdvancements;
import net.minecraft.server.v1_15_R1.PacketPlayOutAnimation;
import net.minecraft.server.v1_15_R1.PacketPlayOutAttachEntity;
import net.minecraft.server.v1_15_R1.PacketPlayOutAutoRecipe;
import net.minecraft.server.v1_15_R1.PacketPlayOutBlockAction;
import net.minecraft.server.v1_15_R1.PacketPlayOutBlockBreak;
import net.minecraft.server.v1_15_R1.PacketPlayOutBlockBreakAnimation;
import net.minecraft.server.v1_15_R1.PacketPlayOutBlockChange;
import net.minecraft.server.v1_15_R1.PacketPlayOutBoss;
import net.minecraft.server.v1_15_R1.PacketPlayOutCamera;
import net.minecraft.server.v1_15_R1.PacketPlayOutChat;
import net.minecraft.server.v1_15_R1.PacketPlayOutCloseWindow;
import net.minecraft.server.v1_15_R1.PacketPlayOutCollect;
import net.minecraft.server.v1_15_R1.PacketPlayOutCombatEvent;
import net.minecraft.server.v1_15_R1.PacketPlayOutCommands;
import net.minecraft.server.v1_15_R1.PacketPlayOutCustomPayload;
import net.minecraft.server.v1_15_R1.PacketPlayOutCustomSoundEffect;
import net.minecraft.server.v1_15_R1.PacketPlayOutEntity;
import net.minecraft.server.v1_15_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_15_R1.PacketPlayOutEntityEffect;
import net.minecraft.server.v1_15_R1.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_15_R1.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_15_R1.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_15_R1.PacketPlayOutEntitySound;
import net.minecraft.server.v1_15_R1.PacketPlayOutEntityStatus;
import net.minecraft.server.v1_15_R1.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_15_R1.PacketPlayOutEntityVelocity;
import net.minecraft.server.v1_15_R1.PacketPlayOutExperience;
import net.minecraft.server.v1_15_R1.PacketPlayOutExplosion;
import net.minecraft.server.v1_15_R1.PacketPlayOutGameStateChange;
import net.minecraft.server.v1_15_R1.PacketPlayOutHeldItemSlot;
import net.minecraft.server.v1_15_R1.PacketPlayOutKeepAlive;
import net.minecraft.server.v1_15_R1.PacketPlayOutKickDisconnect;
import net.minecraft.server.v1_15_R1.PacketPlayOutLightUpdate;
import net.minecraft.server.v1_15_R1.PacketPlayOutLogin;
import net.minecraft.server.v1_15_R1.PacketPlayOutLookAt;
import net.minecraft.server.v1_15_R1.PacketPlayOutMap;
import net.minecraft.server.v1_15_R1.PacketPlayOutMapChunk;
import net.minecraft.server.v1_15_R1.PacketPlayOutMount;
import net.minecraft.server.v1_15_R1.PacketPlayOutMultiBlockChange;
import net.minecraft.server.v1_15_R1.PacketPlayOutNBTQuery;
import net.minecraft.server.v1_15_R1.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_15_R1.PacketPlayOutNamedSoundEffect;
import net.minecraft.server.v1_15_R1.PacketPlayOutOpenBook;
import net.minecraft.server.v1_15_R1.PacketPlayOutOpenSignEditor;
import net.minecraft.server.v1_15_R1.PacketPlayOutOpenWindow;
import net.minecraft.server.v1_15_R1.PacketPlayOutOpenWindowHorse;
import net.minecraft.server.v1_15_R1.PacketPlayOutOpenWindowMerchant;
import net.minecraft.server.v1_15_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_15_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_15_R1.PacketPlayOutPosition;
import net.minecraft.server.v1_15_R1.PacketPlayOutRecipeUpdate;
import net.minecraft.server.v1_15_R1.PacketPlayOutRecipes;
import net.minecraft.server.v1_15_R1.PacketPlayOutRemoveEntityEffect;
import net.minecraft.server.v1_15_R1.PacketPlayOutResourcePackSend;
import net.minecraft.server.v1_15_R1.PacketPlayOutRespawn;
import net.minecraft.server.v1_15_R1.PacketPlayOutScoreboardDisplayObjective;
import net.minecraft.server.v1_15_R1.PacketPlayOutScoreboardObjective;
import net.minecraft.server.v1_15_R1.PacketPlayOutScoreboardScore;
import net.minecraft.server.v1_15_R1.PacketPlayOutScoreboardTeam;
import net.minecraft.server.v1_15_R1.PacketPlayOutSelectAdvancementTab;
import net.minecraft.server.v1_15_R1.PacketPlayOutServerDifficulty;
import net.minecraft.server.v1_15_R1.PacketPlayOutSetCooldown;
import net.minecraft.server.v1_15_R1.PacketPlayOutSetSlot;
import net.minecraft.server.v1_15_R1.PacketPlayOutSpawnEntity;
import net.minecraft.server.v1_15_R1.PacketPlayOutSpawnEntityExperienceOrb;
import net.minecraft.server.v1_15_R1.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_15_R1.PacketPlayOutSpawnEntityPainting;
import net.minecraft.server.v1_15_R1.PacketPlayOutSpawnEntityWeather;
import net.minecraft.server.v1_15_R1.PacketPlayOutSpawnPosition;
import net.minecraft.server.v1_15_R1.PacketPlayOutStatistic;
import net.minecraft.server.v1_15_R1.PacketPlayOutStopSound;
import net.minecraft.server.v1_15_R1.PacketPlayOutTabComplete;
import net.minecraft.server.v1_15_R1.PacketPlayOutTags;
import net.minecraft.server.v1_15_R1.PacketPlayOutTileEntityData;
import net.minecraft.server.v1_15_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_15_R1.PacketPlayOutTransaction;
import net.minecraft.server.v1_15_R1.PacketPlayOutUnloadChunk;
import net.minecraft.server.v1_15_R1.PacketPlayOutUpdateAttributes;
import net.minecraft.server.v1_15_R1.PacketPlayOutUpdateHealth;
import net.minecraft.server.v1_15_R1.PacketPlayOutUpdateTime;
import net.minecraft.server.v1_15_R1.PacketPlayOutVehicleMove;
import net.minecraft.server.v1_15_R1.PacketPlayOutViewCentre;
import net.minecraft.server.v1_15_R1.PacketPlayOutViewDistance;
import net.minecraft.server.v1_15_R1.PacketPlayOutWindowData;
import net.minecraft.server.v1_15_R1.PacketPlayOutWindowItems;
import net.minecraft.server.v1_15_R1.PacketPlayOutWorldBorder;
import net.minecraft.server.v1_15_R1.PacketPlayOutWorldEvent;
import net.minecraft.server.v1_15_R1.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_15_R1.PacketStatusInListener;
import net.minecraft.server.v1_15_R1.PacketStatusInPing;
import net.minecraft.server.v1_15_R1.PacketStatusInStart;
import net.minecraft.server.v1_15_R1.PacketStatusOutListener;
import net.minecraft.server.v1_15_R1.PacketStatusOutPong;
import net.minecraft.server.v1_15_R1.PacketStatusOutServerInfo;

public class SPacketListener
    extends VPacketListener
    implements PacketListenerPlayIn,
    PacketListenerPlayOut,
    PacketStatusInListener,
    PacketStatusOutListener,
    PacketLoginInListener,
    PacketLoginOutListener,
    PacketHandshakingInListener {
    public void a(PacketPlayOutRecipeUpdate packet) {
    }

    public void a(PacketPlayInSettings packet) {
    }

    public void a(PacketPlayInBlockDig packet) {
        this.handleIn(packet, new SPacketPlayInBlockDig());
    }

    public void a(PacketPlayInTrSel packet) {
    }

    public void a(PacketPlayInVehicleMove packet) {
        this.handleIn(packet, new SPacketPlayInVehicleMove());
    }

    public void a(PacketPlayOutEntityVelocity packet) {
        this.handleOut(packet, new SPacketPlayOutEntityVelocity());
    }

    public void a(PacketPlayOutSpawnEntityLiving packet) {
        this.handleOut(packet, new SPacketPlayOutSpawnEntityLiving());
    }

    public void a(PacketPlayInAdvancements packet) {
    }

    public void a(PacketPlayOutBlockAction packet) {
    }

    public void a(PacketPlayOutCloseWindow packet) {
    }

    public void a(PacketPlayOutPlayerInfo packet) {
    }

    public void a(PacketPlayOutMap packet) {
    }

    public void a(PacketPlayOutLogin packet) {
    }

    public void a(IChatBaseComponent packet) {
    }

    public void a(PacketPlayInAbilities packet) {
        this.handleIn(packet, new SPacketPlayInAbilities());
    }

    public void a(PacketStatusInPing packet) {
    }

    public void a(PacketPlayOutPlayerListHeaderFooter packet) {
    }

    public void a(PacketLoginOutEncryptionBegin packet) {
    }

    public void a(PacketPlayOutVehicleMove packet) {
    }

    public void a(PacketPlayInSetCommandBlock packet) {
    }

    public void a(PacketLoginOutSuccess packet) {
    }

    public void a(PacketPlayOutEntityTeleport packet) {
        this.handleOut(packet, new SPacketPlayOutEntityTeleport());
    }

    public void a(PacketStatusOutServerInfo packet) {
    }

    public void a(PacketPlayInTileNBTQuery packet) {
    }

    public void a(PacketPlayInArmAnimation packet) {
        this.handleIn(packet, new SPacketPlayInArmAnimation());
    }

    public void a(PacketPlayOutEntityEquipment packet) {
    }

    public void a(PacketPlayOutKickDisconnect packet) {
    }

    public void a(PacketPlayInAutoRecipe packet) {
    }

    public void a(PacketPlayOutTags packet) {
    }

    public void a(PacketPlayOutCollect packet) {
    }

    public void a(PacketPlayInSteerVehicle packet) {
        this.handleIn(packet, new SPacketPlayInSteerVehicle());
    }

    public void a(PacketPlayOutEntityHeadRotation packet) {
    }

    public void a(PacketPlayOutViewCentre packet) {
    }

    public void a(PacketPlayInItemName packet) {
    }

    public void a(PacketPlayOutStopSound packet) {
    }

    public void a(PacketPlayOutMapChunk packet) {
    }

    public void a(PacketLoginOutDisconnect packet) {
    }

    public void a(PacketPlayOutHeldItemSlot packet) {
    }

    public void a(PacketPlayOutOpenSignEditor packet) {
    }

    public void a(PacketPlayInHeldItemSlot packet) {
        this.handleIn(packet, new SPacketPlayInHeldItemSlot());
    }

    public void a(PacketPlayOutScoreboardScore packet) {
    }

    public void a(PacketPlayInUpdateSign packet) {
    }

    public void a(PacketPlayOutSetSlot packet) {
        this.handleOut(packet, new SPacketPlayOutSetSlot());
    }

    public void a(PacketPlayOutWindowData packet) {
    }

    public void a(PacketPlayOutPosition packet) {
        this.handleOut(packet, new SPacketPlayOutPosition());
    }

    public <T extends PacketListener> void processTyped(Packet<T> packet) {
        if (packet instanceof PacketPlayInCustomPayload) {
            this.a((PacketPlayInCustomPayload)packet);
        } else {
            packet.a(this);
        }
    }

    public void a(PacketPlayOutCustomSoundEffect packet) {
    }

    public void a(PacketPlayOutEntityDestroy packet) {
        this.handleOut(packet, new SPacketPlayOutEntityDestroy());
    }

    public void a(PacketPlayOutStatistic packet) {
    }

    public void a(PacketPlayOutSpawnEntityPainting packet) {
    }

    public void a(PacketPlayInPickItem packet) {
    }

    public void a(PacketPlayOutWindowItems packet) {
    }

    public void a(PacketPlayOutAdvancements packet) {
    }

    public void a(PacketPlayOutMount packet) {
    }

    public void a(PacketPlayInDifficultyChange packet) {
    }

    public void a(PacketPlayInBEdit packet) {
    }

    public void a(PacketLoginOutCustomPayload packet) {
    }

    public void a(PacketPlayOutCamera packet) {
    }

    public void a(PacketPlayOutEntityMetadata packet) {
        this.handleOut(packet, new SPacketPlayOutEntityMetadata());
    }

    public void a(PacketPlayOutResourcePackSend packet) {
    }

    public void a(PacketPlayOutSpawnEntity packet) {
        this.handleOut(packet, new SPacketPlayOutSpawnEntity());
    }

    public void a(PacketPlayInSetJigsaw packet) {
    }

    public void a(PacketPlayOutBlockChange packet) {
    }

    public void a(PacketPlayOutScoreboardObjective packet) {
    }

    public void a(PacketPlayOutNBTQuery packet) {
    }

    public void a(PacketPlayOutExperience packet) {
    }

    public void a(PacketPlayOutLightUpdate packet) {
    }

    public void a(PacketPlayOutCommands packet) {
    }

    public void a(PacketPlayOutGameStateChange packet) {
        this.handleOut(packet, new SPacketPlayOutGameStateChange());
    }

    public void a(PacketPlayOutRecipes packet) {
    }

    public void a(PacketPlayOutBlockBreakAnimation packet) {
    }

    public void a(PacketPlayInUseEntity packet) {
        this.handleIn(packet, new SPacketPlayInUseEntity());
    }

    public void a(PacketPlayOutAutoRecipe packet) {
    }

    public void a(PacketPlayOutServerDifficulty packet) {
    }

    public void a(PacketPlayOutNamedEntitySpawn packet) {
        this.handleOut(packet, new SPacketPlayOutNamedEntitySpawn());
    }

    public void a(PacketPlayInSetCommandMinecart packet) {
    }

    public void a(PacketPlayOutUnloadChunk packet) {
    }

    public void a(PacketPlayOutWorldEvent packet) {
    }

    public void a(PacketPlayOutAbilities packet) {
        this.handleOut(packet, new SPacketPlayOutAbilities());
    }

    public void a(PacketPlayOutMultiBlockChange packet) {
    }

    public void a(PacketPlayInBoatMove packet) {
    }

    public void a(PacketPlayOutCombatEvent packet) {
    }

    public void a(PacketPlayOutEntitySound packet) {
    }

    public void a(PacketPlayOutCustomPayload packet) {
    }

    public void a(PacketPlayInCustomPayload packet) {
        this.handleIn(packet, new SPacketPlayInCustomPayload());
    }

    public void a(PacketPlayOutSpawnEntityWeather packet) {
    }

    public void a(PacketPlayOutViewDistance packet) {
    }

    public void a(PacketPlayInKeepAlive packet) {
        this.handleIn(packet, new SPacketPlayInKeepAlive());
    }

    public void a(PacketPlayOutOpenWindow packet) {
        this.handleOut(packet, new SPacketPlayOutOpenWindow());
    }

    public void a(PacketLoginOutSetCompression packet) {
    }

    public void a(PacketPlayInUseItem packet) {
        this.handleIn(packet, new SPacketPlayInUseItem());
    }

    public void a(PacketPlayOutRespawn packet) {
        this.handleOut(packet, new SPacketPlayOutRespawn());
    }

    public void a(PacketPlayInTeleportAccept packet) {
        this.handleIn(packet, new SPacketPlayInTeleportAccept());
    }

    public void a(PacketPlayOutEntityEffect packet) {
        this.handleOut(packet, new SPacketPlayOutEntityEffect());
    }

    public void a(PacketPlayOutSpawnEntityExperienceOrb packet) {
    }

    public void a(PacketPlayOutNamedSoundEffect packet) {
    }

    public void a(PacketLoginInEncryptionBegin packet) {
    }

    public void a(PacketPlayOutAnimation packet) {
    }

    public void a(PacketPlayOutOpenBook packet) {
    }

    public void a(PacketPlayOutUpdateTime packet) {
    }

    public void a(PacketPlayInTransaction packet) {
        this.handleIn(packet, new SPacketPlayInTransaction());
    }

    public void a(PacketPlayOutUpdateAttributes packet) {
        this.handleOut(packet, new SPacketPlayOutUpdateAttributes());
    }

    public void a(PacketPlayInBlockPlace packet) {
        this.handleIn(packet, new SPacketPlayInBlockPlace());
    }

    public void a(PacketPlayOutOpenWindowMerchant packet) {
    }

    public void a(PacketPlayInChat packet) {
    }

    public void a(PacketPlayInFlying packet) {
        this.handleIn(packet, new SPacketPlayInFlying());
    }

    public SPacketListener(PlayerData playerData) {
        super(playerData);
    }

    public void a(PacketPlayOutBlockBreak packet) {
    }

    public NetworkManager a() {
        return null;
    }

    public void a(PacketPlayOutKeepAlive packet) {
        this.handleOut(packet, new SPacketPlayOutKeepAlive());
    }

    public void a(PacketPlayInBeacon packet) {
    }

    public void a(PacketStatusOutPong packet) {
    }

    public void a(PacketPlayInSetCreativeSlot packet) {
        this.handleIn(packet, new SPacketPlayInSetCreativeSlot());
    }

    public void a(PacketPlayOutExplosion packet) {
        this.handleOut(packet, new SPacketPlayOutExplosion());
    }

    public void a(PacketPlayOutScoreboardDisplayObjective packet) {
    }

    public void a(PacketPlayOutOpenWindowHorse packet) {
    }

    public void a(PacketPlayOutLookAt packet) {
    }

    public void a(PacketPlayOutTabComplete packet) {
    }

    public void a(PacketPlayOutTileEntityData packet) {
    }

    public void a(PacketPlayOutWorldBorder packet) {
    }

    public void a(PacketPlayInRecipeDisplayed packet) {
    }

    public void a(PacketPlayOutEntityStatus packet) {
    }

    public void a(PacketPlayOutSetCooldown packet) {
    }

    public void a(PacketPlayInClientCommand packet) {
        this.handleIn(packet, new SPacketPlayInClientCommand());
    }

    public void a(PacketPlayInEntityAction packet) {
        this.handleIn(packet, new SPacketPlayInEntityAction());
    }

    public void a(PacketPlayInEntityNBTQuery packet) {
    }

    public void a(PacketPlayInStruct packet) {
    }

    public void a(PacketPlayOutTitle packet) {
    }

    public void a(PacketPlayOutAttachEntity packet) {
        this.handleOut(packet, new SPacketPlayOutAttachEntity());
    }

    public void a(PacketPlayInWindowClick packet) {
        this.handleIn(packet, new SPacketPlayInWindowClick());
    }

    public void a(PacketPlayOutBoss packet) {
    }

    public void a(PacketPlayInSpectate packet) {
    }

    public void a(PacketPlayOutScoreboardTeam packet) {
    }

    public void a(PacketHandshakingInSetProtocol packet) {
    }

    public void a(PacketPlayInResourcePackStatus packet) {
    }

    public void a(PacketPlayInEnchantItem packet) {
    }

    public void a(PacketPlayInTabComplete packet) {
    }

    public void a(PacketPlayOutTransaction packet) {
        this.handleOut(packet, new SPacketPlayOutTransaction());
    }

    public void a(PacketPlayOutUpdateHealth packet) {
    }

    @Override
    public void process(Object obj) {
        if (obj instanceof Packet) {
            this.processTyped((Packet)obj);
        }
    }

    public void a(PacketPlayOutSpawnPosition packet) {
        this.handleOut(packet, new SPacketPlayOutSpawnPosition());
    }

    public void a(PacketPlayOutRemoveEntityEffect packet) {
        this.handleOut(packet, new SPacketPlayOutRemoveEntityEffect());
    }

    public void a(PacketPlayOutWorldParticles packet) {
    }

    public void a(PacketLoginInCustomPayload packet) {
    }

    public void a(PacketPlayInCloseWindow packet) {
        this.handleIn(packet, new SPacketPlayInCloseWindow());
    }

    public void a(PacketPlayInDifficultyLock packet) {
    }

    public void a(PacketPlayOutEntity packet) {
        this.handleOut(packet, new SPacketPlayOutEntity());
    }

    public void a(PacketPlayOutSelectAdvancementTab packet) {
    }

    public void a(PacketPlayOutChat packet) {
    }

    public void a(PacketStatusInStart packet) {
    }

    public void a(PacketLoginInStart packet) {
    }
}
