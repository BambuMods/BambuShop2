package com.bambumods.bambushop.menu;

import com.bambumods.bambushop.attachment.ModAttachments;
import com.bambumods.bambushop.registry.ModMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class ShopCategoriesMenu extends AbstractContainerMenu {

    private final long money;
    private final int rank;

    // Constructor servidor (al abrir el menú)
    public ShopCategoriesMenu(int containerId, Inventory inv, ServerPlayer player) {
        super(ModMenus.SHOP_CATEGORIES.get(), containerId);
        this.money = ModAttachments.getMoney(player);
        this.rank = ModAttachments.getRank(player);
    }

    // Constructor cliente (reconstruido desde el buffer)
    public ShopCategoriesMenu(int containerId, Inventory inv, FriendlyByteBuf data) {
        super(ModMenus.SHOP_CATEGORIES.get(), containerId);
        this.money = data.readLong();
        this.rank = data.readInt();
    }

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