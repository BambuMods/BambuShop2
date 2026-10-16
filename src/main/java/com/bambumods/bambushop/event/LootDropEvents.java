package com.bambumods.bambushop.event;

import com.bambumods.bambushop.BambuShop;
import com.bambumods.bambushop.attachment.ModAttachments;
import com.bambumods.bambushop.mob.LootDropHelper;
import com.bambumods.bambushop.mob.PassiveLootHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

import java.util.List;

@EventBusSubscriber(modid = BambuShop.MOD_ID)
public class LootDropEvents {

    @SubscribeEvent
    public static void onMobDeath(LivingDeathEvent event) {
        if (event.getEntity().level().isClientSide()) return;
        if (!(event.getSource().getEntity() instanceof ServerPlayer player)) return;
        if (!(event.getEntity() instanceof Mob mob)) return;
        if (!(mob.level() instanceof ServerLevel serverLevel)) return;

        int level = ModAttachments.getMobLevel(mob);

        System.out.println("[BambuShop] Mob muerto: " + mob.getType() + " nivel " + level
                + " | Es Monster: " + (mob instanceof Monster)
                + " | Es Animal: " + (mob instanceof Animal));

        // Hostiles → drops chetados
        if (mob instanceof Monster) {
            ItemStack drop = LootDropHelper.generateLootDrop(serverLevel, level);
            System.out.println("[BambuShop] -> Drop chetado generado: " + (drop != null ? drop.getHoverName().getString() : "null"));
            if (drop != null) {
                giveToPlayerOrDrop(player, drop);
            }
        }
        // Pasivos → items de rankeo
        else if (mob instanceof Animal) {
            List<ItemStack> drops = PassiveLootHelper.generateRankItems(level);
            System.out.println("[BambuShop] -> Items de rankeo: " + drops.size());
            for (ItemStack drop : drops) {
                giveToPlayerOrDrop(player, drop);
            }
        }
    }

    private static void giveToPlayerOrDrop(ServerPlayer player, ItemStack stack) {
        if (!player.getInventory().add(stack)) {
            player.drop(stack, false);
        }
    }
}