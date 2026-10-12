package com.bambumods.bambushop.menu;

import com.bambumods.bambushop.registry.ModMenus;
import com.bambumods.bambushop.rank.RankData;
import com.bambumods.bambushop.rank.RankRequirement;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class RankMenu extends AbstractContainerMenu {

    // ===== DATOS SINCRONIZADOS DEL SERVIDOR =====
    private final int currentRank;
    private final int nextRankLevel;      // -1 si no hay siguiente (ya eres rango 20)
    private final String nextRankName;
    private final long money;
    private final long moneyCost;
    private final int xpLevel;
    private final int xpCost;
    private final List<SyncedReq> requirements;

    public static class SyncedReq {
        public final ItemStack stack;
        public final int needed;
        public final int has;

        public SyncedReq(ItemStack stack, int needed, int has) {
            this.stack = stack;
            this.needed = needed;
            this.has = has;
        }
    }

    // Constructor del CLIENTE (lee el buffer del servidor)
    public RankMenu(int containerId, Inventory playerInventory, RegistryFriendlyByteBuf data) {
        super(ModMenus.RANK_MENU.get(), containerId);
        this.currentRank = data.readInt();
        this.nextRankLevel = data.readInt();
        this.nextRankName = data.readUtf();
        this.money = data.readLong();
        this.moneyCost = data.readLong();
        this.xpLevel = data.readInt();
        this.xpCost = data.readInt();

        int reqCount = data.readInt();
        this.requirements = new ArrayList<>();
        for (int i = 0; i < reqCount; i++) {
            ItemStack stack = ItemStack.STREAM_CODEC.decode(data);
            int needed = data.readInt();
            int has = data.readInt();
            this.requirements.add(new SyncedReq(stack, needed, has));
        }
    }

    // Constructor del SERVIDOR (llamado desde RankCommand)
    public RankMenu(int containerId, Inventory playerInventory, Player player) {
        super(ModMenus.RANK_MENU.get(), containerId);

        com.bambumods.bambushop.attachment.ModAttachments mod = null; // noop
        int rank = com.bambumods.bambushop.attachment.ModAttachments.getRank(player);
        long playerMoney = com.bambumods.bambushop.attachment.ModAttachments.getMoney(player);

        this.currentRank = rank;
        this.money = playerMoney;
        this.xpLevel = player.experienceLevel;

        RankData next = RankData.getNext(rank);
        if (next == null) {
            this.nextRankLevel = -1;
            this.nextRankName = "MÁXIMO";
            this.moneyCost = 0;
            this.xpCost = 0;
            this.requirements = new ArrayList<>();
        } else {
            this.nextRankLevel = next.getLevel();
            this.nextRankName = next.getName();
            this.moneyCost = next.getMoneyCost();
            this.xpCost = next.getXpCost();
            this.requirements = new ArrayList<>();
            for (RankRequirement req : next.getRequirements()) {
                int has = countItem(player, req);
                this.requirements.add(new SyncedReq(new ItemStack(req.getItem()), req.getAmount(), has));
            }
        }
    }

    private static int countItem(Player player, RankRequirement req) {
        int count = 0;
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack s = player.getInventory().getItem(i);
            if (!s.isEmpty() && s.getItem() == req.getItem()) {
                count += s.getCount();
            }
        }
        return count;
    }

    // ===== GETTERS =====
    public int getCurrentRank() { return currentRank; }
    public int getNextRankLevel() { return nextRankLevel; }
    public String getNextRankName() { return nextRankName; }
    public long getMoney() { return money; }
    public long getMoneyCost() { return moneyCost; }
    public int getXpLevel() { return xpLevel; }
    public int getXpCost() { return xpCost; }
    public List<SyncedReq> getRequirements() { return requirements; }

    // ===== ABSTRACTS =====
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}