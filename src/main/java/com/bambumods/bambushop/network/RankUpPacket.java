package com.bambumods.bambushop.network;

import com.bambumods.bambushop.BambuShop;
import com.bambumods.bambushop.menu.RankMenu;
import com.bambumods.bambushop.rank.RankManager;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record RankUpPacket() implements CustomPacketPayload {

    public static final RankUpPacket INSTANCE = new RankUpPacket();

    public static final CustomPacketPayload.Type<RankUpPacket> TYPE =
            new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(BambuShop.MOD_ID, "rankup"));

    public static final StreamCodec<ByteBuf, RankUpPacket> STREAM_CODEC =
            StreamCodec.unit(INSTANCE);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(RankUpPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer player)) return;

            boolean success = RankManager.rankUp(player);

            if (success) {
                player.sendSystemMessage(Component.literal("§a🎖 ¡Has ascendido de rango!"));
            } else {
                player.sendSystemMessage(Component.literal("§c❌ No cumples los requisitos para ascender."));
            }

            final ServerPlayer sp = player;
            sp.openMenu(
                    new SimpleMenuProvider(
                            (id, inv, p) -> new RankMenu(id, inv, sp),
                            Component.literal("Rangos")
                    ),
                    buf -> {
                        RankMenu menu = new RankMenu(0, sp.getInventory(), sp);
                        buf.writeInt(menu.getCurrentRank());
                        buf.writeInt(menu.getNextRankLevel());
                        buf.writeUtf(menu.getNextRankName());
                        buf.writeLong(menu.getMoney());
                        buf.writeLong(menu.getMoneyCost());
                        buf.writeInt(menu.getXpLevel());
                        buf.writeInt(menu.getXpCost());
                        buf.writeInt(menu.getRequirements().size());
                        for (RankMenu.SyncedReq req : menu.getRequirements()) {
                            net.minecraft.world.item.ItemStack.STREAM_CODEC.encode(buf, req.stack);
                            buf.writeInt(req.needed);
                            buf.writeInt(req.has);
                        }
                    }
            );
        });
    }
}