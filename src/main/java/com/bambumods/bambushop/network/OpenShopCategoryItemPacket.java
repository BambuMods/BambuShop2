package com.bambumods.bambushop.network;

import com.bambumods.bambushop.BambuShop;
import com.bambumods.bambushop.attachment.ModAttachments;
import com.bambumods.bambushop.menu.ShopQuantityMenu;
import com.bambumods.bambushop.shop.ShopEntry;
import com.bambumods.bambushop.shop.ShopManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record OpenShopCategoryItemPacket(String itemId) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<OpenShopCategoryItemPacket> TYPE =
            new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(BambuShop.MOD_ID, "open_shop_category_item"));

    public static final StreamCodec<FriendlyByteBuf, OpenShopCategoryItemPacket> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.STRING_UTF8, OpenShopCategoryItemPacket::itemId,
                    OpenShopCategoryItemPacket::new
            );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(OpenShopCategoryItemPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer player)) return;

            Identifier id = Identifier.tryParse(packet.itemId);
            if (id == null) return;

            ShopEntry entry = ShopManager.getBuyEntry(id);
            if (entry == null) return;

            player.closeContainer();

            final ServerPlayer sp = player;
            final ShopEntry finalEntry = entry;
            sp.openMenu(
                    new SimpleMenuProvider(
                            (cid, inv, p) -> new ShopQuantityMenu(cid, inv, sp, finalEntry),
                            Component.literal("Cantidad")
                    ),
                    buf -> {
                        buf.writeUtf(finalEntry.getItemId().toString());
                        buf.writeLong(ModAttachments.getMoney(sp));
                        buf.writeInt(ModAttachments.getRank(sp));
                    }
            );
        });
    }
}