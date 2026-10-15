package com.bambumods.bambushop.client.gui;

import com.bambumods.bambushop.menu.ShopCategoriesMenu;
import com.bambumods.bambushop.network.OpenSellGuiPacket;
import com.bambumods.bambushop.network.OpenShopCategoryPacket;
import com.bambumods.bambushop.shop.ShopCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;

public class ShopCategoriesScreen extends AbstractContainerScreen<ShopCategoriesMenu> {

    private static final int GUI_WIDTH = 260;
    private static final int GUI_HEIGHT = 240;

    public ShopCategoriesScreen(ShopCategoriesMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth = GUI_WIDTH;
        this.imageHeight = GUI_HEIGHT;
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = -10000;
        this.titleLabelY = -10000;

        int x = this.leftPos;
        int y = this.topPos;

        // Botones de COMPRA (6 categorías en 2 columnas)
        List<ShopCategory> buyCats = ShopCategory.getBuyCategories();
        int buyStartY = y + 48;
        for (int i = 0; i < buyCats.size(); i++) {
            ShopCategory cat = buyCats.get(i);
            int col = i % 2;
            int row = i / 2;
            int bx = x + 10 + col * 124;
            int by = buyStartY + row * 24;
            this.addRenderableWidget(
                    Button.builder(Component.literal(cat.getDisplayName()), b -> {
                                net.neoforged.neoforge.client.network.ClientPacketDistributor.sendToServer(
                                        new OpenShopCategoryPacket(cat.getId()));
                            })
                            .bounds(bx, by, 118, 20)
                            .build()
            );
        }

        // Botón grande VENDER
        int sellY = buyStartY + ((buyCats.size() + 1) / 2) * 24 + 14;
        this.addRenderableWidget(
                Button.builder(Component.literal("💰 VENDER 💰"), b -> {
                            net.neoforged.neoforge.client.network.ClientPacketDistributor.sendToServer(
                                    OpenSellGuiPacket.INSTANCE);
                        })
                        .bounds(x + 30, sellY, 200, 28)
                        .build()
        );

        // Botón Cerrar
        this.addRenderableWidget(
                Button.builder(Component.literal("Cerrar"), b -> this.onClose())
                        .bounds(x + GUI_WIDTH - 62, y + GUI_HEIGHT - 24, 52, 18)
                        .build()
        );
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(g, mouseX, mouseY, partialTick);
        super.render(g, mouseX, mouseY, partialTick);

        int x = this.leftPos;
        int y = this.topPos;

        g.fill(x, y, x + GUI_WIDTH, y + 26, 0xFF2A411F);
        g.fill(x, y + 26, x + GUI_WIDTH, y + 27, 0xFF7BA05B);

        String header = "BAMBU SHOP";
        int headerWidth = this.font.width(header);
        g.drawString(this.font, header,
                x + (GUI_WIDTH - headerWidth) / 2,
                y + 9,
                0xFFFFD75E, true);

        g.drawString(this.font, "COMPRAR", x + 10, y + 34, 0xFF7BFF7B, true);

        List<ShopCategory> buyCats = ShopCategory.getBuyCategories();
        int buyStartY = y + 48;
        int sellTitleY = buyStartY + ((buyCats.size() + 1) / 2) * 24 + 14;
        g.drawString(this.font, "VENDER", x + 10, sellTitleY - 12, 0xFFFF5555, true);

        ShopHudOverlay.render(g, x, y, GUI_WIDTH, menu.getMoney(), menu.getRank());
    }

    @Override
    protected void renderBg(GuiGraphics g, float partialTick, int mouseX, int mouseY) {
        int x = this.leftPos;
        int y = this.topPos;

        g.fill(x, y, x + GUI_WIDTH, y + GUI_HEIGHT, 0xFF3E2A1A);

        int border = 0xFF3E5C2E;
        g.fill(x, y, x + GUI_WIDTH, y + 1, border);
        g.fill(x, y + GUI_HEIGHT - 1, x + GUI_WIDTH, y + GUI_HEIGHT, border);
        g.fill(x, y, x + 1, y + GUI_HEIGHT, border);
        g.fill(x + GUI_WIDTH - 1, y, x + GUI_WIDTH, y + GUI_HEIGHT, border);
    }
}