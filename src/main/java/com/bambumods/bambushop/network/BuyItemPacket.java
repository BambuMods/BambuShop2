package com.bambumods.bambushop.network;

import com.bambumods.bambushop.BambuShop;
import com.bambumods.bambushop.attachment.ModAttachments;
import com.bambumods.bambushop.shop.ShopEntry;
import com.bambumods.bambushop.shop.ShopManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record BuyItemPacket(String itemId, int quantity) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<BuyItemPacket> TYPE =
            new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(BambuShop.MOD_ID, "buy_item"));

    public static final StreamCodec<FriendlyByteBuf, BuyItemPacket> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.STRING_UTF8, BuyItemPacket::itemId,
                    ByteBufCodecs.VAR_INT, BuyItemPacket::quantity,
                    BuyItemPacket::new
            );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(BuyItemPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer player)) return;
            if (packet.quantity <= 0) return;

            Identifier id = Identifier.tryParse(packet.itemId);
            if (id == null) return;

            ShopEntry entry = ShopManager.getBuyEntry(id);
            if (entry == null) return;

            long totalCost = entry.getPrice() * packet.quantity;
            long money = ModAttachments.getMoney(player);

            if (money < totalCost) {
                player.displayClientMessage(
                        Component.literal("§c✘ No tienes suficiente dinero (necesitas "
                                + formatMoney(totalCost) + "$, tienes " + formatMoney(money) + "$)"),
                        false);
                return;
            }

            ItemStack toAdd = entry.createStack();
            toAdd.setCount(packet.quantity);
            if (!player.getInventory().add(toAdd)) {
                player.displayClientMessage(
                        Component.literal("§c✘ Inventario lleno"),
                        false);
                return;
            }

            ModAttachments.takeMoney(player, totalCost);

            player.displayClientMessage(
                    Component.literal("§a✔ Compraste " + packet.quantity + " x "
                            + entry.createStack().getHoverName().getString()
                            + " por " + formatMoney(totalCost) + "$"),
                    false);
        });
    }

    private static String formatMoney(long amount) {
        String s = Long.toString(amount);
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            sb.insert(0, s.charAt(i));
            count++;
            if (count % 3 == 0 && i > 0) sb.insert(0, '.');
        }
        return sb.toString();
    }
}