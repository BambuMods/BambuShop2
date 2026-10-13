package com.bambumods.bambushop.network;

import com.bambumods.bambushop.BambuShop;
import com.bambumods.bambushop.attachment.ModAttachments;
import com.bambumods.bambushop.menu.ShopCategoriesMenu;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record OpenShopCategoriesPacket() implements CustomPacketPayload {

    public static final OpenShopCategoriesPacket INSTANCE = new OpenShopCategoriesPacket();

    public static final CustomPacketPayload.Type<OpenShopCategoriesPacket> TYPE =
            new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(BambuShop.MOD_ID, "open_shop_categories"));

    public static final StreamCodec<ByteBuf, OpenShopCategoriesPacket> STREAM_CODEC =
            StreamCodec.unit(INSTANCE);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(OpenShopCategoriesPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer player)) return;

            player.closeContainer();

            final ServerPlayer sp = player;
            sp.openMenu(
                    new SimpleMenuProvider(
                            (id, inv, p) -> new ShopCategoriesMenu(id, inv, sp),
                            Component.literal("Bambu Shop")
                    ),
                    buf -> {
                        buf.writeLong(ModAttachments.getMoney(sp));
                        buf.writeInt(ModAttachments.getRank(sp));
                    }
            );
        });
    }
}