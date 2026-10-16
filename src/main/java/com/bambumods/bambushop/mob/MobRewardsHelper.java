package com.bambumods.bambushop.mob;

import com.bambumods.bambushop.attachment.ModAttachments;
import com.bambumods.bambushop.rank.RankManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Mob;

import java.util.Random;

public class MobRewardsHelper {

    private static final Random RANDOM = new Random();

    public static long calculateMoneyReward(ServerPlayer player, Mob mob, long baseMoney) {
        int level = ModAttachments.getMobLevel(mob);
        int opTypeId = ModAttachments.getMobOpType(mob);
        MobOpHelper.MobOpType opType = MobOpHelper.MobOpType.fromId(opTypeId);

        int rankMultiplier = RankManager.getMoneyMultiplier(player);
        double randomFactor = 1.0 + (RANDOM.nextDouble() * 4.0);

        double reward = baseMoney * level * rankMultiplier * randomFactor * opType.moneyMult;
        return (long) reward;
    }

    public static int calculateXpReward(ServerPlayer player, Mob mob, int baseXp) {
        int level = ModAttachments.getMobLevel(mob);
        int opTypeId = ModAttachments.getMobOpType(mob);
        MobOpHelper.MobOpType opType = MobOpHelper.MobOpType.fromId(opTypeId);

        int rankXpMultiplier = RankManager.getXpMultiplier(ModAttachments.getRank(player));
        double randomFactor = 1.0 + (RANDOM.nextDouble() * 2.0);

        double xp = baseXp * level * rankXpMultiplier * randomFactor * opType.xpMult;
        return (int) xp;
    }
}