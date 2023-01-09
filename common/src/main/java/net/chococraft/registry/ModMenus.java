package net.chococraft.registry;

import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.chococraft.Chococraft;
import net.chococraft.ChococraftExpectPlatform;
import net.chococraft.common.inventory.SaddleBagMenu;
import net.minecraft.core.Registry;
import net.minecraft.world.inventory.MenuType;

public class ModMenus {
	public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Chococraft.MOD_ID, Registry.MENU_REGISTRY);

	public static final RegistrySupplier<MenuType<SaddleBagMenu>> CHOCOBO = MENU_TYPES.register("chocobo_screen", () ->
			MenuRegistry.ofExtended(ChococraftExpectPlatform::constructMenu));
}
