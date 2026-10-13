package com.bambumods.bambushop.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

import java.util.ArrayList;
import java.util.List;

/**
 * Sistema de notificaciones flotantes.
 * Se dibujan encima de la GUI y desaparecen solas.
 */
public class ShopNotifications {

    private static final List<Notification> ACTIVE = new ArrayList<>();
    private static final long DURATION_MS = 2500; // 2.5 segundos

    public static void success(String message) {
        add(message, 0xFF7BFF7B); // verde
    }

    public static void error(String message) {
        add(message, 0xFFFF5555); // rojo
    }

    public static void info(String message) {
        add(message, 0xFFFFD75E); // dorado
    }

    private static void add(String message, int color) {
        ACTIVE.add(new Notification(message, color, System.currentTimeMillis()));
        // Máximo 3 notificaciones a la vez
        if (ACTIVE.size() > 3) {
            ACTIVE.remove(0);
        }
    }

    /** Se llama cada frame desde las GUIs */
    public static void render(GuiGraphics g, int screenWidth, int guiTop) {
        long now = System.currentTimeMillis();

        // Quitar las caducadas
        ACTIVE.removeIf(n -> now - n.startTime > DURATION_MS);

        int y = guiTop - 40;
        for (Notification n : ACTIVE) {
            long elapsed = now - n.startTime;
            float alpha = 1.0f;
            // Fade out en el último 25% del tiempo
            if (elapsed > DURATION_MS * 0.75) {
                alpha = 1.0f - (elapsed - DURATION_MS * 0.75f) / (DURATION_MS * 0.25f);
            }
            int a = (int) (alpha * 255) << 24;
            int colorWithAlpha = a | (n.color & 0x00FFFFFF);

            Minecraft mc = Minecraft.getInstance();
            int textWidth = mc.font.width(n.message);

            int bx = (screenWidth - textWidth) / 2 - 6;
            int by = y;

            // Fondo semi-transparente
            g.fill(bx - 4, by - 4, bx + textWidth + 4, by + 12, 0xC0000000 | (n.color & 0x000000));
            g.fill(bx - 4, by - 4, bx + textWidth + 4, by - 3, 0xFF000000 | (n.color & 0xFFFFFF));
            g.fill(bx - 4, by + 11, bx + textWidth + 4, by + 12, 0xFF000000 | (n.color & 0xFFFFFF));

            g.drawString(mc.font, n.message, bx, by, colorWithAlpha, true);

            y -= 18;
        }
    }

    private static class Notification {
        String message;
        int color;
        long startTime;

        Notification(String message, int color, long startTime) {
            this.message = message;
            this.color = color;
            this.startTime = startTime;
        }
    }
}