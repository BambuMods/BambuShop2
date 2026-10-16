package com.bambumods.bambushop.event;

import com.bambumods.bambushop.BambuShop;
import com.bambumods.bambushop.attachment.ModAttachments;
import com.bambumods.bambushop.mob.LootDropHelper;
import com.bambumods.bambushop.mob.MobOpHelper;
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
        int opTypeId = ModAttachments.getMobOpType(mob);
        MobOpHelper.MobOpType opType = MobOpHelper.MobOpType.fromId(opTypeId);

        // Hostiles
        if (mob instanceof Monster) {
            if (opType == MobOpHelper.MobOpType.NORMAL) {
                ItemStack drop = LootDropHelper.generateLootDrop(serverLevel, level);
                if (drop != null) {
                    giveToPlayerOrDrop(player, drop);
                }
            } else {
                int minEnch, maxEnch;
                switch (opType) {
                    case EPICO -> { minEnch = 21; maxEnch = 23; }
                    case LEGENDARIO -> { minEnch = 24; maxEnch = 26; }
                    case MITICO -> { minEnch = 27; maxEnch = 30; }
                    default -> { minEnch = 1; maxEnch = 5; }
                }
                ItemStack drop1 = LootDropHelper.generateOpLootDrop(serverLevel, level, minEnch, maxEnch);
                ItemStack drop2 = LootDropHelper.generateOpLootDrop(serverLevel, level, minEnch, maxEnch);
                if (drop1 != null) giveToPlayerOrDrop(player, drop1);
                if (drop2 != null) giveToPlayerOrDrop(player, drop2);
            }
        }
        // Pasivos → items de rankeo
        else if (mob instanceof Animal) {
            List<ItemStack> drops = PassiveLootHelper.generateRankItems(level);
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