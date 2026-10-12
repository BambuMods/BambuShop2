package com.bambumods.bambushop.command;

import com.bambumods.bambushop.attachment.ModAttachments;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = "bambushop")
public class RankCommand {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        // /myrank - Ver tu rango actual
        dispatcher.register(
                Commands.literal("myrank")
                        .executes(context -> {
                            if (context.getSource().getEntity() instanceof ServerPlayer player) {
                                int rank = ModAttachments.getRank(player);
                                player.sendSystemMessage(Component.literal("§6🎖️ Tu rango actual: §e" + rank + " §7(0 = Novato)"));
                                return 1;
                            }
                            return 0;
                        })
        );

        // /setrank <n> - Forzar rango (temporal, para probar)
        dispatcher.register(
                Commands.literal("setrank")
                        .then(Commands.argument("nivel", com.mojang.brigadier.arguments.IntegerArgumentType.integer(0, 20))
                                .executes(context -> {
                                    if (context.getSource().getEntity() instanceof ServerPlayer player) {
                                        int n = com.mojang.brigadier.arguments.IntegerArgumentType.getInteger(context, "nivel");
                                        ModAttachments.setRank(player, n);
                                        player.sendSystemMessage(Component.literal("§a🎖️ Rango establecido a: §e" + n));
                                        return 1;
                                    }
                                    return 0;
                                })
                        )
        );
    }
}