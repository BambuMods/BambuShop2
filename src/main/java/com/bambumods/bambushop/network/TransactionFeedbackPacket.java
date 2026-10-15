package com.bambumods.bambushop.network;

import com.bambumods.bambushop.BambuShop;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

/**
 * Servidor → Cliente: notificación flotante en la GUI.
 * feedbackType: 0 = éxito, 1 = error, 2 = info
 */
public record TransactionFeedbackPacket(String message, int feedbackType) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<TransactionFeedbackPacket> TYPE =
            new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(BambuShop.MOD_ID, "transaction_feedback"));

    public static final StreamCodec<FriendlyByteBuf, TransactionFeedbackPacket> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.STRING_UTF8, TransactionFeedbackPacket::message,
                    ByteBufCodecs.VAR_INT, TransactionFeedbackPacket::feedbackType,
                    TransactionFeedbackPacket::new
            );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}