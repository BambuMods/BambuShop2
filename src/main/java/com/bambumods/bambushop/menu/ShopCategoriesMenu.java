package com.bambumods.bambushop.menu;

import com.bambumods.bambushop.registry.ModMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

/**
 * Contenedor vacío. Solo sirve para abrir la GUI de categorías.
 */
public class ShopCategoriesMenu extends AbstractContainerMenu {

    public ShopCategoriesMenu(int containerId, Inventory inv, FriendlyByteBuf data) {
        super(ModMenus.SHOP_CATEGORIES.get(), containerId);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}