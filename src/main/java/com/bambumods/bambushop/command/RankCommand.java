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

        // /myrank - SOLO OPs
        dispatcher.register(
                Commands.literal("myrank")
                        .requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
                        .executes(context -> {
                            if (context.getSource().getEntity() instanceof ServerPlayer player) {
                                int rank = ModAttachments.getRank(player);
                                player.sendSystemMessage(Component.literal("§6🎖️ Tu rango actual: §e" + rank));
                                return 1;
                            }
                            return 0;
                        })
        );

        // /setrank <n> - SOLO OPs
        dispatcher.register(
                Commands.literal("setrank")
                        .requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
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

        // /rankgui - TODOS pueden usarlo
        dispatcher.register(
                Commands.literal("rankgui")
                        .executes(context -> {
                            if (context.getSource().getEntity() instanceof ServerPlayer player) {
                                player.openMenu(
                                        new SimpleMenuProvider(
                                                (id, inv, p) -> new RankMenu(id, inv, player),
                                                Component.literal("Rangos")
                                        ),
                                        buf -> {
                                            RankMenu menu = new RankMenu(0, player.getInventory(), player);
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
                                return 1;
                            }
                            return 0;
                        })
        );
    }
}