package me.levansj01.verus.compat.v1_12_R1;

import io.netty.channel.Channel;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import me.levansj01.verus.compat.api.wrapper.PlayerAbilities;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.IBlockPosition;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_12_R1.AxisAlignedBB;
import net.minecraft.server.v1_12_R1.Block;
import net.minecraft.server.v1_12_R1.Blocks;
import net.minecraft.server.v1_12_R1.EntityLiving;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.GenericAttributes;
import net.minecraft.server.v1_12_R1.IBlockData;
import net.minecraft.server.v1_12_R1.ITickable;
import net.minecraft.server.v1_12_R1.MinecraftServer;
import net.minecraft.server.v1_12_R1.MovingObjectPosition;
import net.minecraft.server.v1_12_R1.PacketPlayOutBlockChange;
import net.minecraft.server.v1_12_R1.PacketPlayOutTransaction;
import net.minecraft.server.v1_12_R1.PlayerConnection;
import net.minecraft.server.v1_12_R1.Vec3D;
import net.minecraft.server.v1_12_R1.WorldServer;
import net.minecraft.server.v1_12_R1.BlockPosition.MutableBlockPosition;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftInventoryPlayer;
import org.bukkit.craftbukkit.v1_12_R1.util.CraftMagicNumbers;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class NMSManager extends me.levansj01.verus.compat.NMSManager<IBlockData> {
    private static final Field effects = SafeReflection.access(EntityLiving.class, "effects");
    private static final List<ITickable> list = SafeReflection.getLocalField(MinecraftServer.class, MinecraftServer.getServer(), new String[]{"o"});
    private final MutableBlockPosition blockPosition = new MutableBlockPosition();

    @Override
    public Cuboid getBoundingBox(Player player, World world, IBlockPosition block) {
        WorldServer worldServer = ((CraftWorld)world).getHandle();
        this.blockPosition.c(block.getX(), block.getY(), block.getZ());
        if (worldServer.isLoaded(this.blockPosition)) {
            IBlockData type = worldServer.getType(this.blockPosition);
            Block block2 = type.getBlock();
            AxisAlignedBB boundingBox = block2.b(type, worldServer, this.blockPosition);
            return new Cuboid(boundingBox.a, boundingBox.d, boundingBox.b, boundingBox.e, boundingBox.c, boundingBox.f);
        } else {
            return new Cuboid(block).add(new Cuboid(1.0, 1.0, 1.0));
        }
    }

    @Override
    public float getFrictionFactor(org.bukkit.block.Block block) {
        return CraftMagicNumbers.getBlock(block).frictionFactor;
    }

    @Override
    public void inject(PlayerData playerData) {
        Channel channel = ((CraftPlayer)playerData.getPlayer()).getHandle().playerConnection.networkManager.channel;
        if (channel != null) {
            this.getNettyHandler().inject(channel, new SPacketListener(playerData));
        }
    }

    @Override
    public float getDamage(Player player, World world, IBlockPosition block) {
        WorldServer worldServer = ((CraftWorld)world).getHandle();
        this.blockPosition.c(block.getX(), block.getY(), block.getZ());
        if (worldServer.isLoaded(this.blockPosition)) {
            EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();
            IBlockData type = worldServer.getType(this.blockPosition);
            return type.a(entityPlayer, worldServer, this.blockPosition);
        } else {
            return 0.0F;
        }
    }

    @Override
    public boolean isGliding(Player player) {
        return ((CraftPlayer)player).isGliding();
    }

    @Override
    public Material getType(Player player, World world, IBlockPosition block) {
        WorldServer worldServer = ((CraftWorld)world).getHandle();
        this.blockPosition.c(block.getX(), block.getY(), block.getZ());
        return worldServer.isLoaded(this.blockPosition) ? CraftMagicNumbers.getMaterial(worldServer.getType(this.blockPosition).getBlock()) : Material.AIR;
    }

    public static NMSManager getInstance() {
        return (NMSManager)me.levansj01.verus.compat.NMSManager.getInstance();
    }

    @Override
    public void postToMainThread(Runnable runnable) {
        MinecraftServer.getServer().postToMainThread(runnable);
    }

    @Override
    public boolean setInWater(Player player, boolean water) {
        EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();
        boolean inWater = entityPlayer.inWater;
        entityPlayer.inWater = water;
        return inWater;
    }

    public MaterialData toData(IBlockData data) {
        Block block = data.getBlock();
        return CraftMagicNumbers.getMaterial(block).getNewData((byte)block.toLegacyData(data));
    }

    @Override
    public void setAsyncPotionEffects(Player player) {
        EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();
        Map effects2 = entityPlayer.effects;
        if (effects2 instanceof HashMap) {
            SafeReflection.set(effects, entityPlayer, new ConcurrentHashMap(effects2));
        }
    }

    @Override
    public boolean isRunning() {
        return MinecraftServer.getServer().isRunning();
    }

    @Override
    protected ClientVersion defaultVersion() {
        return ClientVersion.V1_12;
    }

    @Override
    public void sendBlockUpdate(Player player, Location location) {
        this.sendBlockUpdate(player, location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    @Override
    public float getFrictionFactor(Player player, World world, IBlockPosition block) {
        WorldServer worldServer = ((CraftWorld)world).getHandle();
        this.blockPosition.c(block.getX(), block.getY(), block.getZ());
        return worldServer.isLoaded(this.blockPosition) ? worldServer.getType(this.blockPosition).getBlock().frictionFactor : Blocks.AIR.frictionFactor;
    }

    @Override
    public void sendTransaction(Player player, short id) {
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(new PacketPlayOutTransaction(0, id, false));
    }

    @Override
    public ItemStack getOffHand(Player player) {
        return ((CraftInventoryPlayer)player.getInventory()).getItemInOffHand();
    }

    @Override
    protected int versionNumber() {
        return 340;
    }

    @Override
    public void close(Player player) {
        Channel channel = ((CraftPlayer)player).getHandle().playerConnection.networkManager.channel;
        if (channel != null) {
            channel.close();
        }
    }

    @Override
    public MaterialData getTypeAndData(Player player, World world, IBlockPosition block) {
        WorldServer worldServer = ((CraftWorld)world).getHandle();
        this.blockPosition.c(block.getX(), block.getY(), block.getZ());
        if (worldServer.isLoaded(this.blockPosition)) {
            IBlockData type = worldServer.getType(this.blockPosition);
            return this.toData(type);
        } else {
            return new MaterialData(0);
        }
    }

    @Override
    public Runnable scheduleEnd(Runnable runnable) {
        ITickable tickable = runnable::run;
        list.add(tickable);
        return () -> list.remove(tickable);
    }

    @Override
    public boolean setOnGround(Player player, boolean ground) {
        EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();
        boolean onGround = entityPlayer.onGround;
        entityPlayer.onGround = ground;
        return onGround;
    }

    @Override
    public void sendBlockUpdate(Player player, World world, int x, int y, int z) {
        WorldServer worldServer = ((CraftWorld)world).getHandle();
        this.blockPosition.c(x, y, z);
        if (worldServer.isLoaded(this.blockPosition)) {
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(new PacketPlayOutBlockChange(worldServer, this.blockPosition));
        }
    }

    @Override
    public boolean isEmpty(World world, Entity entity, Cuboid boundingBox, IBlockPosition block) {
        WorldServer worldServer = ((CraftWorld)world).getHandle();
        this.blockPosition.c(block.getX(), block.getY(), block.getZ());
        if (worldServer.isLoaded(this.blockPosition)) {
            IBlockData type = worldServer.getType(this.blockPosition);
            AxisAlignedBB boundingBox2 = new AxisAlignedBB(boundingBox.getX1(), boundingBox.getY1(), boundingBox.getZ1(), boundingBox.getX2(), boundingBox.getY2(), boundingBox.getZ2());
            ArrayList list = new ArrayList(1);
            type.getBlock().a(type, worldServer, this.blockPosition, boundingBox2, list, ((CraftEntity)entity).getHandle(), false);
            return list.isEmpty();
        } else {
            return true;
        }
    }

    @Override
    public int getCurrentTick() {
        return MinecraftServer.currentTick;
    }

    @Override
    public double getKnockbackResistance(Player player) {
        return ((CraftPlayer)player).getHandle().getAttributeInstance(GenericAttributes.c).getValue();
    }

    @Override
    public double getMovementSpeed(Player player) {
        return ((CraftPlayer)player).getHandle().getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).getValue();
    }

    @Override
    public boolean rayTrace(
        World world, double x, double y, double z, double dX, double dY, double dZ, boolean b1, boolean b2, boolean b3
    ) {
        WorldServer worldServer = ((CraftWorld)world).getHandle();
        MovingObjectPosition movingObjectPosition = worldServer.rayTrace(new Vec3D(x, y, z), new Vec3D(dX, dY, dZ), b1, b2, b3);
        if (movingObjectPosition == null) {
            return false;
        }

        IBlockData type = worldServer.getType(movingObjectPosition.a());
        return type.getBlock() != Blocks.END_PORTAL;
    }

    @Override
    public PlayerAbilities getAbilities(Player player) {
        net.minecraft.server.v1_12_R1.PlayerAbilities abilities = ((CraftPlayer)player).getHandle().abilities;
        return new PlayerAbilities(abilities.isInvulnerable, abilities.isFlying, abilities.canFly, abilities.canInstantlyBuild, abilities.mayBuild, abilities.a(), abilities.b());
    }

    @Override
    public void updatePing(Player player, int ping) {
        ((CraftPlayer)player).getHandle().ping = ping;
    }

    @Override
    public boolean isLoaded(World world, int x, int z) {
        WorldServer worldServer = ((CraftWorld)world).getHandle();
        return worldServer.getChunkProviderServer().isLoaded(x >> 4, z >> 4);
    }

    @Override
    public void uninject(PlayerData playerData) {
        PlayerConnection playerConnection = ((CraftPlayer)playerData.getPlayer()).getHandle().playerConnection;
        if (playerConnection != null && !playerConnection.isDisconnected()) {
            this.getNettyHandler().uninject(playerConnection.networkManager.channel);
        }
    }
}
