package com.bambumods.bambushop.mob;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PassiveLootHelper {

    private static final Random RANDOM = new Random();

    /**
     * Da una lista de items de rankeo según el nivel del mob.
     * Solo items que NO se pueden minar.
     */
    public static List<ItemStack> generateRankItems(int level) {
        List<ItemStack> drops = new ArrayList<>();

        // Probabilidad de drop según nivel
        double dropChance;
        if (level <= 5) dropChance = 0.20;
        else if (level <= 10) dropChance = 0.40;
        else if (level <= 15) dropChance = 0.60;
        else dropChance = 0.80;

        if (RANDOM.nextDouble() > dropChance) {
            return drops; // No hay drop
        }

        // Cantidad de items a dropear (1-3)
        int count = 1 + RANDOM.nextInt(3);

        for (int i = 0; i < count; i++) {
            ItemStack stack = getRandomItemForLevel(level);
            if (stack != null) {
                drops.add(stack);
            }
        }

        return drops;
    }

    private static ItemStack getRandomItemForLevel(int level) {
        if (level <= 10) {
            // Prismarina o Amatista
            Item item = RANDOM.nextBoolean() ? Items.PRISMARINE_SHARD : Items.AMETHYST_SHARD;
            int amount = 1 + RANDOM.nextInt(4); // 1-4
            return new ItemStack(item, amount);
        } else if (level <= 15) {
            // Estrella del Nether o Cabeza de Wither
            if (RANDOM.nextBoolean()) {
                return new ItemStack(Items.NETHER_STAR, 1 + RANDOM.nextInt(2)); // 1-2
            } else {
                return new ItemStack(Items.WITHER_SKELETON_SKULL, 1 + RANDOM.nextInt(2)); // 1-2
            }
        } else {
            // Corazón del Mar o Cabeza de Dragón
            if (RANDOM.nextInt(10) == 0) { // 10% de cabeza de dragón
                return new ItemStack(Items.DRAGON_HEAD, 1);
            } else {
                return new ItemStack(Items.HEART_OF_THE_SEA, 1);
            }
        }
    }
}