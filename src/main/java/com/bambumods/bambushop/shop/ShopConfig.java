package com.bambumods.bambushop.shop;

import com.bambumods.bambushop.BambuShop;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Lee y escribe el JSON de la tienda.
 * Ubicación: run/config/bambushop/shop.json
 */
public class ShopConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String FOLDER = "bambushop";
    private static final String FILE = "shop.json";

    public static class ConfigData {
        public Map<ShopCategory, Map<String, Long>> buy = new HashMap<>();
        public Map<ShopCategory, Map<String, Long>> sell = new HashMap<>();
    }

    /** Devuelve el Path del JSON. */
    public static Path getPath(Path configDir) {
        return configDir.resolve(FOLDER).resolve(FILE);
    }

    /** Carga el JSON. Si no existe, lo crea con valores por defecto. */
    public static ConfigData loadOrCreate(Path configDir) {
        Path path = getPath(configDir);

        try {
            if (!Files.exists(path)) {
                BambuShop.LOGGER.info("[Shop] No existe {}, creando por defecto...", path);
                Files.createDirectories(path.getParent());
                ConfigData defaultData = DefaultShopData.create();
                save(path, defaultData);
                return defaultData;
            }

            String content = Files.readString(path);
            JsonObject root = JsonParser.parseString(content).getAsJsonObject();
            return parse(root);

        } catch (IOException e) {
            BambuShop.LOGGER.error("[Shop] Error leyendo {}: {}", path, e.getMessage());
            return DefaultShopData.create();
        }
    }

    private static ConfigData parse(JsonObject root) {
        ConfigData data = new ConfigData();

        if (root.has("buy")) {
            parseSide(root.getAsJsonObject("buy"), data.buy);
        }
        if (root.has("sell")) {
            parseSide(root.getAsJsonObject("sell"), data.sell);
        }

        return data;
    }

    private static void parseSide(JsonObject side, Map<ShopCategory, Map<String, Long>> target) {
        for (Map.Entry<String, JsonElement> catEntry : side.entrySet()) {
            ShopCategory cat = ShopCategory.fromId(catEntry.getKey());
            if (cat == null) {
                BambuShop.LOGGER.warn("[Shop] Categoría desconocida: {}", catEntry.getKey());
                continue;
            }

            Map<String, Long> items = new HashMap<>();
            JsonObject itemObj = catEntry.getValue().getAsJsonObject();
            for (Map.Entry<String, JsonElement> itemEntry : itemObj.entrySet()) {
                items.put(itemEntry.getKey(), itemEntry.getValue().getAsLong());
            }
            target.put(cat, items);
        }
    }

    private static void save(Path path, ConfigData data) throws IOException {
        JsonObject root = new JsonObject();

        JsonObject buy = new JsonObject();
        for (Map.Entry<ShopCategory, Map<String, Long>> catEntry : data.buy.entrySet()) {
            JsonObject items = new JsonObject();
            for (Map.Entry<String, Long> itemEntry : catEntry.getValue().entrySet()) {
                items.addProperty(itemEntry.getKey(), itemEntry.getValue());
            }
            buy.add(catEntry.getKey().getId(), items);
        }
        root.add("buy", buy);

        JsonObject sell = new JsonObject();
        for (Map.Entry<ShopCategory, Map<String, Long>> catEntry : data.sell.entrySet()) {
            JsonObject items = new JsonObject();
            for (Map.Entry<String, Long> itemEntry : catEntry.getValue().entrySet()) {
                items.addProperty(itemEntry.getKey(), itemEntry.getValue());
            }
            sell.add(catEntry.getKey().getId(), items);
        }
        root.add("sell", sell);

        Files.writeString(path, GSON.toJson(root));
    }
}