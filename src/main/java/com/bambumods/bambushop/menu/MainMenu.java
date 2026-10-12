package com.bambumods.bambushop.menu;

import com.bambumods.bambushop.registry.ModMenus;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class MainMenu extends AbstractContainerMenu {

    private final long money;

    // Constructor del cliente (lee el dinero del buffer)
    public MainMenu(int containerId, Inventory playerInventory, RegistryFriendlyByteBuf data) {
        this(containerId, playerInventory, data.readLong());
    }

    // Constructor principal
    public MainMenu(int containerId, Inventory playerInventory, long money) {
        super(ModMenus.MAIN_MENU.get(), containerId);
        this.money = money;
    }

    public long getMoney() {
        return money;
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