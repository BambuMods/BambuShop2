package com.bambumods.bambushop.rank;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class RankData {

    private final int level;
    private final String name;
    private final long moneyCost;
    private final int xpCost;
    private final long moneyMultiplier;
    private final List<RankRequirement> requirements;

    public RankData(int level, String name, long moneyCost, int xpCost, long moneyMultiplier) {
        this.level = level;
        this.name = name;
        this.moneyCost = moneyCost;
        this.xpCost = xpCost;
        this.moneyMultiplier = moneyMultiplier;
        this.requirements = new ArrayList<>();
    }

    public RankData addRequirement(RankRequirement req) {
        this.requirements.add(req);
        return this;
    }

    public int getLevel() { return level; }
    public String getName() { return name; }
    public long getMoneyCost() { return moneyCost; }
    public int getXpCost() { return xpCost; }
    public long getMoneyMultiplier() { return moneyMultiplier; }
    public List<RankRequirement> getRequirements() { return requirements; }

    // ============================================
    // DEFINICIÓN DE LOS RANGOS (0 = Sin Rango, 1-20 = Rangos)
    // ============================================
    public static final List<RankData> RANKS = new ArrayList<>();

    static {
        RANKS.add(new RankData(0, "Sin Rango", 0, 0, 1));

        RANKS.add(new RankData(1, "Novato", 500, 5, 1)
                .addRequirement(new RankRequirement(Items.COAL, 16))
                .addRequirement(new RankRequirement(Items.IRON_INGOT, 8))
        );

        RANKS.add(new RankData(2, "Aprendiz", 2500, 10, 2)
                .addRequirement(new RankRequirement(Items.IRON_INGOT, 32))
                .addRequirement(new RankRequirement(Items.GOLD_INGOT, 16))
                .addRequirement(new RankRequirement(Items.COPPER_INGOT, 8))
        );

        RANKS.add(new RankData(3, "Escudero", 8000, 15, 3)
                .addRequirement(new RankRequirement(Items.IRON_INGOT, 64))
                .addRequirement(new RankRequirement(Items.GOLD_INGOT, 32))
                .addRequirement(new RankRequirement(Items.REDSTONE, 16))
        );

        RANKS.add(new RankData(4, "Ballestero", 25000, 20, 5)
                .addRequirement(new RankRequirement(Items.DIAMOND, 32))
                .addRequirement(new RankRequirement(Items.LAPIS_LAZULI, 16))
                .addRequirement(new RankRequirement(Items.EMERALD, 8))
        );

        RANKS.add(new RankData(5, "Cazador de Sombras", 75000, 25, 7)
                .addRequirement(new RankRequirement(Items.DIAMOND, 32))
                .addRequirement(new RankRequirement(Items.IRON_INGOT, 64))
                .addRequirement(new RankRequirement(Items.PRISMARINE_SHARD, 16))
        );

        RANKS.add(new RankData(6, "Rastreador", 200000, 30, 9)
                .addRequirement(new RankRequirement(Items.DIAMOND, 64))
                .addRequirement(new RankRequirement(Items.EMERALD, 32))
                .addRequirement(new RankRequirement(Items.AMETHYST_SHARD, 16))
        );

        RANKS.add(new RankData(7, "Guerrero", 500000, 40, 12)
                .addRequirement(new RankRequirement(Items.IRON_BLOCK, 64))
                .addRequirement(new RankRequirement(Items.EMERALD, 32))
                .addRequirement(new RankRequirement(Items.NETHERITE_INGOT, 16))
        );

        RANKS.add(new RankData(8, "Veterano", 1250000, 50, 15)
                .addRequirement(new RankRequirement(Items.GOLD_BLOCK, 64))
                .addRequirement(new RankRequirement(Items.EMERALD, 64))
                .addRequirement(new RankRequirement(Items.NETHERITE_INGOT, 32))
        );

        RANKS.add(new RankData(9, "Élite", 3000000, 60, 20)
                .addRequirement(new RankRequirement(Items.DIAMOND_BLOCK, 128))
                .addRequirement(new RankRequirement(Items.NETHERITE_INGOT, 64))
                .addRequirement(new RankRequirement(Items.EMERALD, 64))
        );

        RANKS.add(new RankData(10, "Caballero", 6000000, 75, 25)
                .addRequirement(new RankRequirement(Items.NETHERITE_BLOCK, 128))
                .addRequirement(new RankRequirement(Items.NETHER_STAR, 16))
        );

        RANKS.add(new RankData(11, "Campeón", 12000000, 100, 32)
                .addRequirement(new RankRequirement(Items.DIAMOND_BLOCK, 192))
                .addRequirement(new RankRequirement(Items.NETHER_STAR, 32))
                .addRequirement(new RankRequirement(Items.WITHER_SKELETON_SKULL, 8))
        );

        RANKS.add(new RankData(12, "Héroe", 24000000, 125, 40)
                .addRequirement(new RankRequirement(Items.NETHERITE_BLOCK, 256))
                .addRequirement(new RankRequirement(Items.NETHER_STAR, 32))
                .addRequirement(new RankRequirement(Items.HEART_OF_THE_SEA, 16))
        );

        RANKS.add(new RankData(13, "Maestro", 45000000, 150, 50)
                .addRequirement(new RankRequirement(Items.DIAMOND_BLOCK, 320))
                .addRequirement(new RankRequirement(Items.NETHER_STAR, 48))
                .addRequirement(new RankRequirement(Items.DRAGON_HEAD, 2))
        );

        RANKS.add(new RankData(14, "Gran Maestro", 80000000, 175, 65)
                .addRequirement(new RankRequirement(Items.NETHERITE_BLOCK, 384))
                .addRequirement(new RankRequirement(Items.NETHER_STAR, 48))
                .addRequirement(new RankRequirement(Items.WITHER_SKELETON_SKULL, 32))
        );

        RANKS.add(new RankData(15, "Leyenda", 140000000, 200, 80)
                .addRequirement(new RankRequirement(Items.DIAMOND_BLOCK, 512))
                .addRequirement(new RankRequirement(Items.NETHER_STAR, 64))
                .addRequirement(new RankRequirement(Items.DRAGON_HEAD, 4))
        );

        RANKS.add(new RankData(16, "Mítico", 250000000, 250, 100)
                .addRequirement(new RankRequirement(Items.NETHERITE_BLOCK, 640))
                .addRequirement(new RankRequirement(Items.NETHER_STAR, 64))
                .addRequirement(new RankRequirement(Items.AMETHYST_SHARD, 32))
        );

        RANKS.add(new RankData(17, "Archimago", 400000000, 300, 130)
                .addRequirement(new RankRequirement(Items.DIAMOND_BLOCK, 768))
                .addRequirement(new RankRequirement(Items.NETHER_STAR, 96))
                .addRequirement(new RankRequirement(Items.WITHER_SKELETON_SKULL, 32))
        );

        RANKS.add(new RankData(18, "Señor de las Sombras", 700000000, 400, 170)
                .addRequirement(new RankRequirement(Items.NETHERITE_BLOCK, 1024))
                .addRequirement(new RankRequirement(Items.NETHER_STAR, 128))
                .addRequirement(new RankRequirement(Items.DRAGON_HEAD, 8))
        );

        RANKS.add(new RankData(19, "Semidiós", 1200000000, 500, 200)
                .addRequirement(new RankRequirement(Items.NETHERITE_BLOCK, 1536))
                .addRequirement(new RankRequirement(Items.NETHER_STAR, 192))
                .addRequirement(new RankRequirement(Items.WITHER_SKELETON_SKULL, 64))
        );

        RANKS.add(new RankData(20, "Dios", 2000000000L, 750, 250)
                .addRequirement(new RankRequirement(Items.NETHERITE_BLOCK, 2048))
                .addRequirement(new RankRequirement(Items.NETHER_STAR, 256))
                .addRequirement(new RankRequirement(Items.DRAGON_HEAD, 16))
                .addRequirement(new RankRequirement(Items.HEART_OF_THE_SEA, 64))
        );
    }

    public static RankData getByLevel(int level) {
        if (level < 0 || level >= RANKS.size()) return RANKS.get(0);
        return RANKS.get(level);
    }

    public static RankData getNext(int currentRank) {
        int next = currentRank + 1;
        if (next >= RANKS.size()) return null;
        return RANKS.get(next);
    }
}