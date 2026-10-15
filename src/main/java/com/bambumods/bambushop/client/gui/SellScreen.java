package com.bambumods.bambushop.client.gui;

import com.bambumods.bambushop.menu.SellMenu;
import com.bambumods.bambushop.network.OpenShopCategoriesPacket;
import com.bambumods.bambushop.network.SellItemPacket;
import com.bambumods.bambushop.shop.ShopManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class SellScreen extends AbstractContainerScreen<SellMenu> {

    private static final int GUI_WIDTH = 460;
    private static final int GUI_HEIGHT = 280;
    private static final int LEFT_ZONE_WIDTH = 260;
    private static final int PANEL_X = 270;
    private static final int PANEL_Y = 32;
    private static final int PANEL_WIDTH = 180;
    private static final int PANEL_HEIGHT = 244;

    private static final int COLOR_MINERALES = 0xFF7BFF7B;
    private static final int COLOR_DROPS_MOBS = 0xFFFFD75E;
    private static final int COLOR_DROPS_ESP = 0xFFFF77FF;
    private static final int COLOR_ITEM = 0xFFFFF0D0;

    private int sellAmount = 1;

    public SellScreen(SellMenu menu, Inventory inv, Component title) {
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

        int row1Y = y + 100;
        this.addRenderableWidget(Button.builder(Component.literal("-1"), b -> change(-1))
                .bounds(x + 15, row1Y, 45, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("-10"), b -> change(-10))
                .bounds(x + 65, row1Y, 45, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("-64"), b -> change(-64))
                .bounds(x + 115, row1Y, 45, 20).build());

        this.addRenderableWidget(Button.builder(Component.literal("+1"), b -> change(1))
                .bounds(x + 15, row1Y + 24, 45, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("+10"), b -> change(10))
                .bounds(x + 65, row1Y + 24, 45, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("+64"), b -> change(64))
                .bounds(x + 115, row1Y + 24, 45, 20).build());

        this.addRenderableWidget(Button.builder(Component.literal("TODOS"), b -> sellAmount = -1)
                .bounds(x + 175, row1Y, 70, 44).build());

        this.addRenderableWidget(
                Button.builder(Component.literal("⬅ Volver"), b -> {
                            net.neoforged.neoforge.client.network.ClientPacketDistributor.sendToServer(
                                    new OpenShopCategoriesPacket());
                        })
                        .bounds(x + 15, y + GUI_HEIGHT - 28, 80, 20)
                        .build()
        );

        this.addRenderableWidget(
                Button.builder(Component.literal("💰 Vender"), b -> {
                            int amountToSend = (sellAmount == -1) ? countInInventory() : sellAmount;
                            if (amountToSend <= 0) return;
                            net.neoforged.neoforge.client.network.ClientPacketDistributor.sendToServer(
                                    new SellItemPacket(amountToSend));
                        })
                        .bounds(x + 155, y + GUI_HEIGHT - 28, 80, 20)
                        .build()
        );
    }

    private void change(int delta) {
        int max = Math.max(1, countInInventory());
        sellAmount = Math.max(1, Math.min(max, sellAmount + delta));
    }

    /** Cuenta los items del inventario + los del slot */
    private int countInInventory() {
        ItemStack slotStack = menu.getSellSlot().getItem();
        if (slotStack.isEmpty()) return 0;

        var player = Minecraft.getInstance().player;
        if (player == null) return 0;

        int count = 0;
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack s = player.getInventory().getItem(i);
            if (!s.isEmpty() && s.getItem() == slotStack.getItem()) {
                count += s.getCount();
            }
        }
        // Sumar el del slot
        count += slotStack.getCount();
        return count;
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(g, mouseX, mouseY, partialTick);

        int x = this.leftPos;
        int y = this.topPos;

        int frameX = x + 100;
        int frameY = y + 50;
        int frameSize = 44;

        g.fill(frameX, frameY, frameX + frameSize, frameY + frameSize, 0xFF7BA05B);
        int slotBorder = 0xFF2A411F;
        g.fill(frameX, frameY, frameX + frameSize, frameY + 1, slotBorder);
        g.fill(frameX, frameY + frameSize - 1, frameX + frameSize, frameY + frameSize, slotBorder);
        g.fill(frameX, frameY, frameX + 1, frameY + frameSize, slotBorder);
        g.fill(frameX + frameSize - 1, frameY, frameX + frameSize, frameY + frameSize, slotBorder);

        super.render(g, mouseX, mouseY, partialTick);

        g.fill(x, y, x + GUI_WIDTH, y + 26, 0xFF2A411F);
        g.fill(x, y + 26, x + GUI_WIDTH, y + 27, 0xFF7BA05B);

        String title = "💰 VENDER 💰";
        int titleWidth = this.font.width(title);
        g.drawString(this.font, title,
                x + (LEFT_ZONE_WIDTH - titleWidth) / 2,
                y + 9, 0xFFFFD75E, true);

        String slotHint = "⬇ PON EL ITEM AQUÍ ⬇";
        int hintWidth = this.font.width(slotHint);
        g.drawString(this.font, slotHint,
                x + (LEFT_ZONE_WIDTH - hintWidth) / 2,
                y + 36, 0xFFFFD75E, true);

        ItemStack stack = menu.getSellSlot().getItem();
        if (!stack.isEmpty()) {
            long pricePerItem = ShopManager.getSellPrice(stack);
            if (pricePerItem > 0) {
                String priceText = "✅ " + stack.getHoverName().getString()
                        + " - " + ShopHudOverlay.formatMoney(pricePerItem) + "$ c/u";
                int pw = this.font.width(priceText);
                g.drawString(this.font, priceText,
                        x + (LEFT_ZONE_WIDTH - pw) / 2,
                        y + 76, 0xFF7BFF7B, true);

                int totalInInv = countInInventory();
                String haveText = "Tienes " + totalInInv + " en total";
                int hw = this.font.width(haveText);
                g.drawString(this.font, haveText,
                        x + (LEFT_ZONE_WIDTH - hw) / 2,
                        y + 88, 0xFFFFF0D0, false);

                int qty = (sellAmount == -1) ? totalInInv : Math.min(sellAmount, totalInInv);
                String qtyText = "Cantidad a vender: " + qty;
                int qw = this.font.width(qtyText);
                g.drawString(this.font, qtyText,
                        x + (LEFT_ZONE_WIDTH - qw) / 2,
                        y + 148, 0xFFFFD75E, true);

                long totalPrice = pricePerItem * qty;
                String totalText = "Total: " + ShopHudOverlay.formatMoney(totalPrice) + " $";
                int tw = this.font.width(totalText);
                g.drawString(this.font, totalText,
                        x + (LEFT_ZONE_WIDTH - tw) / 2,
                        y + 162, 0xFF7BFF7B, true);
            } else {
                String msg = "❌ Este item no se puede vender";
                int mw = this.font.width(msg);
                g.drawString(this.font, msg,
                        x + (LEFT_ZONE_WIDTH - mw) / 2,
                        y + 76, 0xFFFF5555, true);
            }
        } else {
            String hint = "⬆ Pon un item para empezar";
            int hw = this.font.width(hint);
            g.drawString(this.font, hint,
                    x + (LEFT_ZONE_WIDTH - hw) / 2,
                    y + 130, 0xFFBFBFBF, false);
        }

        renderSellableList(g, x + PANEL_X, y + PANEL_Y);

        ShopHudOverlay.renderAt(g, x + PANEL_X, y + 2, menu.getMoney(), menu.getRank());
        ShopNotifications.render(g, this.width, y);
    }

    private void renderSellableList(GuiGraphics g, int px, int py) {
        int panelWidth = PANEL_WIDTH;
        int panelHeight = PANEL_HEIGHT;

        g.fill(px, py, px + panelWidth, py + panelHeight, 0xFF2A1C10);
        int border = 0xFF7BA05B;
        g.fill(px, py, px + panelWidth, py + 1, border);
        g.fill(px, py + panelHeight - 1, px + panelWidth, py + panelHeight, border);
        g.fill(px, py, px + 1, py + panelHeight, border);
        g.fill(px + panelWidth - 1, py, px + panelWidth, py + panelHeight, border);

        g.fill(px, py, px + panelWidth, py + 18, 0xFF2A411F);
        g.fill(px, py + 18, px + panelWidth, py + 19, border);

        String header = "SE PUEDE VENDER";
        int headerWidth = this.font.width(header);
        g.drawString(this.font, header,
                px + (panelWidth - headerWidth) / 2,
                py + 5, 0xFFFFD75E, true);

        int cursorY = py + 24;
        g.drawString(this.font, "MINERALES", px + 6, cursorY, COLOR_MINERALES, true);
        cursorY += 11;
        cursorY = writeTwoColumns(g, px, cursorY,
                new String[]{"Carbón", "Hierro", "Oro", "Diamante", "Esmeralda", "Netherite", "Restos ant."},
                new String[]{"Hierro bruto", "Oro bruto", "Redstone", "Lapis", "Cuarzo", "Amatista", ""});
        cursorY += 3;

        g.drawString(this.font, "DROPS DE MOBS", px + 6, cursorY, COLOR_DROPS_MOBS, true);
        cursorY += 11;
        cursorY = writeTwoColumns(g, px, cursorY,
                new String[]{"Hueso", "Pluma", "Cuerda", "Pólvora", "Perla ender", "Blaze rod", "Ghast tear", "Slime ball", "Ojo araña"},
                new String[]{"Carne podrida", "Cuero", "Magma cream", "Membrana", "Cráneo wither", "Cabeza zombi", "Cabeza creeper", "Cráneo esq.", ""});
        cursorY += 3;

        g.drawString(this.font, "DROPS ESP.", px + 6, cursorY, COLOR_DROPS_ESP, true);
        cursorY += 11;
        writeTwoColumns(g, px, cursorY,
                new String[]{"Estrella Nether", "Cabeza dragón"},
                new String[]{"Huevo dragón", "Corazón mar"});
    }

    private int writeTwoColumns(GuiGraphics g, int px, int y, String[] left, String[] right) {
        int rows = Math.max(left.length, right.length);
        int colLeftX = px + 6;
        int colRightX = px + 92;
        for (int i = 0; i < rows; i++) {
            if (i < left.length && !left[i].isEmpty()) {
                g.drawString(this.font, left[i], colLeftX, y, COLOR_ITEM, false);
            }
            if (i < right.length && !right[i].isEmpty()) {
                g.drawString(this.font, right[i], colRightX, y, COLOR_ITEM, false);
            }
            y += 10;
        }
        return y;
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