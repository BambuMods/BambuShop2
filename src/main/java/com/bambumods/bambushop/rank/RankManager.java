package com.bambumods.bambushop.rank;

import com.bambumods.bambushop.attachment.ModAttachments;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class RankManager {

    /** Cuántos objetos tiene el jugador de un item concreto */
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

    /** Comprueba si el jugador cumple TODOS los requisitos para subir al siguiente rango */
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

    /** Consume los recursos y sube al jugador de rango. Devuelve true si tuvo éxito. */
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

        // NUEVO (FASE 4.6): aplicar los stats del nuevo rango
        RankAttributeEvents.applyRankAttributes(player);

        return true;
    }

    /** Elimina 'amount' items del tipo 'item' del inventario del jugador */
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

    // ===== MULTIPLICADOR DE DINERO POR RANGO (FASE 4.7) =====

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

    // ===== STATS POR RANGO (FASE 4.6) =====

    public static double getBonusHealth(int rank) {
        return switch (rank) {
            case 0, 1 -> 0;
            case 2 -> 2;
            case 3 -> 4;
            case 4 -> 6;
            case 5 -> 8;
            case 6 -> 11;
            case 7 -> 14;
            case 8 -> 17;
            case 9 -> 20;
            case 10 -> 23;
            case 11 -> 27;
            case 12 -> 31;
            case 13 -> 35;
            case 14 -> 39;
            case 15 -> 43;
            case 16 -> 48;
            case 17 -> 53;
            case 18 -> 58;
            case 19 -> 63;
            case 20 -> 68;
            default -> 0;
        };
    }

    public static double getBonusDamage(int rank) {
        return switch (rank) {
            case 0, 1 -> 0;
            case 2 -> 0.5;
            case 3 -> 1;
            case 4 -> 1.5;
            case 5 -> 2;
            case 6 -> 3;
            case 7 -> 4;
            case 8 -> 5;
            case 9 -> 6;
            case 10 -> 7;
            case 11 -> 8.5;
            case 12 -> 10;
            case 13 -> 11.5;
            case 14 -> 13;
            case 15 -> 14.5;
            case 16 -> 16;
            case 17 -> 17.5;
            case 18 -> 19;
            case 19 -> 20.5;
            case 20 -> 22;
            default -> 0;
        };
    }

    public static double getBonusArmor(int rank) {
        return switch (rank) {
            case 0, 1 -> 0;
            case 2 -> 0.5;
            case 3 -> 1;
            case 4 -> 1.5;
            case 5 -> 2;
            case 6 -> 3;
            case 7 -> 4;
            case 8 -> 5;
            case 9 -> 6;
            case 10 -> 7;
            case 11 -> 8.5;
            case 12 -> 10;
            case 13 -> 11.5;
            case 14 -> 13;
            case 15 -> 14.5;
            case 16 -> 16;
            case 17 -> 17.5;
            case 18 -> 19;
            case 19 -> 20.5;
            case 20 -> 22;
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
            case 6 -> 0.30;
            case 7 -> 0.40;
            case 8 -> 0.50;
            case 9 -> 0.60;
            case 10 -> 0.70;
            case 11 -> 0.85;
            case 12 -> 1.00;
            case 13 -> 1.15;
            case 14 -> 1.30;
            case 15 -> 1.45;
            case 16 -> 1.65;
            case 17 -> 1.85;
            case 18 -> 2.05;
            case 19 -> 2.25;
            case 20 -> 2.45;
            default -> 0;
        };
    }

    /** Multiplicador de XP por rango (se aplica a XP de bloques y mobs) */
    public static int getXpMultiplier(int rank) {
        if (rank <= 3) return 1;
        if (rank <= 9) return 2;
        if (rank <= 19) return 3;
        return 4; // rango 20
    }
}