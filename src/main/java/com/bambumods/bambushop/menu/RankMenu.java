package com.bambumods.bambushop.menu;

import com.bambumods.bambushop.registry.ModMenus;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class RankMenu extends AbstractContainerMenu {

    // Constructor del cliente (recibe el buffer del servidor)
    public RankMenu(int containerId, Inventory playerInventory, RegistryFriendlyByteBuf data) {
        this(containerId, playerInventory);
    }

    // Constructor principal
    public RankMenu(int containerId, Inventory playerInventory) {
        super(ModMenus.RANK_MENU.get(), containerId);
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