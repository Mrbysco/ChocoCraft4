package net.chococraft.neoforge.datagen.data;

import net.chococraft.Chococraft;
import net.chococraft.registry.ModRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ItemTagsProvider;

import java.util.concurrent.CompletableFuture;

public class ChocoItemTags extends ItemTagsProvider {

	public ChocoItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, lookupProvider, Chococraft.MOD_ID);
	}

	@Override
	protected void addTags(Provider provider) {
		this.tag(ItemTags.HEAD_ARMOR).add(ModRegistry.CHOCO_DISGUISE_HELMET.get());
		this.tag(ItemTags.CHEST_ARMOR).add(ModRegistry.CHOCO_DISGUISE_CHESTPLATE.get());
		this.tag(ItemTags.LEG_ARMOR).add(ModRegistry.CHOCO_DISGUISE_LEGGINGS.get());
		this.tag(ItemTags.FOOT_ARMOR).add(ModRegistry.CHOCO_DISGUISE_BOOTS.get());

		this.tag(Tags.Items.SEEDS).add(ModRegistry.GYSAHL_GREEN_SEEDS.get());

		// Food tags
		this.tag(Tags.Items.FOODS_COOKED_MEAT).add(ModRegistry.CHOCOBO_DRUMSTICK_COOKED.get());
		this.tag(Tags.Items.FOODS_RAW_MEAT).add(ModRegistry.CHOCOBO_DRUMSTICK_RAW.get());
		this.tag(Tags.Items.FOODS_VEGETABLE).add(
				ModRegistry.GYSAHL_GREEN_ITEM.get(),
				ModRegistry.PICKLED_GYSAHL_RAW.get(),
				ModRegistry.PICKLED_GYSAHL_COOKED.get()
		);
	}
}