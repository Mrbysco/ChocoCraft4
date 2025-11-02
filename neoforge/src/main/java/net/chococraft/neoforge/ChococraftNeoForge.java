package net.chococraft.neoforge;

import net.chococraft.Chococraft;
import net.chococraft.common.entity.AbstractChocobo;
import net.chococraft.neoforge.client.NeoForgeClientHandler;
import net.chococraft.neoforge.common.config.BreedingConfig;
import net.chococraft.neoforge.common.config.BreedingConfigReloadManager;
import net.chococraft.neoforge.common.config.NeoForgeChocoConfig;
import net.chococraft.neoforge.common.entity.NeoForgeChocobo;
import net.chococraft.neoforge.common.modifier.ModModifiers;
import net.chococraft.neoforge.registry.ModDataSerializers;
import net.chococraft.registry.ModEntities;
import net.chococraft.registry.ModRegistry;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

@Mod(Chococraft.MOD_ID)
public class ChococraftNeoForge {
	public ChococraftNeoForge(IEventBus eventBus, ModContainer container, Dist dist) {
		container.registerConfig(ModConfig.Type.COMMON, NeoForgeChocoConfig.commonSpec);
		eventBus.register(NeoForgeChocoConfig.class);

		ModModifiers.BIOME_MODIFIER_SERIALIZERS.register(eventBus);
		ModDataSerializers.ENTITY_DATA_SERIALIZER.register(eventBus);

		NeoForge.EVENT_BUS.register(new BreedingConfigReloadManager());

		eventBus.addListener(this::setup);
		eventBus.addListener(this::registerSpawnPlacements);
		eventBus.addListener(this::registerCapabilities);

		Chococraft.init();

		if (dist.isClient()) {
			container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
			eventBus.addListener(NeoForgeClientHandler::onClientSetup);
			eventBus.addListener(NeoForgeClientHandler::registerEntityRenders);
			eventBus.addListener(NeoForgeClientHandler::registerMenuScreen);
			eventBus.addListener(NeoForgeClientHandler::registerLayerDefinitions);
			eventBus.addListener(NeoForgeClientHandler::registerClientExtension);
		}
	}

	private void setup(final FMLCommonSetupEvent event) {
		ModRegistry.registerCompostables();
		BreedingConfig.initializeConfig();
	}

	private void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
		event.register(ModEntities.CHOCOBO.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractChocobo::checkChocoboSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
	}

	private void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.registerEntity(Capabilities.Item.ENTITY, ModEntities.CHOCOBO.get(), (entity, ctx) -> ((NeoForgeChocobo) entity).getInventory());
	}
}
