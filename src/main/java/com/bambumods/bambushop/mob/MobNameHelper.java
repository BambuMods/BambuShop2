package com.bambumods.bambushop.mob;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;

public class MobNameHelper {

    /**
     * Genera el nombre del mob: "<nombre> [Nv. X] (PY)"
     */
    public static String getMobName(Mob mob, int level, int prestige) {
        String base = mob.getType().getDescription().getString();
        String levelPart = " [Nv. " + level + "]";
        String prestigePart = (prestige > 0) ? " (P" + prestige + ")" : "";
        return base + levelPart + prestigePart;
    }

    /**
     * Devuelve el color según el nivel del mob.
     */
    public static int getColorForLevel(int level) {
        if (level <= 5) return 0xFFFFFF;      // blanco
        if (level <= 10) return 0x55FF55;     // verde
        if (level <= 15) return 0xFFD75E;     // dorado
        return 0xFF5555;                       // rojo
    }

    /**
     * Aplica el nombre y el color al mob.
     */
    public static void applyNameToMob(Mob mob, int level, int prestige) {
        // No sobrescribir si tiene name tag
        if (mob.hasCustomName() && mob.getCustomName() != null
                && !mob.getCustomName().getString().startsWith(mob.getType().getDescription().getString())) {
            return;
        }

        String name = getMobName(mob, level, prestige);
        int color = getColorForLevel(level);

        mob.setCustomName(Component.literal(name).withStyle(Style.EMPTY.withColor(color)));
        mob.setCustomNameVisible(true);
    }
}