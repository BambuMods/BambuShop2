package com.bambumods.bambushop.command;

import com.bambumods.bambushop.attachment.ModAttachments;
import com.bambumods.bambushop.menu.RankMenu;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = "bambushop")
public class RankCommand {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        // /myrank
        dispatcher.register(
                Commands.literal("myrank")
                        .executes(context -> {
                            if (context.getSource().getEntity() instanceof ServerPlayer player) {
                                int rank = ModAttachments.getRank(player);
                                player.sendSystemMessage(Component.literal("§6🎖️ Tu rango actual: §e" + rank));
                                return 1;
                            }
                            return 0;
                        })
        );

        // /setrank <n>
        dispatcher.register(
                Commands.literal("setrank")
                        .then(Commands.argument("nivel", IntegerArgumentType.integer(0, 20))
                                .executes(context -> {
                                    if (context.getSource().getEntity() instanceof ServerPlayer player) {
                                        int n = IntegerArgumentType.getInteger(context, "nivel");
                                        ModAttachments.setRank(player, n);
                                        player.sendSystemMessage(Component.literal("§a🎖️ Rango establecido a: §e" + n));
                                        return 1;
                                    }
                                    return 0;
                                })
                        )
        );

        // /rankgui - Abre la GUI de rangos (TEMPORAL)
        dispatcher.register(
                Commands.literal("rankgui")
                        .executes(context -> {
                            if (context.getSource().getEntity() instanceof ServerPlayer player) {
                                player.openMenu(new SimpleMenuProvider(
                                        (id, inv, p) -> new RankMenu(id, inv),
                                        Component.literal("Rangos")
                                ));
                                return 1;
                            }
                            return 0;
                        })
        );
    }
}