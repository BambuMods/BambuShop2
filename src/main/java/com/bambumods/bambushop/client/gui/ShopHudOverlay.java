package com.bambumods.bambushop.client.gui;

import com.bambumods.bambushop.rank.RankData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class ShopHudOverlay {

    /** Versión estándar: se dibuja justo encima de la GUI, alineado a la derecha */
    public static void render(GuiGraphics g, int guiLeft, int guiTop, int guiWidth, long money, int rankLevel) {
        Minecraft mc = Minecraft.getInstance();
        RankData rank = RankData.getByLevel(rankLevel);
        String rankName = (rank != null) ? rank.getName() : "Sin Rango";

        String moneyLine = "💰 " + formatMoney(money) + " $";
        String rankLine = "🎖 " + rankName;

        int textWidth = Math.max(mc.font.width(moneyLine), mc.font.width(rankLine));
        int boxWidth = textWidth + 20;
        int boxHeight = 32;

        int bx = guiLeft + guiWidth - boxWidth;
        int by = guiTop - boxHeight - 4;

        drawBox(g, bx, by, boxWidth, boxHeight, moneyLine, rankLine);
    }

    /** Versión custom: se dibuja en una posición concreta (x, y) */
    public static void renderAt(GuiGraphics g, int x, int y, long money, int rankLevel) {
        Minecraft mc = Minecraft.getInstance();
        RankData rank = RankData.getByLevel(rankLevel);
        String rankName = (rank != null) ? rank.getName() : "Sin Rango";

        String moneyLine = "💰 " + formatMoney(money) + " $";
        String rankLine = "🎖 " + rankName;

        int textWidth = Math.max(mc.font.width(moneyLine), mc.font.width(rankLine));
        int boxWidth = textWidth + 20;
        int boxHeight = 32;

        drawBox(g, x, y, boxWidth, boxHeight, moneyLine, rankLine);
    }

    private static void drawBox(GuiGraphics g, int bx, int by, int boxWidth, int boxHeight,
                                String moneyLine, String rankLine) {
        Minecraft mc = Minecraft.getInstance();

        g.fill(bx, by, bx + boxWidth, by + boxHeight, 0xE03E2A1A);
        int border = 0xFF7BA05B;
        g.fill(bx, by, bx + boxWidth, by + 1, border);
        g.fill(bx, by + boxHeight - 1, bx + boxWidth, by + boxHeight, border);
        g.fill(bx, by, bx + 1, by + boxHeight, border);
        g.fill(bx + boxWidth - 1, by, bx + boxWidth, by + boxHeight, border);

        g.drawString(mc.font, moneyLine, bx + 10, by + 6, 0xFFFFD75E, true);
        g.drawString(mc.font, rankLine, bx + 10, by + 18, 0xFFFFF0D0, true);
    }

    public static String formatMoney(long amount) {
        String s = Long.toString(amount);
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            sb.insert(0, s.charAt(i));
            count++;
            if (count % 3 == 0 && i > 0) sb.insert(0, '.');
        }
        return sb.toString();
    }
}