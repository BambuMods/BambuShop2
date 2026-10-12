package com.bambumods.bambushop.rank;

import com.bambumods.bambushop.attachment.ModAttachments;
import net.minecraft.server.level.ServerPlayer;
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

        // Dinero
        if (ModAttachments.getMoney(player) < next.getMoneyCost()) return false;

        // XP (niveles vanilla)
        if (player.experienceLevel < next.getXpCost()) return false;

        // Objetos
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

        // Quitar dinero
        ModAttachments.takeMoney(player, next.getMoneyCost());

        // Quitar XP
        player.giveExperienceLevels(-next.getXpCost());

        // Quitar objetos
        for (RankRequirement req : next.getRequirements()) {
            removeItem(player, req.getItem(), req.getAmount());
        }

        // Subir rango
        ModAttachments.setRank(player, next.getLevel());
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
}