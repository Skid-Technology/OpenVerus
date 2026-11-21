package me.levansj01.verus.util.item;

import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class ItemBuilder {
    private short damage;
    private String name;
    private int amount = 1;
    private List<String> lore;
    private Material type;

    public ItemBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public int getAmount() {
        return this.amount;
    }

    public List<String> getLore() {
        return this.lore;
    }

    public ItemBuilder setDamage(int damage) {
        this.damage = (short)damage;
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public Material getType() {
        return this.type;
    }

    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder setTypeAndData(MaterialData typeAndData) {
        this.type = typeAndData.getItemType();
        this.damage = typeAndData.getData();
        return this;
    }

    public short getDamage() {
        return this.damage;
    }

    public String getName() {
        return this.name;
    }

    public ItemBuilder setType(Material type) {
        this.type = type;
        return this;
    }

    public ItemBuilder(Material type) {
        this.type = type;
    }

    public ItemBuilder() {
    }

    public ItemStack build() {
        ItemStack itemStack = new ItemStack(this.type);
        itemStack.setAmount(this.amount);
        itemStack.setDurability(this.damage);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(this.name);
        itemMeta.setLore(this.lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
