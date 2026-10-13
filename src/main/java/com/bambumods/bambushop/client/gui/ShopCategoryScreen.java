package com.bambumods.bambushop.client.gui;

import com.bambumods.bambushop.menu.ShopCategoryMenu;
import com.bambumods.bambushop.network.OpenShopCategoryItemPacket;
import com.bambumods.bambushop.network.OpenShopCategoriesPacket;
import com.bambumods.bambushop.shop.ShopEntry;
import com.bambumods.bambushop.shop.ShopManager;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ShopCategoryScreen extends AbstractContainerScreen<ShopCategoryMenu> {

    private static final int GUI_WIDTH = 260;
    private static final int GUI_HEIGHT = 240;
    private static final int SLOT_SIZE = 28;
    private static final int SLOT_ICON_SIZE = 20;

    public ShopCategoryScreen(ShopCategoryMenu menu, Inventory inv, Component title) {
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

        this.addRenderableWidget(
                Button.builder(Component.literal("⬅ Volver"), b -> {
                            net.neoforged.neoforge.client.network.ClientPacketDistributor.sendToServer(
                                    new OpenShopCategoriesPacket());
                        })
                        .bounds(x + 8, y + GUI_HEIGHT - 24, 70, 18)
                        .build()
        );

        this.addRenderableWidget(
                Button.builder(Component.literal("Cerrar"), b -> this.onClose())
                        .bounds(x + GUI_WIDTH - 58, y + GUI_HEIGHT - 24, 50, 18)
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

        String title = "BAMBU SHOP - " + menu.getCategory().getDisplayName();
        int titleWidth = this.font.width(title);
        g.drawString(this.font, title,
                x + (GUI_WIDTH - titleWidth) / 2,
                y + 9,
                0xFFFFD75E, true);

        List<ShopEntry> entries = ShopManager.getEntries(menu.getCategory());
        int cols = 8;
        int startX = x + 12;
        int startY = y + 34;

        ItemStack hoveredStack = null;
        int hoveredX = 0, hoveredY = 0;

        for (int i = 0; i < entries.size() && i < 40; i++) {
            ShopEntry entry = entries.get(i);
            int col = i % cols;
            int row = i / cols;
            int sx = startX + col * SLOT_SIZE;
            int sy = startY + row * SLOT_SIZE;

            boolean hover = mouseX >= sx && mouseX <= sx + SLOT_ICON_SIZE
                    && mouseY >= sy && mouseY <= sy + SLOT_ICON_SIZE;

            g.fill(sx, sy, sx + SLOT_ICON_SIZE, sy + SLOT_ICON_SIZE,
                    hover ? 0xFF7BA05B : 0xFF2A1C10);
            int border = hover ? 0xFFFFD75E : 0xFF7BA05B;
            g.fill(sx, sy, sx + SLOT_ICON_SIZE, sy + 1, border);
            g.fill(sx, sy + SLOT_ICON_SIZE - 1, sx + SLOT_ICON_SIZE, sy + SLOT_ICON_SIZE, border);
            g.fill(sx, sy, sx + 1, sy + SLOT_ICON_SIZE, border);
            g.fill(sx + SLOT_ICON_SIZE - 1, sy, sx + SLOT_ICON_SIZE, sy + SLOT_ICON_SIZE, border);

            ItemStack stack = entry.createStack();
            g.renderItem(stack, sx + 2, sy + 2);

            // Precio abreviado (100.000$ -> 100k$)
            String priceText = compactPrice(entry.getPrice());
            int priceWidth = this.font.width(priceText);
            g.drawString(this.font, priceText,
                    sx + SLOT_ICON_SIZE / 2 - priceWidth / 2,
                    sy + SLOT_ICON_SIZE + 1,
                    0xFFFFD75E, false);

            if (hover) {
                hoveredStack = stack;
                hoveredX = mouseX;
                hoveredY = mouseY;
            }
        }

        if (hoveredStack != null) {
            g.setTooltipForNextFrame(this.font, hoveredStack.getHoverName(), hoveredX, hoveredY);
        }

        ShopNotifications.render(g, this.width, y);
        ShopHudOverlay.render(g, x, y, GUI_WIDTH, menu.getMoney(), menu.getRank());
    }

    /** Convierte 100000 -> "100k$", 1500000 -> "1.5M$", 200 -> "200$" */
    private static String compactPrice(long price) {
        if (price >= 1_000_000) {
            double m = price / 1_000_000.0;
            return trim(m) + "M$";
        }
        if (price >= 1_000) {
            double k = price / 1_000.0;
            return trim(k) + "k$";
        }
        return price + "$";
    }

    private static String trim(double v) {
        if (v == Math.floor(v)) return String.valueOf((long) v);
        return String.format("%.1f", v);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean isDoubleClick) {
        double mouseX = event.x();
        double mouseY = event.y();

        List<ShopEntry> entries = ShopManager.getEntries(menu.getCategory());
        int cols = 8;
        int startX = this.leftPos + 12;
        int startY = this.topPos + 34;

        for (int i = 0; i < entries.size() && i < 40; i++) {
            int col = i % cols;
            int row = i / cols;
            int sx = startX + col * SLOT_SIZE;
            int sy = startY + row * SLOT_SIZE;

            if (mouseX >= sx && mouseX <= sx + SLOT_ICON_SIZE
                    && mouseY >= sy && mouseY <= sy + SLOT_ICON_SIZE) {
                ShopEntry entry = entries.get(i);
                net.neoforged.neoforge.client.network.ClientPacketDistributor.sendToServer(
                        new OpenShopCategoryItemPacket(entry.getItemId().toString()));
                return true;
            }
        }
        return super.mouseClicked(event, isDoubleClick);
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