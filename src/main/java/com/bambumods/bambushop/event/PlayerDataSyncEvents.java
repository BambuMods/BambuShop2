package com.bambumods.bambushop.event;

import com.bambumods.bambushop.BambuShop;
import com.bambumods.bambushop.attachment.ModAttachments;
import com.bambumods.bambushop.network.SyncPlayerDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EventBusSubscriber(modid = BambuShop.MOD_ID)
public class PlayerDataSyncEvents {

    private static final Map<UUID, PlayerData> LAST_SENT = new HashMap<>();
    private static int tickCounter = 0;

    private record PlayerData(long money, int rank, boolean autoSell) {
        static PlayerData from(ServerPlayer player) {
            return new PlayerData(
                    ModAttachments.getMoney(player),
                    ModAttachments.getRank(player),
                    ModAttachments.isAutoSell(player)
            );
        }
    }

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        tickCounter++;
        if (tickCounter < 20) return; // Cada 1 segundo
        tickCounter = 0;

        for (ServerPlayer player : event.getServer().getPlayerList().getPlayers()) {
            PlayerData current = PlayerData.from(player);
            PlayerData last = LAST_SENT.get(player.getUUID());

            if (last == null || !last.equals(current)) {
                PacketDistributor.sendToPlayer(player, new SyncPlayerDataPacket(
                        current.money(), current.rank(), current.autoSell()
                ));
                LAST_SENT.put(player.getUUID(), current);
            }
        }
    }

    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        PlayerData data = PlayerData.from(player);
        PacketDistributor.sendToPlayer(player, new SyncPlayerDataPacket(
                data.money(), data.rank(), data.autoSell()
        ));
        LAST_SENT.put(player.getUUID(), data);
    }

    @SubscribeEvent
    public static void onLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        LAST_SENT.remove(player.getUUID());
    }
}