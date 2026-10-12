package com.bambumods.bambushop.client.gui;

import com.bambumods.bambushop.menu.RankMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class RankMenuScreen extends AbstractContainerScreen<RankMenu> {

    private static final int PANEL_BG = 0xFF3E2A1A;       // marrón oscuro
    private static final int PANEL_HEADER = 0xFF3E5C2E;   // verde bambú oscuro
    private static final int TEXT_LIGHT = 0xFFFFF0D0;     // crema claro
    private static final int TEXT_OK = 0xFF7BFF7B;        // verde brillante
    private static final int TEXT_BAD = 0xFFFF5555;       // rojo brillante
    private static final int TEXT_GOLD = 0xFFFFD75E;      // dorado
    private static final int TEXT_HEADER = 0xFFFFF8E0;    // blanco cálido

    public RankMenuScreen(RankMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 256;
        this.imageHeight = 220;
    }

    @Override
    protected void init() {
        super.init();

        this.addRenderableWidget(
                Button.builder(Component.literal("⬅ Volver"), b -> this.onClose())
                        .bounds(this.leftPos + this.imageWidth / 2 - 40, this.topPos + this.imageHeight - 30, 80, 20)
                        .build()
        );
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        // Fondo del panel (marrón oscuro)
        graphics.fill(this.leftPos, this.topPos,
                this.leftPos + this.imageWidth, this.topPos + this.imageHeight,
                PANEL_BG);

        // Cabecera verde bambú (arriba, 40px de alto)
        graphics.fill(this.leftPos, this.topPos,
                this.leftPos + this.imageWidth, this.topPos + 40,
                PANEL_HEADER);

        // Bordes verde claro
        int border = 0xFF7BA05B;
        graphics.fill(this.leftPos, this.topPos,
                this.leftPos + this.imageWidth, this.topPos + 2, border);
        graphics.fill(this.leftPos, this.topPos + this.imageHeight - 2,
                this.leftPos + this.imageWidth, this.topPos + this.imageHeight, border);
        graphics.fill(this.leftPos, this.topPos,
                this.leftPos + 2, this.topPos + this.imageHeight, border);
        graphics.fill(this.leftPos + this.imageWidth - 2, this.topPos,
                this.leftPos + this.imageWidth, this.topPos + this.imageHeight, border);
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        // Vacío
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);

        int cx = this.leftPos + this.imageWidth / 2;

        // ===== CABECERA =====
        graphics.drawCenteredString(this.font, "🎖  RANGOS  🎖",
                cx, this.topPos + 16, TEXT_HEADER);

        // ===== RANGO ACTUAL =====
        int left = this.leftPos + 15;
        int y = this.topPos + 55;

        graphics.drawString(this.font, "Rango actual:", left, y, TEXT_GOLD, true);
        graphics.drawString(this.font, "  " + getRankName(this.menu.getCurrentRank()),
                left + 80, y, TEXT_LIGHT, true);

        y += 15;
        graphics.drawString(this.font, "Siguiente:", left, y, TEXT_GOLD, true);
        graphics.drawString(this.font, "  " + this.menu.getNextRankName(),
                left + 80, y, TEXT_LIGHT, true);

        // ===== DINERO =====
        y += 22;
        long money = this.menu.getMoney();
        long cost = this.menu.getMoneyCost();
        boolean moneyOk = money >= cost;
        int moneyColor = moneyOk ? TEXT_OK : TEXT_BAD;
        graphics.drawString(this.font, "💵", left, y, TEXT_GOLD, true);
        graphics.drawString(this.font, money + " / " + cost + " $", left + 20, y, moneyColor, true);

        // ===== XP =====
        y += 15;
        int xp = this.menu.getXpLevel();
        int xpCost = this.menu.getXpCost();
        boolean xpOk = xp >= xpCost;
        int xpColor = xpOk ? TEXT_OK : TEXT_BAD;
        graphics.drawString(this.font, "✨", left, y, TEXT_GOLD, true);
        graphics.drawString(this.font, xp + " / " + xpCost + " niveles", left + 20, y, xpColor, true);

        // ===== REQUISITOS =====
        y += 22;
        graphics.drawString(this.font, "📦 Requisitos:", left, y, TEXT_GOLD, true);
        y += 14;

        for (RankMenu.SyncedReq req : this.menu.getRequirements()) {
            boolean ok = req.has >= req.needed;
            int color = ok ? TEXT_OK : TEXT_BAD;
            String check = ok ? "✔" : "✘";

            graphics.drawString(this.font, check, left + 5, y, color, true);

            // Nombre del item
            String itemName = req.stack.getHoverName().getString();
            graphics.drawString(this.font, itemName, left + 20, y, TEXT_LIGHT, true);

            // Contador a la derecha
            String countText = req.has + " / " + req.needed;
            graphics.drawString(this.font, countText,
                    this.leftPos + this.imageWidth - 60, y, color, true);

            y += 14;
        }

        if (this.menu.getRequirements().isEmpty() && this.menu.getNextRankLevel() != -1) {
            graphics.drawString(this.font, "(sin requisitos)", left + 10, y, TEXT_LIGHT, true);
        }

        if (this.menu.getNextRankLevel() == -1) {
            graphics.drawString(this.font, "¡Has alcanzado el rango máximo!",
                    left, y, TEXT_GOLD, true);
        }
    }

    private String getRankName(int level) {
        com.bambumods.bambushop.rank.RankData data =
                com.bambumods.bambushop.rank.RankData.getByLevel(level);
        return data != null ? data.getName() : "???";
    }
}