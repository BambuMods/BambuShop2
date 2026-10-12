package com.bambumods.bambushop.event;

import com.bambumods.bambushop.BambuShop;
import com.bambumods.bambushop.attachment.ModAttachments;
import com.bambumods.bambushop.config.ShopRewards;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.ItemFishedEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber(modid = BambuShop.MOD_ID)
public class MoneyEvents {

    // ===== MINAR BLOQUES Y TALAR TRONCOS =====
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (!(event.getPlayer() instanceof ServerPlayer player)) return;

        BlockState state = event.getState();
        long reward = 0L;

        // ¿Es un tronco?
        long logReward = ShopRewards.getLogReward(state.getBlock());
        if (logReward > 0) {
            reward = logReward;
        } else {
            // ¿Es un mineral?
            reward = ShopRewards.getBlockReward(state.getBlock());
        }

        if (reward > 0) {
            ModAttachments.addMoney(player, reward);
        }
    }

    // ===== MATAR MOBS =====
    @SubscribeEvent
    public static void onMobKill(LivingDeathEvent event) {
        if (event.getEntity().level().isClientSide()) return;
        if (!(event.getSource().getEntity() instanceof ServerPlayer player)) return;

        long reward = ShopRewards.getMobReward(event.getEntity().getType());
        if (reward > 0) {
            ModAttachments.addMoney(player, reward);
        }
    }

    // ===== PESCAR =====
    @SubscribeEvent
    public static void onFish(ItemFishedEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (player.level().isClientSide()) return;
        if (!(player instanceof ServerPlayer serverPlayer)) return;

        for (ItemStack drop : event.getDrops()) {
            long reward = ShopRewards.getFishReward(drop);
            if (reward > 0) {
                ModAttachments.addMoney(serverPlayer, reward);
            }
        }
    }
}