package com.bambumods.bambushop.menu;

import com.bambumods.bambushop.registry.ModMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class MainMenu extends AbstractContainerMenu {

    // Constructor para el cliente (recibe datos del servidor)
    public MainMenu(int containerId, Inventory playerInventory, FriendlyByteBuf data) {
        this(containerId, playerInventory);
    }

    // Constructor principal
    public MainMenu(int containerId, Inventory playerInventory) {
        super(ModMenus.MAIN_MENU.get(), containerId);
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