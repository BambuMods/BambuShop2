package com.bambumods.bambushop.shop;

import com.bambumods.bambushop.attachment.ModAttachments;
import com.bambumods.bambushop.rank.RankManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class AutoSellHelper {

    public static boolean isEnabled(ServerPlayer player) {
        return ModAttachments.isAutoSell(player);
    }

    public static long calculateSellValue(ServerPlayer player, ItemStack stack) {
        long pricePerItem = ShopManager.getSellPrice(stack);
        if (pricePerItem <= 0) return 0;
        long total = pricePerItem * stack.getCount();
        return RankManager.applyMultiplier(player, total);
    }
}