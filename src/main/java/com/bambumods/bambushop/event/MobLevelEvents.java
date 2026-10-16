package com.bambumods.bambushop.event;

import com.bambumods.bambushop.BambuShop;
import com.bambumods.bambushop.attachment.ModAttachments;
import com.bambumods.bambushop.mob.MobLevelHelper;
import com.bambumods.bambushop.mob.MobNameHelper;
import com.bambumods.bambushop.mob.MobOpHelper;
import com.bambumods.bambushop.mob.MobStatsHelper;
import net.minecraft.server.level.ServerPlayer;
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

        // Calcular nivel
        int level = MobLevelHelper.calculateLevelForMob(mob);
        ModAttachments.setMobLevel(mob, level);

        // Tirar dados de OP
        ServerPlayer nearest = (ServerPlayer) mob.level().getNearestPlayer(mob, 128.0);
        MobOpHelper.MobOpType opType = MobOpHelper.rollMobOpType(nearest);
        ModAttachments.setMobOpType(mob, opType.id);

        // Aplicar nombre con color
        MobNameHelper.applyNameToMob(mob, level, 0, opType);

        // Aplicar stats (HP y daño)
        MobStatsHelper.applyStatsToMob(mob, level, opType);
    }
}