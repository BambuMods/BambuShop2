package com.bambumods.bambushop.mob;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class MobStatsHelper {

    /**
     * Aplica HP y daño al mob según su nivel.
     * HP = 20 + (nivel × 10)
     * Daño = 2 + (nivel × 3)
     */
    public static void applyStatsToMob(Mob mob, int level) {
        // HP
        double hp = 20.0 + (level * 10.0);
        var healthAttr = mob.getAttribute(Attributes.MAX_HEALTH);
        if (healthAttr != null) {
            healthAttr.setBaseValue(hp);
            mob.setHealth((float) hp);
        }

        // Daño
        double damage = 2.0 + (level * 3.0);
        var damageAttr = mob.getAttribute(Attributes.ATTACK_DAMAGE);
        if (damageAttr != null) {
            damageAttr.setBaseValue(damage);
        }
    }
}