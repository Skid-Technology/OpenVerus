package me.levansj01.verus.data.reach;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.data.Transaction;
import me.levansj01.verus.data.transaction.tracker.PacketLocation;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.Intersection;
import me.levansj01.verus.util.java.MathUtil;
import me.levansj01.verus.util.location.PlayerLocation;
import org.bukkit.ChatColor;

public class ReachBase {
    private final int ping;
    private final int nonMoveTicks;
    private PlayerLocation newLast;
    private final List<PacketLocation> recentMoves;
    private final PlayerLocation lastLocation;
    private List<DistanceData> distanceList;
    private final PlayerLocation location;
    private final int connection;
    private static final double EXPAND = 0.03125;
    private double certainty;
    private final Transaction transaction;
    private int i = 0;
    private final PlayerData playerData;
    private List<IntersectionData> intersecionList;
    private DistanceData distanceData;
    private final long timestamp;
    private final boolean sneaking;
    private final int lastPing;

    public PlayerLocation getLocation() {
        return this.location;
    }

    public void dev() {
    }

    public boolean isSneaking() {
        return this.sneaking;
    }

    public List<DistanceData> getDistanceList() {
        return this.distanceList;
    }

    public DistanceData getDistanceData() {
        return this.distanceData;
    }

    public int getNonMoveTicks() {
        return this.nonMoveTicks;
    }

