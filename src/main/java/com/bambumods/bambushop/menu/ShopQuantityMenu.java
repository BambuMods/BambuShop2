package com.bambumods.bambushop.menu;

import com.bambumods.bambushop.attachment.ModAttachments;
import com.bambumods.bambushop.registry.ModMenus;
import com.bambumods.bambushop.shop.ShopEntry;
import com.bambumods.bambushop.shop.ShopManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class ShopQuantityMenu extends AbstractContainerMenu {

    private final ShopEntry entry;
    private long money;
    private final int rank;

    public ShopQuantityMenu(int containerId, Inventory inv, ServerPlayer player, ShopEntry entry) {
        super(ModMenus.SHOP_QUANTITY.get(), containerId);
        this.entry = entry;
        this.money = ModAttachments.getMoney(player);
        this.rank = ModAttachments.getRank(player);
    }

    public ShopQuantityMenu(int containerId, Inventory inv, FriendlyByteBuf data) {
        super(ModMenus.SHOP_QUANTITY.get(), containerId);
        String itemId = data.readUtf();
        Identifier id = Identifier.tryParse(itemId);
        this.entry = (id != null) ? ShopManager.getBuyEntry(id) : null;
        this.money = data.readLong();
        this.rank = data.readInt();
    }

    public ShopEntry getEntry() { return entry; }
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
}