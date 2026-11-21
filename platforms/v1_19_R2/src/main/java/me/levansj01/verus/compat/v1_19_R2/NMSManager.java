package me.levansj01.verus.compat.v1_19_R2;

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
import net.minecraft.commands.CommandDispatcher;
import net.minecraft.core.BlockPosition.MutableBlockPosition;
import net.minecraft.network.protocol.game.ClientboundPingPacket;
import net.minecraft.network.protocol.game.PacketPlayOutBlockChange;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.command.Command;
import org.bukkit.craftbukkit.v1_19_R2.CraftServer;
import org.bukkit.craftbukkit.v1_19_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R2.block.CraftBlock;
import org.bukkit.craftbukkit.v1_19_R2.command.BukkitCommandWrapper;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R2.inventory.CraftInventoryPlayer;
import org.bukkit.craftbukkit.v1_19_R2.util.CraftMagicNumbers;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class NMSManager extends me.levansj01.verus.compat.NMSManager<IBlockData> {
    private static final Field effects = SafeReflection.access(EntityLiving.class, "bS");
    private static final List<Runnable> list = SafeReflection.getLocalField(MinecraftServer.class, MinecraftServer.getServer(), "y", "tickables");
    private final MutableBlockPosition blockPosition;
    private final String[] CLASSES = new String[]{"org.bukkit.craftbukkit.v1_19_R2.util.CraftLegacy", "org.bukkit.craftbukkit.v1_19_R2.legacy.CraftLegacy"};
    private final Map<Block, MaterialData> blockToMaterial;
    private final Map<IBlockData, MaterialData> dataToMaterial = SafeReflection.getLocalField(this.CLASSES, null, "dataToMaterial");

    @Override
    public int getMinYLevel() {
        return -64;
    }

    @Override
    public double getKnockbackResistance(Player player) {
        return ((CraftPlayer)player).getHandle().a(GenericAttributes.c).b();
    }

    @Override
    public boolean isGliding(Player player) {
        return ((CraftPlayer)player).isGliding();
    }

    @Override
    public Material getType(Player player, World world, IBlockPosition block) {
        WorldServer worldServer = ((CraftWorld)world).getHandle();
        this.blockPosition.d(block.getX(), block.getY(), block.getZ());
        if (worldServer.E(this.blockPosition)) {
            IBlockData blockData = worldServer.a_(this.blockPosition);
            MaterialData materialData = this.dataToMaterial.get(blockData);
            if (materialData == null) {
                materialData = this.blockToMaterial.get(blockData.b());
                if (materialData == null) {
                    Material material = CraftMagicNumbers.getMaterial(blockData.b());
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

    public MaterialData toData(IBlockData data) {
        MaterialData materialData = this.dataToMaterial.get(data);
        if (materialData == null) {
            materialData = this.blockToMaterial.get(data.b());
            if (materialData == null) {
                Material material = CraftMagicNumbers.getMaterial(data.b());
                if (material == Material.AIR) {
                    material = MaterialList.AIR;
                }

                materialData = new MaterialData(material);
            }
        }

        return materialData;
    }

    @Override
    public boolean setOnGround(Player player, boolean ground) {
        EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();
        entityPlayer.c(ground);
        return ground;
    }

    @Override
    public float getJumpFactor(Player player, World world, IBlockPosition block) {
        WorldServer worldServer = ((CraftWorld)world).getHandle();
        this.blockPosition.d(block.getX(), block.getY(), block.getZ());
        return worldServer.E(this.blockPosition) ? worldServer.a_(this.blockPosition).b().j() : Blocks.a.j();
    }

    @Override
    public void syncCommands(List<Command> commands) {
        CommandDispatcher commandDispatcher = MinecraftServer.getServer().at.b().d;
        com.mojang.brigadier.CommandDispatcher commandDispatcher2 = commandDispatcher.a();
        commands.forEach(command -> new BukkitCommandWrapper((CraftServer)Bukkit.getServer(), command).register(commandDispatcher2, command.getLabel()));

        for (Player player : Bukkit.getOnlinePlayers()) {
            commandDispatcher.a(((CraftPlayer)player).getHandle());
        }
    }

    @Override
    public void sendBlockUpdate(Player player, World world, int x, int y, int z) {
        WorldServer worldServer = ((CraftWorld)world).getHandle();
        this.blockPosition.d(x, y, z);
        if (worldServer.b(x >> 4, z >> 4)) {
            ((CraftPlayer)player).getHandle().b.a(new PacketPlayOutBlockChange(worldServer, this.blockPosition));
        }
    }

    @Override
    public MaterialData getTypeAndData(Player player, World world, IBlockPosition block) {
        WorldServer worldServer = ((CraftWorld)world).getHandle();
        this.blockPosition.e(block.getX(), block.getY(), block.getZ());
        if (worldServer.E(this.blockPosition)) {
            IBlockData blockData = worldServer.a_(this.blockPosition);
            return this.toData(blockData);
        } else {
            return MaterialList.AIR_DATA;
        }
    }

    @Override
    public Runnable scheduleEnd(Runnable runnable) {
        list.add(runnable);
        return () -> list.remove(runnable);
    }

    @Override
    public ItemStack getOffHand(Player player) {
        return ((CraftInventoryPlayer)player.getInventory()).getItemInOffHand();
    }

    @Override
    public PlayerAbilities getAbilities(Player player) {
        net.minecraft.world.entity.player.PlayerAbilities abilities = ((CraftPlayer)player).getHandle().fF();
        return new PlayerAbilities(abilities.a, abilities.b, abilities.c, abilities.d, abilities.e, abilities.a(), abilities.b());
    }

    @Override
    public void sendTransaction(Player player, short id) {
        ((CraftPlayer)player).getHandle().b.a(new ClientboundPingPacket(id));
    }

    @Override
    public void updatePing(Player player, int ping) {
        ((CraftPlayer)player).getHandle().e = ping;
    }

    @Override
    protected int versionNumber() {
        return 759;
    }

    @Override
    public boolean isRunning() {
        return MinecraftServer.getServer().v();
    }

    @Override
    public void setAsyncPotionEffects(Player player) {
        EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();
        Map map = SafeReflection.fetch(effects, entityPlayer);
        if (map instanceof HashMap) {
            SafeReflection.set(effects, entityPlayer, new ConcurrentHashMap(map));
        }
    }

    @Override
    public float getFrictionFactor(org.bukkit.block.Block block) {
        return CraftMagicNumbers.getBlock(block.getType()).h();
    }

    public NMSManager() {
        this.blockToMaterial = SafeReflection.getLocalField(this.CLASSES, null, "blockToMaterial");
        this.blockPosition = new MutableBlockPosition();
    }

    @Override
    public boolean isLoaded(World world, int x, int z) {
        WorldServer worldServer = ((CraftWorld)world).getHandle();
        return worldServer.k().b(x >> 4, z >> 4);
    }

    @Override
    protected ClientVersion defaultVersion() {
        return ClientVersion.V1_19;
    }

    @Override
    public Cuboid getBoundingBox(Player player, World world, IBlockPosition block) {
        WorldServer worldServer = ((CraftWorld)world).getHandle();
        this.blockPosition.d(block.getX(), block.getY(), block.getZ());
        if (worldServer.E(this.blockPosition)) {
            IBlockData blockData = worldServer.a_(this.blockPosition);
            Block block2 = blockData.b();
            AxisAlignedBB boundingBox = block2.b(blockData, worldServer, this.blockPosition, VoxelShapeCollision.a()).a();
            return new Cuboid(boundingBox.a, boundingBox.d, boundingBox.b, boundingBox.e, boundingBox.c, boundingBox.f);
        } else {
            return new Cuboid(block).add(0.0, 1.0, 0.0, 1.0, 0.0, 1.0);
        }
    }

    @Override
    public int getCurrentTick() {
        return MinecraftServer.currentTick;
    }

    @Override
    public boolean setInWater(Player player, boolean water) {
        EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();
        boolean flag = entityPlayer.T;
        entityPlayer.T = water;
        return flag;
    }

    @Override
    public boolean isWaterLogged(World world, IBlockPosition blockPosition) {
        WorldServer worldServer = ((CraftWorld)world).getHandle();
        this.blockPosition.d(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ());
        if (worldServer.E(this.blockPosition)) {
            org.bukkit.block.Block block = blockPosition.getBlock(world);
            BlockData blockData = ((CraftBlock)block).getBlockData();
            if (blockData instanceof Waterlogged) {
                Waterlogged waterlogged = (Waterlogged)blockData;
                return waterlogged.isWaterlogged();
            }
        }

        return false;
    }

    public static NMSManager getInstance() {
        return (NMSManager)me.levansj01.verus.compat.NMSManager.getInstance();
    }

    @Override
    public double getMovementSpeed(Player player) {
        return ((CraftPlayer)player).getHandle().a(GenericAttributes.d).b();
    }

    @Override
    public float getFrictionFactor(Player player, World world, IBlockPosition block) {
        WorldServer worldServer = ((CraftWorld)world).getHandle();
        this.blockPosition.d(block.getX(), block.getY(), block.getZ());
        return worldServer.E(this.blockPosition) ? worldServer.a_(this.blockPosition).b().h() : Blocks.a.h();
    }

    @Override
    public void uninject(PlayerData playerData) {
        PlayerConnection connection = ((CraftPlayer)playerData.getPlayer()).getHandle().b;
        if (connection != null && !connection.isDisconnected()) {
            this.getNettyHandler().uninject(connection.b.m);
        }
    }

    @Override
    public void close(Player player) {
        Channel channel = ((CraftPlayer)player).getHandle().b.b.m;
        if (channel != null) {
            channel.close();
        }
    }

    @Override
    public void sendBlockUpdate(Player player, Location location) {
        this.sendBlockUpdate(player, location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    @Override
    public void postToMainThread(Runnable runnable) {
        MinecraftServer server = MinecraftServer.getServer();
        if (!server.aa()) {
            server.execute(runnable);
        }
    }

    @Override
    public void inject(PlayerData playerData) {
        Channel channel = ((CraftPlayer)playerData.getPlayer()).getHandle().b.b.m;
        if (channel != null) {
            this.getNettyHandler().inject(channel, new SPacketListener(playerData));
        }
    }

    @Override
    public float getDamage(Player player, World world, IBlockPosition block) {
        WorldServer worldServer = ((CraftWorld)world).getHandle();
        this.blockPosition.e(block.getX(), block.getY(), block.getZ());
        if (worldServer.E(this.blockPosition)) {
            EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();
            IBlockData blockData = worldServer.a_(this.blockPosition);
            return blockData.a(entityPlayer, worldServer, this.blockPosition);
        } else {
            return 0.0F;
        }
    }

    @Override
    public boolean isRiptiding(Player player) {
        return ((CraftPlayer)player).isRiptiding();
    }
}
