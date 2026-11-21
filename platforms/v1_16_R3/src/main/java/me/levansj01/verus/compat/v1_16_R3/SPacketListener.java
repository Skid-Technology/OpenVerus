package me.levansj01.verus.compat.v1_16_R3;

import me.levansj01.verus.compat.api.VPacketListener;
import me.levansj01.verus.compat.v1_16_R3.packets.SPacketPlayInAbilities;
import me.levansj01.verus.compat.v1_16_R3.packets.SPacketPlayInArmAnimation;
import me.levansj01.verus.compat.v1_16_R3.packets.SPacketPlayInBlockDig;
import me.levansj01.verus.compat.v1_16_R3.packets.SPacketPlayInBlockPlace;
import me.levansj01.verus.compat.v1_16_R3.packets.SPacketPlayInClientCommand;
import me.levansj01.verus.compat.v1_16_R3.packets.SPacketPlayInCloseWindow;
import me.levansj01.verus.compat.v1_16_R3.packets.SPacketPlayInCustomPayload;
import me.levansj01.verus.compat.v1_16_R3.packets.SPacketPlayInEntityAction;
import me.levansj01.verus.compat.v1_16_R3.packets.SPacketPlayInFlying;
import me.levansj01.verus.compat.v1_16_R3.packets.SPacketPlayInHeldItemSlot;
import me.levansj01.verus.compat.v1_16_R3.packets.SPacketPlayInKeepAlive;
import me.levansj01.verus.compat.v1_16_R3.packets.SPacketPlayInSetCreativeSlot;
import me.levansj01.verus.compat.v1_16_R3.packets.SPacketPlayInSteerVehicle;
import me.levansj01.verus.compat.v1_16_R3.packets.SPacketPlayInTeleportAccept;
import me.levansj01.verus.compat.v1_16_R3.packets.SPacketPlayInTransaction;
import me.levansj01.verus.compat.v1_16_R3.packets.SPacketPlayInUseEntity;
import me.levansj01.verus.compat.v1_16_R3.packets.SPacketPlayInUseItem;
import me.levansj01.verus.compat.v1_16_R3.packets.SPacketPlayInVehicleMove;
import me.levansj01.verus.compat.v1_16_R3.packets.SPacketPlayInWindowClick;
import me.levansj01.verus.compat.v1_16_R3.packets.SPacketPlayOutAbilities;
import me.levansj01.verus.compat.v1_16_R3.packets.SPacketPlayOutAttachEntity;
import me.levansj01.verus.compat.v1_16_R3.packets.SPacketPlayOutEntity;
import me.levansj01.verus.compat.v1_16_R3.packets.SPacketPlayOutEntityDestroy;
import me.levansj01.verus.compat.v1_16_R3.packets.SPacketPlayOutEntityEffect;
import me.levansj01.verus.compat.v1_16_R3.packets.SPacketPlayOutEntityMetadata;
import me.levansj01.verus.compat.v1_16_R3.packets.SPacketPlayOutEntityTeleport;
import me.levansj01.verus.compat.v1_16_R3.packets.SPacketPlayOutEntityVelocity;
import me.levansj01.verus.compat.v1_16_R3.packets.SPacketPlayOutExplosion;
import me.levansj01.verus.compat.v1_16_R3.packets.SPacketPlayOutGameStateChange;
import me.levansj01.verus.compat.v1_16_R3.packets.SPacketPlayOutKeepAlive;
import me.levansj01.verus.compat.v1_16_R3.packets.SPacketPlayOutNamedEntitySpawn;
import me.levansj01.verus.compat.v1_16_R3.packets.SPacketPlayOutOpenWindow;
import me.levansj01.verus.compat.v1_16_R3.packets.SPacketPlayOutPosition;
import me.levansj01.verus.compat.v1_16_R3.packets.SPacketPlayOutRemoveEntityEffect;
import me.levansj01.verus.compat.v1_16_R3.packets.SPacketPlayOutRespawn;
import me.levansj01.verus.compat.v1_16_R3.packets.SPacketPlayOutSetSlot;
import me.levansj01.verus.compat.v1_16_R3.packets.SPacketPlayOutSpawnEntity;
import me.levansj01.verus.compat.v1_16_R3.packets.SPacketPlayOutSpawnEntityLiving;
import me.levansj01.verus.compat.v1_16_R3.packets.SPacketPlayOutSpawnPosition;
import me.levansj01.verus.compat.v1_16_R3.packets.SPacketPlayOutTransaction;
import me.levansj01.verus.compat.v1_16_R3.packets.SPacketPlayOutUpdateAttributes;
import me.levansj01.verus.data.PlayerData;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.NetworkManager;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketHandshakingInListener;
import net.minecraft.server.v1_16_R3.PacketHandshakingInSetProtocol;
import net.minecraft.server.v1_16_R3.PacketListener;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketLoginInCustomPayload;
import net.minecraft.server.v1_16_R3.PacketLoginInEncryptionBegin;
import net.minecraft.server.v1_16_R3.PacketLoginInListener;
import net.minecraft.server.v1_16_R3.PacketLoginInStart;
import net.minecraft.server.v1_16_R3.PacketLoginOutCustomPayload;
import net.minecraft.server.v1_16_R3.PacketLoginOutDisconnect;
import net.minecraft.server.v1_16_R3.PacketLoginOutEncryptionBegin;
import net.minecraft.server.v1_16_R3.PacketLoginOutListener;
import net.minecraft.server.v1_16_R3.PacketLoginOutSetCompression;
import net.minecraft.server.v1_16_R3.PacketLoginOutSuccess;
import net.minecraft.server.v1_16_R3.PacketPlayInAbilities;
import net.minecraft.server.v1_16_R3.PacketPlayInAdvancements;
import net.minecraft.server.v1_16_R3.PacketPlayInArmAnimation;
import net.minecraft.server.v1_16_R3.PacketPlayInAutoRecipe;
import net.minecraft.server.v1_16_R3.PacketPlayInBEdit;
import net.minecraft.server.v1_16_R3.PacketPlayInBeacon;
import net.minecraft.server.v1_16_R3.PacketPlayInBlockDig;
import net.minecraft.server.v1_16_R3.PacketPlayInBlockPlace;
import net.minecraft.server.v1_16_R3.PacketPlayInBoatMove;
import net.minecraft.server.v1_16_R3.PacketPlayInChat;
import net.minecraft.server.v1_16_R3.PacketPlayInClientCommand;
import net.minecraft.server.v1_16_R3.PacketPlayInCloseWindow;
import net.minecraft.server.v1_16_R3.PacketPlayInCustomPayload;
import net.minecraft.server.v1_16_R3.PacketPlayInDifficultyChange;
import net.minecraft.server.v1_16_R3.PacketPlayInDifficultyLock;
import net.minecraft.server.v1_16_R3.PacketPlayInEnchantItem;
import net.minecraft.server.v1_16_R3.PacketPlayInEntityAction;
import net.minecraft.server.v1_16_R3.PacketPlayInEntityNBTQuery;
import net.minecraft.server.v1_16_R3.PacketPlayInFlying;
import net.minecraft.server.v1_16_R3.PacketPlayInHeldItemSlot;
import net.minecraft.server.v1_16_R3.PacketPlayInItemName;
import net.minecraft.server.v1_16_R3.PacketPlayInJigsawGenerate;
import net.minecraft.server.v1_16_R3.PacketPlayInKeepAlive;
import net.minecraft.server.v1_16_R3.PacketPlayInPickItem;
import net.minecraft.server.v1_16_R3.PacketPlayInRecipeDisplayed;
import net.minecraft.server.v1_16_R3.PacketPlayInRecipeSettings;
import net.minecraft.server.v1_16_R3.PacketPlayInResourcePackStatus;
import net.minecraft.server.v1_16_R3.PacketPlayInSetCommandBlock;
import net.minecraft.server.v1_16_R3.PacketPlayInSetCommandMinecart;
import net.minecraft.server.v1_16_R3.PacketPlayInSetCreativeSlot;
import net.minecraft.server.v1_16_R3.PacketPlayInSetJigsaw;
import net.minecraft.server.v1_16_R3.PacketPlayInSettings;
import net.minecraft.server.v1_16_R3.PacketPlayInSpectate;
import net.minecraft.server.v1_16_R3.PacketPlayInSteerVehicle;
import net.minecraft.server.v1_16_R3.PacketPlayInStruct;
import net.minecraft.server.v1_16_R3.PacketPlayInTabComplete;
import net.minecraft.server.v1_16_R3.PacketPlayInTeleportAccept;
import net.minecraft.server.v1_16_R3.PacketPlayInTileNBTQuery;
import net.minecraft.server.v1_16_R3.PacketPlayInTrSel;
import net.minecraft.server.v1_16_R3.PacketPlayInTransaction;
import net.minecraft.server.v1_16_R3.PacketPlayInUpdateSign;
import net.minecraft.server.v1_16_R3.PacketPlayInUseEntity;
import net.minecraft.server.v1_16_R3.PacketPlayInUseItem;
import net.minecraft.server.v1_16_R3.PacketPlayInVehicleMove;
import net.minecraft.server.v1_16_R3.PacketPlayInWindowClick;
import net.minecraft.server.v1_16_R3.PacketPlayOutAbilities;
import net.minecraft.server.v1_16_R3.PacketPlayOutAdvancements;
import net.minecraft.server.v1_16_R3.PacketPlayOutAnimation;
import net.minecraft.server.v1_16_R3.PacketPlayOutAttachEntity;
import net.minecraft.server.v1_16_R3.PacketPlayOutAutoRecipe;
import net.minecraft.server.v1_16_R3.PacketPlayOutBlockAction;
import net.minecraft.server.v1_16_R3.PacketPlayOutBlockBreak;
import net.minecraft.server.v1_16_R3.PacketPlayOutBlockBreakAnimation;
import net.minecraft.server.v1_16_R3.PacketPlayOutBlockChange;
import net.minecraft.server.v1_16_R3.PacketPlayOutBoss;
import net.minecraft.server.v1_16_R3.PacketPlayOutCamera;
import net.minecraft.server.v1_16_R3.PacketPlayOutChat;
import net.minecraft.server.v1_16_R3.PacketPlayOutCloseWindow;
import net.minecraft.server.v1_16_R3.PacketPlayOutCollect;
import net.minecraft.server.v1_16_R3.PacketPlayOutCombatEvent;
import net.minecraft.server.v1_16_R3.PacketPlayOutCommands;
import net.minecraft.server.v1_16_R3.PacketPlayOutCustomPayload;
import net.minecraft.server.v1_16_R3.PacketPlayOutCustomSoundEffect;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntity;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityEffect;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntitySound;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityStatus;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityVelocity;
import net.minecraft.server.v1_16_R3.PacketPlayOutExperience;
import net.minecraft.server.v1_16_R3.PacketPlayOutExplosion;
import net.minecraft.server.v1_16_R3.PacketPlayOutGameStateChange;
import net.minecraft.server.v1_16_R3.PacketPlayOutHeldItemSlot;
import net.minecraft.server.v1_16_R3.PacketPlayOutKeepAlive;
import net.minecraft.server.v1_16_R3.PacketPlayOutKickDisconnect;
import net.minecraft.server.v1_16_R3.PacketPlayOutLightUpdate;
import net.minecraft.server.v1_16_R3.PacketPlayOutLogin;
import net.minecraft.server.v1_16_R3.PacketPlayOutLookAt;
import net.minecraft.server.v1_16_R3.PacketPlayOutMap;
import net.minecraft.server.v1_16_R3.PacketPlayOutMapChunk;
import net.minecraft.server.v1_16_R3.PacketPlayOutMount;
import net.minecraft.server.v1_16_R3.PacketPlayOutMultiBlockChange;
import net.minecraft.server.v1_16_R3.PacketPlayOutNBTQuery;
import net.minecraft.server.v1_16_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_16_R3.PacketPlayOutNamedSoundEffect;
import net.minecraft.server.v1_16_R3.PacketPlayOutOpenBook;
import net.minecraft.server.v1_16_R3.PacketPlayOutOpenSignEditor;
import net.minecraft.server.v1_16_R3.PacketPlayOutOpenWindow;
import net.minecraft.server.v1_16_R3.PacketPlayOutOpenWindowHorse;
import net.minecraft.server.v1_16_R3.PacketPlayOutOpenWindowMerchant;
import net.minecraft.server.v1_16_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_16_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_16_R3.PacketPlayOutPosition;
import net.minecraft.server.v1_16_R3.PacketPlayOutRecipeUpdate;
import net.minecraft.server.v1_16_R3.PacketPlayOutRecipes;
import net.minecraft.server.v1_16_R3.PacketPlayOutRemoveEntityEffect;
import net.minecraft.server.v1_16_R3.PacketPlayOutResourcePackSend;
import net.minecraft.server.v1_16_R3.PacketPlayOutRespawn;
import net.minecraft.server.v1_16_R3.PacketPlayOutScoreboardDisplayObjective;
import net.minecraft.server.v1_16_R3.PacketPlayOutScoreboardObjective;
import net.minecraft.server.v1_16_R3.PacketPlayOutScoreboardScore;
import net.minecraft.server.v1_16_R3.PacketPlayOutScoreboardTeam;
import net.minecraft.server.v1_16_R3.PacketPlayOutSelectAdvancementTab;
import net.minecraft.server.v1_16_R3.PacketPlayOutServerDifficulty;
import net.minecraft.server.v1_16_R3.PacketPlayOutSetCooldown;
import net.minecraft.server.v1_16_R3.PacketPlayOutSetSlot;
import net.minecraft.server.v1_16_R3.PacketPlayOutSpawnEntity;
import net.minecraft.server.v1_16_R3.PacketPlayOutSpawnEntityExperienceOrb;
import net.minecraft.server.v1_16_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_16_R3.PacketPlayOutSpawnEntityPainting;
import net.minecraft.server.v1_16_R3.PacketPlayOutSpawnPosition;
import net.minecraft.server.v1_16_R3.PacketPlayOutStatistic;
import net.minecraft.server.v1_16_R3.PacketPlayOutStopSound;
import net.minecraft.server.v1_16_R3.PacketPlayOutTabComplete;
import net.minecraft.server.v1_16_R3.PacketPlayOutTags;
import net.minecraft.server.v1_16_R3.PacketPlayOutTileEntityData;
import net.minecraft.server.v1_16_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_16_R3.PacketPlayOutTransaction;
import net.minecraft.server.v1_16_R3.PacketPlayOutUnloadChunk;
import net.minecraft.server.v1_16_R3.PacketPlayOutUpdateAttributes;
import net.minecraft.server.v1_16_R3.PacketPlayOutUpdateHealth;
import net.minecraft.server.v1_16_R3.PacketPlayOutUpdateTime;
import net.minecraft.server.v1_16_R3.PacketPlayOutVehicleMove;
import net.minecraft.server.v1_16_R3.PacketPlayOutViewCentre;
import net.minecraft.server.v1_16_R3.PacketPlayOutViewDistance;
import net.minecraft.server.v1_16_R3.PacketPlayOutWindowData;
import net.minecraft.server.v1_16_R3.PacketPlayOutWindowItems;
import net.minecraft.server.v1_16_R3.PacketPlayOutWorldBorder;
import net.minecraft.server.v1_16_R3.PacketPlayOutWorldEvent;
import net.minecraft.server.v1_16_R3.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_16_R3.PacketStatusInListener;
import net.minecraft.server.v1_16_R3.PacketStatusInPing;
import net.minecraft.server.v1_16_R3.PacketStatusInStart;
import net.minecraft.server.v1_16_R3.PacketStatusOutListener;
import net.minecraft.server.v1_16_R3.PacketStatusOutPong;
import net.minecraft.server.v1_16_R3.PacketStatusOutServerInfo;

