package net.chococraft.neoforge.datagen.data;

import net.chococraft.Chococraft;
import net.chococraft.registry.ModRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;

public class ChocoBlockTags extends BlockTagsProvider {
	public ChocoBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, lookupProvider, Chococraft.MOD_ID);
	}

	@Override
	protected void addTags(Provider provider) {
		this.tag(BlockTags.CROPS).add(ModRegistry.GYSAHL_GREEN.get());
	}
}