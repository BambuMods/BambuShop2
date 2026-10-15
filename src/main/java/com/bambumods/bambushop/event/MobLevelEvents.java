package com.bambumods.bambushop.event;

import com.bambumods.bambushop.BambuShop;
import com.bambumods.bambushop.attachment.ModAttachments;
import com.bambumods.bambushop.mob.MobNameHelper;
import com.bambumods.bambushop.mob.MobStatsHelper;
import net.minecraft.world.entity.Mob;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;

@EventBusSubscriber(modid = BambuShop.MOD_ID)
public class MobLevelEvents {

    @SubscribeEvent
    public static void onFinalizeSpawn(FinalizeSpawnEvent event) {
        if (!(event.getEntity() instanceof Mob mob)) return;
        if (mob.level().isClientSide()) return;

        // Asignar nivel
        int level = 1 + mob.getRandom().nextInt(20);
        ModAttachments.setMobLevel(mob, level);

        // Aplicar nombre con color
        MobNameHelper.applyNameToMob(mob, level, 0);

        // Aplicar stats (HP y daño)
        MobStatsHelper.applyStatsToMob(mob, level);
    }
}