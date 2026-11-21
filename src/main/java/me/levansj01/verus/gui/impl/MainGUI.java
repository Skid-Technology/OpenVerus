package me.levansj01.verus.gui.impl;

import com.google.common.collect.ImmutableSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.api.API;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.data.manager.DataManager;
import me.levansj01.verus.gui.GUI;
import me.levansj01.verus.gui.manager.GUIManager;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.storage.database.DatabaseType;
import me.levansj01.verus.type.VerusTypeLoader;
import me.levansj01.verus.util.BukkitUtil;
import me.levansj01.verus.util.item.ItemBuilder;
import me.levansj01.verus.util.item.MaterialList;
import me.levansj01.verus.util.java.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MainGUI extends GUI {
    public static final Set<UUID> ALLOWED_UUIDS = Collections.emptySet();
    private static final ItemStack infoStack = new ItemStack(MaterialList.PAPER);
    private static final ItemStack blank = new ItemBuilder().setType(MaterialList.STAINED_GLASS_PANE).setName(" ").build();
    private static final String verusType = WordUtils.capitalize(VerusTypeLoader.getVerusType().name().toLowerCase());
    private static final String build = String.valueOf(VerusPlugin.getBuild());
    private static final String implementation = Bukkit.getServer().getClass().getName().split("\\.")[3];
    private static final String format = VerusPlugin.COLOR + "Total %s %s";

    public void updateInfoStack() {
        ItemMeta itemMeta = infoStack.getItemMeta();
        itemMeta.setDisplayName(VerusPlugin.COLOR + VerusPlugin.getNameFormatted() + " Information");
        StorageEngine storageEngine = StorageEngine.getInstance();
        DatabaseType type = storageEngine.getType();
        API api = API.getAPI();
        itemMeta.setLore(
            Arrays.asList(
                "",
                VerusPlugin.COLOR + "Type " + ChatColor.WHITE + verusType,
                VerusPlugin.COLOR + "Build " + ChatColor.WHITE + build + (api == null ? "" : ChatColor.GRAY + " (API v" + api.getVersion() + ")"),
                VerusPlugin.COLOR + "Implementation " + ChatColor.WHITE + implementation,
                VerusPlugin.COLOR + "Storage: " + ChatColor.WHITE + (type != null ? type.name() : "None"),
                "",
                String.format(
                    format, "Bans", storageEngine.isConnected() ? ChatColor.WHITE + String.valueOf(storageEngine.getDatabase().getTotalBans()) : ChatColor.RED + "Not Connected"
                ),
                String.format(
                    format, "Logs", storageEngine.isConnected() ? ChatColor.WHITE + String.valueOf(storageEngine.getDatabase().getTotalLogs()) : ChatColor.RED + "Not Connected"
                ),
                "",
                ChatColor.GRAY + "Click to Refresh"
            )
        );
        infoStack.setItemMeta(itemMeta);
        this.inventory.setItem(13, infoStack);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        int slot = event.getSlot();
        HumanEntity whoClicked = event.getWhoClicked();
        if (whoClicked instanceof Player) {
            Player player = (Player)whoClicked;
            if (slot == 0) {
                if (ALLOWED_UUIDS.contains(player.getUniqueId())) {
                    PlayerData player2 = DataManager.getInstance().getPlayer(player);
                    player2.setDebug(!player2.isDebug());
                    BukkitUtil.setMeta(player, "verus.admin", player2.isDebug());
                    player.sendMessage(VerusPlugin.COLOR + "You are " + (player2.isDebug() ? "now" : "no longer") + " in debug mode");
                }
            } else if (slot == 11) {
                GUIManager.getInstance().getCheckGui().openGui(player);
            } else if (slot == 13) {
                this.updateInfoStack();
            } else if (slot == 15 && BukkitUtil.hasPermission(whoClicked, "verus.restart")) {
                NMSManager.getInstance().postToMainThread(VerusPlugin::restart);
            }
        }
    }

    public MainGUI() {
        super(VerusPlugin.COLOR + VerusPlugin.getNameFormatted() + " AntiCheat", 27);

        for (int i = 0; i < this.inventory.getSize(); i++) {
            if (i <= 9 || i >= 17) {
                this.inventory.setItem(i, blank);
            }
        }

        ArrayList list = new ArrayList();
        list.add(" ");

        for (CheckType type : CheckType.values()) {
            if (!type.ignore()) {
                list.add(ChatColor.WHITE + type.getName() + ChatColor.GRAY + " (ID: " + type.getSuffix() + ")");
            }
        }

        list.add(" ");
        list.add(ChatColor.GRAY + "Click to open Checks menu");
        this.inventory
            .setItem(
                11, new ItemBuilder(MaterialList.BOOK).setName(VerusPlugin.COLOR + "Checks").setLore(list).setAmount(CheckType.values().length - 1).build()
            );
        this.updateInfoStack();
        this.inventory
            .setItem(
                15,
                new ItemBuilder(MaterialList.REDSTONE)
                    .setName(VerusPlugin.COLOR + "Restart")
                    .setLore(Collections.singletonList(ChatColor.GRAY + "Click to Restart " + VerusPlugin.getNameFormatted()))
                    .build()
            );
    }
}
