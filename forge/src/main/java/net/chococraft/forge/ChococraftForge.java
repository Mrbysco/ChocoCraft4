package net.chococraft.forge;

import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.platform.forge.EventBuses;
import net.chococraft.Chococraft;
import net.chococraft.ChococraftClient;
import net.chococraft.forge.client.ForgeClientHandler;
import net.chococraft.forge.common.config.BreedingConfig;
import net.chococraft.forge.common.config.BreedingConfigReloadManager;
import net.chococraft.forge.common.config.ForgeChocoConfig;
import net.chococraft.forge.common.entity.ForgeChocobo;
import net.chococraft.forge.common.modifier.ModModifiers;
import net.chococraft.registry.ModEntities;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Chococraft.MOD_ID)
public class ChococraftForge {
	public ChococraftForge() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ForgeChocoConfig.commonSpec);
		eventBus.register(ForgeChocoConfig.class);

		// Submit our event bus to let architectury register our content on the right time
		EventBuses.registerModEventBus(Chococraft.MOD_ID, eventBus);

		ModModifiers.BIOME_MODIFIER_SERIALIZERS.register(eventBus);

		MinecraftForge.EVENT_BUS.register(new BreedingConfigReloadManager());

		eventBus.addListener(this::setup);
		eventBus.addListener(this::registerSpawnPlacements);

		Chococraft.init();

		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
			eventBus.addListener(ForgeClientHandler::registerEntityRenders);
			eventBus.addListener(ForgeClientHandler::registerLayerDefinitions);

			ClientLifecycleEvent.CLIENT_SETUP.register(e -> {
				ChococraftClient.init();
			});
		});
	}

	private void setup(final FMLCommonSetupEvent event) {
		BreedingConfig.initializeConfig();
	}

	private void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
		event.register(ModEntities.CHOCOBO.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ForgeChocobo::checkChocoboSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
	}
}
