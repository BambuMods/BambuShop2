package com.bambumods.bambushop.shop;

import com.bambumods.bambushop.BambuShop;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Cerebro de la tienda. Carga las entradas del JSON y responde consultas.
 * IMPORTANTE: hay CERO intersección entre BUY y SELL (anti-exploit).
 */
public class ShopManager {

    /** Entradas de compra: itemId -> entry */
    private static final Map<Identifier, ShopEntry> BUY_ENTRIES = new HashMap<>();

    /** Entradas de venta: itemId -> entry */
    private static final Map<Identifier, ShopEntry> SELL_ENTRIES = new HashMap<>();

    /** Entradas por categoría (para construir las GUIs) */
    private static final Map<ShopCategory, List<ShopEntry>> BY_CATEGORY = new HashMap<>();

    /** Estado de carga */
    private static boolean loaded = false;

    // ===== CARGA =====

    public static void load(ShopConfig.ConfigData data) {
        BUY_ENTRIES.clear();
        SELL_ENTRIES.clear();
        BY_CATEGORY.clear();
        loaded = false;

        int buyCount = 0;
        int sellCount = 0;
        int invalidCount = 0;
        int conflictCount = 0;

        // Cargar COMPRA
        for (Map.Entry<ShopCategory, Map<String, Long>> catEntry : data.buy.entrySet()) {
            ShopCategory cat = catEntry.getKey();
            for (Map.Entry<String, Long> itemEntry : catEntry.getValue().entrySet()) {
                Identifier id = parseId(itemEntry.getKey());
                if (id == null) {
                    BambuShop.LOGGER.warn("[Shop] ID de item inválido: {}", itemEntry.getKey());
                    invalidCount++;
                    continue;
                }

                ShopEntry entry = new ShopEntry(id, itemEntry.getValue(), cat);
                if (!entry.isValid()) {
                    BambuShop.LOGGER.warn("[Shop] Item no existe: {}", id);
                    invalidCount++;
                    continue;
                }

                BUY_ENTRIES.put(id, entry);
                BY_CATEGORY.computeIfAbsent(cat, k -> new ArrayList<>()).add(entry);
                buyCount++;
            }
        }

        // Cargar VENTA
        for (Map.Entry<ShopCategory, Map<String, Long>> catEntry : data.sell.entrySet()) {
            ShopCategory cat = catEntry.getKey();
            for (Map.Entry<String, Long> itemEntry : catEntry.getValue().entrySet()) {
                Identifier id = parseId(itemEntry.getKey());
                if (id == null) {
                    BambuShop.LOGGER.warn("[Shop] ID de item inválido: {}", itemEntry.getKey());
                    invalidCount++;
                    continue;
                }

                ShopEntry entry = new ShopEntry(id, itemEntry.getValue(), cat);
                if (!entry.isValid()) {
                    BambuShop.LOGGER.warn("[Shop] Item no existe: {}", id);
                    invalidCount++;
                    continue;
                }

                SELL_ENTRIES.put(id, entry);
                BY_CATEGORY.computeIfAbsent(cat, k -> new ArrayList<>()).add(entry);
                sellCount++;
            }
        }

        // Comprobar conflictos BUY vs SELL (anti-exploit)
        Set<Identifier> intersection = new HashSet<>(BUY_ENTRIES.keySet());
        intersection.retainAll(SELL_ENTRIES.keySet());
        if (!intersection.isEmpty()) {
            BambuShop.LOGGER.error("[Shop] ⚠️ CONFLICTO BUY/SELL detectado en: {}", intersection);
            BambuShop.LOGGER.error("[Shop] Estos items están en compra y venta. ELIMÍNALOS de una de las dos listas.");
            conflictCount = intersection.size();
        }

        loaded = true;

        BambuShop.LOGGER.info("[Shop] Cargados {} items de compra, {} de venta ({} inválidos, {} conflictos)",
                buyCount, sellCount, invalidCount, conflictCount);
    }

    private static Identifier parseId(String s) {
        try {
            return Identifier.parse(s);
        } catch (Exception e) {
            return null;
        }
    }

    // ===== CONSULTAS =====

    public static boolean isLoaded() { return loaded; }

    /** ¿Se puede comprar este item? */
    public static boolean canBuy(ItemStack stack) {
        if (stack.isEmpty()) return false;
        Identifier id = BuiltInRegistriesItemId(stack.getItem());
        return BUY_ENTRIES.containsKey(id);
    }

    /** ¿Se puede vender este item? */
    public static boolean canSell(ItemStack stack) {
        if (stack.isEmpty()) return false;
        Identifier id = BuiltInRegistriesItemId(stack.getItem());
        return SELL_ENTRIES.containsKey(id);
    }

    /** Precio de compra de UN item. 0 si no se puede comprar. */
    public static long getBuyPrice(ItemStack stack) {
        Identifier id = BuiltInRegistriesItemId(stack.getItem());
        ShopEntry e = BUY_ENTRIES.get(id);
        return (e == null) ? 0 : e.getPrice();
    }

    /** Precio de venta de UN item. 0 si no se puede vender. */
    public static long getSellPrice(ItemStack stack) {
        Identifier id = BuiltInRegistriesItemId(stack.getItem());
        ShopEntry e = SELL_ENTRIES.get(id);
        return (e == null) ? 0 : e.getPrice();
    }

    /** Entradas de una categoría (para GUIs). */
    public static List<ShopEntry> getEntries(ShopCategory cat) {
        return BY_CATEGORY.getOrDefault(cat, List.of());
    }

    // Helper para obtener el Identifier de un Item
    private static Identifier BuiltInRegistriesItemId(Item item) {
        return net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(item);
    }
}