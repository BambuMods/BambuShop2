package com.bambumods.bambushop.mob;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class MobStatsHelper {

    /**
     * Aplica HP y daño al mob según su nivel y tipo de OP.
     * HP base = 20 + (nivel × 5)
     * Daño base = 2 + (nivel × 1.5)
     */
    public static void applyStatsToMob(Mob mob, int level, MobOpHelper.MobOpType opType) {
        // HP
        double hp = (20.0 + (level * 5.0)) * opType.hpMult;
        var healthAttr = mob.getAttribute(Attributes.MAX_HEALTH);
        if (healthAttr != null) {
            healthAttr.setBaseValue(hp);
            mob.setHealth((float) hp);
        }

        // Daño
        double damage = (2.0 + (level * 1.5)) * opType.damageMult;
        var damageAttr = mob.getAttribute(Attributes.ATTACK_DAMAGE);
        if (damageAttr != null) {
            damageAttr.setBaseValue(damage);
        }
    }
}