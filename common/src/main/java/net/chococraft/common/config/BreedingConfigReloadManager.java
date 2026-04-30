package net.chococraft.common.config;

import net.chococraft.Chococraft;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

public class BreedingConfigReloadManager implements ResourceManagerReloadListener {
	public static final Identifier ID = Chococraft.modLoc("breeding_config");

	@Override
	public void onResourceManagerReload(ResourceManager resourceManager) {
		BreedingConfig.loadConfig();
	}
}