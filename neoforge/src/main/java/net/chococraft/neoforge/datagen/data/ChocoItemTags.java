package net.chococraft.neoforge.datagen.data;

import net.chococraft.Chococraft;
import net.chococraft.registry.ModRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

public class ChocoItemTags extends ItemTagsProvider {

	public ChocoItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
	                     TagsProvider<Block> blockTagProvider) {
		super(output, lookupProvider, blockTagProvider.contentsGetter(), Chococraft.MOD_ID);
	}

	@Override
	protected void addTags(Provider provider) {
		this.tag(ItemTags.HEAD_ARMOR).add(ModRegistry.CHOCO_DISGUISE_HELMET.getKey());
		this.tag(ItemTags.CHEST_ARMOR).add(ModRegistry.CHOCO_DISGUISE_CHESTPLATE.getKey());
		this.tag(ItemTags.LEG_ARMOR).add(ModRegistry.CHOCO_DISGUISE_LEGGINGS.getKey());
		this.tag(ItemTags.FOOT_ARMOR).add(ModRegistry.CHOCO_DISGUISE_BOOTS.getKey());
	}
}