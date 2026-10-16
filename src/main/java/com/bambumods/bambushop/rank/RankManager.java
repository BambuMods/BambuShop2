package com.bambumods.bambushop.rank;

import com.bambumods.bambushop.attachment.ModAttachments;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class RankManager {

    public static int countItem(ServerPlayer player, RankRequirement req) {
        int count = 0;
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (!stack.isEmpty() && stack.getItem() == req.getItem()) {
                count += stack.getCount();
            }
        }
        return count;
    }

    public static boolean canRankUp(ServerPlayer player) {
        int current = ModAttachments.getRank(player);
        RankData next = RankData.getNext(current);
        if (next == null) return false;

        if (ModAttachments.getMoney(player) < next.getMoneyCost()) return false;
        if (player.experienceLevel < next.getXpCost()) return false;

        for (RankRequirement req : next.getRequirements()) {
            if (countItem(player, req) < req.getAmount()) return false;
        }

        return true;
    }

    public static boolean rankUp(ServerPlayer player) {
        if (!canRankUp(player)) return false;

        int current = ModAttachments.getRank(player);
        RankData next = RankData.getNext(current);
        if (next == null) return false;

        ModAttachments.takeMoney(player, next.getMoneyCost());
        player.giveExperienceLevels(-next.getXpCost());

        for (RankRequirement req : next.getRequirements()) {
            removeItem(player, req.getItem(), req.getAmount());
        }

        ModAttachments.setRank(player, next.getLevel());
        RankAttributeEvents.applyRankAttributes(player);
        return true;
    }

    private static void removeItem(ServerPlayer player, net.minecraft.world.item.Item item, int amount) {
        int remaining = amount;
        for (int i = 0; i < player.getInventory().getContainerSize() && remaining > 0; i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (!stack.isEmpty() && stack.getItem() == item) {
                int take = Math.min(stack.getCount(), remaining);
                stack.shrink(take);
                remaining -= take;
            }
        }
    }

    // ===== MULTIPLICADOR DE DINERO POR RANGO =====
    public static int getMoneyMultiplier(Player player) {
        int rank = ModAttachments.getRank(player);
        return switch (rank) {
            case 0, 1 -> 1;
            case 2 -> 2;
            case 3 -> 3;
            case 4 -> 4;
            case 5 -> 5;
            case 6 -> 7;
            case 7 -> 9;
            case 8 -> 12;
            case 9 -> 15;
            case 10 -> 20;
            case 11 -> 25;
            case 12 -> 32;
            case 13 -> 40;
            case 14 -> 50;
            case 15 -> 65;
            case 16 -> 80;
            case 17 -> 100;
            case 18 -> 130;
            case 19 -> 170;
            case 20 -> 250;
            default -> 1;
        };
    }

    public static long applyMultiplier(Player player, long baseReward) {
        if (baseReward <= 0) return 0;
        return baseReward * getMoneyMultiplier(player);
    }

    // ===== STATS POR RANGO =====
    // Rango 20 = +100 HP (antes +68)

    public static double getBonusHealth(int rank) {
        return switch (rank) {
            case 0, 1 -> 0;
            case 2 -> 4;
            case 3 -> 8;
            case 4 -> 12;
            case 5 -> 16;
            case 6 -> 20;
            case 7 -> 24;
            case 8 -> 28;
            case 9 -> 32;
            case 10 -> 36;
            case 11 -> 42;
            case 12 -> 48;
            case 13 -> 54;
            case 14 -> 60;
            case 15 -> 66;
            case 16 -> 72;
            case 17 -> 80;
            case 18 -> 87;
            case 19 -> 93;
            case 20 -> 100;
            default -> 0;
        };
    }

    public static double getBonusDamage(int rank) {
        return switch (rank) {
            case 0, 1 -> 0;
            case 2 -> 1;
            case 3 -> 2;
            case 4 -> 3;
            case 5 -> 4;
            case 6 -> 5;
            case 7 -> 6;
            case 8 -> 7;
            case 9 -> 8;
            case 10 -> 9;
            case 11 -> 10;
            case 12 -> 11.5;
            case 13 -> 13;
            case 14 -> 14.5;
            case 15 -> 16;
            case 16 -> 17.5;
            case 17 -> 19;
            case 18 -> 20.5;
            case 19 -> 22;
            case 20 -> 24;
            default -> 0;
        };
    }

    public static double getBonusArmor(int rank) {
        return switch (rank) {
            case 0, 1 -> 0;
            case 2 -> 1;
            case 3 -> 2;
            case 4 -> 3;
            case 5 -> 4;
            case 6 -> 5;
            case 7 -> 6;
            case 8 -> 7;
            case 9 -> 8;
            case 10 -> 9;
            case 11 -> 10;
            case 12 -> 11.5;
            case 13 -> 13;
            case 14 -> 14.5;
            case 15 -> 16;
            case 16 -> 17.5;
            case 17 -> 19;
            case 18 -> 20.5;
            case 19 -> 22;
            case 20 -> 24;
            default -> 0;
        };
    }

    public static double getBonusKnockbackResistance(int rank) {
        return switch (rank) {
            case 0, 1 -> 0;
            case 2 -> 0.05;
            case 3 -> 0.10;
            case 4 -> 0.15;
            case 5 -> 0.20;
            case 6 -> 0.25;
            case 7 -> 0.30;
            case 8 -> 0.35;
            case 9 -> 0.40;
            case 10 -> 0.45;
            case 11 -> 0.50;
            case 12 -> 0.55;
            case 13 -> 0.60;
            case 14 -> 0.65;
            case 15 -> 0.70;
            case 16 -> 0.80;
            case 17 -> 0.90;
            case 18 -> 1.00;
            case 19 -> 1.10;
            case 20 -> 1.20;
            default -> 0;
        };
    }

    public static int getXpMultiplier(int rank) {
        if (rank <= 3) return 1;
        if (rank <= 9) return 2;
        if (rank <= 19) return 3;
        return 4;
    }
}