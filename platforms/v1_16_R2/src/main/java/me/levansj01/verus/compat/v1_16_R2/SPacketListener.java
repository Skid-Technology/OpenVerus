package me.levansj01.verus.compat.v1_16_R2;

import me.levansj01.verus.compat.api.VPacketListener;
import me.levansj01.verus.compat.v1_16_R2.packets.SPacketPlayInAbilities;
import me.levansj01.verus.compat.v1_16_R2.packets.SPacketPlayInArmAnimation;
import me.levansj01.verus.compat.v1_16_R2.packets.SPacketPlayInBlockDig;
import me.levansj01.verus.compat.v1_16_R2.packets.SPacketPlayInBlockPlace;
import me.levansj01.verus.compat.v1_16_R2.packets.SPacketPlayInClientCommand;
import me.levansj01.verus.compat.v1_16_R2.packets.SPacketPlayInCloseWindow;
import me.levansj01.verus.compat.v1_16_R2.packets.SPacketPlayInCustomPayload;
import me.levansj01.verus.compat.v1_16_R2.packets.SPacketPlayInEntityAction;
import me.levansj01.verus.compat.v1_16_R2.packets.SPacketPlayInFlying;
import me.levansj01.verus.compat.v1_16_R2.packets.SPacketPlayInHeldItemSlot;
import me.levansj01.verus.compat.v1_16_R2.packets.SPacketPlayInKeepAlive;
import me.levansj01.verus.compat.v1_16_R2.packets.SPacketPlayInSetCreativeSlot;
import me.levansj01.verus.compat.v1_16_R2.packets.SPacketPlayInSteerVehicle;
import me.levansj01.verus.compat.v1_16_R2.packets.SPacketPlayInTeleportAccept;
import me.levansj01.verus.compat.v1_16_R2.packets.SPacketPlayInTransaction;
import me.levansj01.verus.compat.v1_16_R2.packets.SPacketPlayInUseEntity;
import me.levansj01.verus.compat.v1_16_R2.packets.SPacketPlayInUseItem;
import me.levansj01.verus.compat.v1_16_R2.packets.SPacketPlayInVehicleMove;
import me.levansj01.verus.compat.v1_16_R2.packets.SPacketPlayInWindowClick;
import me.levansj01.verus.compat.v1_16_R2.packets.SPacketPlayOutAbilities;
import me.levansj01.verus.compat.v1_16_R2.packets.SPacketPlayOutAttachEntity;
import me.levansj01.verus.compat.v1_16_R2.packets.SPacketPlayOutEntity;
import me.levansj01.verus.compat.v1_16_R2.packets.SPacketPlayOutEntityDestroy;
import me.levansj01.verus.compat.v1_16_R2.packets.SPacketPlayOutEntityEffect;
import me.levansj01.verus.compat.v1_16_R2.packets.SPacketPlayOutEntityMetadata;
import me.levansj01.verus.compat.v1_16_R2.packets.SPacketPlayOutEntityTeleport;
import me.levansj01.verus.compat.v1_16_R2.packets.SPacketPlayOutEntityVelocity;
import me.levansj01.verus.compat.v1_16_R2.packets.SPacketPlayOutExplosion;
import me.levansj01.verus.compat.v1_16_R2.packets.SPacketPlayOutGameStateChange;
import me.levansj01.verus.compat.v1_16_R2.packets.SPacketPlayOutKeepAlive;
import me.levansj01.verus.compat.v1_16_R2.packets.SPacketPlayOutNamedEntitySpawn;
import me.levansj01.verus.compat.v1_16_R2.packets.SPacketPlayOutOpenWindow;
import me.levansj01.verus.compat.v1_16_R2.packets.SPacketPlayOutPosition;
import me.levansj01.verus.compat.v1_16_R2.packets.SPacketPlayOutRemoveEntityEffect;
import me.levansj01.verus.compat.v1_16_R2.packets.SPacketPlayOutRespawn;
import me.levansj01.verus.compat.v1_16_R2.packets.SPacketPlayOutSetSlot;
import me.levansj01.verus.compat.v1_16_R2.packets.SPacketPlayOutSpawnEntity;
import me.levansj01.verus.compat.v1_16_R2.packets.SPacketPlayOutSpawnEntityLiving;
import me.levansj01.verus.compat.v1_16_R2.packets.SPacketPlayOutSpawnPosition;
import me.levansj01.verus.compat.v1_16_R2.packets.SPacketPlayOutTransaction;
import me.levansj01.verus.compat.v1_16_R2.packets.SPacketPlayOutUpdateAttributes;
import me.levansj01.verus.data.PlayerData;
import net.minecraft.server.v1_16_R2.IChatBaseComponent;
import net.minecraft.server.v1_16_R2.NetworkManager;
import net.minecraft.server.v1_16_R2.Packet;
import net.minecraft.server.v1_16_R2.PacketHandshakingInListener;
import net.minecraft.server.v1_16_R2.PacketHandshakingInSetProtocol;
import net.minecraft.server.v1_16_R2.PacketListener;
import net.minecraft.server.v1_16_R2.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R2.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R2.PacketLoginInCustomPayload;
import net.minecraft.server.v1_16_R2.PacketLoginInEncryptionBegin;
import net.minecraft.server.v1_16_R2.PacketLoginInListener;
import net.minecraft.server.v1_16_R2.PacketLoginInStart;
import net.minecraft.server.v1_16_R2.PacketLoginOutCustomPayload;
import net.minecraft.server.v1_16_R2.PacketLoginOutDisconnect;
import net.minecraft.server.v1_16_R2.PacketLoginOutEncryptionBegin;
import net.minecraft.server.v1_16_R2.PacketLoginOutListener;
import net.minecraft.server.v1_16_R2.PacketLoginOutSetCompression;
import net.minecraft.server.v1_16_R2.PacketLoginOutSuccess;
import net.minecraft.server.v1_16_R2.PacketPlayInAbilities;
import net.minecraft.server.v1_16_R2.PacketPlayInAdvancements;
import net.minecraft.server.v1_16_R2.PacketPlayInArmAnimation;
import net.minecraft.server.v1_16_R2.PacketPlayInAutoRecipe;
import net.minecraft.server.v1_16_R2.PacketPlayInBEdit;
import net.minecraft.server.v1_16_R2.PacketPlayInBeacon;
import net.minecraft.server.v1_16_R2.PacketPlayInBlockDig;
import net.minecraft.server.v1_16_R2.PacketPlayInBlockPlace;
import net.minecraft.server.v1_16_R2.PacketPlayInBoatMove;
import net.minecraft.server.v1_16_R2.PacketPlayInChat;
import net.minecraft.server.v1_16_R2.PacketPlayInClientCommand;
import net.minecraft.server.v1_16_R2.PacketPlayInCloseWindow;
import net.minecraft.server.v1_16_R2.PacketPlayInCustomPayload;
import net.minecraft.server.v1_16_R2.PacketPlayInDifficultyChange;
import net.minecraft.server.v1_16_R2.PacketPlayInDifficultyLock;
import net.minecraft.server.v1_16_R2.PacketPlayInEnchantItem;
import net.minecraft.server.v1_16_R2.PacketPlayInEntityAction;
import net.minecraft.server.v1_16_R2.PacketPlayInEntityNBTQuery;
import net.minecraft.server.v1_16_R2.PacketPlayInFlying;
import net.minecraft.server.v1_16_R2.PacketPlayInHeldItemSlot;
import net.minecraft.server.v1_16_R2.PacketPlayInItemName;
import net.minecraft.server.v1_16_R2.PacketPlayInJigsawGenerate;
import net.minecraft.server.v1_16_R2.PacketPlayInKeepAlive;
import net.minecraft.server.v1_16_R2.PacketPlayInPickItem;
import net.minecraft.server.v1_16_R2.PacketPlayInRecipeDisplayed;
import net.minecraft.server.v1_16_R2.PacketPlayInRecipeSettings;
import net.minecraft.server.v1_16_R2.PacketPlayInResourcePackStatus;
import net.minecraft.server.v1_16_R2.PacketPlayInSetCommandBlock;
import net.minecraft.server.v1_16_R2.PacketPlayInSetCommandMinecart;
import net.minecraft.server.v1_16_R2.PacketPlayInSetCreativeSlot;
import net.minecraft.server.v1_16_R2.PacketPlayInSetJigsaw;
import net.minecraft.server.v1_16_R2.PacketPlayInSettings;
import net.minecraft.server.v1_16_R2.PacketPlayInSpectate;
import net.minecraft.server.v1_16_R2.PacketPlayInSteerVehicle;
import net.minecraft.server.v1_16_R2.PacketPlayInStruct;
import net.minecraft.server.v1_16_R2.PacketPlayInTabComplete;
import net.minecraft.server.v1_16_R2.PacketPlayInTeleportAccept;
import net.minecraft.server.v1_16_R2.PacketPlayInTileNBTQuery;
import net.minecraft.server.v1_16_R2.PacketPlayInTrSel;
import net.minecraft.server.v1_16_R2.PacketPlayInTransaction;
import net.minecraft.server.v1_16_R2.PacketPlayInUpdateSign;
import net.minecraft.server.v1_16_R2.PacketPlayInUseEntity;
import net.minecraft.server.v1_16_R2.PacketPlayInUseItem;
import net.minecraft.server.v1_16_R2.PacketPlayInVehicleMove;
import net.minecraft.server.v1_16_R2.PacketPlayInWindowClick;
import net.minecraft.server.v1_16_R2.PacketPlayOutAbilities;
import net.minecraft.server.v1_16_R2.PacketPlayOutAdvancements;
import net.minecraft.server.v1_16_R2.PacketPlayOutAnimation;
import net.minecraft.server.v1_16_R2.PacketPlayOutAttachEntity;
import net.minecraft.server.v1_16_R2.PacketPlayOutAutoRecipe;
import net.minecraft.server.v1_16_R2.PacketPlayOutBlockAction;
import net.minecraft.server.v1_16_R2.PacketPlayOutBlockBreak;
import net.minecraft.server.v1_16_R2.PacketPlayOutBlockBreakAnimation;
import net.minecraft.server.v1_16_R2.PacketPlayOutBlockChange;
import net.minecraft.server.v1_16_R2.PacketPlayOutBoss;
import net.minecraft.server.v1_16_R2.PacketPlayOutCamera;
import net.minecraft.server.v1_16_R2.PacketPlayOutChat;
import net.minecraft.server.v1_16_R2.PacketPlayOutCloseWindow;
import net.minecraft.server.v1_16_R2.PacketPlayOutCollect;
import net.minecraft.server.v1_16_R2.PacketPlayOutCombatEvent;
import net.minecraft.server.v1_16_R2.PacketPlayOutCommands;
import net.minecraft.server.v1_16_R2.PacketPlayOutCustomPayload;
import net.minecraft.server.v1_16_R2.PacketPlayOutCustomSoundEffect;
import net.minecraft.server.v1_16_R2.PacketPlayOutEntity;
import net.minecraft.server.v1_16_R2.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_16_R2.PacketPlayOutEntityEffect;
import net.minecraft.server.v1_16_R2.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_16_R2.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_16_R2.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_16_R2.PacketPlayOutEntitySound;
import net.minecraft.server.v1_16_R2.PacketPlayOutEntityStatus;
import net.minecraft.server.v1_16_R2.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_16_R2.PacketPlayOutEntityVelocity;
import net.minecraft.server.v1_16_R2.PacketPlayOutExperience;
import net.minecraft.server.v1_16_R2.PacketPlayOutExplosion;
import net.minecraft.server.v1_16_R2.PacketPlayOutGameStateChange;
import net.minecraft.server.v1_16_R2.PacketPlayOutHeldItemSlot;
import net.minecraft.server.v1_16_R2.PacketPlayOutKeepAlive;
import net.minecraft.server.v1_16_R2.PacketPlayOutKickDisconnect;
import net.minecraft.server.v1_16_R2.PacketPlayOutLightUpdate;
import net.minecraft.server.v1_16_R2.PacketPlayOutLogin;
import net.minecraft.server.v1_16_R2.PacketPlayOutLookAt;
import net.minecraft.server.v1_16_R2.PacketPlayOutMap;
import net.minecraft.server.v1_16_R2.PacketPlayOutMapChunk;
import net.minecraft.server.v1_16_R2.PacketPlayOutMount;
import net.minecraft.server.v1_16_R2.PacketPlayOutMultiBlockChange;
import net.minecraft.server.v1_16_R2.PacketPlayOutNBTQuery;
import net.minecraft.server.v1_16_R2.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_16_R2.PacketPlayOutNamedSoundEffect;
import net.minecraft.server.v1_16_R2.PacketPlayOutOpenBook;
import net.minecraft.server.v1_16_R2.PacketPlayOutOpenSignEditor;
import net.minecraft.server.v1_16_R2.PacketPlayOutOpenWindow;
import net.minecraft.server.v1_16_R2.PacketPlayOutOpenWindowHorse;
import net.minecraft.server.v1_16_R2.PacketPlayOutOpenWindowMerchant;
import net.minecraft.server.v1_16_R2.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_16_R2.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_16_R2.PacketPlayOutPosition;
import net.minecraft.server.v1_16_R2.PacketPlayOutRecipeUpdate;
import net.minecraft.server.v1_16_R2.PacketPlayOutRecipes;
import net.minecraft.server.v1_16_R2.PacketPlayOutRemoveEntityEffect;
import net.minecraft.server.v1_16_R2.PacketPlayOutResourcePackSend;
import net.minecraft.server.v1_16_R2.PacketPlayOutRespawn;
import net.minecraft.server.v1_16_R2.PacketPlayOutScoreboardDisplayObjective;
import net.minecraft.server.v1_16_R2.PacketPlayOutScoreboardObjective;
import net.minecraft.server.v1_16_R2.PacketPlayOutScoreboardScore;
import net.minecraft.server.v1_16_R2.PacketPlayOutScoreboardTeam;
import net.minecraft.server.v1_16_R2.PacketPlayOutSelectAdvancementTab;
import net.minecraft.server.v1_16_R2.PacketPlayOutServerDifficulty;
import net.minecraft.server.v1_16_R2.PacketPlayOutSetCooldown;
import net.minecraft.server.v1_16_R2.PacketPlayOutSetSlot;
import net.minecraft.server.v1_16_R2.PacketPlayOutSpawnEntity;
import net.minecraft.server.v1_16_R2.PacketPlayOutSpawnEntityExperienceOrb;
import net.minecraft.server.v1_16_R2.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_16_R2.PacketPlayOutSpawnEntityPainting;
import net.minecraft.server.v1_16_R2.PacketPlayOutSpawnPosition;
import net.minecraft.server.v1_16_R2.PacketPlayOutStatistic;
import net.minecraft.server.v1_16_R2.PacketPlayOutStopSound;
import net.minecraft.server.v1_16_R2.PacketPlayOutTabComplete;
import net.minecraft.server.v1_16_R2.PacketPlayOutTags;
import net.minecraft.server.v1_16_R2.PacketPlayOutTileEntityData;
import net.minecraft.server.v1_16_R2.PacketPlayOutTitle;
import net.minecraft.server.v1_16_R2.PacketPlayOutTransaction;
import net.minecraft.server.v1_16_R2.PacketPlayOutUnloadChunk;
import net.minecraft.server.v1_16_R2.PacketPlayOutUpdateAttributes;
import net.minecraft.server.v1_16_R2.PacketPlayOutUpdateHealth;
import net.minecraft.server.v1_16_R2.PacketPlayOutUpdateTime;
import net.minecraft.server.v1_16_R2.PacketPlayOutVehicleMove;
import net.minecraft.server.v1_16_R2.PacketPlayOutViewCentre;
import net.minecraft.server.v1_16_R2.PacketPlayOutViewDistance;
import net.minecraft.server.v1_16_R2.PacketPlayOutWindowData;
import net.minecraft.server.v1_16_R2.PacketPlayOutWindowItems;
import net.minecraft.server.v1_16_R2.PacketPlayOutWorldBorder;
import net.minecraft.server.v1_16_R2.PacketPlayOutWorldEvent;
import net.minecraft.server.v1_16_R2.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_16_R2.PacketStatusInListener;
import net.minecraft.server.v1_16_R2.PacketStatusInPing;
import net.minecraft.server.v1_16_R2.PacketStatusInStart;
import net.minecraft.server.v1_16_R2.PacketStatusOutListener;
import net.minecraft.server.v1_16_R2.PacketStatusOutPong;
import net.minecraft.server.v1_16_R2.PacketStatusOutServerInfo;

