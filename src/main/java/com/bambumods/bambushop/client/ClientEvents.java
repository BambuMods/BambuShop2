package com.bambumods.bambushop.client;

import com.bambumods.bambushop.BambuShop;
import com.bambumods.bambushop.client.gui.MainMenuScreen;
import com.bambumods.bambushop.client.gui.RankMenuScreen;
import com.bambumods.bambushop.registry.ModMenus;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = BambuShop.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenus.MAIN_MENU.get(), MainMenuScreen::new);
        event.register(ModMenus.RANK_MENU.get(), RankMenuScreen::new);
    }
}