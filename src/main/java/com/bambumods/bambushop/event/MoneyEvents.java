package com.bambumods.bambushop.event;

import com.bambumods.bambushop.BambuShop;
import com.bambumods.bambushop.attachment.ModAttachments;
import com.bambumods.bambushop.config.ShopRewards;
import com.bambumods.bambushop.mob.MobRewardsHelper;
import com.bambumods.bambushop.rank.RankManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.entity.player.ItemFishedEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.BlockDropsEvent;

import java.util.Random;

@EventBusSubscriber(modid = BambuShop.MOD_ID)
public class MoneyEvents {

    private static final Random RANDOM = new Random();

    // ===== DINERO AL MINAR/TALAR =====

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (!(event.getPlayer() instanceof ServerPlayer player)) return;

        BlockState state = event.getState();
        long reward;

        long logReward = ShopRewards.getLogReward(state.getBlock());
        if (logReward > 0) {
            reward = logReward;
        } else {
            reward = ShopRewards.getBlockReward(state.getBlock());
        }

        if (reward > 0) {
            long withRank = RankManager.applyMultiplier(player, reward);
            // Factor aleatorio 1.0 - 10.0
            double randomFactor = 1.0 + (RANDOM.nextDouble() * 9.0);
            long finalReward = (long) (withRank * randomFactor);
            ModAttachments.addMoney(player, finalReward);
        }
    }

    /** Aplica el multiplicador de XP al dropear XP de bloques (minerales) */
    @SubscribeEvent
    public static void onBlockDrops(BlockDropsEvent event) {
        if (!(event.getBreaker() instanceof ServerPlayer player)) return;
        int vanillaXp = event.getDroppedExperience();
        if (vanillaXp <= 0) return;

        int multiplier = RankManager.getXpMultiplier(ModAttachments.getRank(player));
        if (multiplier <= 1) return;

        event.setDroppedExperience(vanillaXp * multiplier);
    }

    // ===== DINERO AL MATAR MOBS =====

    @SubscribeEvent
    public static void onMobKill(LivingDeathEvent event) {
        if (event.getEntity().level().isClientSide()) return;
        if (!(event.getSource().getEntity() instanceof ServerPlayer player)) return;
        if (!(event.getEntity() instanceof Mob mob)) return;

        long baseMoney = ShopRewards.getMobReward(event.getEntity().getType());
        if (baseMoney > 0) {
            long finalReward = MobRewardsHelper.calculateMoneyReward(player, mob, baseMoney);
            ModAttachments.addMoney(player, finalReward);
        }
    }

    /** Aplica el multiplicador de XP + nivel del mob al dropear XP de mobs */
    @SubscribeEvent
    public static void onLivingXpDrop(LivingExperienceDropEvent event) {
        if (!(event.getAttackingPlayer() instanceof ServerPlayer player)) return;
        if (!(event.getEntity() instanceof Mob mob)) return;

        int vanillaXp = event.getDroppedExperience();
        if (vanillaXp <= 0) return;

        int finalXp = MobRewardsHelper.calculateXpReward(player, mob, vanillaXp);
        event.setDroppedExperience(finalXp);
    }

    // ===== DINERO AL PESCAR =====

    @SubscribeEvent
    public static void onFish(ItemFishedEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        for (ItemStack drop : event.getDrops()) {
            long reward = ShopRewards.getFishReward(drop);
            if (reward > 0) {
                long withRank = RankManager.applyMultiplier(player, reward);
                // Factor aleatorio 1.0 - 5.0
                double randomFactor = 1.0 + (RANDOM.nextDouble() * 4.0);
                long finalReward = (long) (withRank * randomFactor);
                ModAttachments.addMoney(player, finalReward);
            }
        }
    }
}