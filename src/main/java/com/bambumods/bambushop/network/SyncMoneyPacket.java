package com.bambumods.bambushop.network;

import com.bambumods.bambushop.BambuShop;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

/**
 * Servidor → Cliente: sincronizar el dinero del jugador con la GUI abierta.
 */
public record SyncMoneyPacket(long money) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<SyncMoneyPacket> TYPE =
            new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(BambuShop.MOD_ID, "sync_money"));

    public static final StreamCodec<FriendlyByteBuf, SyncMoneyPacket> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.VAR_LONG, SyncMoneyPacket::money,
                    SyncMoneyPacket::new
            );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}