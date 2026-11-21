package me.levansj01.verus.util;

import me.levansj01.verus.compat.NMSManager;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

public interface IBlockPosition {
    default MaterialData getTypeAndData(Player player, World world) {
        return NMSManager.getInstance().getTypeAndDataWithAPI(player, world, this);
    }

    default boolean sameChunkXZ(IBlockPosition blockPosition) {
        return this.getX() >> 4 == blockPosition.getX() >> 4 && this.getZ() >> 4 == blockPosition.getZ() >> 4;
    }

    int getZ();

    default boolean sameChunkY(IBlockPosition blockPosition) {
        return this.getY() >> 4 == blockPosition.getY() >> 4;
    }

    default float getFrictionFactor(Player player, World world) {
        return NMSManager.getInstance().getFrictionFactor(player, world, this);
    }

    @Deprecated
    default Block getBlock(World block) {
        return block.getBlockAt(this.getX(), this.getY(), this.getZ());
    }

    default Material getType(Player player, World world) {
        return NMSManager.getInstance().getTypeWithAPI(player, world, this);
    }

    int getX();

    int getY();

    default boolean isWaterLogged(World world) {
        return NMSManager.getInstance().isWaterLogged(world, this);
    }
}
