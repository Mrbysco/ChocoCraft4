package net.chococraft.neoforge.common.config;

import net.chococraft.Chococraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.AddClientReloadListenersEvent;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;

public class BreedingConfigReloadManager implements ResourceManagerReloadListener {
	private static final ResourceLocation ID = Chococraft.modLoc("breeding_config");

	@Override
	public void onResourceManagerReload(ResourceManager resourceManager) {
		BreedingConfig.loadConfig();
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onAddReloadListeners(AddServerReloadListenersEvent event) {
		event.addListener(ID, this);
	}
}