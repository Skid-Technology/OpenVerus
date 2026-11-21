package me.levansj01.verus.compat.v1_7_R4;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import me.levansj01.verus.compat.api.wrapper.PlayerAbilities;
import me.levansj01.verus.compat.netty.NettyHandler;
import me.levansj01.verus.compat.v1_7_R4.trove.LongObjectMapWrapper;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.IBlockPosition;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_7_R4.AxisAlignedBB;
import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.Blocks;
import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.GenericAttributes;
import net.minecraft.server.v1_7_R4.IUpdatePlayerListBox;
import net.minecraft.server.v1_7_R4.MinecraftServer;
import net.minecraft.server.v1_7_R4.MovingObjectPosition;
import net.minecraft.server.v1_7_R4.NetworkManager;
import net.minecraft.server.v1_7_R4.PacketPlayOutBlockChange;
import net.minecraft.server.v1_7_R4.PacketPlayOutTransaction;
import net.minecraft.server.v1_7_R4.PlayerConnection;
import net.minecraft.server.v1_7_R4.Vec3D;
import net.minecraft.server.v1_7_R4.WorldServer;
import net.minecraft.util.com.google.common.cache.CacheBuilder;
import net.minecraft.util.gnu.trove.map.hash.TLongObjectHashMap;
import net.minecraft.util.io.netty.channel.Channel;
import net.minecraft.util.org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_7_R4.util.CraftMagicNumbers;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.PluginManager;

public class NMSManager extends me.levansj01.verus.compat.NMSManager<Pair<Block, Integer>> {
    private static final Field effects = SafeReflection.access(EntityLiving.class, "effects");
    private static final Field channel_field = SafeReflection.access(NetworkManager.class, "m", "Channel", "channel");
    private static final List<IUpdatePlayerListBox> list = SafeReflection.getLocalField(MinecraftServer.class, MinecraftServer.getServer(), new String[]{"n"});

    @Override
    public Runnable scheduleEnd(Runnable runnable) {
        IUpdatePlayerListBox updatePlayerListBox = runnable::run;
        list.add(updatePlayerListBox);
        return () -> list.remove(updatePlayerListBox);
    }

