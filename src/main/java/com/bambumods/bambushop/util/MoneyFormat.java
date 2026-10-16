package com.bambumods.bambushop.util;

public class MoneyFormat {

    /**
     * Formato compacto: 999, 1k, 1.5k, 15k, 150k, 1M, 1.5M, 1B, 1T
     */
    public static String format(long amount) {
        if (amount < 1000) return String.valueOf(amount);
        if (amount < 1_000_000) return formatUnit(amount, 1_000, "k");
        if (amount < 1_000_000_000) return formatUnit(amount, 1_000_000, "M");
        if (amount < 1_000_000_000_000L) return formatUnit(amount, 1_000_000_000L, "B");
        return formatUnit(amount, 1_000_000_000_000L, "T");
    }

    private static String formatUnit(long amount, long divisor, String suffix) {
        double value = (double) amount / divisor;
        if (value == Math.floor(value)) return (long) value + suffix;
        if (value < 10) return String.format("%.1f%s", value, suffix);
        return (long) value + suffix;
    }
}