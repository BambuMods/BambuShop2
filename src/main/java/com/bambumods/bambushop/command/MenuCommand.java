package com.bambumods.bambushop.command;

import com.bambumods.bambushop.menu.MainMenu;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = "bambushop")
public class MenuCommand {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(
                Commands.literal("menu")
                        .executes(context -> {
                            if (context.getSource().getEntity() instanceof ServerPlayer player) {
                                player.openMenu(new SimpleMenuProvider(
                                        (id, inv, p) -> new MainMenu(id, inv),
                                        Component.literal("Bambu Shop")
                                ));
                                return 1;
                            }
                            context.getSource().sendFailure(Component.literal("Este comando solo lo pueden usar jugadores"));
                            return 0;
                        })
        );
    }
}