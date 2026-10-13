package com.bambumods.bambushop;

import com.bambumods.bambushop.attachment.ModAttachments;
import com.bambumods.bambushop.registry.ModMenus;
import com.bambumods.bambushop.shop.ShopConfig;
import com.bambumods.bambushop.shop.ShopManager;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(BambuShop.MOD_ID)
public class BambuShop {

    public static final String MOD_ID = "bambushop";
    public static final Logger LOGGER = LoggerFactory.getLogger("BambuShop");

    public BambuShop(IEventBus modBus, ModContainer container) {
        LOGGER.info("🍃 Bambu Shop cargando...");
        ModAttachments.register(modBus);
        ModMenus.register(modBus);

        // Registrar este objeto en el game event bus (para onServerStarting)
        NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("🍃 Bambu Shop: cargando tienda...");
        ShopConfig.ConfigData data = ShopConfig.loadOrCreate(FMLPaths.CONFIGDIR.get());
        ShopManager.load(data);
    }
}