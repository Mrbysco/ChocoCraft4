package net.chococraft.fabric.datagen.data;

import net.chococraft.registry.ModRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.tags.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ChocoItemTags extends FabricTagProvider.ItemTagProvider {
    public ChocoItemTags(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture,
                         FabricTagProvider.BlockTagProvider blockTags) {
		super(output, completableFuture, blockTags);
	}

	@Override
	protected void addTags(Provider provider) {
		this.tag(ItemTags.HEAD_ARMOR).add(ModRegistry.CHOCO_DISGUISE_HELMET.getKey());
		this.tag(ItemTags.CHEST_ARMOR).add(ModRegistry.CHOCO_DISGUISE_CHESTPLATE.getKey());
		this.tag(ItemTags.LEG_ARMOR).add(ModRegistry.CHOCO_DISGUISE_LEGGINGS.getKey());
		this.tag(ItemTags.FOOT_ARMOR).add(ModRegistry.CHOCO_DISGUISE_BOOTS.getKey());

		// Food tags
		this.tag(ConventionalItemTags.COOKED_MEAT_FOODS).add(ModRegistry.CHOCOBO_DRUMSTICK_COOKED.getKey());
		this.tag(ConventionalItemTags.RAW_MEAT_FOODS).add(ModRegistry.CHOCOBO_DRUMSTICK_RAW.getKey());
		this.tag(ConventionalItemTags.VEGETABLE_FOODS).add(
				ModRegistry.GYSAHL_GREEN_ITEM.getKey(),
				ModRegistry.PICKLED_GYSAHL_RAW.getKey(),
				ModRegistry.PICKLED_GYSAHL_COOKED.getKey()
		);
	}
}