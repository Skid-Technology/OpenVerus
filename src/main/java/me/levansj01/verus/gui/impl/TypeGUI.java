package me.levansj01.verus.gui.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.manager.CheckManager;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.gui.GUI;
import me.levansj01.verus.gui.manager.GUIManager;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.util.item.ItemBuilder;
import me.levansj01.verus.util.item.MaterialList;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TypeGUI extends GUI {
    private static final ItemStack previousPage = new ItemBuilder()
        .setType(MaterialList.STAINED_GLASS_PANE)
        .setName(ChatColor.RED + "Previous Page")
        .setLore(Collections.singletonList(ChatColor.GRAY + "Click to go back a page"))
        .build();
    private final CheckType type;
    private boolean loadedBans;
    private final List<Check> checks;
    private final Map<Integer, Check> checksById = new HashMap<>();
    private final Map<String, Integer> bansById = new HashMap<>();

    @Override
    public void openGui(Player player) {
        if (this.inventory != null) {
            if (!this.loadedBans) {
                StorageEngine storageEngine = StorageEngine.getInstance();
                if (storageEngine.isConnected()) {
                    CheckManager checkManager = CheckManager.getInstance();
                    int value = 9;

                    for (Check check : this.checks) {
                        int value2 = value++;
                        if (value2 >= this.inventory.getSize()) {
                            break;
                        }

                        ItemStack itemStack = this.inventory.getContents()[value2];
                        if (itemStack != null) {
                            storageEngine.getDatabase().getCheckBans(check, bans -> {
                                this.bansById.put(check.identifier(), bans);
                                NMSManager.getInstance().postToMainThread(() -> this.updateLore(itemStack, value2, check, checkManager, bans));
                            });
                        }
                    }
                }

                this.loadedBans = true;
            }

            player.openInventory(this.inventory);
        }
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        int slot = event.getSlot();
        CheckType nextType = this.type.next();
        CheckType previousType = this.type.previous();
        if (slot == 0 && previousType != this.type) {
            GUIManager.getInstance().getTypeGuis().get(previousType).openGui((Player)event.getWhoClicked());
        } else if (slot == 8 && nextType != this.type) {
            GUIManager.getInstance().getTypeGuis().get(nextType).openGui((Player)event.getWhoClicked());
        } else if (slot >= 0 && slot < 9) {
            GUIManager.getInstance().getCheckGui().openGui((Player)event.getWhoClicked());
        } else {
            ClickType click = event.getClick();
            ItemStack currentItem = event.getCurrentItem();
            if (currentItem != null && currentItem.getItemMeta() != null) {
                Check check = this.checksById.get(slot);
                if (check != null && ChatColor.stripColor(currentItem.getItemMeta().getDisplayName()).equalsIgnoreCase(check.name())) {
                    CheckManager checkManager = CheckManager.getInstance();
                    switch (click) {
                        case LEFT:
                            if (!this.loadedBans) {
                                return;
                            }

                            checkManager.setEnabled(check, !checkManager.isEnabled(check));
                            break;
                        case RIGHT:
                            if (!this.loadedBans) {
                                return;
                            }

                            checkManager.setAutoban(check, !checkManager.isAutoban(check));
                            break;
                        case MIDDLE:
                            StorageEngine storageEngine = StorageEngine.getInstance();
                            if (storageEngine.isConnected()) {
                                storageEngine.getDatabase().getCheckBans(check, bans -> {
                                    this.bansById.put(check.identifier(), bans);
                                    NMSManager.getInstance().postToMainThread(() -> this.updateLore(currentItem, slot, check, checkManager, bans));
                                });
                            }
                        default:
                            return;
                    }

                    this.updateLore(currentItem, slot, check, checkManager, this.bansById.getOrDefault(check.identifier(), 0));
                }
            }
        }
    }

    private void updateLore(ItemStack itemStack, int value, Check check, CheckManager checkManager, int value2) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(
            Arrays.asList(
                "",
                VerusPlugin.COLOR + "Display: " + ChatColor.WHITE + check.getFriendlyName(),
                "",
                VerusPlugin.COLOR + "Total Bans: " + ChatColor.WHITE + (value2 == 0 ? "None" : value2),
                VerusPlugin.COLOR + "Ban VL: " + ChatColor.WHITE + (checkManager.getMaxViolation(check) == Integer.MAX_VALUE ? "None" : checkManager.getMaxViolation(check)),
                "",
                (checkManager.isEnabled(check) ? ChatColor.GREEN : ChatColor.RED) + "Alerts",
                (checkManager.isAutoban(check) ? ChatColor.GREEN : ChatColor.RED) + "Bannable",
                "",
                ChatColor.GRAY + "Left-Click to toggle alerts for this check",
                ChatColor.GRAY + "Middle-Click to update ban count",
                ChatColor.GRAY + "Right-Click to toggle auto banning for this check"
            )
        );
        itemStack.setItemMeta(itemMeta);
        this.inventory.setItem(value, itemStack);
    }

    public TypeGUI(CheckType type, List<Check> checks) {
        super(VerusPlugin.COLOR + type.getName() + " Checks (" + checks.size() + ")", 45);
        this.type = type;
        this.checks = checks;
        if (!checks.isEmpty()) {
            for (int i = 0; i < 9; i++) {
                this.inventory.setItem(i, previousPage);
            }

            CheckType previousType = type.previous();
            CheckType nextType = type.next();
            if (previousType != type) {
                this.inventory
                    .setItem(
                        0,
                        new ItemBuilder(Material.ARROW)
                            .setName(ChatColor.RED + previousType.getName() + " Checks")
                            .setLore(Collections.singletonList(ChatColor.GRAY + "Click to view " + previousType.getName() + " checks"))
                            .build()
                    );
            }

            if (nextType != type) {
                this.inventory
                    .setItem(
                        8,
                        new ItemBuilder(Material.ARROW)
                            .setName(ChatColor.RED + nextType.getName() + " Checks")
                            .setLore(Collections.singletonList(ChatColor.GRAY + "Click to view " + nextType.getName() + " checks"))
                            .build()
                    );
            }

            checks.sort(Comparator.comparing(Check::getSubType));
            CheckManager checkManager = CheckManager.getInstance();
            int value = 9;

            for (Check check : checks) {
                if (value >= this.inventory.getSize()) {
                    break;
                }

                ItemBuilder builder = new ItemBuilder().setType(MaterialList.PAPER).setName(VerusPlugin.COLOR + check.name());
                this.updateLore(builder.build(), value, check, checkManager, 0);
                this.checksById.put(value++, check);
            }
        }
    }

    @Override
    public void clear() {
        super.clear();
        this.checks.clear();
        this.bansById.clear();
        this.checksById.clear();
    }
}
