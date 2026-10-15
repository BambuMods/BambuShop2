package com.bambumods.bambushop.registry;

import com.bambumods.bambushop.BambuShop;
import com.bambumods.bambushop.menu.MainMenu;
import com.bambumods.bambushop.menu.RankMenu;
import com.bambumods.bambushop.menu.SellMenu;
import com.bambumods.bambushop.menu.ShopCategoriesMenu;
import com.bambumods.bambushop.menu.ShopCategoryMenu;
import com.bambumods.bambushop.menu.ShopQuantityMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenus {

    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, BambuShop.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<MainMenu>> MAIN_MENU =
            MENUS.register("main_menu", () -> IMenuTypeExtension.create(MainMenu::new));

    public static final DeferredHolder<MenuType<?>, MenuType<RankMenu>> RANK_MENU =
            MENUS.register("rank_menu", () -> IMenuTypeExtension.create(RankMenu::new));

    public static final DeferredHolder<MenuType<?>, MenuType<ShopCategoriesMenu>> SHOP_CATEGORIES =
            MENUS.register("shop_categories", () -> IMenuTypeExtension.create(ShopCategoriesMenu::new));

    public static final DeferredHolder<MenuType<?>, MenuType<ShopCategoryMenu>> SHOP_CATEGORY =
            MENUS.register("shop_category", () -> IMenuTypeExtension.create(ShopCategoryMenu::new));

    public static final DeferredHolder<MenuType<?>, MenuType<ShopQuantityMenu>> SHOP_QUANTITY =
            MENUS.register("shop_quantity", () -> IMenuTypeExtension.create(ShopQuantityMenu::new));

    public static final DeferredHolder<MenuType<?>, MenuType<SellMenu>> SELL_MENU =
            MENUS.register("sell_menu", () -> IMenuTypeExtension.create(SellMenu::new));

    public static void register(IEventBus modBus) {
        MENUS.register(modBus);
    }
}