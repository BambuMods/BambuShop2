package com.bambumods.bambushop.command;

import com.bambumods.bambushop.BambuShop;
import com.bambumods.bambushop.attachment.ModAttachments;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = BambuShop.MOD_ID)
public class AutoSellCommand {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<net.minecraft.commands.CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("autosell")
                .executes(ctx -> {
                    if (!(ctx.getSource().getEntity() instanceof ServerPlayer player)) return 0;

                    ModAttachments.toggleAutoSell(player);
                    boolean active = ModAttachments.isAutoSell(player);

                    String msg = active ? "§a✔ Auto-venta ACTIVADA" : "§c✘ Auto-venta DESACTIVADA";
                    player.displayClientMessage(Component.literal(msg), false);
                    return 1;
                })
        );
    }
}