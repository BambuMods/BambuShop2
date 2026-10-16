package com.bambumods.bambushop.mob;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LootDropHelper {

    private static final Random RANDOM = new Random();

    public static ItemStack generateLootDrop(ServerLevel level, int mobLevel) {
        double dropChance;
        if (mobLevel <= 5) dropChance = 0.02;
        else if (mobLevel <= 10) dropChance = 0.05;
        else if (mobLevel <= 15) dropChance = 0.10;
        else dropChance = 0.15;

        if (RANDOM.nextDouble() > dropChance) return null;

        int tipo = RANDOM.nextInt(7);
        Item item = switch (tipo) {
            case 0 -> getSwordMaterial(mobLevel);
            case 1 -> getPickaxeMaterial(mobLevel);
            case 2 -> getAxeMaterial(mobLevel);
            case 3 -> getArmorMaterial(mobLevel);
            case 4 -> Items.BOW;
            case 5 -> Items.CROSSBOW;
            case 6 -> Items.TRIDENT;
            default -> Items.IRON_SWORD;
        };

        ItemStack stack = new ItemStack(item);

        int minEnch, maxEnch;
        if (mobLevel <= 5) { minEnch = 1; maxEnch = 5; }
        else if (mobLevel <= 10) { minEnch = 5; maxEnch = 10; }
        else if (mobLevel <= 15) { minEnch = 10; maxEnch = 15; }
        else { minEnch = 15; maxEnch = 20; }

        applyEnchantments(level, stack, minEnch, maxEnch);
        return stack;
    }

    public static ItemStack generateOpLootDrop(ServerLevel level, int mobLevel, int minEnch, int maxEnch) {
        int tipo = RANDOM.nextInt(7);
        Item item = switch (tipo) {
            case 0 -> Items.NETHERITE_SWORD;
            case 1 -> Items.NETHERITE_PICKAXE;
            case 2 -> Items.NETHERITE_AXE;
            case 3 -> Items.NETHERITE_CHESTPLATE;
            case 4 -> Items.BOW;
            case 5 -> Items.CROSSBOW;
            case 6 -> Items.TRIDENT;
            default -> Items.NETHERITE_SWORD;
        };

        ItemStack stack = new ItemStack(item);
        applyEnchantments(level, stack, minEnch, maxEnch);
        return stack;
    }

    private static Item getSwordMaterial(int level) {
        if (level <= 5) return Items.IRON_SWORD;
        if (level <= 10) return Items.DIAMOND_SWORD;
        return Items.NETHERITE_SWORD;
    }

    private static Item getPickaxeMaterial(int level) {
        if (level <= 5) return Items.IRON_PICKAXE;
        if (level <= 10) return Items.DIAMOND_PICKAXE;
        return Items.NETHERITE_PICKAXE;
    }

    private static Item getAxeMaterial(int level) {
        if (level <= 5) return Items.IRON_AXE;
        if (level <= 10) return Items.DIAMOND_AXE;
        return Items.NETHERITE_AXE;
    }

    private static Item getArmorMaterial(int level) {
        if (level <= 5) return Items.IRON_CHESTPLATE;
        if (level <= 10) return Items.DIAMOND_CHESTPLATE;
        return Items.NETHERITE_CHESTPLATE;
    }

    private static void applyEnchantments(ServerLevel level, ItemStack stack, int minEnch, int maxEnch) {
        Enchantment ench = getRandomEnchantment(stack, level);
        if (ench != null) {
            int nivelEnch = randomCampana(minEnch, maxEnch);
            stack.enchant(getHolder(level, ench), nivelEnch);
        }

        if (RANDOM.nextDouble() < 0.30) {
            Enchantment ench2 = getRandomEnchantment(stack, level);
            if (ench2 != null && ench2 != ench) {
                int nivelEnch2 = randomCampana(minEnch, maxEnch) / 2;
                stack.enchant(getHolder(level, ench2), nivelEnch2);
            }
        }

        if (RANDOM.nextDouble() < 0.15) {
            Enchantment ench3 = getRandomEnchantment(stack, level);
            if (ench3 != null && ench3 != ench) {
                int nivelEnch3 = randomCampana(minEnch, maxEnch) / 3;
                stack.enchant(getHolder(level, ench3), nivelEnch3);
            }
        }
    }

    private static int randomCampana(int min, int max) {
        double r = RANDOM.nextDouble() * RANDOM.nextDouble();
        double invertido = 1.0 - r;
        return min + (int) ((max - min) * invertido);
    }

    private static Enchantment getRandomEnchantment(ItemStack stack, ServerLevel level) {
        List<Enchantment> opciones = new ArrayList<>();
        var reg = level.registryAccess();

        if (stack.is(Items.NETHERITE_SWORD) || stack.is(Items.DIAMOND_SWORD) || stack.is(Items.IRON_SWORD)) {
            opciones.add(reg.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SHARPNESS).value());
            opciones.add(reg.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.LOOTING).value());
            opciones.add(reg.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FIRE_ASPECT).value());
            opciones.add(reg.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.UNBREAKING).value());
        } else if (stack.is(Items.NETHERITE_PICKAXE) || stack.is(Items.DIAMOND_PICKAXE) || stack.is(Items.IRON_PICKAXE)) {
            opciones.add(reg.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE).value());
            opciones.add(reg.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.EFFICIENCY).value());
            opciones.add(reg.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.UNBREAKING).value());
        } else if (stack.is(Items.NETHERITE_AXE) || stack.is(Items.DIAMOND_AXE) || stack.is(Items.IRON_AXE)) {
            opciones.add(reg.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SHARPNESS).value());
            opciones.add(reg.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.EFFICIENCY).value());
            opciones.add(reg.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.UNBREAKING).value());
        } else if (stack.is(Items.NETHERITE_CHESTPLATE) || stack.is(Items.DIAMOND_CHESTPLATE) || stack.is(Items.IRON_CHESTPLATE)) {
            opciones.add(reg.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.PROTECTION).value());
            opciones.add(reg.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.THORNS).value());
            opciones.add(reg.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.UNBREAKING).value());
        } else if (stack.is(Items.BOW)) {
            opciones.add(reg.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.POWER).value());
            opciones.add(reg.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FLAME).value());
            opciones.add(reg.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.INFINITY).value());
        } else if (stack.is(Items.CROSSBOW)) {
            opciones.add(reg.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.QUICK_CHARGE).value());
            opciones.add(reg.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.MULTISHOT).value());
            opciones.add(reg.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.PIERCING).value());
        } else if (stack.is(Items.TRIDENT)) {
            opciones.add(reg.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.LOYALTY).value());
            opciones.add(reg.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.IMPALING).value());
            opciones.add(reg.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.CHANNELING).value());
        }

        if (opciones.isEmpty()) return null;
        return opciones.get(RANDOM.nextInt(opciones.size()));
    }

    private static Holder<Enchantment> getHolder(ServerLevel level, Enchantment ench) {
        return level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).wrapAsHolder(ench);
    }
}