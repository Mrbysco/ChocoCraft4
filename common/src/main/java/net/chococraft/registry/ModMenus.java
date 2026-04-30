package net.chococraft.registry;

import dev.chococraft.registration.RegistrationProvider;
import dev.chococraft.registration.RegistryObject;
import net.chococraft.Chococraft;
import net.chococraft.common.inventory.SaddleBagMenu;
import net.chococraft.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;

public class ModMenus {
	public static final RegistrationProvider<MenuType<?>> MENU_TYPES = RegistrationProvider.get(BuiltInRegistries.MENU, Chococraft.MOD_ID);

	public static final RegistryObject<MenuType<?>, MenuType<SaddleBagMenu>> CHOCOBO = MENU_TYPES.register(
			"chocobo_screen",
			Services.PLATFORM::createMenuType);


	public static void load() {
		// Load class
	}
}
