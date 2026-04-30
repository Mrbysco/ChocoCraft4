package net.chococraft.neoforge.registry;

import net.chococraft.common.entity.AbstractChocobo;
import net.chococraft.neoforge.common.entity.NeoForgeChocobo;
import net.chococraft.registry.ModEntities;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

@EventBusSubscriber
public class EntitySetup {


	@SubscribeEvent
	public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
		event.register(ModEntities.CHOCOBO.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractChocobo::checkChocoboSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
	}

	@SubscribeEvent
	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.registerEntity(Capabilities.Item.ENTITY, ModEntities.CHOCOBO.get(), (entity, ctx) -> ((NeoForgeChocobo) entity).getInventory());
	}

	@SubscribeEvent
	public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
		event.put(ModEntities.CHOCOBO.get(), AbstractChocobo.createAttributes().build());
	}
}
