package net.chococraft.fabric.datagen.data;

import net.chococraft.registry.ModRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.tags.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ChocoBlockTags extends FabricTagProvider.BlockTagProvider {
	public ChocoBlockTags(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
		super(output, completableFuture);
	}

	@Override
	protected void addTags(Provider provider) {
		this.builder(BlockTags.CROPS).add(ModRegistry.GYSAHL_GREEN.getKey());
	}
}