package com.bambumods.bambushop;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(value = BambuShop.MOD_ID, dist = Dist.CLIENT)
public class BambuShopClient {

    public BambuShopClient() {
        BambuShop.LOGGER.info("🍃 Bambu Shop Client cargando...");
    }
}