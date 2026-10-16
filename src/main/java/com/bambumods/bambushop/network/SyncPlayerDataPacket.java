package com.bambumods.bambushop.network;

import com.bambumods.bambushop.BambuShop;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record SyncPlayerDataPacket(long money, int rank, boolean autoSell) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<SyncPlayerDataPacket> TYPE =
            new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(BambuShop.MOD_ID, "sync_player_data"));

    public static final StreamCodec<FriendlyByteBuf, SyncPlayerDataPacket> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.VAR_LONG, SyncPlayerDataPacket::money,
                    ByteBufCodecs.VAR_INT, SyncPlayerDataPacket::rank,
                    ByteBufCodecs.BOOL, SyncPlayerDataPacket::autoSell,
                    SyncPlayerDataPacket::new
            );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
