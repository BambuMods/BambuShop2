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

        // Cliente → Servidor
        registrar.playToServer(RankUpPacket.TYPE, RankUpPacket.STREAM_CODEC, RankUpPacket::handle);
        registrar.playToServer(OpenRankGuiPacket.TYPE, OpenRankGuiPacket.STREAM_CODEC, OpenRankGuiPacket::handle);
        registrar.playToServer(OpenShopCategoriesPacket.TYPE, OpenShopCategoriesPacket.STREAM_CODEC, OpenShopCategoriesPacket::handle);
        registrar.playToServer(OpenShopCategoryPacket.TYPE, OpenShopCategoryPacket.STREAM_CODEC, OpenShopCategoryPacket::handle);
        registrar.playToServer(OpenShopCategoryItemPacket.TYPE, OpenShopCategoryItemPacket.STREAM_CODEC, OpenShopCategoryItemPacket::handle);
        registrar.playToServer(BuyItemPacket.TYPE, BuyItemPacket.STREAM_CODEC, BuyItemPacket::handle);
        registrar.playToServer(OpenSellGuiPacket.TYPE, OpenSellGuiPacket.STREAM_CODEC, OpenSellGuiPacket::handle);
        registrar.playToServer(SellItemPacket.TYPE, SellItemPacket.STREAM_CODEC, SellItemPacket::handle);

        // Servidor → Cliente
        registrar.playToClient(TransactionFeedbackPacket.TYPE, TransactionFeedbackPacket.STREAM_CODEC);
        registrar.playToClient(SyncPlayerDataPacket.TYPE, SyncPlayerDataPacket.STREAM_CODEC);
    }
}