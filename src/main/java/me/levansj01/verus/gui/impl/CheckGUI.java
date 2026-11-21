package me.levansj01.verus.gui.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.gui.GUI;
import me.levansj01.verus.gui.manager.GUIManager;
import me.levansj01.verus.util.item.ItemBuilder;
import me.levansj01.verus.util.item.MaterialList;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class CheckGUI extends GUI {
    private static final ItemStack previousPage = new ItemBuilder()
        .setType(MaterialList.STAINED_GLASS_PANE)
        .setName(ChatColor.RED + "Previous Page")
        .setAmount(1)
        .setLore(Collections.singletonList(ChatColor.GRAY + "Click to go back a page"))
        .build();
    private final Map<Integer, CheckType> typeById = new HashMap<>();

    @Override
    public void onClick(InventoryClickEvent event) {
        int slot = event.getSlot();
        if (slot >= 0 && slot < 9) {
            GUIManager.getInstance().getMainGui().openGui((Player)event.getWhoClicked());
        } else {
            ItemStack currentItem = event.getCurrentItem();
            if (currentItem != null && currentItem.getItemMeta() != null) {
                CheckType type = this.typeById.get(slot);
                if (type != null) {
                    String text = ChatColor.stripColor(currentItem.getItemMeta().getDisplayName().replace(" Checks", ""));
                    if (text.equalsIgnoreCase(type.getName())) {
                        GUIManager.getInstance().getTypeGui(type).openGui((Player)event.getWhoClicked());
                    }
                }
            }
        }
    }

    @Override
    public void clear() {
        super.clear();
        this.typeById.clear();
    }

    public CheckGUI() {
        super(VerusPlugin.COLOR + VerusPlugin.getNameFormatted() + " Checks", 45);

        for (int i = 0; i < 9; i++) {
            this.inventory.setItem(i, previousPage);
        }

        int value = 9;

        for (CheckType type : CheckType.values()) {
            if (!type.ignore()) {
                this.inventory
                    .setItem(
                        value,
                        new ItemBuilder()
                            .setType(MaterialList.DIAMOND_SWORD)
                            .setName(VerusPlugin.COLOR + type.getName() + " Checks")
                            .setLore(Collections.singletonList(VerusPlugin.COLOR + "ID: " + ChatColor.WHITE + type.getSuffix()))
                            .setAmount(1)
                            .build()
                    );
                this.typeById.put(value++, type);
            }
        }
    }
}
