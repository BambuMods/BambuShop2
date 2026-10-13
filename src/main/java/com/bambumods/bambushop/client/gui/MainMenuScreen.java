package com.bambumods.bambushop.client.gui;

import com.bambumods.bambushop.menu.MainMenu;
import com.bambumods.bambushop.network.OpenRankGuiPacket;
import com.bambumods.bambushop.network.OpenShopCategoriesPacket;
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

        // Botón TIENDA - abre la GUI de categorías
        this.addRenderableWidget(
                Button.builder(Component.literal("🛒 TIENDA"), b -> {
                            net.neoforged.neoforge.client.network.ClientPacketDistributor.sendToServer(OpenShopCategoriesPacket.INSTANCE);
                        })
                        .bounds(centerX - 90, centerY - 20, 80, 40)
                        .build()
        );

        // Botón RANGOS - abre la GUI de rangos
        this.addRenderableWidget(
                Button.builder(Component.literal("🎖️ RANGOS"), b -> {
                            net.neoforged.neoforge.client.network.ClientPacketDistributor.sendToServer(OpenRankGuiPacket.INSTANCE);
                        })
                        .bounds(centerX + 10, centerY - 20, 80, 40)
                        .build()
        );

        // Botón Cerrar
        this.addRenderableWidget(
                Button.builder(Component.literal("Cerrar"), b -> this.onClose())
                        .bounds(centerX - 40, this.topPos + this.imageHeight - 30, 80, 20)
                        .build()
        );
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        graphics.fill(this.leftPos, this.topPos,
                this.leftPos + this.imageWidth, this.topPos + this.imageHeight,
                0xFFE8D5A8);

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
        // Vacío
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);

        int centerX = this.leftPos + this.imageWidth / 2;

        graphics.drawCenteredString(
                this.font,
                "🌿 BAMBU SHOP 🌿",
                centerX,
                this.topPos + 20,
                0xFF3E5C2E
        );

        String moneyText = "💵 " + this.menu.getMoney() + " $";
        graphics.drawCenteredString(
                this.font,
                moneyText,
                centerX,
                this.topPos + 45,
                0xFFD4A537
        );
    }
}