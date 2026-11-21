package me.levansj01.verus.gui;

import java.util.LinkedList;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public abstract class GUI implements InventoryHolder {
    private final Integer size;
    private final String header;
    public Inventory inventory;

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    public GUI(String header, Integer size) {
        this.inventory = Bukkit.createInventory(this, size, header);
        this.header = header;
        this.size = size;
    }

    public void openGui(Player player) {
        if (this.inventory != null) {
            player.openInventory(this.inventory);
        }
    }

    public Integer getSize() {
        return this.size;
    }

    public void clear() {
        try {
            new LinkedList<>(this.inventory.getViewers()).forEach(HumanEntity::closeInventory);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public String getHeader() {
        return this.header;
    }

    public abstract void onClick(InventoryClickEvent event);
}
