package com.bambumods.bambushop.client;

public class ClientPlayerDataCache {

    private static long money = 0;
    private static int rank = 0;
    private static boolean autoSell = false;

    public static void update(long money, int rank, boolean autoSell) {
        ClientPlayerDataCache.money = money;
        ClientPlayerDataCache.rank = rank;
        ClientPlayerDataCache.autoSell = autoSell;
    }

    public static long getMoney() { return money; }
    public static int getRank() { return rank; }
    public static boolean isAutoSell() { return autoSell; }
}