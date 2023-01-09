package net.chococraft.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.chococraft.Chococraft;
import net.chococraft.ChococraftExpectPlatform;
import net.chococraft.common.entity.AbstractChocobo;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;

public class ModEntities {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Chococraft.MOD_ID, Registry.ENTITY_TYPE_REGISTRY);

	public static final RegistrySupplier<EntityType<? extends AbstractChocobo>> CHOCOBO = ENTITY_TYPES.register("chocobo", () ->
			ChococraftExpectPlatform.constructChocoboEntityType().build("chocobo"));
}
