package me.levansj01.verus.data;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import me.levansj01.verus.check.AimCheck;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.MovementCheck;
import me.levansj01.verus.check.MovementCheck2;
import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.ReachCheck;
import me.levansj01.verus.check.VehicleCheck;
import me.levansj01.verus.check.checks.badpackets.BadPacketsG1;
import me.levansj01.verus.check.checks.badpackets.BadPacketsG2;
import me.levansj01.verus.check.checks.badpackets.BadPacketsG4;
import me.levansj01.verus.check.checks.badpackets.BadPacketsG8;
import me.levansj01.verus.check.checks.manual.ManualA;
import me.levansj01.verus.check.checks.payload.CustomPayloadB;
import me.levansj01.verus.check.handler.TeleportCheck;
import me.levansj01.verus.check.manager.CheckManager;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;

public class CheckData {
    private BadPacketsG4 pingSpoofCheck;
    private PacketHandler[][] packetHandlerByClass;
    private ManualA manualBanCheck;
    private PingHandler[] pingHandlers;
    private VehicleCheck[] vehicleChecks;
    private AimCheck[] aimChecks;
    private BadPacketsG2 transactionCheck;
    private final PlayerData playerData;
    @Deprecated
    private TeleportCheck[] teleportChecks;
    private Check skipTransactionCheck;
    private MovementCheck[] movementChecks;
    private PacketHandler[] packetHandlers;
    private PacketCheck[] packetChecks;
    private final Map<String, Check> byIdentifier = new ConcurrentHashMap<>();
    private final Check[] originalChecks;
    private Check[] checks;
    private ReachCheck[] reachChecks;
    private MovementCheck2[] movementChecks2;
    private CustomPayloadB brandCheck;
    private BadPacketsG1 keepAliveCheck;

    public <T> T getSingle(Class<T> clazz) {
        return (T)Arrays.stream(this.checks).filter(clazz::isInstance).findFirst().orElse(null);
    }

    @Deprecated
    public TeleportCheck[] getTeleportChecks() {
        return this.teleportChecks;
    }

    public MovementCheck2[] getMovementChecks2() {
        return this.movementChecks2;
    }

    public Stream<Check> update() {
        return Arrays.stream(this.originalChecks).filter(Array -> Array.getViolations() > Array.getMinViolation());
    }

    private <T> T[] getArray(Class<T> clazz, IntFunction<T[]> intFunction) {
        return (T[])Arrays.stream(this.checks).filter(clazz::isInstance).map(Array -> Array).toArray(intFunction);
    }

    public ManualA getManualBanCheck() {
        return this.manualBanCheck;
    }

    public VehicleCheck[] getVehicleChecks() {
        return this.vehicleChecks;
    }

    public PacketHandler[] getPacketHandlers() {
        return this.packetHandlers;
    }

    public BadPacketsG2 getTransactionCheck() {
        return this.transactionCheck;
    }

    public PacketHandler[][] getPacketHandlerByClass() {
        return this.packetHandlerByClass;
    }

    public Check[] getChecks() {
        return this.checks;
    }

    private <T> List<T> getList(Class<T> clazz) {
        return Arrays.stream(this.checks).filter(clazz::isInstance).map(Array -> (T)Array).collect(Collectors.toList());
    }

    public Check getSkipTransactionCheck() {
        return this.skipTransactionCheck;
    }

    public Map<String, Check> getByIdentifier() {
        return this.byIdentifier;
    }

    private <T> T getSingle(String str) {
        return (T)Arrays.stream(this.checks).filter(Array -> Array.getClass().getSimpleName().equals(str)).findFirst().orElse(null);
    }

    public PacketHandler[] getHandle(VPacket<?> packet) {
        int ordinal = packet.ordinal();
        return ordinal >= this.packetHandlerByClass.length ? null : this.packetHandlerByClass[ordinal];
    }

