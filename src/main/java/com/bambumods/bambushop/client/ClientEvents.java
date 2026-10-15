package com.bambumods.bambushop.client;

import com.bambumods.bambushop.BambuShop;
import com.bambumods.bambushop.client.gui.MainMenuScreen;
import com.bambumods.bambushop.client.gui.RankMenuScreen;
import com.bambumods.bambushop.client.gui.SellScreen;
import com.bambumods.bambushop.client.gui.ShopCategoriesScreen;
import com.bambumods.bambushop.client.gui.ShopCategoryScreen;
import com.bambumods.bambushop.client.gui.ShopNotifications;
import com.bambumods.bambushop.client.gui.ShopQuantityScreen;
import com.bambumods.bambushop.menu.SellMenu;
import com.bambumods.bambushop.menu.ShopQuantityMenu;
import com.bambumods.bambushop.network.SyncMoneyPacket;
import com.bambumods.bambushop.network.TransactionFeedbackPacket;
import com.bambumods.bambushop.registry.ModMenus;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;

@EventBusSubscriber(modid = BambuShop.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenus.MAIN_MENU.get(), MainMenuScreen::new);
        event.register(ModMenus.RANK_MENU.get(), RankMenuScreen::new);
        event.register(ModMenus.SHOP_CATEGORIES.get(), ShopCategoriesScreen::new);
        event.register(ModMenus.SHOP_CATEGORY.get(), ShopCategoryScreen::new);
        event.register(ModMenus.SHOP_QUANTITY.get(), ShopQuantityScreen::new);
        event.register(ModMenus.SELL_MENU.get(), SellScreen::new);
    }

    @SubscribeEvent
    public static void registerClientPayloads(RegisterClientPayloadHandlersEvent event) {
        event.register(TransactionFeedbackPacket.TYPE, (packet, ctx) -> {
            ctx.enqueueWork(() -> {
                int t = packet.feedbackType();
                String msg = packet.message();
                if (t == 0) {
                    ShopNotifications.success(msg);
                } else if (t == 1) {
                    ShopNotifications.error(msg);
                } else {
                    ShopNotifications.info(msg);
                }
            });
        });

        event.register(SyncMoneyPacket.TYPE, (packet, ctx) -> {
            ctx.enqueueWork(() -> {
                var player = Minecraft.getInstance().player;
                if (player == null) return;

                // Actualizar el MENÚ (que es lo que lee la pantalla)
                if (player.containerMenu instanceof SellMenu sellMenu) {
                    sellMenu.setMoney(packet.money());
                } else if (player.containerMenu instanceof ShopQuantityMenu qMenu) {
                    qMenu.setMoney(packet.money());
                }
            });
        });
    }
}