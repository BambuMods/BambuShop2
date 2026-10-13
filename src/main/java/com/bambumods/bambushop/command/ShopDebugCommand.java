package com.bambumods.bambushop.command;

import com.bambumods.bambushop.BambuShop;
import com.bambumods.bambushop.shop.ShopCategory;
import com.bambumods.bambushop.shop.ShopManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = BambuShop.MOD_ID)
public class ShopDebugCommand {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("shopdebug")
                .requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
                .executes(ShopDebugCommand::run));
    }

    private static int run(CommandContext<CommandSourceStack> ctx) {
        CommandSourceStack src = ctx.getSource();

        if (!(src.getEntity() instanceof ServerPlayer player)) {
            src.sendFailure(Component.literal("Solo jugadores."));
            return 0;
        }

        src.sendSuccess(() -> Component.literal("§e[Shop] Cargado: §a" + ShopManager.isLoaded()), false);

        ItemStack inHand = player.getMainHandItem();
        if (inHand.isEmpty()) {
            src.sendSuccess(() -> Component.literal("§e[Shop] Pon un item en la mano para consultar su precio."), false);
        } else {
            long buy = ShopManager.getBuyPrice(inHand);
            long sell = ShopManager.getSellPrice(inHand);
            boolean canBuy = ShopManager.canBuy(inHand);
            boolean canSell = ShopManager.canSell(inHand);

            src.sendSuccess(() -> Component.literal("§e[Shop] Item: §f" + inHand.getHoverName().getString()), false);
            src.sendSuccess(() -> Component.literal("§e[Shop]   ¿Se compra? §f" + canBuy + " §7(precio: §a" + buy + "$§7)"), false);
            src.sendSuccess(() -> Component.literal("§e[Shop]   ¿Se vende?   §f" + canSell + " §7(precio: §a" + sell + "$§7)"), false);
        }

        for (ShopCategory cat : ShopCategory.values()) {
            int count = ShopManager.getEntries(cat).size();
            src.sendSuccess(() -> Component.literal("§7  - " + cat.getId() + ": §f" + count + " items"), false);
        }

        return 1;
    }
}