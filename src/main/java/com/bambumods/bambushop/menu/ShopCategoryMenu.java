package com.bambumods.bambushop.menu;

import com.bambumods.bambushop.attachment.ModAttachments;
import com.bambumods.bambushop.registry.ModMenus;
import com.bambumods.bambushop.shop.ShopCategory;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class ShopCategoryMenu extends AbstractContainerMenu {

    private final ShopCategory category;
    private final long money;
    private final int rank;

    public ShopCategoryMenu(int containerId, Inventory inv, ServerPlayer player, ShopCategory category) {
        super(ModMenus.SHOP_CATEGORY.get(), containerId);
        this.category = category;
        this.money = ModAttachments.getMoney(player);
        this.rank = ModAttachments.getRank(player);
    }

    public ShopCategoryMenu(int containerId, Inventory inv, FriendlyByteBuf data) {
        super(ModMenus.SHOP_CATEGORY.get(), containerId);
        String catId = data.readUtf();
        this.category = ShopCategory.fromId(catId);
        this.money = data.readLong();
        this.rank = data.readInt();
    }

    public ShopCategory getCategory() { return category; }
    public long getMoney() { return money; }
    public int getRank() { return rank; }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}