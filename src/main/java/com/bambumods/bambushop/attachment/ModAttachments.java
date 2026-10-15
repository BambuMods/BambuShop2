package com.bambumods.bambushop.attachment;

import com.bambumods.bambushop.BambuShop;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModAttachments {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, BambuShop.MOD_ID);

    /** Dinero del jugador. Persiste al morir. */
    public static final Supplier<AttachmentType<Long>> MONEY = ATTACHMENT_TYPES.register(
            "money",
            () -> AttachmentType.builder(() -> 0L)
                    .serialize(Codec.LONG.fieldOf("money"))
                    .copyOnDeath()
                    .build()
    );

    /** Rango del jugador (0 = sin rango, 1-20). Persiste al morir. */
    public static final Supplier<AttachmentType<Integer>> RANK = ATTACHMENT_TYPES.register(
            "rank",
            () -> AttachmentType.builder(() -> 0)
                    .serialize(Codec.INT.fieldOf("rank"))
                    .copyOnDeath()
                    .build()
    );

    /** Prestigio del jugador (0 = sin prestigio, 1, 2, 3...). Persiste al morir. */
    public static final Supplier<AttachmentType<Integer>> PRESTIGE = ATTACHMENT_TYPES.register(
            "prestige",
            () -> AttachmentType.builder(() -> 0)
                    .serialize(Codec.INT.fieldOf("prestige"))
                    .copyOnDeath()
                    .build()
    );

    /** Nivel del mob (1-20). NO persiste al morir (el mob muere con él). */
    public static final Supplier<AttachmentType<Integer>> MOB_LEVEL = ATTACHMENT_TYPES.register(
            "mob_level",
            () -> AttachmentType.builder(() -> 1)
                    .serialize(Codec.INT.fieldOf("mob_level"))
                    .build()
    );

    public static void register(IEventBus modBus) {
        ATTACHMENT_TYPES.register(modBus);
    }

    // ===== DINERO =====
    public static long getMoney(Player player) {
        return player.getData(MONEY);
    }

    public static void setMoney(Player player, long amount) {
        player.setData(MONEY, Math.max(0L, amount));
    }

    public static void addMoney(Player player, long amount) {
        setMoney(player, getMoney(player) + amount);
    }

    public static boolean takeMoney(Player player, long amount) {
        long current = getMoney(player);
        if (current < amount) return false;
        setMoney(player, current - amount);
        return true;
    }

    // ===== RANGO =====
    public static int getRank(Player player) {
        return player.getData(RANK);
    }

    public static void setRank(Player player, int rank) {
        int clamped = Math.max(0, Math.min(20, rank));
        player.setData(RANK, clamped);
    }

    public static void addRank(Player player, int amount) {
        setRank(player, getRank(player) + amount);
    }

    // ===== PRESTIGIO =====
    public static int getPrestige(Player player) {
        return player.getData(PRESTIGE);
    }

    public static void setPrestige(Player player, int prestige) {
        int clamped = Math.max(0, prestige);
        player.setData(PRESTIGE, clamped);
    }

    public static void addPrestige(Player player, int amount) {
        setPrestige(player, getPrestige(player) + amount);
    }

    // ===== NIVEL DEL MOB =====
    public static int getMobLevel(Entity entity) {
        return entity.getData(MOB_LEVEL);
    }

    public static void setMobLevel(Entity entity, int level) {
        int clamped = Math.max(1, Math.min(20, level));
        entity.setData(MOB_LEVEL, clamped);
    }
}