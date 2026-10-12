package com.bambumods.bambushop.rank;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class RankRequirement {

    private final Item item;
    private final int amount;

    public RankRequirement(Item item, int amount) {
        this.item = item;
        this.amount = amount;
    }

    public Item getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }

    public ItemStack getDisplayStack() {
        return new ItemStack(item);
    }
}