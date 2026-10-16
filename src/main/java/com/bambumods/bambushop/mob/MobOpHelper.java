package com.bambumods.bambushop.mob;

import com.bambumods.bambushop.attachment.ModAttachments;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Mob;

import java.util.Random;

public class MobOpHelper {

    private static final Random RANDOM = new Random();

    public enum MobOpType {
        NORMAL(0, "Normal", 1.0, 1.0, 1.0, 1.0, 0.0),
        EPICO(1, "Épico", 2.0, 1.2, 5.0, 3.0, 15.0),
        LEGENDARIO(2, "Legendario", 3.0, 1.5, 15.0, 6.0, 25.0),
        MITICO(3, "Mítico", 5.0, 2.0, 50.0, 12.0, 35.0);

        public final int id;
        public final String displayName;
        public final double hpMult;
        public final double damageMult;
        public final double moneyMult;
        public final double xpMult;
        public final double armorPiercing;

        MobOpType(int id, String displayName, double hpMult, double damageMult,
                  double moneyMult, double xpMult, double armorPiercing) {
            this.id = id;
            this.displayName = displayName;
            this.hpMult = hpMult;
            this.damageMult = damageMult;
            this.moneyMult = moneyMult;
            this.xpMult = xpMult;
            this.armorPiercing = armorPiercing;
        }

        public static MobOpType fromId(int id) {
            for (MobOpType t : values()) {
                if (t.id == id) return t;
            }
            return NORMAL;
        }
    }

    public static MobOpType rollMobOpType(ServerPlayer nearest) {
        int rank = (nearest != null) ? ModAttachments.getRank(nearest) : 0;

        if (rank >= 15 && RANDOM.nextInt(750) == 0) {
            return MobOpType.MITICO;
        }
        if (rank >= 10 && RANDOM.nextInt(250) == 0) {
            return MobOpType.LEGENDARIO;
        }
        if (rank >= 5 && RANDOM.nextInt(50) == 0) {
            return MobOpType.EPICO;
        }
        return MobOpType.NORMAL;
    }
}