package com.bambumods.bambushop.config;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.HashMap;
import java.util.Map;

public class ShopRewards {

    // ===== DINERO POR MINAR BLOQUES =====
    public static final Map<Block, Long> BLOCK_REWARDS = new HashMap<>();

    // ===== DINERO POR MATAR MOBS =====
    public static final Map<EntityType<?>, Long> MOB_REWARDS = new HashMap<>();

    // ===== DINERO POR TALAR (troncos) =====
    public static final Map<Block, Long> LOG_REWARDS = new HashMap<>();

    // ===== DINERO POR PESCAR =====
    public static final Map<ItemStack, Long> FISH_REWARDS = new HashMap<>();

    static {
        // ----- MINERALES -----
        BLOCK_REWARDS.put(Blocks.COAL_ORE, 2L);
        BLOCK_REWARDS.put(Blocks.DEEPSLATE_COAL_ORE, 2L);
        BLOCK_REWARDS.put(Blocks.COPPER_ORE, 3L);
        BLOCK_REWARDS.put(Blocks.DEEPSLATE_COPPER_ORE, 3L);
        BLOCK_REWARDS.put(Blocks.IRON_ORE, 5L);
        BLOCK_REWARDS.put(Blocks.DEEPSLATE_IRON_ORE, 5L);
        BLOCK_REWARDS.put(Blocks.GOLD_ORE, 8L);
        BLOCK_REWARDS.put(Blocks.DEEPSLATE_GOLD_ORE, 8L);
        BLOCK_REWARDS.put(Blocks.REDSTONE_ORE, 4L);
        BLOCK_REWARDS.put(Blocks.DEEPSLATE_REDSTONE_ORE, 4L);
        BLOCK_REWARDS.put(Blocks.LAPIS_ORE, 6L);
        BLOCK_REWARDS.put(Blocks.DEEPSLATE_LAPIS_ORE, 6L);
        BLOCK_REWARDS.put(Blocks.DIAMOND_ORE, 50L);
        BLOCK_REWARDS.put(Blocks.DEEPSLATE_DIAMOND_ORE, 50L);
        BLOCK_REWARDS.put(Blocks.EMERALD_ORE, 80L);
        BLOCK_REWARDS.put(Blocks.DEEPSLATE_EMERALD_ORE, 80L);
        BLOCK_REWARDS.put(Blocks.NETHER_QUARTZ_ORE, 3L);
        BLOCK_REWARDS.put(Blocks.NETHER_GOLD_ORE, 6L);
        BLOCK_REWARDS.put(Blocks.ANCIENT_DEBRIS, 500L);

        // ----- MOBS -----
        MOB_REWARDS.put(EntityType.ZOMBIE, 2L);
        MOB_REWARDS.put(EntityType.SKELETON, 3L);
        MOB_REWARDS.put(EntityType.CREEPER, 5L);
        MOB_REWARDS.put(EntityType.SPIDER, 3L);
        MOB_REWARDS.put(EntityType.CAVE_SPIDER, 3L);
        MOB_REWARDS.put(EntityType.ENDERMAN, 20L);
        MOB_REWARDS.put(EntityType.BLAZE, 25L);
        MOB_REWARDS.put(EntityType.WITHER_SKELETON, 30L);
        MOB_REWARDS.put(EntityType.PIGLIN, 10L);
        MOB_REWARDS.put(EntityType.PIGLIN_BRUTE, 20L);
        MOB_REWARDS.put(EntityType.HOGLIN, 15L);
        MOB_REWARDS.put(EntityType.GHAST, 30L);
        MOB_REWARDS.put(EntityType.WITHER, 5000L);
        MOB_REWARDS.put(EntityType.ENDER_DRAGON, 50000L);
        MOB_REWARDS.put(EntityType.COW, 1L);
        MOB_REWARDS.put(EntityType.PIG, 1L);
        MOB_REWARDS.put(EntityType.SHEEP, 1L);
        MOB_REWARDS.put(EntityType.CHICKEN, 1L);

        // ----- TRONCOS -----
        LOG_REWARDS.put(Blocks.OAK_LOG, 1L);
        LOG_REWARDS.put(Blocks.SPRUCE_LOG, 1L);
        LOG_REWARDS.put(Blocks.BIRCH_LOG, 1L);
        LOG_REWARDS.put(Blocks.JUNGLE_LOG, 2L);
        LOG_REWARDS.put(Blocks.ACACIA_LOG, 1L);
        LOG_REWARDS.put(Blocks.DARK_OAK_LOG, 1L);
        LOG_REWARDS.put(Blocks.MANGROVE_LOG, 2L);
        LOG_REWARDS.put(Blocks.CHERRY_LOG, 3L);
        LOG_REWARDS.put(Blocks.CRIMSON_STEM, 3L);
        LOG_REWARDS.put(Blocks.WARPED_STEM, 3L);

        // ----- PESCA -----
        FISH_REWARDS.put(new ItemStack(Items.COD), 3L);
        FISH_REWARDS.put(new ItemStack(Items.SALMON), 5L);
        FISH_REWARDS.put(new ItemStack(Items.TROPICAL_FISH), 10L);
        FISH_REWARDS.put(new ItemStack(Items.PUFFERFISH), 15L);
    }

    // Métodos de consulta
    public static long getBlockReward(Block block) {
        return BLOCK_REWARDS.getOrDefault(block, 0L);
    }

    public static long getMobReward(EntityType<?> type) {
        return MOB_REWARDS.getOrDefault(type, 0L);
    }

    public static long getLogReward(Block block) {
        return LOG_REWARDS.getOrDefault(block, 0L);
    }

    public static long getFishReward(ItemStack stack) {
        for (Map.Entry<ItemStack, Long> entry : FISH_REWARDS.entrySet()) {
            if (ItemStack.isSameItem(entry.getKey(), stack)) {
                return entry.getValue();
            }
        }
        return 0L;
    }
}