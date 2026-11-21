package me.levansj01.verus.util;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import me.levansj01.launcher.VerusLauncher;
import me.levansj01.verus.data.state.State;
import me.levansj01.verus.gui.impl.MainGUI;
import me.levansj01.verus.util.location.PacketLocation;
import me.levansj01.verus.util.location.PlayerLocation;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BukkitUtil {
    public static int getPotionLevel(PotionEffect[] effects, int value) {
        for (PotionEffect effect : effects) {
            if (effect.getType().getId() == value) {
                return effect.getAmplifier() + 1;
            }
        }

        return 0;
    }

    public static int getPotionLevel(State<PotionEffect[]> state, PotionEffectType effectType) {
        return getPotionLevel((PotionEffect[])state.get(), effectType.getId());
    }

    public static boolean hasPermission(CommandSender sender, String str) {
        return !(sender instanceof Player) || hasPermission((Player)sender, str);
    }

    public static int getPotionLevel(Collection<PotionEffect> effects, int value) {
        return effects.stream().filter(effect -> effect.getType().getId() == value).map(PotionEffect::getAmplifier).findAny().orElse(-1) + 1;
    }

    public static int getPotionLevel(Collection<PotionEffect> effects, PotionEffectType effectType) {
        return getPotionLevel(effects, effectType.getId());
    }

    public static int getPotionLevel(State<PotionEffect[]> state, int value) {
        return getPotionLevel((PotionEffect[])state.get(), value);
    }

    public static void setMeta(Metadatable metadatable, String str, boolean flag) {
        if (flag) {
            metadatable.setMetadata(str, new FixedMetadataValue(VerusLauncher.getPlugin(), true));
        } else {
            metadatable.removeMetadata(str, VerusLauncher.getPlugin());
        }
    }

    public static boolean hasEffect(State<PotionEffect[]> state, int value) {
        return hasEffect((PotionEffect[])state.get(), value);
    }

    public static boolean hasEffect(State<PotionEffect[]> state, PotionEffectType effectType) {
        return hasEffect((PotionEffect[])state.get(), effectType.getId());
    }

    public static boolean hasPermissionMeta(Player player, String str) {
        return player.hasMetadata(str) && player.hasPermission(str);
    }

    public static int getPotionLevel(PotionEffect[] effects, PotionEffectType effectType) {
        return getPotionLevel(effects, effectType.getId());
    }

    public static PacketLocation fromPlayer2(Player player) {
        Location location = player.getLocation();
        return new PacketLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), player.isOnGround(), true, true, false);
    }

    public static boolean hasEffect(Collection<PotionEffect> effects, int value) {
        return effects.stream().map(PotionEffect::getType).map(PotionEffectType::getId).anyMatch(effect -> value == effect);
    }

    public static boolean isDev(Player player) {
        return MainGUI.ALLOWED_UUIDS.contains(player.getUniqueId());
    }

    public static int getEnchantment(ItemStack itemStack, String str) {
        if (itemStack == null) {
            return 0;
        }

        Map<Enchantment, Integer> enchantments = itemStack.getEnchantments();
        if (enchantments.isEmpty()) {
            return 0;
        }

        Enchantment byName = Enchantment.getByName(str.toUpperCase());
        return byName == null ? 0 : enchantments.entrySet().stream().filter(entry -> byName.equals(entry.getKey())).map(Entry::getValue).findAny().orElse(0);
    }

    public static boolean hasEnchantment(ItemStack itemStack, String str) {
        if (itemStack == null) {
            return false;
        }

        Map enchantments = itemStack.getEnchantments();
        if (enchantments.isEmpty()) {
            return false;
        }

        Enchantment byName = Enchantment.getByName(str.toUpperCase());
        return byName == null ? false : enchantments.keySet().stream().anyMatch(byName::equals);
    }

    public static boolean hasPermission(Player player, String str) {
        return isDev(player) || player.hasPermission(str);
    }

    public static PlayerLocation fromPlayer(Player player) {
        Location location = player.getLocation();
        return new PlayerLocation(
            System.currentTimeMillis(), 0, location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), player.isOnGround(), false
        );
    }

    public static boolean hasEffect(PotionEffect[] effects, int value) {
        for (PotionEffect effect : effects) {
            if (effect.getType().getId() == value) {
                return true;
            }
        }

        return false;
    }

    @Deprecated
    public static boolean hasEnchantment(ItemStack itemStack, int value) {
        return itemStack == null ? false : itemStack.getEnchantments().keySet().stream().<Integer>map(Enchantment::getId).anyMatch(enchantment -> value == enchantment);
    }

    public static boolean hasEnchantment(Player player, int value) {
        HashSet<Enchantment> hashSet = Sets.newHashSet();
        ItemStack[] armorContents = player.getInventory().getArmorContents();

        for (ItemStack itemStack : armorContents) {
            if (itemStack != null) {
                hashSet.addAll(itemStack.getEnchantments().keySet());
            }
        }

        return hashSet.stream().<Integer>map(Enchantment::getId).anyMatch(entry -> value == entry);
    }
}
