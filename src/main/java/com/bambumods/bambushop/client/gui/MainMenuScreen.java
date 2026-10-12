package com.bambumods.bambushop.client.gui;

import com.bambumods.bambushop.menu.MainMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class MainMenuScreen extends AbstractContainerScreen<MainMenu> {

    public MainMenuScreen(MainMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 256;
        this.imageHeight = 200;
    }

    @Override
    protected void init() {
        super.init();

        int centerX = this.leftPos + this.imageWidth / 2;
        int centerY = this.topPos + this.imageHeight / 2;

        // Botón TIENDA (izquierda)
        this.addRenderableWidget(
                Button.builder(Component.literal("🛒 TIENDA"), b -> this.onClose())
                        .bounds(centerX - 90, centerY - 20, 80, 40)
                        .build()
        );

        // Botón RANGOS (derecha)
        this.addRenderableWidget(
                Button.builder(Component.literal("🎖️ RANGOS"), b -> this.onClose())
                        .bounds(centerX + 10, centerY - 20, 80, 40)
                        .build()
        );

        // Botón Cerrar (abajo)
        this.addRenderableWidget(
                Button.builder(Component.literal("Cerrar"), b -> this.onClose())
                        .bounds(centerX - 40, this.topPos + this.imageHeight - 30, 80, 20)
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
        // Vacío a propósito: quitamos "Bambu Shop" e "Inventory"
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);

        // Título centrado
        graphics.drawCenteredString(
                this.font,
                "🌿 BAMBU SHOP 🌿",
                this.leftPos + this.imageWidth / 2,
                this.topPos + 20,
                0xFF3E5C2E
        );
    }
}