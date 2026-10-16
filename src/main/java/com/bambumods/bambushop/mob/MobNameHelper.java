package com.bambumods.bambushop.mob;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;

public class MobNameHelper {

    public static String getMobName(Mob mob, int level, int prestige, MobOpHelper.MobOpType opType) {
        String base = mob.getType().getDescription().getString();
        String opPart = (opType != MobOpHelper.MobOpType.NORMAL) ? " " + opType.displayName : "";
        String levelPart = " [Nv. " + level + "]";
        String prestigePart = (prestige > 0) ? " (P" + prestige + ")" : "";
        return base + opPart + levelPart + prestigePart;
    }

    public static int getColorForMob(int level, MobOpHelper.MobOpType opType) {
        // Colores especiales para mobs OP
        return switch (opType) {
            case EPICO -> 0xFF8800;        // naranja
            case LEGENDARIO -> 0xFF3333;   // rojo
            case MITICO -> 0xDD44FF;       // morado
            case NORMAL -> getColorForLevel(level);
        };
    }

    public static int getColorForLevel(int level) {
        if (level <= 5) return 0xFFFFFF;
        if (level <= 10) return 0x55FF55;
        if (level <= 15) return 0xFFD75E;
        return 0xFF5555;
    }

    public static void applyNameToMob(Mob mob, int level, int prestige, MobOpHelper.MobOpType opType) {
        String name = getMobName(mob, level, prestige, opType);
        int color = getColorForMob(level, opType);

        mob.setCustomName(Component.literal(name).withStyle(Style.EMPTY.withColor(color)));
        mob.setCustomNameVisible(true);
    }
}