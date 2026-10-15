package com.bambumods.bambushop.menu;

import com.bambumods.bambushop.attachment.ModAttachments;
import com.bambumods.bambushop.registry.ModMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SellMenu extends AbstractContainerMenu {

    private final Container sellContainer;
    private final Slot sellSlot;

    private long money;
    private final int rank;

    public SellMenu(int containerId, Inventory inv, ServerPlayer player) {
        super(ModMenus.SELL_MENU.get(), containerId);
        this.money = ModAttachments.getMoney(player);
        this.rank = ModAttachments.getRank(player);

        this.sellContainer = new SimpleContainer(1);
        this.sellSlot = new Slot(sellContainer, 0, 114, 64);
        this.addSlot(sellSlot);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(inv, col + row * 9 + 9, 8 + col * 18, 140 + row * 18));
            }
        }
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(inv, col, 8 + col * 18, 198));
        }
    }

    public SellMenu(int containerId, Inventory inv, FriendlyByteBuf data) {
        super(ModMenus.SELL_MENU.get(), containerId);
        this.money = data.readLong();
        this.rank = data.readInt();

        this.sellContainer = new SimpleContainer(1);
        this.sellSlot = new Slot(sellContainer, 0, 114, 64);
        this.addSlot(sellSlot);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(inv, col + row * 9 + 9, 8 + col * 18, 140 + row * 18));
            }
        }
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(inv, col, 8 + col * 18, 198));
        }
    }

    public Slot getSellSlot() { return sellSlot; }
    public long getMoney() { return money; }
    public void setMoney(long money) { this.money = money; }
    public int getRank() { return rank; }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        if (!player.level().isClientSide()) {
            ItemStack stack = sellContainer.removeItemNoUpdate(0);
            if (!stack.isEmpty()) {
                if (!player.getInventory().add(stack)) {
                    player.drop(stack, false);
                }
            }
        }
    }
}