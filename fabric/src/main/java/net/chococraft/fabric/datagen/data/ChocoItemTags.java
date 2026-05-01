package net.chococraft.fabric.datagen.data;

import net.chococraft.Chococraft;
import net.chococraft.registry.ModRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.tags.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ChocoItemTags extends FabricTagsProvider.ItemTagsProvider {
    public ChocoItemTags(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture,
                         FabricTagsProvider.BlockTagsProvider blockTags) {
		super(output, completableFuture, blockTags);
	}

	@Override
	protected void addTags(Provider provider) {
		this.builder(Chococraft.REPAIRS_CHOCO_DISGUISE).add(ModRegistry.CHOCOBO_FEATHER.getResourceKey());
		this.builder(ItemTags.HEAD_ARMOR).add(ModRegistry.CHOCO_DISGUISE_HELMET.getResourceKey());
		this.builder(ItemTags.CHEST_ARMOR).add(ModRegistry.CHOCO_DISGUISE_CHESTPLATE.getResourceKey());
		this.builder(ItemTags.LEG_ARMOR).add(ModRegistry.CHOCO_DISGUISE_LEGGINGS.getResourceKey());
		this.builder(ItemTags.FOOT_ARMOR).add(ModRegistry.CHOCO_DISGUISE_BOOTS.getResourceKey());

		this.builder(ConventionalItemTags.SEEDS).add(ModRegistry.GYSAHL_GREEN_SEEDS.getResourceKey());

		// Food tags
		this.builder(ConventionalItemTags.COOKED_MEAT_FOODS).add(ModRegistry.CHOCOBO_DRUMSTICK_COOKED.getResourceKey());
		this.builder(ConventionalItemTags.RAW_MEAT_FOODS).add(ModRegistry.CHOCOBO_DRUMSTICK_RAW.getResourceKey());
		this.builder(ConventionalItemTags.VEGETABLE_FOODS).add(
				ModRegistry.GYSAHL_GREEN_ITEM.getResourceKey(),
				ModRegistry.PICKLED_GYSAHL_RAW.getResourceKey(),
				ModRegistry.PICKLED_GYSAHL_COOKED.getResourceKey()
		);
	}
}