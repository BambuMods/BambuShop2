package com.bambumods.bambushop.shop;

import java.util.HashMap;
import java.util.Map;

/**
 * Datos por defecto de la tienda. Se usan solo la primera vez
 * (cuando no existe shop.json). Después el jugador edita el JSON a su gusto.
 *
 * REGLA: COMPRA y VENTA NUNCA comparten items.
 * - COMPRA: armas, armaduras, herramientas, pociones, bloques, comida, utilería, especiales
 * - VENTA: minerales en bruto/procesados, drops de mobs, drops especiales
 */
public class DefaultShopData {

    public static ShopConfig.ConfigData create() {
        ShopConfig.ConfigData data = new ShopConfig.ConfigData();

        // ===== COMPRA =====

        // --- Bloques ---
        addBuy(data, ShopCategory.BUY_BLOQUES, "minecraft:stone", 1);
        addBuy(data, ShopCategory.BUY_BLOQUES, "minecraft:dirt", 1);
        addBuy(data, ShopCategory.BUY_BLOQUES, "minecraft:cobblestone", 1);
        addBuy(data, ShopCategory.BUY_BLOQUES, "minecraft:oak_log", 3);
        addBuy(data, ShopCategory.BUY_BLOQUES, "minecraft:spruce_log", 3);
        addBuy(data, ShopCategory.BUY_BLOQUES, "minecraft:glass", 5);
        addBuy(data, ShopCategory.BUY_BLOQUES, "minecraft:sand", 2);

        // --- Comida ---
        addBuy(data, ShopCategory.BUY_COMIDA, "minecraft:bread", 10);
        addBuy(data, ShopCategory.BUY_COMIDA, "minecraft:cooked_beef", 15);
        addBuy(data, ShopCategory.BUY_COMIDA, "minecraft:cooked_porkchop", 15);
        addBuy(data, ShopCategory.BUY_COMIDA, "minecraft:golden_apple", 200);
        addBuy(data, ShopCategory.BUY_COMIDA, "minecraft:enchanted_golden_apple", 20000);

        // --- Pociones ---
        addBuy(data, ShopCategory.BUY_POCIONES, "minecraft:potion", 50);
        addBuy(data, ShopCategory.BUY_POCIONES, "minecraft:splash_potion", 100);
        addBuy(data, ShopCategory.BUY_POCIONES, "minecraft:lingering_potion", 150);

        // --- Combate (armas, armaduras, herramientas) ---
        addBuy(data, ShopCategory.BUY_COMBATE, "minecraft:iron_sword", 200);
        addBuy(data, ShopCategory.BUY_COMBATE, "minecraft:diamond_sword", 800);
        addBuy(data, ShopCategory.BUY_COMBATE, "minecraft:netherite_sword", 3000);
        addBuy(data, ShopCategory.BUY_COMBATE, "minecraft:iron_pickaxe", 250);
        addBuy(data, ShopCategory.BUY_COMBATE, "minecraft:diamond_pickaxe", 1000);
        addBuy(data, ShopCategory.BUY_COMBATE, "minecraft:netherite_pickaxe", 3500);
        addBuy(data, ShopCategory.BUY_COMBATE, "minecraft:iron_helmet", 200);
        addBuy(data, ShopCategory.BUY_COMBATE, "minecraft:iron_chestplate", 300);
        addBuy(data, ShopCategory.BUY_COMBATE, "minecraft:iron_leggings", 250);
        addBuy(data, ShopCategory.BUY_COMBATE, "minecraft:iron_boots", 200);
        addBuy(data, ShopCategory.BUY_COMBATE, "minecraft:diamond_helmet", 800);
        addBuy(data, ShopCategory.BUY_COMBATE, "minecraft:diamond_chestplate", 1200);
        addBuy(data, ShopCategory.BUY_COMBATE, "minecraft:diamond_leggings", 1000);
        addBuy(data, ShopCategory.BUY_COMBATE, "minecraft:diamond_boots", 800);
        addBuy(data, ShopCategory.BUY_COMBATE, "minecraft:bow", 50);
        addBuy(data, ShopCategory.BUY_COMBATE, "minecraft:arrow", 1);
        addBuy(data, ShopCategory.BUY_COMBATE, "minecraft:shield", 100);

        // --- Utilería ---
        addBuy(data, ShopCategory.BUY_UTILERIA, "minecraft:torch", 2);
        addBuy(data, ShopCategory.BUY_UTILERIA, "minecraft:chest", 15);
        addBuy(data, ShopCategory.BUY_UTILERIA, "minecraft:crafting_table", 10);
        addBuy(data, ShopCategory.BUY_UTILERIA, "minecraft:furnace", 15);
        addBuy(data, ShopCategory.BUY_UTILERIA, "minecraft:anvil", 500);
        addBuy(data, ShopCategory.BUY_UTILERIA, "minecraft:enchanting_table", 1000);
        addBuy(data, ShopCategory.BUY_UTILERIA, "minecraft:ender_chest", 2000);
        addBuy(data, ShopCategory.BUY_UTILERIA, "minecraft:white_bed", 30);
        addBuy(data, ShopCategory.BUY_UTILERIA, "minecraft:red_bed", 30);

        // --- Especiales ---
        addBuy(data, ShopCategory.BUY_ESPECIALES, "minecraft:elytra", 100000);
        addBuy(data, ShopCategory.BUY_ESPECIALES, "minecraft:totem_of_undying", 80000);
        addBuy(data, ShopCategory.BUY_ESPECIALES, "minecraft:shulker_shell", 5000);
        addBuy(data, ShopCategory.BUY_ESPECIALES, "minecraft:trident", 50000);
        addBuy(data, ShopCategory.BUY_ESPECIALES, "minecraft:beacon", 60000);

        // ===== VENTA =====
        // (Solo minerales, drops de mobs, drops especiales)

        addSell(data, ShopCategory.SELL_MINERALES, "minecraft:coal", 3);
        addSell(data, ShopCategory.SELL_MINERALES, "minecraft:raw_iron", 10);
        addSell(data, ShopCategory.SELL_MINERALES, "minecraft:iron_ingot", 20);
        addSell(data, ShopCategory.SELL_MINERALES, "minecraft:raw_gold", 15);
        addSell(data, ShopCategory.SELL_MINERALES, "minecraft:gold_ingot", 35);
        addSell(data, ShopCategory.SELL_MINERALES, "minecraft:diamond", 100);
        addSell(data, ShopCategory.SELL_MINERALES, "minecraft:emerald", 150);
        addSell(data, ShopCategory.SELL_MINERALES, "minecraft:netherite_ingot", 500);
        addSell(data, ShopCategory.SELL_MINERALES, "minecraft:ancient_debris", 400);
        addSell(data, ShopCategory.SELL_MINERALES, "minecraft:redstone", 4);
        addSell(data, ShopCategory.SELL_MINERALES, "minecraft:lapis_lazuli", 6);
        addSell(data, ShopCategory.SELL_MINERALES, "minecraft:quartz", 5);
        addSell(data, ShopCategory.SELL_MINERALES, "minecraft:amethyst_shard", 20);

        addSell(data, ShopCategory.SELL_DROPS_MOBS, "minecraft:bone", 3);
        addSell(data, ShopCategory.SELL_DROPS_MOBS, "minecraft:feather", 2);
        addSell(data, ShopCategory.SELL_DROPS_MOBS, "minecraft:string", 2);
        addSell(data, ShopCategory.SELL_DROPS_MOBS, "minecraft:gunpowder", 10);
        addSell(data, ShopCategory.SELL_DROPS_MOBS, "minecraft:ender_pearl", 30);
        addSell(data, ShopCategory.SELL_DROPS_MOBS, "minecraft:blaze_rod", 50);
        addSell(data, ShopCategory.SELL_DROPS_MOBS, "minecraft:ghast_tear", 60);
        addSell(data, ShopCategory.SELL_DROPS_MOBS, "minecraft:slime_ball", 5);
        addSell(data, ShopCategory.SELL_DROPS_MOBS, "minecraft:spider_eye", 5);
        addSell(data, ShopCategory.SELL_DROPS_MOBS, "minecraft:rotten_flesh", 1);
        addSell(data, ShopCategory.SELL_DROPS_MOBS, "minecraft:leather", 3);
        addSell(data, ShopCategory.SELL_DROPS_MOBS, "minecraft:magma_cream", 15);
        addSell(data, ShopCategory.SELL_DROPS_MOBS, "minecraft:phantom_membrane", 25);
        addSell(data, ShopCategory.SELL_DROPS_MOBS, "minecraft:wither_skeleton_skull", 500);
        addSell(data, ShopCategory.SELL_DROPS_MOBS, "minecraft:zombie_head", 50);
        addSell(data, ShopCategory.SELL_DROPS_MOBS, "minecraft:creeper_head", 50);
        addSell(data, ShopCategory.SELL_DROPS_MOBS, "minecraft:skeleton_skull", 50);

        addSell(data, ShopCategory.SELL_DROPS_ESPECIALES, "minecraft:nether_star", 5000);
        addSell(data, ShopCategory.SELL_DROPS_ESPECIALES, "minecraft:dragon_head", 20000);
        addSell(data, ShopCategory.SELL_DROPS_ESPECIALES, "minecraft:dragon_egg", 50000);
        addSell(data, ShopCategory.SELL_DROPS_ESPECIALES, "minecraft:heart_of_the_sea", 1000);

        return data;
    }

    private static void addBuy(ShopConfig.ConfigData data, ShopCategory cat, String id, long price) {
        data.buy.computeIfAbsent(cat, k -> new HashMap<>()).put(id, price);
    }

    private static void addSell(ShopConfig.ConfigData data, ShopCategory cat, String id, long price) {
        data.sell.computeIfAbsent(cat, k -> new HashMap<>()).put(id, price);
    }
}