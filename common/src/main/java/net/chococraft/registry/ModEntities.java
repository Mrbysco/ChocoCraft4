package net.chococraft.registry;

import dev.chococraft.registration.RegistrationProvider;
import dev.chococraft.registration.RegistryObject;
import net.chococraft.Chococraft;
import net.chococraft.common.entity.AbstractChocobo;
import net.chococraft.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;

public class ModEntities {
	public static final RegistrationProvider<EntityType<?>> ENTITY_TYPES = RegistrationProvider.get(BuiltInRegistries.ENTITY_TYPE, Chococraft.MOD_ID);

	public static final RegistryObject<EntityType<?>, EntityType<? extends AbstractChocobo>> CHOCOBO = ENTITY_TYPES.register("chocobo", () ->
			Services.PLATFORM.constructChocoboEntityType().build(createEntityID("chocobo")));

	private static ResourceKey<EntityType<?>> createEntityID(String path) {
		return ResourceKey.create(Registries.ENTITY_TYPE, Chococraft.modLoc(path));
	}

	public static void load() {
		// Load class
	}
}
