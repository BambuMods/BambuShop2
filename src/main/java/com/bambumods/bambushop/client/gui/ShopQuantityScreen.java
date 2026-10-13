package com.bambumods.bambushop.client.gui;

import com.bambumods.bambushop.attachment.ModAttachments;
import com.bambumods.bambushop.menu.ShopQuantityMenu;
import com.bambumods.bambushop.network.BuyItemPacket;
import com.bambumods.bambushop.shop.ShopEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class ShopQuantityScreen extends AbstractContainerScreen<ShopQuantityMenu> {

    private static final int GUI_WIDTH = 220;
    private static final int GUI_HEIGHT = 240;

    private int quantity = 1;

    public ShopQuantityScreen(ShopQuantityMenu menu, Inventory inv, Component title) {
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

        // Fila 1: -1 -10 -64
        this.addRenderableWidget(Button.builder(Component.literal("-1"), b -> change(-1))
                .bounds(x + 20, y + 90, 50, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("-10"), b -> change(-10))
                .bounds(x + 80, y + 90, 60, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("-64"), b -> change(-64))
                .bounds(x + 150, y + 90, 50, 20).build());

        // Fila 2: +1 +10 +64
        this.addRenderableWidget(Button.builder(Component.literal("+1"), b -> change(1))
                .bounds(x + 20, y + 120, 50, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("+10"), b -> change(10))
                .bounds(x + 80, y + 120, 60, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("+64"), b -> change(64))
                .bounds(x + 150, y + 120, 50, 20).build());

        // Botón Volver (izquierda)
        this.addRenderableWidget(Button.builder(Component.literal("⬅ Volver"), b -> {
                    net.neoforged.neoforge.client.network.ClientPacketDistributor.sendToServer(
                            new com.bambumods.bambushop.network.OpenShopCategoriesPacket());
                })
                .bounds(x + 15, y + GUI_HEIGHT - 28, 80, 20).build());

        // Botón Comprar (derecha)
        this.addRenderableWidget(Button.builder(Component.literal("✔ Comprar"), b -> {
                    if (menu.getEntry() == null) return;
                    net.neoforged.neoforge.client.network.ClientPacketDistributor.sendToServer(
                            new BuyItemPacket(menu.getEntry().getItemId().toString(), quantity));
                })
                .bounds(x + GUI_WIDTH - 95, y + GUI_HEIGHT - 28, 80, 20).build());
    }

    private void change(int delta) {
        quantity = Math.max(1, quantity + delta);
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(g, mouseX, mouseY, partialTick);
        super.render(g, mouseX, mouseY, partialTick);

        int x = this.leftPos;
        int y = this.topPos;

        g.fill(x, y, x + GUI_WIDTH, y + 26, 0xFF2A411F);
        g.fill(x, y + 26, x + GUI_WIDTH, y + 27, 0xFF7BA05B);

        String title = "CANTIDAD";
        int titleWidth = this.font.width(title);
        g.drawString(this.font, title,
                x + (GUI_WIDTH - titleWidth) / 2,
                y + 9,
                0xFFFFD75E, true);

        ShopEntry entry = menu.getEntry();
        if (entry == null) {
            g.drawString(this.font, "Error: item no encontrado", x + 20, y + 60, 0xFFFF5555, true);
            return;
        }

        ItemStack stack = entry.createStack();
        int iconX = x + GUI_WIDTH / 2 - 16;
        g.renderItem(stack, iconX, y + 45);
        g.renderItemDecorations(this.font, stack, iconX, y + 45);

        String qtyText = "x" + quantity;
        int qtyWidth = this.font.width(qtyText);
        g.drawString(this.font, qtyText,
                x + (GUI_WIDTH - qtyWidth) / 2,
                y + 70,
                0xFFFFF0D0, true);

        long total = entry.getPrice() * quantity;
        String totalText = "Total: " + ShopHudOverlay.formatMoney(total) + " $";
        int totalWidth = this.font.width(totalText);
        g.drawString(this.font, totalText,
                x + (GUI_WIDTH - totalWidth) / 2,
                y + 155,
                0xFFFFD75E, true);

        long money = ModAttachments.getMoney(Minecraft.getInstance().player);
        String moneyText = "Tienes: " + ShopHudOverlay.formatMoney(money) + " $";
        int moneyWidth = this.font.width(moneyText);
        int moneyColor = (money >= total) ? 0xFF7BFF7B : 0xFFFF5555;
        g.drawString(this.font, moneyText,
                x + (GUI_WIDTH - moneyWidth) / 2,
                y + 172,
                moneyColor, true);

        ShopNotifications.render(g, this.width, y);
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