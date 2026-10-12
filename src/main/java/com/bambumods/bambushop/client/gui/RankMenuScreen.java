package com.bambumods.bambushop.client.gui;

import com.bambumods.bambushop.menu.RankMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class RankMenuScreen extends AbstractContainerScreen<RankMenu> {

    public RankMenuScreen(RankMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 256;
        this.imageHeight = 220;
    }

    @Override
    protected void init() {
        super.init();

        // Botón Volver
        this.addRenderableWidget(
                Button.builder(Component.literal("⬅️ Volver"), b -> this.onClose())
                        .bounds(this.leftPos + this.imageWidth / 2 - 40, this.topPos + this.imageHeight - 30, 80, 20)
                        .build()
        );
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        // Fondo beige bambú
        graphics.fill(this.leftPos, this.topPos,
                this.leftPos + this.imageWidth, this.topPos + this.imageHeight,
                0xFFE8D5A8);

        // Bordes verde bambú
        graphics.fill(this.leftPos, this.topPos,
                this.leftPos + this.imageWidth, this.topPos + 2, 0xFF3E5C2E);
        graphics.fill(this.leftPos, this.topPos + this.imageHeight - 2,
                this.leftPos + this.imageWidth, this.topPos + this.imageHeight, 0xFF3E5C2E);
        graphics.fill(this.leftPos, this.topPos,
                this.leftPos + 2, this.topPos + this.imageHeight, 0xFF3E5C2E);
        graphics.fill(this.leftPos + this.imageWidth - 2, this.topPos,
                this.leftPos + this.imageWidth, this.topPos + this.imageHeight, 0xFF3E5C2E);
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        // Vacío a propósito
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);

        // Título provisional
        graphics.drawCenteredString(
                this.font,
                "🎖️ RANGOS - BAMBU SHOP 🎖️",
                this.leftPos + this.imageWidth / 2,
                this.topPos + 20,
                0xFF3E5C2E
        );
    }
}