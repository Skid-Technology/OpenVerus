package me.levansj01.verus.compat.v1_16_R3;

import io.netty.channel.Channel;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import me.levansj01.verus.compat.api.wrapper.PlayerAbilities;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.IBlockPosition;
import me.levansj01.verus.util.item.MaterialList;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_16_R3.AxisAlignedBB;
import net.minecraft.server.v1_16_R3.Block;
import net.minecraft.server.v1_16_R3.Blocks;
import net.minecraft.server.v1_16_R3.CommandDispatcher;
import net.minecraft.server.v1_16_R3.DedicatedServer;
import net.minecraft.server.v1_16_R3.Entity;
import net.minecraft.server.v1_16_R3.EntityLiving;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.GenericAttributes;
import net.minecraft.server.v1_16_R3.IBlockData;
import net.minecraft.server.v1_16_R3.MinecraftServer;
import net.minecraft.server.v1_16_R3.PacketPlayOutBlockChange;
import net.minecraft.server.v1_16_R3.PacketPlayOutTransaction;
import net.minecraft.server.v1_16_R3.PlayerConnection;
import net.minecraft.server.v1_16_R3.VoxelShapeCollision;
import net.minecraft.server.v1_16_R3.WorldServer;
import net.minecraft.server.v1_16_R3.BlockPosition.MutableBlockPosition;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.command.Command;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_16_R3.command.BukkitCommandWrapper;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftInventoryPlayer;
import org.bukkit.craftbukkit.v1_16_R3.util.CraftMagicNumbers;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class NMSManager extends me.levansj01.verus.compat.NMSManager<IBlockData> {
    private static final Field ground_field = SafeReflection.access(Entity.class, "onGround");
    private static final Field effects = SafeReflection.access(EntityLiving.class, "effects");
    private static final List<Runnable> list = SafeReflection.getLocalField(MinecraftServer.class, MinecraftServer.getServer(), new String[]{"tickables"});
    private final Map<Block, MaterialData> blockToMaterial;
    private final Map<IBlockData, MaterialData> dataToMaterial;
    private final String[] CLASSES = new String[]{"org.bukkit.craftbukkit.v1_16_R3.util.CraftLegacy", "org.bukkit.craftbukkit.v1_16_R3.legacy.CraftLegacy"};
    private final MutableBlockPosition blockPosition;

    @Override
    public boolean setInWater(Player player, boolean water) {
        EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();
        boolean inWater = entityPlayer.inWater;
        entityPlayer.inWater = water;
        return inWater;
    }

    @Override
    public Runnable scheduleEnd(Runnable runnable) {
        list.add(runnable);
        return () -> list.remove(runnable);
    }

    @Override
    public void sendTransaction(Player player, short id) {
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(new PacketPlayOutTransaction(0, id, false));
    }

    @Override
    public float getJumpFactor(Player player, World world, IBlockPosition block) {
        WorldServer worldServer = ((CraftWorld)world).getHandle();
        this.blockPosition.d(block.getX(), block.getY(), block.getZ());
        return worldServer.isLoaded(this.blockPosition) ? worldServer.getType(this.blockPosition).getBlock().getJumpFactor() : Blocks.AIR.getJumpFactor();
    }

    @Override
    public Cuboid getBoundingBox(Player player, World world, IBlockPosition block) {
        WorldServer worldServer = ((CraftWorld)world).getHandle();
        this.blockPosition.d(block.getX(), block.getY(), block.getZ());
        if (worldServer.isLoaded(this.blockPosition)) {
            IBlockData type = worldServer.getType(this.blockPosition);
            Block block2 = type.getBlock();
            AxisAlignedBB boundingBox = block2.b(type, worldServer, this.blockPosition, VoxelShapeCollision.a()).getBoundingBox();
            return new Cuboid(boundingBox.minX, boundingBox.maxX, boundingBox.minY, boundingBox.maxY, boundingBox.minZ, boundingBox.maxZ);
        } else {
            return new Cuboid(block).add(0.0, 1.0, 0.0, 1.0, 0.0, 1.0);
        }
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
    public void close(Player player) {
        Channel channel = ((CraftPlayer)player).getHandle().playerConnection.networkManager.channel;
        if (channel != null) {
            channel.close();
        }
    }

    @Override
    public void sendBlockUpdate(Player player, World world, int x, int y, int z) {
        WorldServer worldServer = ((CraftWorld)world).getHandle();
        this.blockPosition.d(x, y, z);
        if (worldServer.isChunkLoaded(x >> 4, z >> 4)) {
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(new PacketPlayOutBlockChange(worldServer, this.blockPosition));
        }
    }

    @Override
    public float getFrictionFactor(Player player, World world, IBlockPosition block) {
        WorldServer worldServer = ((CraftWorld)world).getHandle();
        this.blockPosition.d(block.getX(), block.getY(), block.getZ());
        return worldServer.isLoaded(this.blockPosition) ? worldServer.getType(this.blockPosition).getBlock().getFrictionFactor() : Blocks.AIR.getFrictionFactor();
    }

    @Override
    public Material getType(Player player, World world, IBlockPosition block) {
        WorldServer worldServer = ((CraftWorld)world).getHandle();
        this.blockPosition.d(block.getX(), block.getY(), block.getZ());
        if (worldServer.isLoaded(this.blockPosition)) {
            IBlockData type = worldServer.getType(this.blockPosition);
            MaterialData materialData = this.dataToMaterial.get(type);
            if (materialData == null) {
                materialData = this.blockToMaterial.get(type.getBlock());
                if (materialData == null) {
                    Material material = CraftMagicNumbers.getMaterial(type.getBlock());
                    if (material == Material.AIR) {
                        material = MaterialList.AIR;
                    }

                    return material;
                }
            }

            return materialData.getItemType();
        } else {
            return MaterialList.AIR;
        }
    }

    @Override
    public boolean isRiptiding(Player player) {
        return ((CraftPlayer)player).isRiptiding();
    }

    @Override
    public void uninject(PlayerData playerData) {
        PlayerConnection playerConnection = ((CraftPlayer)playerData.getPlayer()).getHandle().playerConnection;
        if (playerConnection != null && !playerConnection.isDisconnected()) {
            this.getNettyHandler().uninject(playerConnection.networkManager.channel);
        }
    }

    @Override
    public boolean setOnGround(Player player, boolean ground) {
        EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();
        boolean onGround = entityPlayer.isOnGround();
        SafeReflection.set(ground_field, entityPlayer, onGround);
        return onGround;
    }

    public static NMSManager getInstance() {
        return (NMSManager)me.levansj01.verus.compat.NMSManager.getInstance();
    }

    @Override
    public void updatePing(Player player, int ping) {
        ((CraftPlayer)player).getHandle().ping = ping;
    }

    @Override
    public boolean isGliding(Player player) {
        return ((CraftPlayer)player).isGliding();
    }

    @Override
    public ItemStack getOffHand(Player player) {
        return ((CraftInventoryPlayer)player.getInventory()).getItemInOffHand();
    }

    @Override
    public PlayerAbilities getAbilities(Player player) {
        net.minecraft.server.v1_16_R3.PlayerAbilities abilities = ((CraftPlayer)player).getHandle().abilities;
        return new PlayerAbilities(abilities.isInvulnerable, abilities.isFlying, abilities.canFly, abilities.canInstantlyBuild, abilities.mayBuild, abilities.a(), abilities.b());
    }

    @Override
    public MaterialData getTypeAndData(Player player, World world, IBlockPosition block) {
        WorldServer worldServer = ((CraftWorld)world).getHandle();
        this.blockPosition.e(block.getX(), block.getY(), block.getZ());
        if (worldServer.isLoaded(this.blockPosition)) {
            IBlockData type = worldServer.getType(this.blockPosition);
            return this.toData(type);
        } else {
            return MaterialList.AIR_DATA;
        }
    }

    @Override
    public double getMovementSpeed(Player player) {
        return ((CraftPlayer)player).getHandle().getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).getValue();
    }

    @Override
    public int getCurrentTick() {
        return MinecraftServer.currentTick;
    }

    @Override
    public double getKnockbackResistance(Player player) {
        return ((CraftPlayer)player).getHandle().getAttributeInstance(GenericAttributes.KNOCKBACK_RESISTANCE).getValue();
    }

    @Override
    public boolean isWaterLogged(World world, IBlockPosition blockPosition) {
        WorldServer worldServer = ((CraftWorld)world).getHandle();
        this.blockPosition.d(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ());
        if (worldServer.isLoaded(this.blockPosition)) {
            org.bukkit.block.Block block = blockPosition.getBlock(world);
            BlockData blockData = ((CraftBlock)block).getBlockData();
            if (blockData instanceof Waterlogged) {
                Waterlogged waterlogged = (Waterlogged)blockData;
                return waterlogged.isWaterlogged();
            }
        }

        return false;
    }

    @Override
    public void postToMainThread(Runnable runnable) {
        MinecraftServer server = MinecraftServer.getServer();
        if (!server.isStopped()) {
            server.execute(runnable);
        }
    }

    @Override
    public float getFrictionFactor(org.bukkit.block.Block block) {
        return CraftMagicNumbers.getBlock(block.getType()).getFrictionFactor();
    }

    @Override
    public void inject(PlayerData playerData) {
        Channel channel = ((CraftPlayer)playerData.getPlayer()).getHandle().playerConnection.networkManager.channel;
        if (channel != null) {
            this.getNettyHandler().inject(channel, new SPacketListener(playerData));
        }
    }

    @Override
    protected int versionNumber() {
        return 754;
    }

    @Override
    public float getDamage(Player player, World world, IBlockPosition block) {
        WorldServer worldServer = ((CraftWorld)world).getHandle();
        this.blockPosition.d(block.getX(), block.getY(), block.getZ());
        if (worldServer.isLoaded(this.blockPosition)) {
            EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();
            IBlockData type = worldServer.getType(this.blockPosition);
            return type.getDamage(entityPlayer, worldServer, this.blockPosition);
        } else {
            return 0.0F;
        }
    }

    @Override
    public boolean isRunning() {
        return MinecraftServer.getServer().isRunning();
    }

    public NMSManager() {
        this.dataToMaterial = SafeReflection.getLocalField(this.CLASSES, null, "dataToMaterial");
        this.blockToMaterial = SafeReflection.getLocalField(this.CLASSES, null, "blockToMaterial");
        this.blockPosition = new MutableBlockPosition();
    }

    @Override
    public void sendBlockUpdate(Player player, Location location) {
        this.sendBlockUpdate(player, location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public MaterialData toData(IBlockData data) {
        MaterialData materialData = this.dataToMaterial.get(data);
        if (materialData == null) {
            materialData = this.blockToMaterial.get(data.getBlock());
            if (materialData == null) {
                Material material = CraftMagicNumbers.getMaterial(data.getBlock());
                if (material == Material.AIR) {
                    material = MaterialList.AIR;
                }

                materialData = new MaterialData(material);
            }
        }

        return materialData;
    }

    @Override
    protected ClientVersion defaultVersion() {
        return ClientVersion.V1_16;
    }

    @Override
    public boolean isLoaded(World world, int x, int z) {
        WorldServer worldServer = ((CraftWorld)world).getHandle();
        return worldServer.getChunkProvider().isLoaded(x >> 4, z >> 4);
    }

    @Override
    public void syncCommands(List<Command> commands) {
        DedicatedServer server = (DedicatedServer)MinecraftServer.getServer();
        CommandDispatcher commandDispatcher = server.dataPackResources.commandDispatcher;
        commands.forEach(command -> new BukkitCommandWrapper((CraftServer)Bukkit.getServer(), command).register(commandDispatcher.a(), command.getLabel()));

        for (Player player : Bukkit.getOnlinePlayers()) {
            commandDispatcher.a(((CraftPlayer)player).getHandle());
        }
    }
}
