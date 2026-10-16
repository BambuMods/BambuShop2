package com.bambumods.bambushop.network;

import com.bambumods.bambushop.BambuShop;
import com.bambumods.bambushop.attachment.ModAttachments;
import com.bambumods.bambushop.menu.SellMenu;
import com.bambumods.bambushop.shop.ShopManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SellItemPacket(int amount) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<SellItemPacket> TYPE =
            new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(BambuShop.MOD_ID, "sell_item"));

    public static final StreamCodec<FriendlyByteBuf, SellItemPacket> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.VAR_INT, SellItemPacket::amount,
                    SellItemPacket::new
            );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(SellItemPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer player)) return;
            if (packet.amount <= 0) return;
            if (!(player.containerMenu instanceof SellMenu sellMenu)) return;

            ItemStack slotStack = sellMenu.getSellSlot().getItem();
            if (slotStack.isEmpty()) {
                PacketDistributor.sendToPlayer(player,
                        new TransactionFeedbackPacket("✘ Pon un item en el slot primero", 1));
                return;
            }

            long pricePerItem = ShopManager.getSellPrice(slotStack);
            if (pricePerItem <= 0) {
                PacketDistributor.sendToPlayer(player,
                        new TransactionFeedbackPacket("✘ Este item no se puede vender", 1));
                return;
            }

            int available = countInInventory(player, slotStack);
            int toSell = Math.min(packet.amount, available);

            if (toSell <= 0) {
                PacketDistributor.sendToPlayer(player,
                        new TransactionFeedbackPacket("✘ No tienes items de ese tipo", 1));
                return;
            }

            int removed = removeFromInventory(player, slotStack, toSell);
            long total = pricePerItem * removed;

            // El slot no se vacía (se queda el item "marcado")

            ModAttachments.addMoney(player, total);

            PacketDistributor.sendToPlayer(player,
                    new TransactionFeedbackPacket(
                            "✔ Vendiste " + removed + " x "
                                    + slotStack.getHoverName().getString()
                                    + " por " + formatMoney(total) + "$",
                            0));

            PacketDistributor.sendToPlayer(player,
                    new SyncPlayerDataPacket(
                            ModAttachments.getMoney(player),
                            ModAttachments.getRank(player),
                            ModAttachments.isAutoSell(player)
                    ));
        });
    }

    private static int countInInventory(ServerPlayer player, ItemStack stack) {
        int count = 0;
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack s = player.getInventory().getItem(i);
            if (!s.isEmpty() && s.getItem() == stack.getItem()) {
                count += s.getCount();
            }
        }
        return count;
    }

    private static int removeFromInventory(ServerPlayer player, ItemStack reference, int amount) {
        int remaining = amount;
        for (int i = 0; i < player.getInventory().getContainerSize() && remaining > 0; i++) {
            ItemStack s = player.getInventory().getItem(i);
            if (!s.isEmpty() && s.getItem() == reference.getItem()) {
                int take = Math.min(s.getCount(), remaining);
                s.shrink(take);
                remaining -= take;
            }
        }
        return amount - remaining;
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