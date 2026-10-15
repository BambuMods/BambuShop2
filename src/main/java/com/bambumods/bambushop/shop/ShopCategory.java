package com.bambumods.bambushop.shop;

import java.util.Arrays;
import java.util.List;

public enum ShopCategory {

    // ===== COMPRA (6 categorías) =====
    BUY_BLOQUES("bloques", "Bloques", Side.BUY),
    BUY_COMIDA("comida", "Comida", Side.BUY),
    BUY_POCIONES("pociones", "Pociones", Side.BUY),
    BUY_COMBATE("combate", "Combate", Side.BUY),
    BUY_UTILERIA("utileria", "Utilería", Side.BUY),
    BUY_ESPECIALES("especiales", "Especiales", Side.BUY),

    // ===== VENTA (1 sola categoría, se accede desde un único botón) =====
    SELL("sell", "Vender", Side.SELL);

    public enum Side { BUY, SELL }

    private final String id;
    private final String displayName;
    private final Side side;

    ShopCategory(String id, String displayName, Side side) {
        this.id = id;
        this.displayName = displayName;
        this.side = side;
    }

    public String getId() { return id; }
    public String getDisplayName() { return displayName; }
    public Side getSide() { return side; }

    public static List<ShopCategory> getBuyCategories() {
        return Arrays.stream(values())
                .filter(c -> c.side == Side.BUY)
                .toList();
    }

    public static List<ShopCategory> getSellCategories() {
        return Arrays.stream(values())
                .filter(c -> c.side == Side.SELL)
                .toList();
    }

    public static ShopCategory fromId(String id) {
        for (ShopCategory c : values()) {
            if (c.id.equals(id)) return c;
        }
        return null;
    }
}