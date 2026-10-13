package com.bambumods.bambushop.shop;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Optional;

/**
 * Una entrada de la tienda: un item, su precio, y en qué categoría está.
 * El lado (BUY o SELL) se deriva de la categoría.
 */
public class ShopEntry {

    private final Identifier itemId;
    private final Item item;
    private final long price;
    private final ShopCategory category;

    public ShopEntry(Identifier itemId, long price, ShopCategory category) {
        this.itemId = itemId;
        this.price = price;
        this.category = category;

        Optional<Item> resolved = BuiltInRegistries.ITEM.getOptional(itemId);
        // Si el item no existe, guardamos AIR y lo detectamos al validar
        this.item = resolved.orElse(Items.AIR);
    }

    public Identifier getItemId() { return itemId; }
    public Item getItem() { return item; }
    public long getPrice() { return price; }
    public ShopCategory getCategory() { return category; }

    public boolean isValid() {
        return item != Items.AIR;
    }

    public ItemStack createStack() {
        return new ItemStack(item);
    }

    @Override
    public String toString() {
        return category.getId() + ":" + itemId + "=" + price + "$";
    }
}