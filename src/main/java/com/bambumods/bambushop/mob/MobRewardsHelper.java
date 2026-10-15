package com.bambumods.bambushop.mob;

import com.bambumods.bambushop.attachment.ModAttachments;
import com.bambumods.bambushop.rank.RankManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Mob;

import java.util.Random;

public class MobRewardsHelper {

    private static final Random RANDOM = new Random();

    /**
     * Calcula el dinero que da un mob al morir.
     * Fórmula: baseMob × nivelMob × multiplicadorRango × aleatorio(1.0 - 5.0)
     */
    public static long calculateMoneyReward(ServerPlayer player, Mob mob, long baseMoney) {
        int level = ModAttachments.getMobLevel(mob);
        int rankMultiplier = RankManager.getMoneyMultiplier(player);
        double randomFactor = 1.0 + (RANDOM.nextDouble() * 4.0); // entre 1.0 y 5.0

        double reward = baseMoney * level * rankMultiplier * randomFactor;
        return (long) reward;
    }

    /**
     * Calcula el XP que da un mob al morir.
     * Fórmula: baseXp × nivelMob × multiplicadorRangoXp × aleatorio(1.0 - 3.0)
     */
    public static int calculateXpReward(ServerPlayer player, Mob mob, int baseXp) {
        int level = ModAttachments.getMobLevel(mob);
        int rankXpMultiplier = RankManager.getXpMultiplier(ModAttachments.getRank(player));
        double randomFactor = 1.0 + (RANDOM.nextDouble() * 2.0); // entre 1.0 y 3.0

        double xp = baseXp * level * rankXpMultiplier * randomFactor;
        return (int) xp;
    }
}