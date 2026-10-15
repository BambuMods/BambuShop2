package com.bambumods.bambushop.network;

import com.bambumods.bambushop.BambuShop;
import com.bambumods.bambushop.attachment.ModAttachments;
import com.bambumods.bambushop.menu.SellMenu;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record OpenSellGuiPacket() implements CustomPacketPayload {

    public static final OpenSellGuiPacket INSTANCE = new OpenSellGuiPacket();

    public static final CustomPacketPayload.Type<OpenSellGuiPacket> TYPE =
            new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(BambuShop.MOD_ID, "open_sell_gui"));

    public static final StreamCodec<ByteBuf, OpenSellGuiPacket> STREAM_CODEC =
            StreamCodec.unit(INSTANCE);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(OpenSellGuiPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer player)) return;

            player.closeContainer();

            final ServerPlayer sp = player;
            sp.openMenu(
                    new SimpleMenuProvider(
                            (id, inv, p) -> new SellMenu(id, inv, sp),
                            Component.literal("Vender")
                    ),
                    buf -> {
                        buf.writeLong(ModAttachments.getMoney(sp));
                        buf.writeInt(ModAttachments.getRank(sp));
                    }
            );
        });
    }
}