package com.bambumods.bambushop.registry;

import com.bambumods.bambushop.BambuShop;
import com.bambumods.bambushop.menu.MainMenu;
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

    public static void register(IEventBus modBus) {
        MENUS.register(modBus);
    }
}