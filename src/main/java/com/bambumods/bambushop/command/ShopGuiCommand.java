package com.bambumods.bambushop.command;

import com.bambumods.bambushop.BambuShop;
import com.bambumods.bambushop.menu.ShopCategoriesMenu;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.minecraft.world.SimpleMenuProvider;

@EventBusSubscriber(modid = BambuShop.MOD_ID)
public class ShopGuiCommand {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("shopgui")
                .executes(ctx -> {
                    if (!(ctx.getSource().getEntity() instanceof ServerPlayer player)) return 0;

                    player.openMenu(new SimpleMenuProvider(
                            (id, inv, p) -> new ShopCategoriesMenu(id, inv, null),
                            Component.literal("Bambu Shop")
                    ));
                    return 1;
                }));
    }
}