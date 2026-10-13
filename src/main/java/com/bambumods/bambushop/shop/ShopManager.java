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

public class ShopManager {

    private static final Map<Identifier, ShopEntry> BUY_ENTRIES = new HashMap<>();
    private static final Map<Identifier, ShopEntry> SELL_ENTRIES = new HashMap<>();
    private static final Map<ShopCategory, List<ShopEntry>> BY_CATEGORY = new HashMap<>();
    private static boolean loaded = false;

    public static void load(ShopConfig.ConfigData data) {
        BUY_ENTRIES.clear();
        SELL_ENTRIES.clear();
        BY_CATEGORY.clear();
        loaded = false;

        int buyCount = 0;
        int sellCount = 0;
        int invalidCount = 0;
        int conflictCount = 0;

        for (Map.Entry<ShopCategory, Map<String, Long>> catEntry : data.buy.entrySet()) {
            ShopCategory cat = catEntry.getKey();
            for (Map.Entry<String, Long> itemEntry : catEntry.getValue().entrySet()) {
                Identifier id = parseId(itemEntry.getKey());
                if (id == null) { invalidCount++; continue; }

                ShopEntry entry = new ShopEntry(id, itemEntry.getValue(), cat);
                if (!entry.isValid()) { invalidCount++; continue; }

                BUY_ENTRIES.put(id, entry);
                BY_CATEGORY.computeIfAbsent(cat, k -> new ArrayList<>()).add(entry);
                buyCount++;
            }
        }

        for (Map.Entry<ShopCategory, Map<String, Long>> catEntry : data.sell.entrySet()) {
            ShopCategory cat = catEntry.getKey();
            for (Map.Entry<String, Long> itemEntry : catEntry.getValue().entrySet()) {
                Identifier id = parseId(itemEntry.getKey());
                if (id == null) { invalidCount++; continue; }

                ShopEntry entry = new ShopEntry(id, itemEntry.getValue(), cat);
                if (!entry.isValid()) { invalidCount++; continue; }

                SELL_ENTRIES.put(id, entry);
                BY_CATEGORY.computeIfAbsent(cat, k -> new ArrayList<>()).add(entry);
                sellCount++;
            }
        }

        Set<Identifier> intersection = new HashSet<>(BUY_ENTRIES.keySet());
        intersection.retainAll(SELL_ENTRIES.keySet());
        if (!intersection.isEmpty()) {
            BambuShop.LOGGER.error("[Shop] CONFLICTO BUY/SELL: {}", intersection);
            conflictCount = intersection.size();
        }

        loaded = true;
        BambuShop.LOGGER.info("[Shop] Cargados {} compra, {} venta ({} inválidos, {} conflictos)",
                buyCount, sellCount, invalidCount, conflictCount);
    }

    private static Identifier parseId(String s) {
        try {
            return Identifier.parse(s);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean isLoaded() { return loaded; }

    public static boolean canBuy(ItemStack stack) {
        if (stack.isEmpty()) return false;
        return BUY_ENTRIES.containsKey(BuiltInRegistriesItemId(stack.getItem()));
    }

    public static boolean canSell(ItemStack stack) {
        if (stack.isEmpty()) return false;
        return SELL_ENTRIES.containsKey(BuiltInRegistriesItemId(stack.getItem()));
    }

    public static long getBuyPrice(ItemStack stack) {
        ShopEntry e = BUY_ENTRIES.get(BuiltInRegistriesItemId(stack.getItem()));
        return (e == null) ? 0 : e.getPrice();
    }

    public static long getSellPrice(ItemStack stack) {
        ShopEntry e = SELL_ENTRIES.get(BuiltInRegistriesItemId(stack.getItem()));
        return (e == null) ? 0 : e.getPrice();
    }

    public static List<ShopEntry> getEntries(ShopCategory cat) {
        return BY_CATEGORY.getOrDefault(cat, List.of());
    }

    /** Devuelve la entrada de compra de un item (o null si no se compra) */
    public static ShopEntry getBuyEntry(Identifier id) {
        return BUY_ENTRIES.get(id);
    }

    /** Devuelve la entrada de venta de un item (o null si no se vende) */
    public static ShopEntry getSellEntry(Identifier id) {
        return SELL_ENTRIES.get(id);
    }

    private static Identifier BuiltInRegistriesItemId(Item item) {
        return net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(item);
    }
}