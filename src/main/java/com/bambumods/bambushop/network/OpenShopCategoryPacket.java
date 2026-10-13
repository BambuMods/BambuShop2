package com.bambumods.bambushop.network;

import com.bambumods.bambushop.BambuShop;
import com.bambumods.bambushop.attachment.ModAttachments;
import com.bambumods.bambushop.menu.ShopCategoryMenu;
import com.bambumods.bambushop.shop.ShopCategory;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record OpenShopCategoryPacket(String categoryId) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<OpenShopCategoryPacket> TYPE =
            new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(BambuShop.MOD_ID, "open_shop_category"));

    public static final StreamCodec<FriendlyByteBuf, OpenShopCategoryPacket> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.STRING_UTF8, OpenShopCategoryPacket::categoryId,
                    OpenShopCategoryPacket::new
            );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(OpenShopCategoryPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer player)) return;

            ShopCategory cat = ShopCategory.fromId(packet.categoryId);
            if (cat == null) return;

            player.closeContainer();

            final ServerPlayer sp = player;
            final ShopCategory finalCat = cat;
            sp.openMenu(
                    new SimpleMenuProvider(
                            (cid, inv, p) -> new ShopCategoryMenu(cid, inv, sp, finalCat),
                            Component.literal("Bambu Shop - " + finalCat.getDisplayName())
                    ),
                    buf -> {
                        buf.writeUtf(finalCat.getId());
                        buf.writeLong(ModAttachments.getMoney(sp));
                        buf.writeInt(ModAttachments.getRank(sp));
                    }
            );
        });
    }
}