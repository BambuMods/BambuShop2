package com.bambumods.bambushop.mob;

import com.bambumods.bambushop.attachment.ModAttachments;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Mob;

import java.util.Random;

public class MobLevelHelper {

    private static final Random RANDOM = new Random();

    /**
     * Calcula el nivel del mob según el rango del jugador más cercano.
     * min = max(1, rango - 3)
     * max = min(20, rango + 5)
     */
    public static int calculateLevelForMob(Mob mob) {
        ServerPlayer nearest = findNearestPlayer(mob);
        int rank = (nearest != null) ? ModAttachments.getRank(nearest) : 0;

        int min = Math.max(1, rank - 3);
        int max = Math.min(20, rank + 5);

        // Si por lo que sea max < min, corregir
        if (max < min) max = min;

        return min + RANDOM.nextInt(max - min + 1);
    }

    /**
     * Busca el jugador más cercano al mob en un radio de 128 bloques.
     * Devuelve null si no hay ninguno.
     */
    private static ServerPlayer findNearestPlayer(Mob mob) {
        var player = mob.level().getNearestPlayer(mob, 128.0);
        return (player instanceof ServerPlayer sp) ? sp : null;
    }
}