    @Override
    public void sendBlockUpdate(Player player, Location location) {
        this.sendBlockUpdate(player, location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    @Override
    public boolean isEmpty(World world, Entity entity, Cuboid boundingBox, IBlockPosition block) {
        WorldServer worldServer = ((CraftWorld)world).getHandle();
        if (worldServer.isLoaded(block.getX(), block.getY(), block.getZ())) {
            Block type = worldServer.getType(block.getX(), block.getY(), block.getZ());
            AxisAlignedBB boundingBox2 = AxisAlignedBB.a(boundingBox.getX1(), boundingBox.getY1(), boundingBox.getZ1(), boundingBox.getX2(), boundingBox.getY2(), boundingBox.getZ2());
            ArrayList list = new ArrayList(1);
            type.a(worldServer, block.getX(), block.getY(), block.getZ(), boundingBox2, list, ((CraftEntity)entity).getHandle());
            return list.isEmpty();
        } else {
            return true;
        }
    }

    @Override
    public <V> Map<Long, V> long2ObjectHashMap(int initialCapacity, float loadFactor) {
        return new LongObjectMapWrapper(new TLongObjectHashMap(initialCapacity, loadFactor));
    }

    @Override
    public double getKnockbackResistance(Player player) {
        return ((CraftPlayer)player).getHandle().getAttributeInstance(GenericAttributes.c).getValue();
    }

    @Override
    public ClientVersion getVersion(Player player) {
        ClientVersion clientVersion;
        switch (this.getConnection(player).networkManager.getVersion()) {
            case 4:
            case 5:
                clientVersion = ClientVersion.V1_7;
                break;
            case 47:
                clientVersion = ClientVersion.V1_8;
                break;
            default:
                clientVersion = ClientVersion.VERSION_UNSUPPORTED;
        }

        return clientVersion;
    }

    @Override
    public boolean rayTrace(
        World world, double x, double y, double z, double dX, double dY, double dZ, boolean b1, boolean b2, boolean b3
    ) {
        WorldServer worldServer = ((CraftWorld)world).getHandle();
        MovingObjectPosition movingObjectPosition = worldServer.rayTrace(Vec3D.a(x, y, z), Vec3D.a(dX, dY, dZ), b1, b2, b3);
        if (movingObjectPosition == null) {
            return false;
        }

        Block type = worldServer.getType(movingObjectPosition.b, movingObjectPosition.c, movingObjectPosition.d);
        return type != Blocks.ENDER_PORTAL;
    }

    @Override
    public boolean isLoaded(World world, int x, int z) {
        WorldServer worldServer = ((CraftWorld)world).getHandle();
        return worldServer.chunkProvider.isChunkLoaded(x >> 4, z >> 4);
    }

    @Override
    public void updatePing(Player player, int ping) {
        ((CraftPlayer)player).getHandle().ping = ping;
    }

    @Override
    public <K, V> Map<K, V> createCache(Long access, Long write) {
        CacheBuilder builder = CacheBuilder.newBuilder();
        if (access != null) {
            builder.expireAfterAccess(access, TimeUnit.MILLISECONDS);
        }

        if (write != null) {
            builder.expireAfterWrite(write, TimeUnit.MILLISECONDS);
        }

        return builder.build().asMap();
    }

    @Override
    public int getCurrentTick() {
        return MinecraftServer.currentTick;
    }

    @Override
    public void uninject(PlayerData playerData) {
        PlayerConnection playerConnection = ((CraftPlayer)playerData.getPlayer()).getHandle().playerConnection;
        if (playerConnection != null && !playerConnection.isDisconnected()) {
            this.getNettyHandler().uninject(this.getChannel(playerData.getPlayer()));
        }
    }

    public NMSManager() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        this.lunarClientAPI = pluginManager.isPluginEnabled("LunarClient-API");
        this.floodGateAPI = pluginManager.isPluginEnabled("floodgate");
    }

    @Override
    protected NettyHandler<?> newNettyHandler() {
        return new ShadedNettyHandler();
    }

    @Override
    public void close(Player player) {
        Channel channel = this.getChannel(player);
        if (channel != null) {
            channel.close();
        }
    }

    @Override
    public void setAsyncPotionEffects(Player player) {
        EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();
        Map map = SafeReflection.fetch(effects, entityPlayer);
        if (map.getClass() == HashMap.class) {
            SafeReflection.set(effects, entityPlayer, new WrappedHashMap(new ConcurrentHashMap(map)));
        }
    }

    @Override
    public boolean setInWater(Player player, boolean water) {
        EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();
        boolean inWater = entityPlayer.inWater;
        entityPlayer.inWater = water;
        return inWater;
    }

    public PlayerConnection getConnection(Player player) {
        return ((CraftPlayer)player).getHandle().playerConnection;
    }

    @Override
    public float getDamage(Player player, World world, IBlockPosition block) {
        WorldServer worldServer = ((CraftWorld)world).getHandle();
        if (worldServer.isLoaded(block.getX(), block.getY(), block.getZ())) {
            EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();
            Block type = worldServer.getType(block.getX(), block.getY(), block.getZ());
            return type.getDamage(entityPlayer, worldServer, block.getX(), block.getY(), block.getZ());
        } else {
            return 0.0F;
        }
    }

    public MaterialData toData(Pair<Block, Integer> data) {
        Block left = (Block)data.getLeft();
        int right = (Integer)data.getRight();
        return CraftMagicNumbers.getMaterial(left).getNewData((byte)right);
    }

    @Override
    public Material getType(Player player, World world, IBlockPosition block) {
        WorldServer worldServer = ((CraftWorld)world).getHandle();
        return worldServer.isLoaded(block.getX(), block.getY(), block.getZ())
            ? CraftMagicNumbers.getMaterial(worldServer.getType(block.getX(), block.getY(), block.getZ()))
            : Material.AIR;
    }

    @Override
    public MaterialData getTypeAndData(Player player, World world, IBlockPosition block) {
        WorldServer worldServer = ((CraftWorld)world).getHandle();
        if (worldServer.isLoaded(block.getX(), block.getY(), block.getZ())) {
            Block type = worldServer.getType(block.getX(), block.getY(), block.getZ());
            int data = worldServer.getData(block.getX(), block.getY(), block.getZ());
            return CraftMagicNumbers.getMaterial(type).getNewData((byte)data);
        } else {
            return new MaterialData(0);
        }
    }

    @Override
    public PlayerAbilities getAbilities(Player player) {
        net.minecraft.server.v1_7_R4.PlayerAbilities abilities = ((CraftPlayer)player).getHandle().abilities;
        return new PlayerAbilities(abilities.isInvulnerable, abilities.isFlying, abilities.canFly, abilities.canInstantlyBuild, abilities.mayBuild, abilities.a(), abilities.b());
    }

    @Override
    public void inject(PlayerData playerData) {
        Channel channel = this.getChannel(playerData.getPlayer());
        if (channel != null) {
            this.getNettyHandler().inject(channel, new SPacketListener(playerData));
        }
    }

    @Override
    public float getFrictionFactor(Player player, World world, IBlockPosition block) {
        WorldServer worldServer = ((CraftWorld)world).getHandle();
        return worldServer.isLoaded(block.getX(), block.getY(), block.getZ())
            ? worldServer.getType(block.getX(), block.getY(), block.getZ()).frictionFactor
            : Blocks.AIR.frictionFactor;
    }

    @Override
    public void sendTransaction(Player player, short id) {
        this.getConnection(player).sendPacket(new PacketPlayOutTransaction(0, id, false));
    }

    @Override
    public void sendBlockUpdate(Player player, World world, int x, int y, int z) {
        WorldServer worldServer = ((CraftWorld)world).getHandle();
        if (worldServer.isLoaded(x, y, z)) {
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(new PacketPlayOutBlockChange(x, y, z, worldServer));
        }
    }

    @Override
    public Cuboid getBoundingBox(Player player, World world, IBlockPosition block) {
        WorldServer worldServer = ((CraftWorld)world).getHandle();
        if (worldServer != null && worldServer.isLoaded(block.getX(), block.getY(), block.getZ())) {
            Block type = worldServer.getType(block.getX(), block.getY(), block.getZ());
            if (type != null) {
                AxisAlignedBB boundingBox = type.a(worldServer, block.getX(), block.getY(), block.getZ());
                if (boundingBox != null) {
                    return new Cuboid(boundingBox.a, boundingBox.d, boundingBox.b, boundingBox.e, boundingBox.c, boundingBox.f);
                }
            }
        }

        return new Cuboid(block).add(new Cuboid(1.0, 1.0, 1.0));
    }

    @Override
    protected int versionNumber() {
        return 5;
    }

    @Override
    protected ClientVersion defaultVersion() {
        return ClientVersion.V1_7;
    }

    @Override
    public double getMovementSpeed(Player player) {
        return ((CraftPlayer)player).getHandle().getAttributeInstance(GenericAttributes.d).getValue();
    }

    public Channel getChannel(Player player) {
        return SafeReflection.fetch(channel_field, ((CraftPlayer)player).getHandle().playerConnection.networkManager);
    }

    @Override
    public boolean setOnGround(Player player, boolean ground) {
        EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();
        boolean onGround = entityPlayer.onGround;
        entityPlayer.onGround = ground;
        return onGround;
    }

    @Override
    public boolean isRunning() {
        return MinecraftServer.getServer().isRunning();
    }

    @Override
    public float getFrictionFactor(org.bukkit.block.Block block) {
        return CraftMagicNumbers.getBlock(block).frictionFactor;
    }

    @Override
    public void postToMainThread(Runnable runnable) {
        MinecraftServer.getServer().processQueue.add(runnable);
    }
}