    CheckData(PlayerData playerData, Check... originalChecks) {
        this.playerData = playerData;
        this.originalChecks = originalChecks;

        for (Check check : this.originalChecks) {
            check.setPlayerData(playerData);
            check.setPlayer(playerData.getPlayer());
            this.byIdentifier.put(check.identifier(), check);
        }

        this.updateChecks();
    }

    private void initPacketHandles() {
        int length = this.packetHandlers.length;
        int[][] valueses = new int[length][];

        for (int i = 0; i < length; i++) {
            valueses[i] = this.packetHandlers[i].parse();
        }

        int value = VPacket.PACKET_COUNT.get();
        PacketHandler[][] packetHandlerses = new PacketHandler[value][];
        List[] lists = new List[value];

        for (int i = 0; i < valueses.length; i++) {
            for (int value2 : valueses[i]) {
                List list = lists[value2];
                if (list == null) {
                    list = lists[value2] = new LinkedList();
                }

                list.add(this.packetHandlers[i]);
            }
        }

        for (int i = 0; i < value; i++) {
            List<PacketHandler> list = lists[i];
            if (list != null) {
                packetHandlerses[i] = list.toArray(new PacketHandler[0]);
            }
        }

        this.packetHandlerByClass = packetHandlerses;
    }

    public BadPacketsG4 getPingSpoofCheck() {
        return this.pingSpoofCheck;
    }

    public Check[] getOriginalChecks() {
        return this.originalChecks;
    }

    public MovementCheck[] getMovementChecks() {
        return this.movementChecks;
    }

    public PacketCheck[] getPacketChecks() {
        return this.packetChecks;
    }

    public AimCheck[] getAimChecks() {
        return this.aimChecks;
    }

    public void load(Check check, double violations) {
        check.setViolations(violations);
        check.setLastViolation((int)Math.floor(violations));
    }

    public CustomPayloadB getBrandCheck() {
        return this.brandCheck;
    }

    public PingHandler[] getPingHandlers() {
        return this.pingHandlers;
    }

    public PlayerData getPlayerData() {
        return this.playerData;
    }

    public ReachCheck[] getReachChecks() {
        return this.reachChecks;
    }

    public void updateChecks() {
        this.checks = Arrays.stream(this.originalChecks).filter(Check::supported).toArray(Check[]::new);
        CheckManager checkManager = CheckManager.getInstance();

        for (Check check : this.checks) {
            check.setPlayerData(this.playerData);
            check.setPlayer(this.playerData.getPlayer());
            check.setMaxViolation(checkManager.getMaxViolation(check));
        }

        this.packetChecks = this.getArray(PacketCheck.class, PacketCheck[]::new);
        this.packetHandlers = this.getArray(PacketHandler.class, PacketHandler[]::new);
        this.movementChecks = this.getArray(MovementCheck.class, MovementCheck[]::new);
        this.movementChecks2 = this.getArray(MovementCheck2.class, MovementCheck2[]::new);
        this.vehicleChecks = this.getArray(VehicleCheck.class, VehicleCheck[]::new);
        this.teleportChecks = this.getArray(TeleportCheck.class, TeleportCheck[]::new);
        this.aimChecks = this.getArray(AimCheck.class, AimCheck[]::new);
        this.reachChecks = this.getArray(ReachCheck.class, ReachCheck[]::new);
        this.pingHandlers = this.getArray(PingHandler.class, PingHandler[]::new);
        this.keepAliveCheck = this.getSingle(BadPacketsG1.class);
        this.transactionCheck = this.getSingle(BadPacketsG2.class);
        this.pingSpoofCheck = this.getSingle(BadPacketsG4.class);
        this.brandCheck = this.getSingle(CustomPayloadB.class);
        this.skipTransactionCheck = this.getSingle(BadPacketsG8.class);
        this.manualBanCheck = this.getSingle(ManualA.class);
        this.initPacketHandles();
    }

    public BadPacketsG1 getKeepAliveCheck() {
        return this.keepAliveCheck;
    }
}
