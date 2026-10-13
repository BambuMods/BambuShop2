package com.bambumods.bambushop.network;

import com.bambumods.bambushop.BambuShop;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = BambuShop.MOD_ID)
public class ModNetworking {

    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");

        registrar.playToServer(
                RankUpPacket.TYPE,
                RankUpPacket.STREAM_CODEC,
                RankUpPacket::handle
        );

        registrar.playToServer(
                OpenRankGuiPacket.TYPE,
                OpenRankGuiPacket.STREAM_CODEC,
                OpenRankGuiPacket::handle
        );

        registrar.playToServer(
                OpenShopCategoriesPacket.TYPE,
                OpenShopCategoriesPacket.STREAM_CODEC,
                OpenShopCategoriesPacket::handle
        );
    }
}