public class SPacketListener
    extends VPacketListener
    implements PacketListenerPlayIn,
    PacketListenerPlayOut,
    PacketStatusInListener,
    PacketStatusOutListener,
    PacketLoginInListener,
    PacketLoginOutListener,
    PacketHandshakingInListener {
    public void a(PacketStatusOutServerInfo packet) {
    }

    public void a(PacketPlayOutBoss packet) {
    }

    public void a(PacketPlayOutGameStateChange packet) {
        this.handleOut(packet, new SPacketPlayOutGameStateChange());
    }

    public void a(PacketPlayOutAnimation packet) {
    }

    public void a(PacketPlayOutKeepAlive packet) {
        this.handleOut(packet, new SPacketPlayOutKeepAlive());
    }

    public void a(PacketPlayOutResourcePackSend packet) {
    }

    public void a(PacketPlayInBEdit packet) {
    }

    public void a(PacketPlayOutEntitySound packet) {
    }

    public void a(PacketLoginOutSetCompression packet) {
    }

    public void a(PacketHandshakingInSetProtocol packet) {
    }

    public void a(PacketPlayOutSpawnEntityLiving packet) {
        this.handleOut(packet, new SPacketPlayOutSpawnEntityLiving());
    }

    public void a(PacketPlayOutUpdateHealth packet) {
    }

    public void a(PacketPlayOutOpenWindowHorse packet) {
    }

    public void a(PacketPlayInSettings packet) {
    }

    public void a(PacketPlayOutStopSound packet) {
    }

    public void a(PacketPlayOutLookAt packet) {
    }

    public void a(PacketPlayInChat packet) {
    }

    public void a(PacketPlayInSetJigsaw packet) {
    }

    public void a(PacketPlayOutEntityMetadata packet) {
        this.handleOut(packet, new SPacketPlayOutEntityMetadata());
    }

    public void a(PacketPlayOutEntityVelocity packet) {
        this.handleOut(packet, new SPacketPlayOutEntityVelocity());
    }

    public void a(PacketPlayInRecipeSettings packet) {
    }

    public void a(PacketPlayOutUpdateTime packet) {
    }

    public void a(PacketPlayOutHeldItemSlot packet) {
    }

    public void a(PacketPlayOutBlockBreakAnimation packet) {
    }

    public void a(PacketPlayOutServerDifficulty packet) {
    }

    public void a(PacketPlayOutMapChunk packet) {
    }

    public void a(PacketPlayOutScoreboardObjective packet) {
    }

    public void a(PacketPlayOutLightUpdate packet) {
    }

    public void a(PacketPlayOutNamedSoundEffect packet) {
    }

    public void a(PacketPlayInRecipeDisplayed packet) {
    }

    public void a(PacketPlayInUseEntity packet) {
        this.handleIn(packet, new SPacketPlayInUseEntity());
    }

    public void a(PacketPlayOutTransaction packet) {
        this.handleOut(packet, new SPacketPlayOutTransaction());
    }

    public void a(PacketPlayOutCamera packet) {
    }

    public void a(PacketPlayInStruct packet) {
    }

    public void a(PacketPlayOutExplosion packet) {
        this.handleOut(packet, new SPacketPlayOutExplosion());
    }

    public void a(PacketPlayOutCustomPayload packet) {
    }

    public void a(PacketPlayOutScoreboardDisplayObjective packet) {
    }

    public void a(PacketPlayInFlying packet) {
        this.handleIn(packet, new SPacketPlayInFlying());
    }

    public void a(PacketPlayOutEntityHeadRotation packet) {
    }

    public void a(PacketPlayInPickItem packet) {
    }

    public void a(PacketLoginInCustomPayload packet) {
    }

    public void a(PacketPlayInEntityAction packet) {
        this.handleIn(packet, new SPacketPlayInEntityAction());
    }

    public void a(PacketPlayInSteerVehicle packet) {
        this.handleIn(packet, new SPacketPlayInSteerVehicle());
    }

    public void a(PacketPlayOutWorldBorder packet) {
    }

    public void a(PacketPlayOutWindowData packet) {
    }

    public void a(PacketPlayOutExperience packet) {
    }

    public void a(PacketPlayInTransaction packet) {
        this.handleIn(packet, new SPacketPlayInTransaction());
    }

    public void a(PacketLoginInStart packet) {
    }

    public void a(PacketPlayOutCombatEvent packet) {
    }

    public void a(PacketPlayOutPlayerListHeaderFooter packet) {
    }

    public void a(PacketStatusInStart packet) {
    }

    public void a(PacketPlayOutPlayerInfo packet) {
    }

    public void a(PacketPlayOutViewDistance packet) {
    }

    public void a(PacketPlayOutSelectAdvancementTab packet) {
    }

    public void a(PacketPlayOutNBTQuery packet) {
    }

    public void a(PacketPlayInDifficultyChange packet) {
    }

    public void a(PacketPlayInHeldItemSlot packet) {
        this.handleIn(packet, new SPacketPlayInHeldItemSlot());
    }

    public SPacketListener(PlayerData playerData) {
        super(playerData);
    }

    public void a(PacketLoginOutSuccess packet) {
    }

    public void a(PacketPlayOutLogin packet) {
    }

    public void a(PacketLoginOutDisconnect packet) {
    }

    public void a(PacketPlayInAutoRecipe packet) {
    }

    public void a(PacketPlayOutSpawnEntity packet) {
        this.handleOut(packet, new SPacketPlayOutSpawnEntity());
    }

    public void a(PacketPlayOutBlockAction packet) {
    }

    public void a(PacketPlayInItemName packet) {
    }

    public void a(PacketPlayOutAbilities packet) {
        this.handleOut(packet, new SPacketPlayOutAbilities());
    }

    public void a(PacketPlayInAbilities packet) {
        this.handleIn(packet, new SPacketPlayInAbilities());
    }

    public void a(PacketPlayInBlockDig packet) {
        this.handleIn(packet, new SPacketPlayInBlockDig());
    }

    public void a(PacketPlayOutMultiBlockChange packet) {
    }

    public void a(PacketPlayOutNamedEntitySpawn packet) {
        this.handleOut(packet, new SPacketPlayOutNamedEntitySpawn());
    }

    public void a(PacketPlayOutAdvancements packet) {
    }

    public void a(PacketPlayOutUpdateAttributes packet) {
        this.handleOut(packet, new SPacketPlayOutUpdateAttributes());
    }

    public void a(PacketPlayOutMount packet) {
    }

    public void a(PacketPlayInTabComplete packet) {
    }

    public void a(PacketPlayOutOpenWindow packet) {
        this.handleOut(packet, new SPacketPlayOutOpenWindow());
    }

    public void a(PacketPlayOutSetSlot packet) {
        this.handleOut(packet, new SPacketPlayOutSetSlot());
    }

    public void a(PacketPlayOutBlockBreak packet) {
    }

    public void a(PacketPlayInKeepAlive packet) {
        this.handleIn(packet, new SPacketPlayInKeepAlive());
    }

    public void a(PacketPlayOutBlockChange packet) {
    }

    public void a(PacketPlayOutOpenSignEditor packet) {
    }

    public void a(PacketPlayOutMap packet) {
    }

    public void a(PacketPlayOutWindowItems packet) {
    }

    @Override
    public void process(Object obj) {
        if (obj instanceof Packet) {
            this.processTyped((Packet)obj);
        }
    }

    public void a(PacketPlayInEntityNBTQuery packet) {
    }

    public NetworkManager a() {
        return null;
    }

    public void a(PacketLoginOutCustomPayload packet) {
    }

    public void a(PacketPlayOutRecipes packet) {
    }

    public void a(PacketPlayInSetCommandMinecart packet) {
    }

    public void a(PacketPlayOutWorldParticles packet) {
    }

    public void a(PacketPlayInSetCommandBlock packet) {
    }

    public void a(PacketPlayOutAutoRecipe packet) {
    }

    public void a(PacketPlayOutTitle packet) {
    }

    public void a(PacketPlayInEnchantItem packet) {
    }

    public void a(PacketPlayOutChat packet) {
    }

    public void a(PacketPlayInUpdateSign packet) {
    }

    public void a(PacketPlayInCloseWindow packet) {
        this.handleIn(packet, new SPacketPlayInCloseWindow());
    }

    public void a(IChatBaseComponent packet) {
    }

    public void a(PacketPlayOutSpawnEntityExperienceOrb packet) {
    }

    public void a(PacketPlayInBeacon packet) {
    }

    public void a(PacketPlayOutEntity packet) {
        this.handleOut(packet, new SPacketPlayOutEntity());
    }

    public void a(PacketPlayOutAttachEntity packet) {
        this.handleOut(packet, new SPacketPlayOutAttachEntity());
    }

    public void a(PacketPlayOutKickDisconnect packet) {
    }

    public void a(PacketPlayOutVehicleMove packet) {
    }

    public void a(PacketPlayOutEntityEquipment packet) {
    }

    public void a(PacketPlayOutSetCooldown packet) {
    }

    public void a(PacketPlayInAdvancements packet) {
    }

    public void a(PacketPlayOutCustomSoundEffect packet) {
    }

    public void a(PacketPlayOutSpawnPosition packet) {
        this.handleOut(packet, new SPacketPlayOutSpawnPosition());
    }

    public void a(PacketPlayOutOpenWindowMerchant packet) {
    }

    public void a(PacketPlayOutTabComplete packet) {
    }

    public void a(PacketPlayOutRemoveEntityEffect packet) {
        this.handleOut(packet, new SPacketPlayOutRemoveEntityEffect());
    }

    public void a(PacketPlayInJigsawGenerate packet) {
    }

    public void a(PacketPlayInBoatMove packet) {
    }

    public void a(PacketPlayOutScoreboardScore packet) {
    }

    public void a(PacketPlayOutTags packet) {
    }

    public void a(PacketPlayInTeleportAccept packet) {
        this.handleIn(packet, new SPacketPlayInTeleportAccept());
    }

    public void a(PacketPlayInArmAnimation packet) {
        this.handleIn(packet, new SPacketPlayInArmAnimation());
    }

    public void a(PacketPlayOutCommands packet) {
    }

    public void a(PacketPlayOutScoreboardTeam packet) {
    }

    public void a(PacketPlayInClientCommand packet) {
        this.handleIn(packet, new SPacketPlayInClientCommand());
    }

    public void a(PacketPlayInWindowClick packet) {
        this.handleIn(packet, new SPacketPlayInWindowClick());
    }

    public void a(PacketPlayInTileNBTQuery packet) {
    }

    public void a(PacketPlayInDifficultyLock packet) {
    }

    public void a(PacketPlayInVehicleMove packet) {
        this.handleIn(packet, new SPacketPlayInVehicleMove());
    }

    public void a(PacketPlayOutEntityEffect packet) {
        this.handleOut(packet, new SPacketPlayOutEntityEffect());
    }

    public void a(PacketPlayOutCollect packet) {
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

    public void a(PacketLoginInEncryptionBegin packet) {
    }

    public void a(PacketPlayOutEntityTeleport packet) {
        this.handleOut(packet, new SPacketPlayOutEntityTeleport());
    }

    public void a(PacketStatusInPing packet) {
    }

    public void a(PacketPlayInResourcePackStatus packet) {
    }

    public void a(PacketPlayOutWorldEvent packet) {
    }

    public void a(PacketPlayOutRespawn packet) {
        this.handleOut(packet, new SPacketPlayOutRespawn());
    }

    public void a(PacketPlayOutViewCentre packet) {
    }

    public void a(PacketPlayOutOpenBook packet) {
    }

    public void a(PacketPlayOutStatistic packet) {
    }

    public void a(PacketPlayOutEntityDestroy packet) {
        this.handleOut(packet, new SPacketPlayOutEntityDestroy());
    }

    public void a(PacketLoginOutEncryptionBegin packet) {
    }

    public void a(PacketPlayOutUnloadChunk packet) {
    }

    public void a(PacketPlayOutCloseWindow packet) {
    }

    public void a(PacketPlayOutRecipeUpdate packet) {
    }

    public void a(PacketStatusOutPong packet) {
    }

    public void a(PacketPlayOutEntityStatus packet) {
    }

    public void a(PacketPlayInCustomPayload packet) {
        this.handleIn(packet, new SPacketPlayInCustomPayload());
    }

    public void a(PacketPlayInUseItem packet) {
        this.handleIn(packet, new SPacketPlayInUseItem());
    }

    public void a(PacketPlayInSpectate packet) {
    }

    public void a(PacketPlayInBlockPlace packet) {
        this.handleIn(packet, new SPacketPlayInBlockPlace());
    }

    public void a(PacketPlayOutSpawnEntityPainting packet) {
    }

    public void a(PacketPlayInTrSel packet) {
    }

    public void a(PacketPlayInSetCreativeSlot packet) {
        this.handleIn(packet, new SPacketPlayInSetCreativeSlot());
    }

    public void a(PacketPlayOutTileEntityData packet) {
    }
}