public class SPacketListener
    extends VPacketListener
    implements PacketListenerPlayIn,
    PacketListenerPlayOut,
    PacketStatusInListener,
    PacketStatusOutListener,
    PacketLoginInListener,
    PacketLoginOutListener,
    PacketHandshakingInListener {
    public void a(PacketPlayOutCustomSoundEffect packet) {
    }

    public void a(PacketPlayOutTags packet) {
    }

    public void a(PacketPlayOutSpawnEntityPainting packet) {
    }

    public void a(PacketPlayOutNamedSoundEffect packet) {
    }

    public void a(PacketPlayOutPlayerInfo packet) {
    }

    public void a(PacketPlayOutViewDistance packet) {
    }

    public void a(PacketPlayOutBlockAction packet) {
    }

    public void a(PacketPlayOutWindowData packet) {
    }

    public void a(PacketPlayInWindowClick packet) {
        this.handleIn(packet, new SPacketPlayInWindowClick());
    }

    public void a(PacketPlayOutNamedEntitySpawn packet) {
        this.handleOut(packet, new SPacketPlayOutNamedEntitySpawn());
    }

    public void a(PacketStatusInStart packet) {
    }

    public void a(PacketPlayInVehicleMove packet) {
        this.handleIn(packet, new SPacketPlayInVehicleMove());
    }

    public void a(PacketPlayOutLightUpdate packet) {
    }

    public void a(PacketPlayInTeleportAccept packet) {
        this.handleIn(packet, new SPacketPlayInTeleportAccept());
    }

    public void a(PacketPlayOutWorldEvent packet) {
    }

    public void a(PacketPlayOutTitle packet) {
    }

    public void a(PacketPlayOutWindowItems packet) {
    }

    public void a(PacketPlayOutUpdateAttributes packet) {
        this.handleOut(packet, new SPacketPlayOutUpdateAttributes());
    }

    public void a(PacketLoginInEncryptionBegin packet) {
    }

    public void a(PacketPlayOutAttachEntity packet) {
        this.handleOut(packet, new SPacketPlayOutAttachEntity());
    }

    public void a(PacketPlayOutEntity packet) {
        this.handleOut(packet, new SPacketPlayOutEntity());
    }

    public void a(PacketPlayInSteerVehicle packet) {
        this.handleIn(packet, new SPacketPlayInSteerVehicle());
    }

    public void a(PacketPlayInDifficultyLock packet) {
    }

    public void a(PacketPlayInTileNBTQuery packet) {
    }

    public void a(PacketPlayOutEntityEquipment packet) {
    }

    public void a(PacketPlayOutMap packet) {
    }

    public void a(PacketPlayOutServerDifficulty packet) {
    }

    public void a(PacketPlayOutEntityEffect packet) {
        this.handleOut(packet, new SPacketPlayOutEntityEffect());
    }

    public void a(PacketPlayInTabComplete packet) {
    }

    public void a(PacketPlayOutResourcePackSend packet) {
    }

    public void a(PacketPlayOutOpenBook packet) {
    }

    public void a(PacketPlayInSettings packet) {
    }

    public void a(PacketPlayOutRecipes packet) {
    }

    public void a(PacketPlayInAdvancements packet) {
    }

    public void a(PacketPlayOutWorldBorder packet) {
    }

    public void a(PacketPlayInRecipeSettings packet) {
    }

    public void a(PacketPlayOutRecipeUpdate packet) {
    }

    public void a(PacketPlayOutPlayerListHeaderFooter packet) {
    }

    public void a(PacketPlayOutWorldParticles packet) {
    }

    public void a(PacketStatusOutServerInfo packet) {
    }

    public void a(PacketPlayOutUpdateTime packet) {
    }

    public void a(PacketPlayOutPosition packet) {
        this.handleOut(packet, new SPacketPlayOutPosition());
    }

    public void a(PacketPlayOutCustomPayload packet) {
    }

    public void a(PacketPlayOutEntityDestroy packet) {
        this.handleOut(packet, new SPacketPlayOutEntityDestroy());
    }

    public void a(PacketPlayInUseItem packet) {
        this.handleIn(packet, new SPacketPlayInUseItem());
    }

    public void a(PacketPlayInDifficultyChange packet) {
    }

    public void a(PacketPlayOutStopSound packet) {
    }

    public void a(PacketPlayInKeepAlive packet) {
        this.handleIn(packet, new SPacketPlayInKeepAlive());
    }

    public void a(PacketLoginInStart packet) {
    }

    public void a(PacketPlayOutCommands packet) {
    }

    public void a(PacketPlayOutAnimation packet) {
    }

    public void a(PacketPlayInBlockDig packet) {
        this.handleIn(packet, new SPacketPlayInBlockDig());
    }

    public void a(PacketPlayInBoatMove packet) {
    }

    public void a(PacketPlayOutGameStateChange packet) {
        this.handleOut(packet, new SPacketPlayOutGameStateChange());
    }

    public void a(PacketPlayOutScoreboardTeam packet) {
    }

    public void a(PacketPlayInEntityAction packet) {
        this.handleIn(packet, new SPacketPlayInEntityAction());
    }

    public void a(PacketPlayInSetJigsaw packet) {
    }

    public void a(PacketPlayInBeacon packet) {
    }

    public void a(PacketPlayOutOpenSignEditor packet) {
    }

    public void a(PacketPlayInAbilities packet) {
        this.handleIn(packet, new SPacketPlayInAbilities());
    }

    public void a(PacketPlayInSetCreativeSlot packet) {
        this.handleIn(packet, new SPacketPlayInSetCreativeSlot());
    }

    public void a(PacketPlayOutVehicleMove packet) {
    }

    public void a(PacketPlayOutTransaction packet) {
        this.handleOut(packet, new SPacketPlayOutTransaction());
    }

    public void a(PacketPlayOutLookAt packet) {
    }

    public void a(PacketPlayOutHeldItemSlot packet) {
    }

    public void a(PacketPlayOutCombatEvent packet) {
    }

    public void a(PacketPlayInFlying packet) {
        this.handleIn(packet, new SPacketPlayInFlying());
    }

    public void a(PacketPlayInArmAnimation packet) {
        this.handleIn(packet, new SPacketPlayInArmAnimation());
    }

    public void a(PacketPlayOutCollect packet) {
    }

    public void a(PacketPlayOutEntityMetadata packet) {
        this.handleOut(packet, new SPacketPlayOutEntityMetadata());
    }

    public void a(PacketPlayInTrSel packet) {
    }

    public void a(PacketPlayOutSpawnEntityLiving packet) {
        this.handleOut(packet, new SPacketPlayOutSpawnEntityLiving());
    }

    public void a(PacketStatusOutPong packet) {
    }

    public void a(PacketPlayInSetCommandBlock packet) {
    }

    public void a(PacketLoginOutDisconnect packet) {
    }

    public void a(PacketPlayOutSpawnPosition packet) {
        this.handleOut(packet, new SPacketPlayOutSpawnPosition());
    }

    public void a(PacketPlayOutEntityStatus packet) {
    }

    public void a(PacketPlayOutAbilities packet) {
        this.handleOut(packet, new SPacketPlayOutAbilities());
    }

    public void a(PacketPlayOutEntitySound packet) {
    }

    public void a(PacketPlayOutBlockBreak packet) {
    }

    public void a(PacketPlayOutSpawnEntityExperienceOrb packet) {
    }

    public void a(PacketPlayOutTileEntityData packet) {
    }

    public void a(PacketPlayOutKickDisconnect packet) {
    }

    public void a(PacketPlayOutStatistic packet) {
    }

    public void a(PacketPlayOutExperience packet) {
    }

    public void a(PacketPlayOutChat packet) {
    }

    public void a(PacketPlayOutOpenWindowHorse packet) {
    }

    public void a(PacketPlayInStruct packet) {
    }

    public void a(PacketPlayOutKeepAlive packet) {
        this.handleOut(packet, new SPacketPlayOutKeepAlive());
    }

    public void a(PacketPlayOutMount packet) {
    }

    public void a(PacketLoginInCustomPayload packet) {
    }

    public void a(IChatBaseComponent packet) {
    }

    public void a(PacketPlayInSetCommandMinecart packet) {
    }

    public void a(PacketPlayOutSetCooldown packet) {
    }

    public void a(PacketPlayOutOpenWindow packet) {
        this.handleOut(packet, new SPacketPlayOutOpenWindow());
    }

    public void a(PacketPlayOutCamera packet) {
    }

    public void a(PacketPlayOutUpdateHealth packet) {
    }

    public void a(PacketPlayOutOpenWindowMerchant packet) {
    }

    public void a(PacketPlayOutEntityTeleport packet) {
        this.handleOut(packet, new SPacketPlayOutEntityTeleport());
    }

    public NetworkManager a() {
        return null;
    }

    public void a(PacketPlayInBlockPlace packet) {
        this.handleIn(packet, new SPacketPlayInBlockPlace());
    }

    public void a(PacketPlayOutSelectAdvancementTab packet) {
    }

    public void a(PacketLoginOutEncryptionBegin packet) {
    }

    public void a(PacketPlayOutNBTQuery packet) {
    }

    public void a(PacketLoginOutSuccess packet) {
    }

    public void a(PacketPlayOutUnloadChunk packet) {
    }

    public void a(PacketPlayInJigsawGenerate packet) {
    }

    public void a(PacketPlayInTransaction packet) {
        this.handleIn(packet, new SPacketPlayInTransaction());
    }

    public void a(PacketLoginOutSetCompression packet) {
    }

    public void a(PacketPlayInItemName packet) {
    }

    public void a(PacketPlayInChat packet) {
    }

    public void a(PacketPlayOutViewCentre packet) {
    }

    public void a(PacketPlayOutSpawnEntity packet) {
        this.handleOut(packet, new SPacketPlayOutSpawnEntity());
    }

    public void a(PacketPlayOutScoreboardObjective packet) {
    }

    @Override
    public void process(Object obj) {
        if (obj instanceof Packet) {
            this.processTyped((Packet)obj);
        }
    }

    public void a(PacketPlayInEnchantItem packet) {
    }

    public void a(PacketPlayOutMapChunk packet) {
    }

    public void a(PacketPlayInCloseWindow packet) {
        this.handleIn(packet, new SPacketPlayInCloseWindow());
    }

    public void a(PacketPlayOutAdvancements packet) {
    }

    public void a(PacketPlayOutScoreboardDisplayObjective packet) {
    }

    public void a(PacketPlayInClientCommand packet) {
        this.handleIn(packet, new SPacketPlayInClientCommand());
    }

    public void a(PacketPlayOutBlockBreakAnimation packet) {
    }

    public void a(PacketPlayOutBlockChange packet) {
    }

    public <T extends PacketListener> void processTyped(Packet<T> packet) {
        if ((Object)packet instanceof PacketPlayInCustomPayload) {
            this.a((PacketPlayInCustomPayload)(Object)packet);
        } else {
            packet.a(this);
        }
    }

    public void a(PacketPlayOutSetSlot packet) {
        this.handleOut(packet, new SPacketPlayOutSetSlot());
    }

    public void a(PacketPlayInResourcePackStatus packet) {
    }

    public void a(PacketPlayInCustomPayload packet) {
        this.handleIn(packet, new SPacketPlayInCustomPayload());
    }

    public void a(PacketPlayInHeldItemSlot packet) {
        this.handleIn(packet, new SPacketPlayInHeldItemSlot());
    }

    public void a(PacketPlayOutLogin packet) {
    }

    public void a(PacketPlayOutCloseWindow packet) {
    }

    public SPacketListener(PlayerData playerData) {
        super(playerData);
    }

    public void a(PacketStatusInPing packet) {
    }

    public void a(PacketHandshakingInSetProtocol packet) {
    }

    public void a(PacketPlayOutRespawn packet) {
        this.handleOut(packet, new SPacketPlayOutRespawn());
    }

    public void a(PacketPlayInEntityNBTQuery packet) {
    }

    public void a(PacketPlayInUseEntity packet) {
        this.handleIn(packet, new SPacketPlayInUseEntity());
    }

    public void a(PacketPlayInAutoRecipe packet) {
    }

    public void a(PacketPlayInUpdateSign packet) {
    }

    public void a(PacketPlayOutRemoveEntityEffect packet) {
        this.handleOut(packet, new SPacketPlayOutRemoveEntityEffect());
    }

    public void a(PacketPlayInBEdit packet) {
    }

    public void a(PacketPlayInSpectate packet) {
    }

    public void a(PacketPlayOutScoreboardScore packet) {
    }

    public void a(PacketPlayOutTabComplete packet) {
    }

    public void a(PacketPlayOutEntityHeadRotation packet) {
    }

    public void a(PacketPlayOutEntityVelocity packet) {
        this.handleOut(packet, new SPacketPlayOutEntityVelocity());
    }

    public void a(PacketPlayOutAutoRecipe packet) {
    }

    public void a(PacketPlayOutBoss packet) {
    }

    public void a(PacketLoginOutCustomPayload packet) {
    }

    public void a(PacketPlayInRecipeDisplayed packet) {
    }

    public void a(PacketPlayOutExplosion packet) {
        this.handleOut(packet, new SPacketPlayOutExplosion());
    }

    public void a(PacketPlayInPickItem packet) {
    }

    public void a(PacketPlayOutMultiBlockChange packet) {
    }
}
