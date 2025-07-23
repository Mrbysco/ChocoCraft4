package net.chococraft.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.chococraft.Chococraft;
import net.chococraft.common.entity.AbstractChocobo;
import net.chococraft.platform.Services;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;

public class ModEntities {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Chococraft.MOD_ID, Registries.ENTITY_TYPE);

	public static final RegistrySupplier<EntityType<? extends AbstractChocobo>> CHOCOBO = ENTITY_TYPES.register("chocobo", () ->
			Services.PLATFORM.constructChocoboEntityType().build(createEntityID("chocobo")));

	private static ResourceKey<EntityType<?>> createEntityID(String path) {
		return ResourceKey.create(Registries.ENTITY_TYPE, Chococraft.modLoc(path));
	}
}
