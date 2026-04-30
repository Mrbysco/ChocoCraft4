package net.chococraft.neoforge;

import net.chococraft.Chococraft;
import net.chococraft.common.config.BreedingConfig;
import net.chococraft.common.config.BreedingConfigReloadManager;
import net.chococraft.common.config.ChocoConfig;
import net.chococraft.neoforge.common.modifier.ModModifiers;
import net.chococraft.neoforge.registry.ModDataSerializers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;

@Mod(Chococraft.MOD_ID)
public class ChococraftNeoForge {
	public ChococraftNeoForge(IEventBus eventBus, ModContainer container, Dist dist) {
		container.registerConfig(ModConfig.Type.COMMON, ChocoConfig.commonSpec);

		ModModifiers.BIOME_MODIFIER_SERIALIZERS.register(eventBus);
		ModDataSerializers.ENTITY_DATA_SERIALIZER.register(eventBus);

		NeoForge.EVENT_BUS.addListener(this::onAddReloadListeners);
		eventBus.addListener(this::setup);

		Chococraft.init();

		if (dist.isClient()) {
			container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
		}
	}

	private void setup(final FMLCommonSetupEvent event) {
		Chococraft.registerCompostables();
		BreedingConfig.initializeConfig();
	}

	private void onAddReloadListeners(AddServerReloadListenersEvent event) {
		event.addListener(BreedingConfigReloadManager.ID, new BreedingConfigReloadManager());
	}
}
