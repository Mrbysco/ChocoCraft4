package net.chococraft.neoforge;

import dev.architectury.event.events.client.ClientLifecycleEvent;
import net.chococraft.Chococraft;
import net.chococraft.ChococraftClient;
import net.chococraft.common.entity.AbstractChocobo;
import net.chococraft.neoforge.client.NeoForgeClientHandler;
import net.chococraft.neoforge.common.config.BreedingConfig;
import net.chococraft.neoforge.common.config.BreedingConfigReloadManager;
import net.chococraft.neoforge.common.config.NeoForgeChocoConfig;
import net.chococraft.neoforge.common.entity.NeoForgeChocobo;
import net.chococraft.neoforge.common.modifier.ModModifiers;
import net.chococraft.neoforge.registry.ModDataSerializers;
import net.chococraft.registry.ModEntities;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.SpawnPlacementRegisterEvent;

@Mod(Chococraft.MOD_ID)
public class ChococraftNeoForge {
	public ChococraftNeoForge(IEventBus eventBus) {
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, NeoForgeChocoConfig.commonSpec);
		eventBus.register(NeoForgeChocoConfig.class);

		ModModifiers.BIOME_MODIFIER_SERIALIZERS.register(eventBus);
		ModDataSerializers.ENTITY_DATA_SERIALIZER.register(eventBus);

		NeoForge.EVENT_BUS.register(new BreedingConfigReloadManager());

		eventBus.addListener(this::setup);
		eventBus.addListener(this::registerSpawnPlacements);
		eventBus.addListener(this::registerCapabilities);

		Chococraft.init();

		if (FMLEnvironment.dist.isClient()) {
			eventBus.addListener(NeoForgeClientHandler::registerEntityRenders);
			eventBus.addListener(NeoForgeClientHandler::registerLayerDefinitions);

			ClientLifecycleEvent.CLIENT_SETUP.register(e -> {
				ChococraftClient.init();
			});
		}
	}

	private void setup(final FMLCommonSetupEvent event) {
		BreedingConfig.initializeConfig();
	}

	private void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
		event.register(ModEntities.CHOCOBO.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractChocobo::checkChocoboSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
	}

	private void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.registerEntity(Capabilities.ItemHandler.ENTITY, ModEntities.CHOCOBO.get(), (entity, ctx) -> ((NeoForgeChocobo) entity).getInventory());
	}
}