    public int getConnection() {
        return this.connection;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void execute() {
        this.raytraceInterpolate(this.calculateMoves(this.playerData.getVersion() == ClientVersion.V1_7 ? 160 : 210, 60));
        this.calculateLegacy(this.calculateMoves(125, 50));
    }

    public double getCertainty() {
        return this.certainty;
    }

    public double getUncertaintyReachValue() {
        double reach = this.distanceData.getReach();
        return reach > 3.0 ? 3.0 + (reach - 3.0) * this.certainty : reach;
    }

    private String rayTraceDebug(List<PacketLocation> locations, PacketLocation location) {
        PlayerLocation lastLocation = this.lastLocation.clone();
        lastLocation.setPitch(this.location.getPitch());
        lastLocation.setYaw(this.location.getYaw());
        IntersectionData intersectionData;
        if (this.playerData.getVersion() != ClientVersion.V1_8 && this.playerData.getVersion() != ClientVersion.VERSION_UNSUPPORTED) {
            intersectionData = this.getReachRayTrace(location, 3.0, lastLocation);
        } else {
            intersectionData = this.getReachRayTrace(location, 3.0, this.lastLocation, lastLocation);
        }

        boolean flag = locations.contains(location);
        boolean flag2 = intersectionData.getMinReach() != null;
        ChatColor chatColor = flag ? (flag2 ? ChatColor.DARK_GREEN : ChatColor.DARK_RED) : (flag2 ? ChatColor.GREEN : ChatColor.RED);
        return chatColor + "[" + this.i++ + "]";
    }

    public int getI() {
        return this.i;
    }

    public ReachBase(
        PlayerData playerData,
        PlayerLocation location,
        PlayerLocation lastLocation,
        long timestamp,
        int ping,
        int lastPing,
        int connection,
        int nonMoveTicks,
        List<PacketLocation> recentMoves,
        boolean sneaking,
        Transaction transaction
    ) {
        this.playerData = playerData;
        this.location = location;
        this.lastLocation = lastLocation;
        this.timestamp = timestamp;
        this.ping = ping;
        this.lastPing = lastPing;
        this.connection = connection;
        this.nonMoveTicks = nonMoveTicks;
        this.recentMoves = recentMoves;
        this.sneaking = sneaking;
        this.transaction = transaction;
    }

    private IntersectionData getReachRayTrace(PacketLocation location, double value, PlayerLocation... locations) {
        Cuboid cuboid = location.hitbox();
        Intersection[] intersections = Arrays.stream(locations)
            .map(Array -> cuboid.calculateIntercept(Array.toEyeVector(this.sneaking), Array.toEyeVector(this.sneaking).add(Array.getDirection().multiply(value))))
            .toArray(Intersection[]::new);
        return new IntersectionData(location, cuboid, intersections);
    }

    public PlayerData getPlayerData() {
        return this.playerData;
    }

    public PlayerLocation getLastLocation() {
        return this.lastLocation;
    }

    public int getPing() {
        return this.ping;
    }

    private List<PacketLocation> calculateMoves(int value, int value2) {
        ArrayList list = new ArrayList();
        int value3 = Math.max(this.lastPing, this.ping);
        int diff = Math.abs(this.ping - this.lastPing);
        long diff2 = this.timestamp - value - value3 - this.connection;
        Iterator iterator = this.recentMoves.iterator();
        PacketLocation next = (PacketLocation)iterator.next();

        while (iterator.hasNext()) {
            PacketLocation next2 = (PacketLocation)iterator.next();
            long diff3 = next2.getTimestamp() - diff2;
            if (diff3 > 0L) {
                list.add(next);
                if (diff3 - diff > value2 + this.connection) {
                    next = next2;
                    break;
                }
            } else {
                this.certainty = -diff3;
            }

            next = next2;
        }

        if (list.isEmpty()) {
            list.add(next);
        }

        this.certainty /= 50.0;
        return list;
    }

    public int getLastPing() {
        return this.lastPing;
    }

    public Transaction getTransaction() {
        return this.transaction;
    }

    private IntersectionData getReachRayTrace(PacketLocation location) {
        return this.playerData.getVersion() != ClientVersion.V1_8 && this.playerData.getVersion() != ClientVersion.VERSION_UNSUPPORTED
            ? this.getReachRayTrace(location, 3.0, this.newLast)
            : this.getReachRayTrace(location, 3.0, this.lastLocation, this.newLast);
    }

    private void raytraceInterpolate(List<PacketLocation> locations) {
        if (locations.isEmpty()) {
            this.intersecionList = Collections.emptyList();
        } else {
            this.newLast = this.lastLocation.clone();
            this.newLast.setPitch(this.location.getPitch());
            this.newLast.setYaw(this.location.getYaw());
            this.intersecionList = new ArrayList<>(locations.size() * 3 - 2);
            Iterator iterator = locations.iterator();
            PacketLocation next = (PacketLocation)iterator.next();
            this.intersecionList.add(this.getReachRayTrace(next));

            while (iterator.hasNext()) {
                PacketLocation location = next;
                next = (PacketLocation)iterator.next();

                for (int i = 0; i < 3; i++) {
                    this.intersecionList.add(this.getReachRayTrace(next.interpolateFrom(location, i + 1)));
                }
            }
        }
    }

    private DistanceData getReach(PacketLocation location, PlayerLocation location2, PlayerLocation location3) {
        Cuboid cuboid = location.hitbox().expand(0.03125, 0.0, 0.03125);
        double value = MathUtil.positiveSmaller(location2.getX() - cuboid.cX(), location3.getX() - cuboid.cX());
        double value2 = MathUtil.positiveSmaller(location2.getZ() - cuboid.cZ(), location3.getZ() - cuboid.cZ());
        double value3 = Math.min(cuboid.distanceXZ(location3), cuboid.distanceXZ(location2));
        double minimumAngle = MathUtil.getMinimumAngle(location, location2, location3);
        double minimumAngle2 = MathUtil.getMinimumAngle(location, location3);
        double value4 = 0.0;

        double value5;
        for (value5 = 0.0; minimumAngle > 90.0; value5 += value3) {
            minimumAngle -= 90.0;
            value4 += value3;
        }

        value4 += Math.abs(Math.sin(Math.toRadians(minimumAngle))) * value3;
        value5 += Math.abs(Math.sin(Math.toRadians(minimumAngle2))) * value3;
        double value6 = location2.getPitch() > 0.0F != location3.getPitch() > 0.0F ? 0.0 : MathUtil.lowestAbs(location2.getPitch(), location3.getPitch());
        double value7 = Math.max(
            Math.abs(Math.tan(Math.toRadians(Math.abs(value6 * (value3 > 0.8 ? 0.98 : 0.95))))) * value3,
            Math.abs(Math.sin(Math.toRadians(Math.abs(value6)))) * MathUtil.average(3, value3)
        );
        double diff = MathUtil.hypot(value3, value7, value4) - 0.001;
        return new DistanceData(cuboid, value, value2, value3, minimumAngle, value4, value7, diff, value5);
    }

    public PlayerLocation getNewLast() {
        return this.newLast;
    }

    public List<IntersectionData> getIntersecionList() {
        return this.intersecionList;
    }

    private void calculateLegacy(List<PacketLocation> locations) {
        this.distanceList = locations.stream().map(location -> this.getReach(location, this.location, this.lastLocation)).collect(Collectors.toList());
        this.distanceData = this.distanceList.stream().min(Comparator.comparingDouble(DistanceData::getReach)).get();
    }

    private void raytrace(List<PacketLocation> locations) {
        this.newLast = this.lastLocation.clone();
        this.newLast.setPitch(this.location.getPitch());
        this.newLast.setYaw(this.location.getYaw());
        this.intersecionList = locations.stream().map(this::getReachRayTrace).collect(Collectors.toList());
    }

    public List<PacketLocation> getRecentMoves() {
        return this.recentMoves;
    }
}
