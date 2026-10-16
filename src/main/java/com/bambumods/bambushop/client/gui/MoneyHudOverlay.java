package com.bambumods.bambushop.client.gui;

import com.bambumods.bambushop.client.ClientPlayerDataCache;
import com.bambumods.bambushop.rank.RankData;
import com.bambumods.bambushop.util.MoneyFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class MoneyHudOverlay {

    public static void render(GuiGraphics g, int screenWidth) {
        Minecraft mc = Minecraft.getInstance();

        long money = ClientPlayerDataCache.getMoney();
        int rankLevel = ClientPlayerDataCache.getRank();
        boolean autoSell = ClientPlayerDataCache.isAutoSell();

        RankData rank = RankData.getByLevel(rankLevel);
        String rankName = (rank != null) ? rank.getName() : "Sin Rango";

        String moneyLine = "Dinero: " + MoneyFormat.format(money) + "$";
        String rankLine = "Rango: " + rankName;
        String autoLine = "Auto-Sell: " + (autoSell ? "ON" : "OFF");
        int autoColor = autoSell ? 0xFF7BFF7B : 0xFF808080;

        int boxWidth = 140;
        int boxHeight = 48;
        int bx = screenWidth - boxWidth - 8;
        int by = 8;

        // Fondo opaco
        g.fill(bx, by, bx + boxWidth, by + boxHeight, 0xFF3E2A1A);
        int border = 0xFF7BA05B;
        g.fill(bx, by, bx + boxWidth, by + 1, border);
        g.fill(bx, by + boxHeight - 1, bx + boxWidth, by + boxHeight, border);
        g.fill(bx, by, bx + 1, by + boxHeight, border);
        g.fill(bx + boxWidth - 1, by, bx + boxWidth, by + boxHeight, border);

        // Texto
        g.drawString(mc.font, moneyLine, bx + 8, by + 6, 0xFFFFD75E, true);
        g.drawString(mc.font, rankLine, bx + 8, by + 18, 0xFFFFF0D0, true);
        g.drawString(mc.font, autoLine, bx + 8, by + 30, autoColor, true);
    }
}