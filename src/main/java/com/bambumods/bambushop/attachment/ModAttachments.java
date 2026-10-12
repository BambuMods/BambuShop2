package com.bambumods.bambushop.attachment;

import com.bambumods.bambushop.BambuShop;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModAttachments {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, BambuShop.MOD_ID);

    public static final Supplier<AttachmentType<Long>> MONEY = ATTACHMENT_TYPES.register(
            "money",
            () -> AttachmentType.builder(() -> 0L)
                    .serialize(Codec.LONG.fieldOf("money"))
                    .copyOnDeath()
                    .build()
    );

    public static void register(IEventBus modBus) {
        ATTACHMENT_TYPES.register(modBus);
    }

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
}