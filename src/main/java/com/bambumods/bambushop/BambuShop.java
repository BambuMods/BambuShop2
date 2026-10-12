package com.bambumods.bambushop;

import com.bambumods.bambushop.registry.ModMenus;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(BambuShop.MOD_ID)
public class BambuShop {

    public static final String MOD_ID = "bambushop";
    public static final Logger LOGGER = LoggerFactory.getLogger("BambuShop");

    public BambuShop(IEventBus modBus, ModContainer container) {
        LOGGER.info("🍃 Bambu Shop cargando...");
        ModMenus.register(modBus);
    }
}